/**
 * @author Shaun Schreiber
 * The purpose of this class is to tokenize the given format and to identify illegal characters. 
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class Scanner {
	
	private FileInputStream dfa_file;
	private char currentChar;
	
	public static void main(String[] args) 
	{
		Scanner d = new Scanner();
		try {
			d.setup("test1");
			Token D = null;
			try {
				D = d.next();
			} catch (IOException | IllegalTokenException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			while (D.getType() != Token.Types.EOF) {
				if(D.getLexeme().equals("")) {
					System.out.println(D.toStringType());
				} else {
					System.out.println(D.getLexeme());
				}
				try {
					D = d.next();
				} catch (IOException | IllegalTokenException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Opens the connection to the file where the DFA EBNF description is stored.
	 * @param dfa_file The file name.
	 * @throws FileNotFoundException
	 */
	public void setup(String dfa_file) throws FileNotFoundException 
	{
		this.dfa_file = new FileInputStream(new File(dfa_file));
		getChar();
	}
	
	/**
	 * Reads the current character and then build up a valid token and assigns the correct token type. 
	 * @return The next valid token.
	 * @throws IOException 
	 */
	public Token next() throws IOException, IllegalTokenException 
	{	
		Token next_token = null;
		consumeWhiteSpace();
		if (currentChar == 0) {
			
		} else if (Character.isAlphabetic(currentChar)) {
			next_token = readWord();
		} else if (Character.isDigit(currentChar)) {
			next_token = readNumber();
		} else {
			switch (currentChar){
			case '(':
				next_token = new Token(Token.Types.OPEN_PARENTHESIS, 0, "");
				getChar();
				break;
			case ')':
				next_token = new Token(Token.Types.CLOSED_PARENTHESIS, 0, "");
				getChar();
				break;
			case ',':
				next_token = new Token(Token.Types.COMMA, 0, "");
				getChar();
				break;
			case '{':
				next_token = new Token(Token.Types.OPEN_BRACE, 0, "");
				getChar();
				break;
			case '}':
				next_token = new Token(Token.Types.CLOSED_BRACE, 0, "");
				getChar();
				break;
			case '-':
				getChar();
				if (currentChar == '>') {
					next_token = new Token(Token.Types.ARROW, 0, "");
					getChar();
				} else {
					//Unrecognized token
				}
				break;
			case '=':
				next_token = new Token(Token.Types.EQUALS, 0, "");
				getChar();
				break;
			default:	
				if (dfa_file.available() == 0) {
					next_token = new Token(Token.Types.EOF, 0, "");
				} else {
					throw new IllegalTokenException(""+currentChar);
				}
				break;
			}
		}
		return next_token;	
	}
	
	/**
	 * Builds up a DFA, SYMBOL or ID token
	 * @return DFA, SYMBOL or ID token.
	 */
	public Token readWord() 
	{
		StringBuilder word = new StringBuilder();
		Token token;
		
		while (Character.isAlphabetic(currentChar) || Character.isDigit(currentChar)) {
			word.append(currentChar);
			getChar();
		}

		if (word.length() == 1) {
			token = new Token(Token.Types.SYMBOL, 0, word.toString());
		} else if(word.toString().equals("DFA")) {
			token = new Token(Token.Types.DFA, 0, word.toString());
		} else {
			token = new Token(Token.Types.ID, 0, word.toString());
		}
		return token;
	}
	
	/**
	 * Builds up a SYMBOL that has an integer value.
	 * @return SYMBOL
	 */
	public Token readNumber()
	{
		char oldchar = currentChar;
		getChar();
		return new Token(Token.Types.SYMBOL, new Integer(oldchar)-48 , "");
	}
	
	/**
	 * Consumes spaces, tabs and newlines.
	 */
	private void consumeWhiteSpace() 
	{
		while (currentChar == ' ' || currentChar  == '\t' || currentChar == '\n') {
			getChar();
		}
	}
	
	/**
	 * Returns the next character. Note it ignores newline characters.
	 */
	private void getChar()
	{
		try {
			currentChar = (char)dfa_file.read();
			while (currentChar == '\n') {
				currentChar = (char)dfa_file.read();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

class IllegalTokenException extends Exception
{
	String invalid ;
	
	public IllegalTokenException(String invalidToken) {
		invalid = invalidToken;
	}
	
	public String toString() 
	{
		return "IllegalTokenException: " + invalid;
	}
}
