import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;




public class Parser {
	
	private Scanner sc;
	private Token currentToken;
	private ArrayList<String> states;
	private ArrayList<String> alphabet;
	private ArrayList<String> acceptStates;
	private String startState;
	private HashMap<String, String> aRb;
	
	public static void main (String [] args) {
		Parser s= new Parser();
		try {
				s.setup("test1");
				s.parse();
		} catch (IllegalDFAFormatException | IOException | IllegalTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void setup(String dfa_file) throws FileNotFoundException, IOException, IllegalTokenException {
		sc = new Scanner();
		aRb = new HashMap<String, String>();
		sc.setup(dfa_file);
		currentToken = sc.next();
		
	}
	
	public void parse() throws IllegalDFAFormatException, IOException, IllegalTokenException
	{
		automaton();
	}
	
	
	private void automaton() throws IllegalDFAFormatException, IOException, IllegalTokenException {
		expectToken(Token.Types.DFA);
		expectToken(Token.Types.EQUALS);
		expectToken(Token.Types.OPEN_PARENTHESIS);
		states();
		expectToken(Token.Types.COMMA);
		alphabet();
		expectToken(Token.Types.COMMA);
		tfunction();
		expectToken(Token.Types.COMMA);
		start();
		expectToken(Token.Types.COMMA);
		accept();
		expectToken(Token.Types.CLOSED_PARENTHESIS);
	}
	
	private void states() throws IllegalDFAFormatException, IOException, IllegalTokenException {
		states = idset();
	}
	
	private void alphabet() throws IllegalDFAFormatException, IOException, IllegalTokenException {
		alphabet = new ArrayList<String>();
		expectToken(Token.Types.OPEN_BRACE);
		alphabet.add(expectSymbol());
		while (Token.Types.CLOSED_BRACE != currentToken.getType()) {
			expectToken(Token.Types.COMMA);
			alphabet.add(expectSymbol());
		}
		expectToken(Token.Types.CLOSED_BRACE);
	}
	
	private void tfunction() throws IllegalDFAFormatException, IOException, IllegalTokenException {
		expectToken(Token.Types.OPEN_BRACE);
		String [] mapping = map();
	
		if (!isValidState(mapping[0]) || !isValidAlphabet( mapping[1]) || !isValidState(mapping[2])) {
			throw new IllegalDFAFormatException();
		}
		aRb.put(mapping[0] + mapping[1], mapping[2]);
		while (Token.Types.CLOSED_BRACE != currentToken.getType()) {
			expectToken(Token.Types.COMMA);
			mapping = map();
			if (!isValidState(mapping[0]) || !isValidAlphabet( mapping[1]) || !isValidState(mapping[2])) {
				throw new IllegalDFAFormatException();
			}
			aRb.put(mapping[0] + mapping[1], mapping[2]);
			
		}
		expectToken(Token.Types.CLOSED_BRACE);
	}
	
	private void start() throws IllegalDFAFormatException, IOException, IllegalTokenException {
		startState = expectID();
		if (!isValidState(startState)) {
			throw new IllegalDFAFormatException();
		}
	}
	
	private void accept() throws IllegalDFAFormatException, IOException, IllegalTokenException {
		acceptStates = idset();
		for (String i : acceptStates) {
			if (!isValidState(i)) {
				throw new IllegalDFAFormatException();
			}
		}
	}
	
	private ArrayList<String> idset() throws IllegalDFAFormatException, IOException, IllegalTokenException {
		ArrayList<String> set = new ArrayList<String>();
		expectToken(Token.Types.OPEN_BRACE);
		set.add(expectID());
		while (Token.Types.CLOSED_BRACE != currentToken.getType()) {
			expectToken(Token.Types.COMMA);
			set.add(expectID());
		}
		expectToken(Token.Types.CLOSED_BRACE);
		return set;
	}
	
	private String[] map() throws IllegalDFAFormatException, IOException, IllegalTokenException {
		String [] tMap = new String[3];
		expectToken(Token.Types.OPEN_PARENTHESIS);
		tMap[0] = expectID();
		expectToken(Token.Types.COMMA);
		tMap[1] = expectSymbol();
		expectToken(Token.Types.CLOSED_PARENTHESIS);
		expectToken(Token.Types.ARROW);
		tMap[2] = expectID();
		return tMap;
	}
	
	
	
	private void expectToken(Token.Types type) throws IllegalDFAFormatException, IOException, IllegalTokenException {
		
		if(type != currentToken.getType()) {
			System.out.println("Expected: " + type + " But found: " +  currentToken.getType());
			throw new IllegalDFAFormatException();
		}
		currentToken = sc.next();
	}
	
	private String expectID() throws IllegalDFAFormatException, IOException, IllegalTokenException {
		String id;
		if(Token.Types.ID != currentToken.getType()) {
			System.out.println("Expected: " + Token.Types.SYMBOL + " But found: " +  currentToken.getType());
			throw new IllegalDFAFormatException();
		}
		id = currentToken.getLexeme();
		currentToken = sc.next();
		return id;
	}
	
	private String expectSymbol() throws IllegalDFAFormatException, IOException, IllegalTokenException {
		String symbol;
		if(Token.Types.SYMBOL != currentToken.getType()) {
			System.out.println("Expected: " + Token.Types.SYMBOL + " But found: " +  currentToken.getType());
			throw new IllegalDFAFormatException();
		}
		if (currentToken.getLexeme().equals("")) {
			symbol = Integer.toString(currentToken.getNumber());
		} else {
			symbol = currentToken.getLexeme();
		}
		
		currentToken = sc.next();
		return symbol;
	}
	
	public boolean isValidState(String st) {
		for (String i : states) {
			if (i.equals(st)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isValidAlphabet(String alp) {
		for (String i : alphabet) {
			if (i.equals(alp)) {
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<String> getStates() {
		return states;
	}
	public ArrayList<String> getAlphabet() {
		return alphabet;
	}
	public ArrayList<String> getAcceptStates() {
		return acceptStates;
	}
	public String getStartState() {
		return startState;
	}
	public HashMap<String, String> getaRb() {
		return aRb;
	}

}

class IllegalDFAFormatException extends Exception
{
	public String toString() 
	{
		return "IllegalDFAFormatException";
	}
}
