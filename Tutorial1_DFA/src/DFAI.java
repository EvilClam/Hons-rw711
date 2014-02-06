import java.io.FileNotFoundException;
import java.util.HashMap;

public class DFAI {

	public static void main(String[] args) 
	{
		//Change back to args[0]
		String DFADesciption = "test1";
		//Change back to args[1]
		String DFAInput = "1010101010001011111100000";
		parser pr = new parser();
		try {
			pr.setup(DFADesciption);
			pr.parse();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalDFAFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HashMap<String, String> mapping = pr.getaRb();
		String currentState = pr.getStartState();
		String currentKey;
		for (int i = 0 ; i < DFAInput.length() ; i++) {
			
			currentKey = currentState + DFAInput.subSequence(i, i + 1);
			currentState = mapping.get(currentKey);
			System.out.println(mapping);
			System.out.println(currentKey);
			
		}
		for (String i : pr.getAcceptStates()) {
			if (currentState.equals(i)) {
				System.out.println("accept");
				return;
			}
		}
		System.out.println("rejected");
	}

}
