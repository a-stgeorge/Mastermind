/**
 * Game.java - The board class for the Mastermind game. Handles all the work for the model.
 * @author Aidan St. George
 * @version 1.3 - 12/19/19
 */

package application;

import java.util.Random;

public class Game {

	/** Array that holds the characters representing the colors of the buttons. 
	 * Used to check if patterns are valid and to translate the characters to numbers
	 */
	public static final char[] COLORS = {'r', 'g', 'b', 'y', 'o', 'p'};
	
	/** value of the maximum guesses before player loses. */
	private final int MAX_GUESSES = 10;
	
	/** Array that houses the correct code for the game */
	private char[] correctPattern;
	
	/** Array of Guess Objects that houses the previous guesses that the player made */
	private Guess[] guesses;
	
	/** Value that represents the number of guesses that the player has made */
	private int currentGuessNumber = 0;
	
	/**
	 * Constructor for the game, where the secret code is given
	 * @param newPattern The new secret code
	 * @throws Exception throws Exception when the new code is invalid
	 */
	public Game(char[] newPattern) throws Exception {
		setCorrectPattern(newPattern);
		guesses = new Guess[MAX_GUESSES];
	}
	
	/**
	 * Default constructor for the game class. Creates a random code to set as the correct pattern
	 */
	public Game() {		
		Random random = new Random();
		char[] newPattern = new char[4];
		
		for (int i = 0; i < 4; i++) {
			newPattern[i] = COLORS[ Math.abs(random.nextInt() % 6) ];
		}
		
		try {
			setCorrectPattern(newPattern);
		} catch (Exception e) {
			e.printStackTrace();
		}
		guesses = new Guess[MAX_GUESSES];
	}
	
	/**
	 * Setter for the correct pattern. Calls patternIsValid to make sure that it is a correct set of characters.
	 * @param newPattern The code to set as the correct pattern
	 * @throws Exception thrown when the pattern is not valid
	 */
	public void setCorrectPattern(char[] newPattern) throws Exception {
		if (patternIsValid(newPattern))		
			correctPattern = newPattern;
	}
	
	/**
	 * Getter for the correct pattern.
	 * @return A Guess instance with the correct pattern
	 */
	public Guess getCorrectPattern() {
		return new Guess(correctPattern);
	}
	
	/**
	 * getter for the array of Guesses
	 * @return the array of Guesses
	 */
	public Guess[] getGuesses() {
		return guesses;
	}
	
	/**
	 * getter for the current guess number
	 * @return the current guess number
	 */
	public int getCurrentGuessNumber() {
		return currentGuessNumber;
	}
	
	/**
	 * the getter for the most recent guess
	 * @return Guess instance for the most recent guess
	 */
	public Guess getCurrentGuess() {
		return guesses[currentGuessNumber - 1];
	}
	
	/**
	 * Method that checks and stores a pattern as a guess 
	 * @param guess the guess pattern that needs to be checked and stored
	 * @return true if the guess is correct, false otherwise
	 * @throws Exception if the guess is an invalid guess
	 */
	public boolean checkGuess(char[] guess) throws Exception {		
		if (!patternIsValid(guess)) {
			throw new Exception("Invalid Pattern!");
		}
		
		int[] clues = new int[4]; // 2 - black, 1 - white
		
		// Count number of white and black feedback pins.
		for (int i = 0; i < 4; i++) {
			if (guess[i] == correctPattern[i]) {
				clues[i] = 2;
			} else {
				for (int j = 0; j < 4; j++) {
					if (i != j && clues[j] == 0 && guess[j] == correctPattern[i]) {
						clues[j] = 1;
						j = 4;
					}
				}
			}
		}
		
		int black = 0;
		int white = 0;
		for (int i = 0; i < 4; i++) {
			if (clues[i] == 1)
				white++;
			else if(clues[i] == 2)
				black++;
		}
		
		guesses[currentGuessNumber] = new Guess(guess, black, white);
		currentGuessNumber++;
		
		return black == 4;
	}
		
	/**
	 * Helper method to check if a given pattern is a valid pattern consisting of exactly 4 characters in Game.COLORS
	 * @param pattern the pattern to be checked
	 * @return true if the pattern is valid
	 * @throws Exception if the pattern is invalid
	 */
	private boolean patternIsValid(char[] pattern) throws Exception {
		if (pattern == null) {
			throw new Exception("Pattern is null!");
		}
		if (pattern.length != 4) {
			throw new Exception("Correct Pattern Length must be exactly 4!");
		} else {
			
			boolean valid = true;
			for (int i = 0; i < 4; i++) {
				if (!colorIsValid(pattern[i])) {
					valid = false;
				}
			}
			
			if (!valid) {
				throw new Exception("Pattern must contain only 'r', 'g', 'b', 'y', 'o', or 'p'!");		
			}
			return valid;
		}
	}
	
	/** 
	 * Helper method that checks if a given color in the form of a character is in Game.COLORS
	 * @param c the color/character that is to be checked
	 * @return true if the color is in Game.COLORS, false otherwise
	 */
	private boolean colorIsValid(char c) {
		
		for (int i = 0; i < COLORS.length; i++) {
			if (c == COLORS[i]) {
				return true;
			}
		}
		
		return false;
	}
	
}
