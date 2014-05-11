/**
 * 
 * @author evilclam
 * 
 */
public class Parser {

	/**
	 * 
	 */
	private Token currentToken;

	/**
	 * My own scanner to handle the given input
	 */
	private Scanner sc;

	private NFA parserTree;

	private Node tree;
	
	private boolean debug = true;
	
	/**
	 * @throws Exception
	 * 
	 */
	public Parser(StringBuilder regex) {
		sc = new Scanner(regex);
		parserTree = new NFA();
		try {
			nextToken();
		} catch (IllegalExpressionFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean parse() {
		try {
			tree = regex();
			parserTree.setRoot(tree);
			return true;
		} catch (IllegalExpressionFormatException e) {
			return false;
		}
	}

	public Node regex() throws IllegalExpressionFormatException {
		Node returnNode = term();
		if (currentToken.getToken_type() == TYPE.OR) {
			if (debug) {
				System.out.print("|");
			}
			nextToken();
			Node right = regex();
			returnNode = parserTree.union(returnNode, right);
		}
		return returnNode;
	}

	public Node term() throws IllegalExpressionFormatException {
		Node returnNode = null;
		while (currentToken.getToken_type() != TYPE.OR
				&& currentToken.getToken_type() != TYPE.END && currentToken.getToken_type() != TYPE.CLOSED_PERENTHESIS) {
			Node right = factor();
			returnNode = parserTree.concatenate(returnNode, right);
			
		}
		return returnNode;
	}

	public Node factor() throws IllegalExpressionFormatException {
		Node returnNode = base();

		if (currentToken.getToken_type() == TYPE.STAR
				|| currentToken.getToken_type() == TYPE.RELUCTENTSTAR) {
			if (currentToken.getToken_type() == TYPE.STAR) {
				returnNode = parserTree.createStarNode(returnNode);
				if (debug) {
					System.out.print("*");
				}
			} else {
				returnNode = parserTree.createReluctentNode(returnNode);
				if (debug) {
					System.out.print("*?");
				}
			}

			nextToken();
		}
		return returnNode;
	}

	public Node base() throws IllegalExpressionFormatException {
		Node returnNode = null;
		if (currentToken.getToken_type() == TYPE.FULLSTOP) {
			returnNode = parserTree.createUniaryNode(currentToken);
			if (debug) {
				System.out.print(".");
			}
			nextToken();
		} else if (currentToken.getToken_type() == TYPE.LITTERAL) {
			returnNode = parserTree.createUniaryNode(currentToken);
			if (debug) {
				System.out.print(currentToken.getLiteral());
			}
			nextToken();
		} else if (currentToken.getToken_type() == TYPE.BACKSLASH) {

			nextToken();
			expect(TYPE.INTEGER);//TODO:
			if (debug) {
				System.out.print("\\"+ currentToken.getValue());
			}
			nextToken();
		} else {
			expect(TYPE.OPEN_PERENTHASIS);
			if (debug) {
				System.out.print("(");
			}
			nextToken();

			returnNode = regex();

			expect(TYPE.CLOSED_PERENTHESIS);
			if (debug) {
				System.out.print(")");
			}
			nextToken();
		}
		return returnNode;
	}

	public void expect(TYPE tokenType) throws IllegalExpressionFormatException {
		if (currentToken.getToken_type() != tokenType) {
			throw new IllegalExpressionFormatException();
		}
	}

	private boolean nextToken() throws IllegalExpressionFormatException {
		boolean hasNext = false;
		currentToken = sc.getNextToken();
		return hasNext;
	}
	
	public NFA getParserTree() {
		return parserTree;
	}

	public void setParserTree(NFA parserTree) {
		this.parserTree = parserTree;
	}

}

class IllegalExpressionFormatException extends Exception {
	public String toString() {
		return "IllegalDFAFormatException";
	}
}
