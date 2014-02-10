import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @file Parser.java
 * @author EvilClam
 * @date 09/2/2014
 * A syntax analyser for the DFA EBNF.
 */
public class Parser {
	
	private Scanner sc;
	private Token currentToken;
	private ArrayList<String> states;
	private ArrayList<String> alphabet;
	private ArrayList<String> acceptStates;
	private String startState;
	private HashMap<String, String> aRb;
	
	public static void main (String [] args) 
	{
		Parser s= new Parser();
		try {
				s.setup("test1");
				s.parse();
		} catch (IllegalDFAFormatException | IOException | IllegalTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Initializes and setups the Scanner and sets the first Token.  
	 * @param dfa_file The file name of the file that constains the DFA.
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws IllegalTokenException
	 */
	public void setup(String dfa_file) throws FileNotFoundException, IOException, IllegalTokenException 
	{
		sc = new Scanner();
		aRb = new HashMap<String, String>();
		sc.setup(dfa_file);
		currentToken = sc.next();
		
	}
	
	/**
	 * Parses the syntax of the EBNF file.
	 * @throws IllegalDFAFormatException
	 * @throws IOException
	 * @throws IllegalTokenException
	 */
	public void parse() throws IllegalDFAFormatException, IOException, IllegalTokenException
	{
		automaton();
	}
	
	/**
	 * automaton = "DFA" "=" "(" states "," alphabet "," tfunction "," start "," accept ")"
	 */
	private void automaton() throws IllegalDFAFormatException, IOException, IllegalTokenException 
	{
		expectToken(Token.Types.DFA);
		expectToken(Token.Types.EQUALS);
		expectToken(Token.Types.OPEN_PARENTHESIS);
		states();
		expectToken(Token.Types.COMMA);
		alphabet();
		expectToken(Token.Types.COMMA);
		tFunction();
		expectToken(Token.Types.COMMA);
		start();
		expectToken(Token.Types.COMMA);
		accept();
		expectToken(Token.Types.CLOSED_PARENTHESIS);
	}
	
	/**
	 * states = idset
	 */
	private void states() throws IllegalDFAFormatException, IOException, IllegalTokenException 
	{
		states = idSet();
	}
	
	/**
	 * alphabet = "{" symbol { "," symbol } "}"
	 */
	private void alphabet() throws IllegalDFAFormatException, IOException, IllegalTokenException 
	{
		alphabet = new ArrayList<String>();
		expectToken(Token.Types.OPEN_BRACE);
		alphabet.add(expectSymbol());
		while (Token.Types.CLOSED_BRACE != currentToken.getType()) {
			expectToken(Token.Types.COMMA);
			alphabet.add(expectSymbol());
		}
		expectToken(Token.Types.CLOSED_BRACE);
	}
	
	/**
	 * tfunction = "{" map {"," map} "}"
	 */
	private void tFunction() throws IllegalDFAFormatException, IOException, IllegalTokenException 
	{
		expectToken(Token.Types.OPEN_BRACE);
		String [] mapping = map();
	
		if (!isValidState(mapping[0]) || !isValidAlphabet( mapping[1]) || !isValidState(mapping[2])) 
		{
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
	
	/**
	 * start = id
	 */
	private void start() throws IllegalDFAFormatException, IOException, IllegalTokenException 
	{
		startState = expectID();
		if (!isValidState(startState)) {
			throw new IllegalDFAFormatException();
		}
	}
	
	/**
	 * accept = idset
	 */
	private void accept() throws IllegalDFAFormatException, IOException, IllegalTokenException 
	{
		acceptStates = idSet();
		for (String i : acceptStates) {
			if (!isValidState(i)) {
				throw new IllegalDFAFormatException();
			}
		}
	}
	
	/**
	 * idset = "{" id { "," id } "}"
	 */
	private ArrayList<String> idSet() throws IllegalDFAFormatException, IOException, IllegalTokenException 
	{
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
	
	/**
	 * map = "(" id "," symbol ")" "->" id ")"
	 */
	private String[] map() throws IllegalDFAFormatException, IOException, IllegalTokenException 
	{
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
	
	/**
	 * Compares the current token type to the given type, that is specified by the parameter type. 
	 * @param type
	 * @throws IllegalDFAFormatException
	 * @throws IOException
	 * @throws IllegalTokenException
	 */
	private void expectToken(Token.Types type) throws IllegalDFAFormatException, IOException, IllegalTokenException 
	{
		if(type != currentToken.getType()) {
			System.out.println("Expected: " + type + " But found: " +  currentToken.getType());
			throw new IllegalDFAFormatException();
		}
		currentToken = sc.next();
	}
	
	/**
	 * Compares the current token type to the ID type. 
	 * If they are equal the correct lexeme 
	 * will be returned else the correct exception will be thrown. 
	 * @param type
	 * @throws IllegalDFAFormatException
	 * @throws IOException
	 * @throws IllegalTokenException
	 */
	private String expectID() throws IllegalDFAFormatException, IOException, IllegalTokenException 
	{
		String id;
		if(Token.Types.SYMBOL != currentToken.getType() && Token.Types.ID != currentToken.getType()) {
			System.out.println("Expected: " + Token.Types.ID + " But found: " +  currentToken.getType());
			throw new IllegalDFAFormatException();
		} else if (Token.Types.SYMBOL == currentToken.getType() && currentToken.getLexeme().equals("")) {
			System.out.println("Expected: " + Token.Types.ID + " But found: " +  currentToken.getType());
                	throw new IllegalDFAFormatException();

		}
		id = currentToken.getLexeme();
		currentToken = sc.next();
		return id;
	}
	
	/**
	 * Compares the current token type to the Symbol type. 
	 * If they are equal the correct lexeme or number 
	 * will be returned else the correct exception will be thrown. 
	 * @param type
	 * @throws IllegalDFAFormatException
	 * @throws IOException
	 * @throws IllegalTokenException
	 */
	private String expectSymbol() throws IllegalDFAFormatException, IOException, IllegalTokenException 
	{
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
	
	public boolean isValidState(String st) 
	{
		for (String i : states) {
			if (i.equals(st)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isValidAlphabet(String alp) 
	{
		for (String i : alphabet) {
			if (i.equals(alp)) {
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<String> getStates() 
	{
		return states;
	}
	
	public ArrayList<String> getAlphabet() 
	{
		return alphabet;
	}
	
	public ArrayList<String> getAcceptStates() 
	{
		return acceptStates;
	}
	
	public String getStartState() 
	{
		return startState;
	}
	
	public HashMap<String, String> getaRb() 
	{
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
