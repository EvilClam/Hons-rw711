import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class DFAI {

	parser pr ;
	HashMap<String, String> mapping;
	public static void main(String[] args) 
	{
		String DFADesciption =  args[0];
		String DFAInput =  args[1];
		DFAI dfa = new DFAI();
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
	
	public void setup(String DFADesciption) throws IOException, IllegalTokenException, IllegalDFAFormatException {
		pr = new parser();
		pr.setup(DFADesciption);
		pr.parse();
		mapping = pr.getaRb();
		
		
	}
	
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

class IllegalInputException extends Exception
{
	String invalid ;

	public IllegalInputException(String invalidToken) {
		invalid = invalidToken;
	}
	
	public String toString() 
	{
		return "IllegalInputException: " + invalid;
	}
}
