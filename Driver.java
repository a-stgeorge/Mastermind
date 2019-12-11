package finalProjectConsole;

import java.util.Scanner;

public class Driver {
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Welcome to Mastermind!");
		boolean inGame = true;
		
		do {
			// Number of Players
			int players = 0;
			do {
				System.out.print("Enter number of players (1-2): ");
				try {
					players = Integer.parseInt(scanner.nextLine());
				} catch (NumberFormatException e) {
					System.out.println("Must be an integer!");
				}
			} while (players != 1 && players != 2);
			
			// Set Pattern
			Board game = null;
			if (players == 1) {
				game = new Board();
			} else {
				do {
					try {
						char[] pattern = getUserPattern(scanner);
						game = new Board(pattern);
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
				} while (game == null);
			}
			
			int guessNumber = 0; // Number of guesses that the player is on, if 10 they lose
			boolean win = false;
			
			// Guessing
			while (!win && guessNumber < 10) {				
				guessNumber++;
				System.out.print("Guess #" + guessNumber + ", ");
				
				boolean validInput = false;
				do {
					try {
						win = game.checkGuess(getUserPattern(scanner));
						validInput = true;
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
				} while (!validInput);
				
				Guess guess = game.getCurrentGuess();
				System.out.println("\t\t\t\tBlack: " + guess.getBlack() + ", White: " + guess.getWhite());
			}
			
			System.out.println("Win: " + win + ", Score: " + (10 - guessNumber) + "\n");
			
			// Check for restart
			int action = -1;
			do {
				System.out.print("Enter 0 to quit, 1 to restart: ");
				try {
					action = Integer.parseInt(scanner.nextLine());
				} catch (NumberFormatException e) {
					System.out.println("Must be an integer!");
				}
			} while (action != 0 && action != 1);
			
			if (action == 0) {
				inGame = false;
			}
			
		} while (inGame);
		
		scanner.close();
		
	}
	
	public static char[] getUserPattern(Scanner scanner) {
		boolean validPattern = false;
		char[] pattern;
		
		do {
			System.out.print("Enter a pattern: ");
			String input = scanner.nextLine();

			pattern = new char[4];
			int colorNumber = 0;
			
			for (int i = 0; i < input.length(); i++) {
				if (input.charAt(i) != ' ') {
					try {
						pattern[colorNumber] = Character.toLowerCase(input.charAt(i));
					} catch (Exception e) {
						colorNumber++; // So validPattern is not set true
						break;
					}
					colorNumber++;
				}
			}
			
			if (colorNumber == 4) {
				validPattern = true;
			} else {
				System.out.println("Pattern must be four colors long!");
			}
		} while (!validPattern);
		
		return pattern;
		
	}
	
}
