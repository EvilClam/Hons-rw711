import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class brinkhw2 {
	
	public static void main (String [] args) {
		brinkhw2.quadratic();
		brinkhw2.cubic();
		brinkhw2.Exponential();
	}
	
	public static void quadratic() {
		Pattern pt = Pattern.compile(".+.+C");
		String init = "b";
		StringBuffer current = new StringBuffer(init);
		String end = "X";
		int NOI = 15;// Number of iterations
		double previousT = 0;
		double finalvalue = 0;
		for (int i = 0 ; i < NOI ; i++ ) {
			long currentT = System.currentTimeMillis();
			Matcher matcher = pt.matcher(current+end);
			matcher.matches();
			currentT = System.currentTimeMillis() - currentT;
			try {
				finalvalue =(double)(currentT) / (double)(previousT);
				previousT = (double)(currentT);
			} catch (Exception e) {
				System.out.println("Ignore divisoin by zero");
				
			}
			current.append(current);
		}
		System.out.println("quadratic: "+finalvalue);
	}
	
	public static void cubic() {
		Pattern pt = Pattern.compile("b*b*b*0");
		String init = "b";
		StringBuffer current = new StringBuffer(init);
		String end = "X";
		int NOI = 11;// Number of iterations
		double previousT = 0;
		double finalvalue = 0;
		for (int i = 0 ; i < NOI ; i++ ) {
			long currentT = System.currentTimeMillis();
			Matcher matcher = pt.matcher(current+end);
			matcher.matches();
			currentT = System.currentTimeMillis() - currentT;
			try {
				//System.out.println((double)(currentT) / (double)(previousT));
				finalvalue = (double)(currentT) / (double)(previousT);
				previousT = (double)(currentT);
			} catch (Exception e) {
				System.out.println("Ignore divisoin by zero");
				
			}
			current.append(current);
		}
		System.out.println("cubic: "+finalvalue);
	}
	
	public static void Exponential() {
		Pattern pt = Pattern.compile("(b|bb)*");
		String init = "b";
		StringBuffer current = new StringBuffer(init);
		String end = "X";
		int NOI = 35;// Number of iterations
		double previousT = 0;
		double finalvalue = 0;
		for (int i = 0 ; i < NOI ; i++ ) {
			long currentT = System.currentTimeMillis();
			Matcher matcher = pt.matcher(current+end);
			matcher.matches();
			currentT = System.currentTimeMillis() - currentT;
			try {
				//System.out.println((double)(currentT) / (double)(previousT));
				finalvalue = (double)(currentT) / (double)(previousT);
				previousT = (double)(currentT);
			} catch (Exception e) {
				System.out.println("Ignore divisoin by zero");
				
			}
			current.append(init);
		}
		System.out.println("Exponential: "+finalvalue);
		System.out.println("Note that the exponent is not done in the same way as the previous two.");
		System.out.println("Instead of doubling the input size, the input size is increased by only one at a time");
		
	}
	
}
