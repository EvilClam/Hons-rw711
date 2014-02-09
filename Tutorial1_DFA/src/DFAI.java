import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class DFAI {

	parser pr ;
	HashMap<String, String> mapping;
	public static void main(String[] args) 
	{
		//Change back to args[0]
		String DFADesciption =  args[0];
		//Change back to args[1]
		String DFAInput =  args[1];
		DFAI dfa = new DFAI();
		dfa.setup(DFADesciption);
		String result;
		try {	
			result = dfa.simulateDFA(DFAInput);
			System.out.println(result);
		} catch (IllegalInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void setup(String DFADesciption) {
		pr = new parser();
		try {
			pr.setup(DFADesciption);
			pr.parse();
			mapping = pr.getaRb();
		} catch (IOException | IllegalTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalDFAFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String simulateDFA(String DFAInput) throws IllegalInputException
	{
		String currentState = pr.getStartState();
		String currentKey;
		for (int i = 0 ; i < DFAInput.length() ; i++) {
			
			currentKey = currentState + DFAInput.subSequence(i, i + 1);
			currentState = mapping.get(currentKey);
			if (currentState == null) {
				throw new IllegalInputException(DFAInput.substring(i, i + 1));
			}
			
		}
		for (String i : pr.getAcceptStates()) {
			if (currentState.equals(i)) {
				return "accept";
			}
		}
		return "rejected";

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
