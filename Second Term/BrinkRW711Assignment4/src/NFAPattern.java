
/**
 * @author Shaun Schreiber
 * 
 */
public class NFAPattern {
	
	private Parser parser;

	private NFAPattern(StringBuilder regex) {
		parser = new Parser(new StringBuilder(regex));
		parser.parse();
	}
	
	/**
	 * 
	 * @param regex
	 * @return
	 */
	public static NFAPattern compile(String regex) {
		return new NFAPattern(new StringBuilder(regex));
	}
	
	/**
	 * 
	 * @param charSequence
	 * @return
	 */
	public NFAMatcher matcher(CharSequence charSequence) {
		NFAMatcher mc = new NFAMatcher(this, new StringBuilder(charSequence));
		return mc;
	}
	
	public Parser getParser() {
		return parser;
	}

	public void setParser(Parser parser) {
		this.parser = parser;
	}
}
