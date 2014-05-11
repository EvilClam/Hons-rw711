/**
 * 
 * @author evilclam
 * 
 */
public class Token {

	/**
	 * The type of the current token.
	 */
	private TYPE token_type;

	/**
	 * The actual string.
	 */
	private String literal;

	/**
	 * The integers.
	 */
	private Integer value;

	public Token(TYPE token_type) {
		this.token_type = token_type;
		this.literal = null;
		this.value = null;
	}

	public Token(TYPE token_type, String literal) {
		this.token_type = token_type;
		this.literal = literal;
		this.value = null;
	}

	public Token(TYPE token_type, Integer value) {
		this.token_type = token_type;
		this.literal = null;
		this.value = value;
	}

	public Token(TYPE token_type, String literal, Integer value) {
		this.token_type = token_type;
		this.literal = literal;
		this.value = value;

	}

	public TYPE getToken_type() {
		return token_type;
	}

	public void setToken_type(TYPE token_type) {
		this.token_type = token_type;
	}

	public String getLiteral() {
		return literal;
	}

	public void setLiteral(String literal) {
		this.literal = literal;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public boolean equals(Object o) {

		if (o instanceof Token) {
			Token current = (Token) o;
			if (current.getToken_type() == getToken_type()) {
				if (current.getToken_type() == TYPE.LITTERAL) {
					if (getLiteral() != null && current.getLiteral() != null
							&& getLiteral().equals(current.getLiteral())) {
						return true;
					} else {
						return false;
					}
				} else if (current.getToken_type() == TYPE.INTEGER) {
					if (getValue() != null && current.getValue() != null
							&& getValue().equals(current.getValue())) {
						return true;
					} else {
						return false;
					}
				}
				return true;
			}
			return false;
		}
		return false;
	}

	public String toString() {
		String s = "Token Type: " + token_type + " Literal: " + literal
				+ " Integer:" + value;
		return s;
	}
}
