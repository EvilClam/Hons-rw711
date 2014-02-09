/**
 * @author EvilClam
 * Stores a single Token.
 */

public class Token {
	
	private final Types type;
	private final int number;
	private final String lexeme;
	
	public Token(Types type, int number, String lexeme) 
	{
		this.type = type;
		this.number = number;
		this.lexeme = lexeme;
	}
	
	public Types getType() 
	{
		return type;
	}
	
	public int getNumber() 
	{
		return number;
	}
	
	public String getLexeme() 
	{
		return lexeme;
	}
	
	public enum Types 
	{
		ID, DFA, SYMBOL, OPEN_PARENTHESIS, CLOSED_PARENTHESIS, COMMA, OPEN_BRACE, CLOSED_BRACE, ARROW, EQUALS, EOF
	}
	
	public String toStringType()
	{
		String sType = "";
		switch (type) {
		case ID:
			sType = "ID";
			break;
		case DFA:
			sType = "DFA";
			break;
		case SYMBOL:
			sType = "Symbol";
			break;
		case OPEN_PARENTHESIS:
			sType = "OPEN_PARENTHESIS";
			break;
		case CLOSED_PARENTHESIS:
			sType = "CLOSED_PARENTHESIS";
			break;
		case COMMA:
			sType = "COMMA";
			break;
		case OPEN_BRACE:
			sType = "OPEN_BRACE";
			break;
		case CLOSED_BRACE:
			sType = "CLOSED_BRACE";
			break;
		case ARROW:
			sType = "ARROW";
			break;
		case EQUALS:
			sType = "EQUALS";
			break;
		case EOF:
			sType = "EOF";
			break;
		default:
			break;
		}
		return sType;
	}

}
