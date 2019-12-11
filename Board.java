package finalProjectConsole;

import java.util.Random;

public class Board {

	private final char[] COLORS = {'r', 'g', 'b', 'y', 'o', 'p'};
	private final boolean COLOR_LOCK = true;
	private final int MAX_GUESSES = 10;
	
	private char[] correctPattern;
	private Guess[] guesses;
	
	private int currentGuessNumber = 0;
	
	public Board(char[] newPattern) throws Exception {
		setCorrectPattern(newPattern);
		guesses = new Guess[MAX_GUESSES];
	}
	
	public Board() {		
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
	
	public void setCorrectPattern(char[] newPattern) throws Exception {
		if (patternIsValid(newPattern))		
			correctPattern = newPattern;
	}
	
	public char[] getCorrectPattern() {
		return correctPattern;
	}
	
	public Guess[] getGuesses() {
		return guesses;
	}
	
	public int getCurrentGuessNumber() {
		return currentGuessNumber;
	}
	
	public Guess getCurrentGuess() {
		return guesses[currentGuessNumber - 1];
	}
	
	public boolean checkGuess(char[] guess) throws Exception {		
		if (!patternIsValid(guess)) {
			throw new Exception("Invalid Pattern!");
		}
		
		int[] clues = new int[4]; // 2 - black, 1 - white
		
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
	
	public boolean colorIsValid(char c) {
		if (!COLOR_LOCK) {
			return true;
		}
		
		for (int i = 0; i < COLORS.length; i++) {
			if (c == COLORS[i]) {
				return true;
			}
		}
		
		return false;
	}
	
}
