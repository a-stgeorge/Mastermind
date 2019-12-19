/**
 * Guess.java - Helper class for the mastermind game. Stores a pattern and the feedback (white and black) for that pattern
 * @author Aidan St. George
 * @version 1.1 - 12/19/19
 */

package application;

public class Guess {
	
	/** The pattern that Guess stores */
	private char[] pattern;
	
	/** Value that stores the number of black feedback pins that this pattern receives */
	private int black;

	/** Value that stores the number of white feedback pins that this pattern receives */
	private int white;
	
	/**
	 * Constructor for a Guess that has feedback values
	 * @param newPattern the pattern that is to be stored
	 * @param newBlack the number of black feedback pins that this pattern receives
	 * @param newWhite the number of white feedback pins that this pattern receives
	 */
	public Guess(char[] newPattern, int newBlack, int newWhite) {
		pattern = newPattern;
		black = newBlack;
		white = newWhite;
	}
	
	/**
	 * Constructor for a Guess that doesn't have feedback values
	 * @param newPattern the pattern that is to be stored.
	 */
	public Guess(char[] newPattern) {
		pattern = newPattern;
	}
	
	/**
	 * Getter for the stored pattern 
	 * @return the stored pattern
	 */
	public char[] getPattern() {
		return pattern;
	}
	
	/**
	 * Getter for the number of black feedback pins
	 * @return the number of black feedback pins
	 */
	public int getBlack() {
		return black;
	}
	
	/**
	 * Getter for the number of white feedback pins
	 * @return the number of white feedback pins
	 */
	public int getWhite() {
		return white;
	}

	/**
	 * Method that returns the pattern as a string of characters
	 * @return String the pattern as a string of characters
	 */
	@Override
	public String toString() {
		String stringGuess = "";
				
		for (int i = 0; i < pattern.length - 1; i++) {
			stringGuess += pattern[i] + ", ";
		}
		
		stringGuess += pattern[pattern.length - 1];
		
		return stringGuess;	
	}
	
}
