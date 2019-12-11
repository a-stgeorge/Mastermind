package finalProjectConsole;

public class Guess {
	
	private char[] pattern;
	private int black;
	private int white;
	
	public Guess(char[] newPattern, int newBlack, int newWhite) {
		pattern = newPattern;
		black = newBlack;
		white = newWhite;
	}
	
	public char[] getPattern() {
		return pattern;
	}
	
	public int getBlack() {
		return black;
	}
	
	public int getWhite() {
		return white;
	}

	@Override
	public String toString() {
		String stringGuess = "";
				
		for (int i = 0; i < pattern.length - 1; i++) {
			stringGuess += pattern[i] + ", ";
		}
		
		stringGuess += pattern[pattern.length - 1] + "; black: " + black + ", white: " + white;
		
		return stringGuess;	
	}
	
}
