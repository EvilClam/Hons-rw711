import java.io.FileNotFoundException;
import java.util.ArrayList;


public class parser {
	scanner sc = new scanner();
	TOKEN currentToken;
	ArrayList<String> states;
	public static void main (String [] args) {
		parser s= new parser();
		try {
			s.setup("test1");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void setup(String dfa_file) throws FileNotFoundException {
		sc.setup(dfa_file);
		automaton();
	}
	
	public void automaton() {
		expectToken(TOKEN.Types.DFA);
		expectToken(TOKEN.Types.EQUALS);
		expectToken(TOKEN.Types.OPEN_PARENTHESIS);
		states();
		expectToken(TOKEN.Types.COMMA);
		alphabet();
		expectToken(TOKEN.Types.COMMA);
		tfunction();
		expectToken(TOKEN.Types.COMMA);
		start();
		expectToken(TOKEN.Types.COMMA);
		accept();
		expectToken(TOKEN.Types.CLOSED_PARENTHESIS);
	}
	
	public void states() {
		states = idset();
	}
	
	public void alphabet() {
		expectToken(TOKEN.Types.OPEN_BRACE);
		expectToken(TOKEN.Types.SYMBOL);
		while (TOKEN.Types.CLOSED_BRACE != currentToken.getType()) {
			expectToken(TOKEN.Types.COMMA);
			expectToken(TOKEN.Types.SYMBOL);
		}
		expectToken(TOKEN.Types.CLOSED_BRACE);
	}
	
	public void tfunction() {
		expectToken(TOKEN.Types.OPEN_BRACE);
		expectToken(TOKEN.Types.SYMBOL);
		while (TOKEN.Types.CLOSED_BRACE != currentToken.getType()) {
			expectToken(TOKEN.Types.COMMA);
			expectToken(TOKEN.Types.SYMBOL);
		}
		expectToken(TOKEN.Types.CLOSED_BRACE);
	}
	
	public void start() {
		
	}
	
	public void accept() {
		
	}
	
	public ArrayList<String> idset() {
		return null;
	}
	
	public String[] map() {
		return new String[3];
	}
	
	
	
	public boolean expectToken(TOKEN.Types type) {
		return type == sc.next().getType();
	}
}

class IllegalDFAFormatException extends Exception
{
	public String toString() 
	{
		return "IllegalDFAFormatException";
	}
}
