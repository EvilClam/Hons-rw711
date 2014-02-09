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
	/**
	 * 
	 */
	private Parser pr ;
	
	/**
	 * @
	 */
	private HashMap<String, String> mapping;
	
	public static void main(String[] args) 
	{
		String DFADesciption =  args[0];
		String DFAInput =  args[1];
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
	 * 
	 * @param DFADesciption
	 * @throws IOException
	 * @throws IllegalTokenException
	 * @throws IllegalDFAFormatException
	 */
	public void setup(String DFADesciption) throws IOException, IllegalTokenException, IllegalDFAFormatException {
		pr = new Parser();
		pr.setup(DFADesciption);
		pr.parse();
		mapping = pr.getaRb();
		
		
	}
	
	/**
	 * 
	 * @param DFAInput
	 * @return
	 * @throws IllegalInputException
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
 * 
 * @author EvilClam
 *
 */
class IllegalInputException extends Exception
{
	/**
	 * 
	 */
	String invalid ;

	/**
	 * 
	 * @param invalidToken
	 */
	public IllegalInputException(String invalidToken) {
		invalid = invalidToken;
	}
	
	/**
	 * 
	 */
	public String toString() 
	{
		return "IllegalInputException: " + invalid;
	}
}
