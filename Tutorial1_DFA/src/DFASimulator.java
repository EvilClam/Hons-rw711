/**
 * @author Shaun Schreiber
 * @version Final
 * 
 * Takes two commandline arguments as input. The first argument is the DFA file in that is in the correct EBNF format.
 * The second argument is a String that will simulate the input to the DFA.
 */
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class DFASimulator {
	

	private Parser pr ;
	
	/**
	 * Stores all of the given transformations that were specified in the given EBNF.
	 */
	private HashMap<String, String> mapping;
	
	public static void main(String[] args) 
	{	
		String DFADesciption = "";
		String DFAInput = "";
		if (args.length  > 1) {
			DFADesciption =  args[0];
			DFAInput =  args[1];
		} else if (args.length == 1) {
			DFADesciption =  args[0];
		} else {
			System.out.println("No arguments specified <DFA file> <DFA input>");
			return;
		}
		DFASimulator dfa = new DFASimulator();
		try {
			dfa.setup(DFADesciption);
		} catch (IOException | IllegalTokenException e) {
			System.out.println(e + "\nreject");
			return;
		} catch (IllegalDFAFormatException e) {
			System.out.println(e + "\nreject");
			return;
		}
		String result;
		try {	
			result = dfa.simulateDFA(DFAInput);
			System.out.println(result);
		} catch (IllegalInputException e) {
			System.out.println(e + "\nreject");
			
		}		
	}
	
	/**
	 * Initializes and runs the scanner and parser on the specified file.
	 * @param DFADesciption The path to the DFA file.
	 * @throws IOException If the File could not be found.
	 * @throws IllegalTokenException The DFA file contains illegal characters.  
	 * @throws IllegalDFAFormatException 	
	 *  */
	public void setup(String DFADesciption) throws IOException, IllegalTokenException, IllegalDFAFormatException {
		pr = new Parser();
		pr.setup(DFADesciption);
		pr.parse();
		mapping = pr.getaRb();	
	}
	
	/**
	 * Simulates a DFA and uses the DFAInput to simulate the input to the DFA.
	 * @param DFAInput String that is used to simulate the input to the DFA.
	 * @return accept if the DFAIpnut string is in an accept state when i the input finishes else reject.
	 * @throws IllegalInputException The input contains characters that is not part of the specified alphabet.
	 */
	public String simulateDFA(String DFAInput) throws IllegalInputException
	{
		String currentState = pr.getStartState();
		String currentKey;
		for (int i = 0 ; i < DFAInput.length() ; i++) {
			currentKey = currentState + DFAInput.subSequence(i, i + 1);
			if (pr.isValidAlphabet("" + DFAInput.subSequence(i, i + 1))) {
				currentState = mapping.get(currentKey);
			} else {
				throw new IllegalInputException(DFAInput.substring(i, i + 1));
			}
			
		}
		for (String i : pr.getAcceptStates()) {
			if (currentState.equals(i)) {
				return "accept";
			}
		}
		return "reject";
	}
}

/**
 * @author EvilClam
 * This exception gets throw when a given input contains a character that is not in the given alphabet. 
 */
class IllegalInputException extends Exception
{
	/**
	 * The invalid character.
	 */
	String invalid ;

	public IllegalInputException(String invalidToken) {
		invalid = invalidToken;
	}
	
	public String toString() 
	{
		return "IllegalInputException: " + invalid;
	}
}
