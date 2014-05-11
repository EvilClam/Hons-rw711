import java.util.Stack;

/**
 * @author Shaun Schreiber Backtracking regex matcher.
 */
public class NFAMatcher {

	/**
	 * The regular expression the input string is going to be matched to.
	 */
	private NFAPattern regex;

	/**
	 * The input character sequence.
	 */
	private StringBuilder input;

	/**
	 * Creates a Matcher with pattern regex and input character sequence
	 * 'input'.
	 * 
	 * @param regex
	 *            The regular expression the pattern is matched against.
	 * @param input
	 *            The input character sequence.
	 */
	public NFAMatcher(String regex, CharSequence input) {
		this.regex = NFAPattern.compile(regex);
		this.input = new StringBuilder(input);
	}

	/**
	 * Creates a Matcher with pattern regex and input character sequence
	 * 'input'.
	 * 
	 * @param regex
	 *            Pattern with a given regular expression.
	 * @param input
	 *            The input character sequence.
	 */
	public NFAMatcher(NFAPattern pattern, CharSequence input) {
		this.regex = pattern;
		this.input = new StringBuilder(input);
	}

	/**
	 * Matches the given input string to the given regex.
	 * 
	 * @return True if the pattern matches else false.
	 */
	public Boolean matches() {
		boolean matches = false;
		Stack<Node> stack = new Stack<Node>();
	    NFA tree = regex.getParser().getParserTree();
		stack.push(tree.getRoot());
		while (!stack.isEmpty()) {
			
			Node currentNode = stack.pop();
			if (currentNode.getPreviousState() == null) {
				currentNode.setPreviousState(currentNode.clone());
			} else {
				if (currentNode.getHasMatched() == currentNode.getPreviousState().getHasMatched()) {
					continue;
				} else {
					currentNode.setPreviousState(currentNode.clone());
				}
			}
			
			if ((currentNode.getHasMatched() == input.length()) && (currentNode.getOp() == OPERANDS.FINAL)) {
				 matches = true;
				 break;
			}
			
			if (currentNode.getHasMatched() <= input.length() || input.length() == 0) {
				if (currentNode.getNumberOfChildren() == 2) {
					Node tmp = (currentNode.getRight());
					tmp.setHasMatched(currentNode.getHasMatched());
					stack.push(tmp);
					
					tmp = currentNode.getLeft();
					tmp.setHasMatched(currentNode.getHasMatched());
					stack.push(tmp);
				} else if (currentNode.getHasMatched() < input.length() && currentNode.getNumberOfChildren() == 1 && currentNode.getTransitionFromThisNode().getToken_type() != TYPE.EPSILON) {
					Node tmp = currentNode.getLeft().clone();
					if ( currentNode.getTransitionFromThisNode().getToken_type() == TYPE.FULLSTOP || currentNode.getTransitionFromThisNode().getLiteral().equals(""+input.charAt(currentNode.getHasMatched()))) {
						tmp.setHasMatched(currentNode.getHasMatched() + 1);
						stack.push(tmp);
					}
				} else if (currentNode.getNumberOfChildren() == 1 && currentNode.getTransitionFromThisNode().getToken_type() == TYPE.EPSILON) {
					Node tmp = currentNode.getLeft();
					tmp.setHasMatched(currentNode.getHasMatched());
					stack.push(tmp);
				} else {
					System.out.println("Pie");
				}
			}
		}
		return matches;
	}

	/**
	 * 
	 * @param i
	 * @return
	 */
	public String group(int i) {
		return "";
	}
}
