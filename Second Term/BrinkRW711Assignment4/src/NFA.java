
public class NFA {
	
	private Node tree;
	
	
	public Node concatenate(Node left, Node right) {
		if (left == null) {
			return right;
		} else if (right == null) {
			return left;
		}
		left.getFinal().setLeft(right.getLeft());
		left.getFinal().setRight(right.getRight());
		left.getFinal().setNumberOfChildren(right.getNumberOfChildren());
		left.getFinal().setPriority((short)0);
		left.getFinal().setOp(right.getOp());
		left.getFinal().setTransitionFromThisNode(right.getTransitionFromThisNode());
		left.getFinal().setFinal(null);
		left.setFinal(right.getFinal());
		return left;
	}
	
	public Node createUniaryNode(Token type) {
		
		//Create the starting node.
		Node returnNode = new Node();
		returnNode.setStart(true);
		returnNode.setOp(OPERANDS.UNARY);
		returnNode.setNumberOfChildren((short)1);
		returnNode.setTransitionFromThisNode(type);
		
		//Create the child node.
		Node valueNode = new Node();
		valueNode.setNumberOfChildren((short)0);
		valueNode.setOp(OPERANDS.FINAL);
		valueNode.setPriority((short)0);
		valueNode.setFinal(null);
		
		//Add child node to parent node.
		returnNode.setLeft(valueNode);
		returnNode.setFinal(valueNode);
		return returnNode;
	}
	
	public Node createStarNode(Node input) {
		Node returnNode = input.getFinal();
		returnNode.setStart(true);
		returnNode.setOp(OPERANDS.STAR);
		returnNode.setTransitionFromThisNode(new Token(TYPE.EPSILON));
		
		Node FinalNode = new Node();
		FinalNode.setNumberOfChildren((short)0);
		FinalNode.setOp(OPERANDS.FINAL);
		FinalNode.setPriority((short)1);
		returnNode.setRight(FinalNode);
		returnNode.setFinal(FinalNode);
		
		returnNode.setNumberOfChildren((short)2);
		input.setPriority((short)0);
		input.setStart(false);
		input.setFinal(null);
		input.setOp(OPERANDS.UNARY);
		returnNode.setLeft(input);
		
		return returnNode;
	}
	
	public Node createReluctentNode(Node input) {
		Node returnNode = input.getFinal();
		returnNode.setStart(true);
		returnNode.setOp(OPERANDS.RSTAR);
		returnNode.setTransitionFromThisNode(new Token(TYPE.EPSILON));
		
		Node FinalNode = new Node();
		FinalNode.setNumberOfChildren((short)0);
		FinalNode.setOp(OPERANDS.FINAL);
		FinalNode.setPriority((short)0);
		returnNode.setLeft(FinalNode);
		returnNode.setFinal(FinalNode);
		
		returnNode.setNumberOfChildren((short)2);
		input.setPriority((short)1);
		input.setOp(OPERANDS.UNARY);
		input.setStart(false);
		input.setFinal(null);
		
		returnNode.setRight(input);
		
		return returnNode;
	}
	
	public Node union(Node left, Node right) {
		Node startNode = new Node();
		startNode.setNumberOfChildren((short)2);
		startNode.setOp(OPERANDS.OR);
		startNode.setStart(true);
		startNode.setTransitionFromThisNode(new Token(TYPE.EPSILON));
		
		left.setStart(false);
		//left.setTransitionFromThisNode(new Token(TYPE.EPSILON));
		left.setPriority((short)0);
		
		right.setStart(false);
		//right.setTransitionToThisNode(new Token(TYPE.EPSILON));		
		right.setPriority((short)1);

		Node Final = new Node();
		//Final.setTransitionToThisNode(new Token(TYPE.EPSILON));
		Final.setPriority((short)0);
		Final.setOp(OPERANDS.FINAL);
		Final.setNumberOfChildren((short)0);
		
		left.getFinal().setLeft(Final);
		left.getFinal().setOp(OPERANDS.UNARY);
		left.getFinal().setNumberOfChildren((short)1);
		left.getFinal().setTransitionFromThisNode(new Token(TYPE.EPSILON));
		left.getFinal().setStart(false); //just incase
		
		right.getFinal().setLeft(Final);
		right.getFinal().setOp(OPERANDS.UNARY);
		right.getFinal().setNumberOfChildren((short)1);
		right.getFinal().setTransitionFromThisNode(new Token(TYPE.EPSILON));
		right.getFinal().setStart(false);//just incase
		
		left.setFinal(null);
		right.setFinal(null);
		
		startNode.setLeft(left);
		startNode.setRight(right);
		startNode.setFinal(Final);
		
		return startNode;
	}
	
	public Node getRoot() {
		return tree;
	}
	
	public void setRoot(Node tree) {
		this.tree = tree;
	}
	
}

class Node {
	
	/**
	 * The number of children nodes the current node has.
	 */
	private short numberOfChildren;
	
	private Node Left;

	private Node Right;
	
	private Node Final;
	
	private short priority;
	
	private OPERANDS op;
	
	private Token transitionFromThisNode;
	
	private boolean start;
	
	private int hasMatched;
	
	private Node previousState;

	public Node getPreviousState() {
		return previousState;
	}

	public void setPreviousState(Node previousState) {
		this.previousState = previousState;
	}

	public int getHasMatched() {
		return hasMatched;
	}

	public void setHasMatched(int hasMatched) {
		this.hasMatched = hasMatched;
	}

	public Token getTransitionFromThisNode() {
		return transitionFromThisNode;
	}

	public void setTransitionFromThisNode(Token transitionFromThisNode) {
		this.transitionFromThisNode = transitionFromThisNode;
	}

	public boolean isStart() {
		return start;
	}

	public void setStart(boolean start) {
		this.start = start;
	}

	public short getNumberOfChildren() {
		return numberOfChildren;
	}

	public void setNumberOfChildren(short numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
	}

	public Node getLeft() {
		return Left;
	}

	public void setLeft(Node left) {
		Left = left;
	}

	public Node getRight() {
		return Right;
	}

	public void setRight(Node right) {
		Right = right;
	}
	
	public Node getFinal() {
		return Final;
	}

	public void setFinal(Node finall) {
		Final = finall;
	}

	public short getPriority() {
		return priority;
	}

	public void setPriority(short priority) {
		this.priority = priority;
	}

	public OPERANDS getOp() {
		return op;
	}

	public void setOp(OPERANDS op) {
		this.op = op;
	}
	
	public Node clone() {
		Node returnNode = new Node();
		returnNode.setFinal(getFinal());
		returnNode.setHasMatched(getHasMatched());
		returnNode.setLeft(getLeft());
		returnNode.setNumberOfChildren(getNumberOfChildren());
		returnNode.setOp(getOp());
		returnNode.setPriority(getPriority());
		returnNode.setRight(getRight());
		returnNode.setStart(isStart());
		returnNode.setTransitionFromThisNode(getTransitionFromThisNode());
		return returnNode;
	}
}

enum OPERANDS {
	OR,
	CAT,
	STAR,
	RSTAR,
	UNARY,
	FINAL
}


