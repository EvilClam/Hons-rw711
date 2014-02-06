import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;


public class scanner {
	
	private FileInputStream dfa_file;
	private char currentChar;
	
	public static void main(String[] args) 
	{
		scanner d = new scanner();
		try {
			d.setup("test1");
			TOKEN D = d.next();
			while (D.getType() != TOKEN.Types.EOF) {
				if(D.getLexeme().equals("")) {
					System.out.println(D.toStringType());
				} else {
					System.out.println(D.getLexeme());
				}
				D = d.next();
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
	 * 
	 * @return
	 */
	public TOKEN next() 
	{	
		TOKEN next_token = null;
		consumeWhiteSpace();
		if (currentChar == 0) {
			
		} else if (Character.isAlphabetic(currentChar)) {
			next_token = readWord();
		} else if (Character.isDigit(currentChar)) {
			next_token = readNumber();
		} else {
			switch (currentChar){
			case '(':
				next_token = new TOKEN(TOKEN.Types.OPEN_PARENTHESIS, 0, "");
				getChar();
				break;
			case ')':
				next_token = new TOKEN(TOKEN.Types.CLOSED_PARENTHESIS, 0, "");
				getChar();
				break;
			case ',':
				next_token = new TOKEN(TOKEN.Types.COMMA, 0, "");
				getChar();
				break;
			case '{':
				next_token = new TOKEN(TOKEN.Types.OPEN_BRACE, 0, "");
				getChar();
				break;
			case '}':
				next_token = new TOKEN(TOKEN.Types.CLOSED_BRACE, 0, "");
				getChar();
				break;
			case '-':
				getChar();
				if (currentChar == '>') {
					next_token = new TOKEN(TOKEN.Types.ARROW, 0, "");
					getChar();
				} else {
					//Unrecognized token
				}
				break;
			case '=':
				next_token = new TOKEN(TOKEN.Types.EQUALS, 0, "");
				getChar();
				break;
			default:
				next_token = new TOKEN(TOKEN.Types.EOF, 0, "");
				break;
			}
		}
		return next_token;	
	}
	
	public TOKEN readWord() {
		StringBuilder word = new StringBuilder();
		TOKEN token;
		
		while (Character.isAlphabetic(currentChar) || Character.isDigit(currentChar)) {
			word.append(currentChar);
			getChar();
		}

		if (word.length() == 1) {
			token = new TOKEN(TOKEN.Types.SYMBOL, 0, word.toString());
		} else if(word.toString().equals("DFA")) {
			token = new TOKEN(TOKEN.Types.DFA, 0, word.toString());
		} else {
			token = new TOKEN(TOKEN.Types.ID, 0, word.toString());
		}
		return token;
	}
	
	public TOKEN readNumber(){
		char oldchar = currentChar;
		getChar();
		return new TOKEN(TOKEN.Types.SYMBOL, new Integer(oldchar)-48 , "");
	}
	
	
	private void consumeWhiteSpace() {
		while (currentChar == ' ' || currentChar  == '\t' || currentChar == '\n') {
			getChar();
		}
	}
	
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
