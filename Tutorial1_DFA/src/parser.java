import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;




public class parser {
	
	private scanner sc;
	private TOKEN currentToken;
	private ArrayList<String> states;
	private ArrayList<String> alphabet;
	private ArrayList<String> acceptStates;
	private String startState;
	private HashMap<String, String> aRb;
	
	public static void main (String [] args) {
		parser s= new parser();
		try {
			s.setup("test1");
			s.parse();
		} catch (FileNotFoundException | IllegalDFAFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void setup(String dfa_file) throws FileNotFoundException {
		sc = new scanner();
		aRb = new HashMap<String, String>();
		sc.setup(dfa_file);
		currentToken = sc.next();
		
	}
	
	public void parse() throws IllegalDFAFormatException 
	{
		automaton();
	}
	
	
	private void automaton() throws IllegalDFAFormatException {
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
	
	private void states() throws IllegalDFAFormatException {
		states = idset();
	}
	
	private void alphabet() throws IllegalDFAFormatException {
		alphabet = new ArrayList<String>();
		expectToken(TOKEN.Types.OPEN_BRACE);
		alphabet.add(expectSymbol());
		while (TOKEN.Types.CLOSED_BRACE != currentToken.getType()) {
			expectToken(TOKEN.Types.COMMA);
			alphabet.add(expectSymbol());
		}
		expectToken(TOKEN.Types.CLOSED_BRACE);
	}
	
	private void tfunction() throws IllegalDFAFormatException {
		expectToken(TOKEN.Types.OPEN_BRACE);
		String [] mapping = map();
		aRb.put(mapping[0] + mapping[1], mapping[2]);
		while (TOKEN.Types.CLOSED_BRACE != currentToken.getType()) {
			expectToken(TOKEN.Types.COMMA);
			mapping = map();
			System.out.println((mapping[0] + mapping[1] +" :"+ mapping[2]));
			aRb.put(mapping[0] + mapping[1], mapping[2]);
			
		}
		expectToken(TOKEN.Types.CLOSED_BRACE);
	}
	
	private void start() throws IllegalDFAFormatException {
		startState = expectID();
	}
	
	private void accept() throws IllegalDFAFormatException {
		acceptStates = idset();
	}
	
	private ArrayList<String> idset() throws IllegalDFAFormatException {
		ArrayList<String> set = new ArrayList<String>();
		expectToken(TOKEN.Types.OPEN_BRACE);
		set.add(expectID());
		while (TOKEN.Types.CLOSED_BRACE != currentToken.getType()) {
			expectToken(TOKEN.Types.COMMA);
			set.add(expectID());
		}
		expectToken(TOKEN.Types.CLOSED_BRACE);
		return set;
	}
	
	private String[] map() throws IllegalDFAFormatException {
		String [] tMap = new String[3];
		expectToken(TOKEN.Types.OPEN_PARENTHESIS);
		tMap[0] = expectID();
		expectToken(TOKEN.Types.COMMA);
		tMap[1] = expectSymbol();
		expectToken(TOKEN.Types.CLOSED_PARENTHESIS);
		expectToken(TOKEN.Types.ARROW);
		tMap[2] = expectID();
		return tMap;
	}
	
	
	
	private void expectToken(TOKEN.Types type) throws IllegalDFAFormatException {
		
		if(type != currentToken.getType()) {
			System.out.println("Expected: " + type + " But found: " +  currentToken.getType());
			throw new IllegalDFAFormatException();
		}
		currentToken = sc.next();
	}
	
	private String expectID() throws IllegalDFAFormatException {
		String id;
		if(TOKEN.Types.ID != currentToken.getType()) {
			System.out.println("Expected: " + TOKEN.Types.SYMBOL + " But found: " +  currentToken.getType());
			throw new IllegalDFAFormatException();
		}
		id = currentToken.getLexeme();
		currentToken = sc.next();
		return id;
	}
	
	private String expectSymbol() throws IllegalDFAFormatException {
		String symbol;
		if(TOKEN.Types.SYMBOL != currentToken.getType()) {
			System.out.println("Expected: " + TOKEN.Types.SYMBOL + " But found: " +  currentToken.getType());
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
