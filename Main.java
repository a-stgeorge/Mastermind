/**
 * Main.java The main driver class for the Mastermind GUI game. Sets up the GUI and handles the user input
 * @author Aidan St. George
 * @version 1.2 - 12/19/19
 */

package application;
	
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Main extends Application implements EventHandler<ActionEvent> {
	
	/** Array of pins on the left side of the window that show the previously entered codes */
	private ArrayList<Circle> pins;
	
	/** Array of pins on the left side of the window that show the feedback for the previously entered codes */ 
	private ArrayList<Circle> feedbackPins;
	
	/** Array of buttons that is the sandbox for the user to put a code together to guess */
	private ArrayList<Button> guessPatternButtons;
	
	/** Array of buttons that represents the colors that the user can select to use in a guess */
	private ArrayList<Button> guessColorButtons;
	
	/** Button that is pressed when the user wants to submit the code as a guess. Doubles a button
	 *  to submit a code to be the correct code in two player mode.
	 */
	private Button guessButton;
	
	/** Button that starts a new one player game */
	private Button newGameButton;
	
	/** Button that starts a two player game and switches the function of the guess button and changes the label
	 *  above the code guessing area so a player can pick a code.
	 */
	private Button twoPlayerButton;
	
	/** Label that is above the code guessing area. Changes in two player mode to tell user to pick correct code. */
	private Label patternLabel;
	
	
	/** Array of colors that are used in codes. In the same order as the array in Board */
	private Color[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.ORANGE, Color.PURPLE};
	
	/** Array that holds the current value of the colors in the guessing pattern buttons. Easier to check than the actual color of the button 
	 *  A -1 means that that pin has not been guessed, -2 means that that pin is being changed, and 0-5 represent the guessed colors.
	 */
	private int[] guessedColors = {-1, -1, -1, -1};
	
	/** Value that holds the current pin that is being guessed */
	private int selectedPin = -1;
	
	/** Boolean value that represents whether the code is being set (twoPlayerMode = true) or being guessed */
	private boolean twoPlayerMode;
	
	/** The instance of the game */
	private Game game;
	
	/**
	 * The start method for this JavaFX program. Sets up all of the GUI for the window
	 * @param primaryStage
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			// Initialize Variables
			
			GridPane root = new GridPane();
			pins = new ArrayList<Circle>();
			feedbackPins = new ArrayList<Circle>();
			guessPatternButtons = new ArrayList<Button>();
			guessColorButtons = new ArrayList<Button>();
			GridPane boardArea = new GridPane();
			GridPane guessArea = new GridPane();
			patternLabel = new Label("Guess Code Here:");
			
			game = new Game();
			twoPlayerMode = false;
			
			// Root Set Up
			
		    root.add(new Label("Previous Guesses and Feedback:"), 0, 0);
		    root.add(patternLabel, 1, 0);
		    root.getRowConstraints().add(new RowConstraints(30));
			root.add(boardArea, 0, 1);
			root.add(guessArea, 1, 1);
		    root.getColumnConstraints().add(new ColumnConstraints(200)); // column 0 is 200 wide
		    
		    // Previous Guess Display Area
		    
		    for (int i = 0; i < 4; i++)
		    	boardArea.getColumnConstraints().add(new ColumnConstraints(40));
		    GridPane currentFeedbackPinGrid = null; // To add feedback pins to
		    for (int i = 0; i < 40; i++) {
		    	pins.add(new Circle(15));
		    	pins.get(i).setFill(Color.DARKGRAY);
		    	boardArea.add(pins.get(i), i % 4, i / 4);
		    	if (i % 4 == 0) {
		    		boardArea.getRowConstraints().add(new RowConstraints(35));
		    		currentFeedbackPinGrid = new GridPane();
		    		for (int j = 0; j < 2; j++) {
		    			currentFeedbackPinGrid.getColumnConstraints().add(new ColumnConstraints(15));
		    			currentFeedbackPinGrid.getRowConstraints().add(new RowConstraints(15));
		    		}
		    		boardArea.add(currentFeedbackPinGrid, 5, i /4);
		    	}
		    	
		    	feedbackPins.add(new Circle(7));
		    	feedbackPins.get(i).setFill(Color.DARKGRAY);
		    	currentFeedbackPinGrid.add(feedbackPins.get(i), (i % 4) % 2, (i % 4) / 2);
		    	
		    }
		    
		    // Temporary Pattern Buttons
		    
		    GridPane patternButtons = new GridPane();
		    guessArea.add(patternButtons, 0, 0);
		    for (int i = 0; i < 4; i++) {
		    	guessPatternButtons.add(new Button());
		    	guessPatternButtons.get(i).setPrefSize(40, 40);
				guessPatternButtons.get(i).setBackground(new Background(
						new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
				patternButtons.getColumnConstraints().add(new ColumnConstraints(50));
				patternButtons.add(guessPatternButtons.get(i), i, 1);
				guessPatternButtons.get(i).setOnAction(this);
		    }
		    guessArea.getRowConstraints().add(new RowConstraints(40));
		    
		    // Color Options Buttons
		    
		    guessArea.add(new Label("Colors:"), 0, 1);
		    guessArea.getRowConstraints().add(new RowConstraints(30));
		    GridPane colorButtons = new GridPane();
		    guessArea.add(colorButtons, 0, 2);
		    for (int i = 0; i < 6; i++) {
		    	guessColorButtons.add(new Button());
		    	guessColorButtons.get(i).setPrefSize(30, 30);
		    	guessColorButtons.get(i).setBackground(new Background(
		    			new BackgroundFill(colors[i], CornerRadii.EMPTY, Insets.EMPTY)));
				colorButtons.getColumnConstraints().add(new ColumnConstraints(34));
		    	colorButtons.add(guessColorButtons.get(i), i, 3);
		    	guessColorButtons.get(i).setOnAction(this);
		    }
		    
		    // Action Buttons
		    
		    GridPane buttonArea = new GridPane();
		    guessArea.add(buttonArea, 0, 3);
		    buttonArea.add(new Label("Actions:     "), 0, 1);
		    
		    guessButton = new Button("   Guess!    ");
		    guessButton.setOnAction(this);
		    buttonArea.add(guessButton, 1, 0);	
		    buttonArea.getRowConstraints().add(new RowConstraints(40));
		    
		    newGameButton = new Button("New Game");
		    newGameButton.setOnAction(this);
		    buttonArea.add(newGameButton, 1, 1);
		    buttonArea.getRowConstraints().add(new RowConstraints(20));
		    
		    twoPlayerButton = new Button("Two Player");
		    twoPlayerButton.setOnAction(this);
		    buttonArea.add(twoPlayerButton, 1, 2);
		    buttonArea.getRowConstraints().add(new RowConstraints(40));
		    
		    // Instructions
		    guessArea.add(new Label("Instructions:                          "), 0, 4);
		    guessArea.add(new Label("For two player, the first code is the"), 0, 5);
		    guessArea.add(new Label("correct code. For one player, the first"), 0, 6);
		    guessArea.add(new Label("code is a guess. For the feedback, a"), 0, 7);
		    guessArea.add(new Label("black dot means one color was the"), 0, 9);
		    guessArea.add(new Label("right color in the right spot, and a"), 0, 10);
		    guessArea.add(new Label("white dot means that one color was the"), 0, 11);
		    guessArea.add(new Label("right color in the wrong spot. The dots"), 0, 12);
		    guessArea.add(new Label("are  in no specific order."), 0, 13);

		    // Window Set Up
		    
			Scene scene = new Scene(root, 400, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Mastermind - Aidan StG");
			primaryStage.setResizable(false); // Keep grid sizing constant
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * The handle method for Main as an EventHandler. Handles all user input and passes it to game.
	 */
	@Override
	public void handle(ActionEvent event) {
		
		try {
			
			// Button Events
			
			if (event.getSource() == newGameButton) { // New Game Button Actions
				if (twoPlayerMode) { // Take game off two player mode
					twoPlayerMode = false;
					patternLabel.setText("Guess Code Here:");
					guessButton.setText("   Guess!    ");
				}
				resetGame();
				game = new Game();
			} else if (!twoPlayerMode && event.getSource() == guessButton) { // Guess Button Actions
				if (game == null) {
					throw new Exception("No current game! You must press new game before starting.");
				}
				
				boolean win = game.checkGuess(getPattern());
				int black = game.getCurrentGuess().getBlack();
				int white = game.getCurrentGuess().getWhite();
				for (int i = 0; i < 4; i++) {
					int gamePinIndex  = (game.getCurrentGuessNumber() - 1) * 4 + i; // Index of game pin to change colors
					pins.get(gamePinIndex).setFill(colors[guessedColors[i]]);
					if (black > 0) {
						feedbackPins.get(gamePinIndex).setFill(Color.BLACK);
						black--;
					} else if (white > 0) {
						feedbackPins.get(gamePinIndex).setFill(Color.WHITE);
						white--;
					}
				}
				if (win) {
					Alert message = new Alert(Alert.AlertType.INFORMATION);
					message.setTitle("You Win!");
					message.setContentText("You guessed the correct pattern! Your score is " + (11 - game.getCurrentGuessNumber()) + ".");
					message.showAndWait();
					game = null;
				} else if (game.getCurrentGuessNumber() > 9) {
					Alert message = new Alert(Alert.AlertType.INFORMATION);
					message.setTitle("You Lose.");
					message.setContentText("You lose. You used up all of your guesses! The correct pattern was " + game.getCorrectPattern() + ".");
					message.showAndWait();
					game = null;
				}
				
			} else if (twoPlayerMode && event.getSource() == guessButton) {
				game = new Game(getPattern());
				twoPlayerMode = false;
				patternLabel.setText("Guess Code Here:");
				guessButton.setText("   Guess!    ");
				for (int i = 0; i < 4; i++) {
					guessPatternButtons.get(i).setBackground(new Background(
							new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
				}
			} else if (event.getSource() == twoPlayerButton) {
				patternLabel.setText("Enter Secret Code Here:");
				guessButton.setText(" Set Code! ");
				resetGame();
				game = null;
				twoPlayerMode = true;
			}
			
			// Guessing Pattern and Color Button Events
			
			else {
				// Guessing Pattern
				for (int i = 0; i < 4; i++) { 
					if (event.getSource() == guessPatternButtons.get(i)) {
						if (selectedPin != -1 && guessedColors[selectedPin] == -2) {
					    	guessPatternButtons.get(selectedPin).setBackground(new Background(
					    			new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
					    	guessedColors[selectedPin] = -1;
						}
						selectedPin = i;
				    	guessPatternButtons.get(selectedPin).setBackground(new Background(
				    			new BackgroundFill(Color.DIMGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
				    	guessedColors[i] = -2;
						break;
					}
				}
				
				// Color Pattern
				for (int i = 0; i < 6; i++) {
					if (event.getSource() == guessColorButtons.get(i)) {
						if (selectedPin != -1) {
							guessPatternButtons.get(selectedPin).setBackground(new Background(
									new BackgroundFill(colors[i], CornerRadii.EMPTY, Insets.EMPTY)));
							guessedColors[selectedPin] = i;
						}
					}
				}
			}
			
		} catch (Exception e) {
			Alert message = new Alert(Alert.AlertType.ERROR);
			message.setTitle("User Error");
			message.setContentText(e.getMessage());
			message.showAndWait();
		}
		
	}
	
	/**
	 * Helper method to reset all of the pins and buttons for a new game
	 */
	private void resetGame() {
		game = null;
		selectedPin = -1;
		
		for (int i = 0; i < 40; i++) {
			if (i < 4) {
				guessPatternButtons.get(i).setBackground(new Background(
						new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
				guessedColors[i] = -1;
			}
			
			pins.get(i).setFill(Color.DARKGREY);
			feedbackPins.get(i).setFill(Color.DARKGREY);
		}
	}
	
	/**
	 * Helper method to get the current pattern in the guessing buttons
	 * @return an array of length 4 with characters representing the current color set on the guessing buttons
	 * @throws Exception when the pattern is an invalid pattern.
	 */
	private char[] getPattern() throws Exception {
		char[] pattern = new char[4];
		
		for (int i = 0; i < 4; i++) {
			if (guessedColors[i] < 0) {
				throw new Exception("Guessing pins must not be left blank!");
			} else {
				pattern[i] = Game.COLORS[guessedColors[i]];
			}
		}
		
		return pattern;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
