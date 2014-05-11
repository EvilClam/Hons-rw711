/**
 * @author Shaun Schreiber Reads the given regex token per token.
 */
public class Scanner {

	/**
	 * Regular Expression thats to be parsed.
	 */
	private StringBuilder regex;

	/**
	 * Index where the scanner is currently reading at.
	 */
	private int currentIndex;

	/**
	 * The current slected Char.
	 */
	private char currentChar;

	/**
	 * Stores the regular expression.
	 * 
	 * @param reg
	 *            THe regular expression.
	 * @throws Exception
	 */
	public Scanner(StringBuilder reg)  {
		regex = reg;
		getChar();
	}

	/**
	 * Returns the next Token in the regex
	 * 
	 * @return
	 * @throws Exception
	 */
	public Token getNextToken() {
		Token token = null;
		if (regex.length() > 0) {

			if (Character.isAlphabetic(currentChar)) {
				token = readWord();

			} else if (Character.isDigit(currentChar)) {
				token = readNumber();
			} else {
				switch (currentChar) {
				case '*':
					getChar();
					if (currentChar == '?') {
						token = new Token(TYPE.RELUCTENTSTAR);
						getChar();
					} else {
						token = new Token(TYPE.STAR);
					}
					break;
				case '\\':
					token = new Token(TYPE.BACKSLASH);
					getChar();
					break;
				case '(':
					token = new Token(TYPE.OPEN_PERENTHASIS);
					getChar();
					break;
				case ')':
					token = new Token(TYPE.CLOSED_PERENTHESIS);
					getChar();
					break;
				case '.':
					token = new Token(TYPE.FULLSTOP);
					getChar();
					break;
				case '\0':
					token = new Token(TYPE.END);
					break;
				case '|':
					token = new Token(TYPE.OR);
					getChar();
					break;
				default:
					token = new Token(TYPE.ERROR);
					getChar();
					break;
				}

			}
		}
		return token;
	}

	public void getChar() {
		if (hasNext()) {
			currentChar = regex.charAt(currentIndex);
			currentIndex++;
		} else {
			currentChar = '\0';
		}
	}

	/**
	 * Builds up a LITERAL token
	 * 
	 * @return LITERAL token
	 * @throws Exception
	 */
	public Token readWord() {
		StringBuilder word = new StringBuilder();
		Token token;

		if (Character.isAlphabetic(currentChar)) {
			word.append(currentChar);
			getChar();
		}

		token = new Token(TYPE.LITTERAL, word.toString());
		return token;
	}

	/**
	 * Builds up a INTEGER that has an integer value.
	 * 
	 * @return Token that is in TYPE.INTEGER and has its numberic value.
	 * @throws Exception
	 */
	public Token readNumber() {
		StringBuilder number = new StringBuilder();
		Token token;

		if (Character.isDigit(currentChar)) {
			number.append(currentChar);
			getChar();
		}

		token = new Token(TYPE.INTEGER, Integer.parseInt(number.toString()));
		return token;
	}

	/**
	 * Checks to see if it has a next symbol.
	 * 
	 * @return True if there are still a remaining element else false.
	 */
	public boolean hasNext() {
		return currentIndex < regex.length();
	}

	public static void main(String[] args) {
		Scanner sc;
		try {
			sc = new Scanner(new StringBuilder("a1.*?*d1()\\|"));
			TYPE[] answers = { TYPE.LITTERAL, TYPE.INTEGER, TYPE.FULLSTOP,
					TYPE.RELUCTENTSTAR, TYPE.STAR, TYPE.LITTERAL, TYPE.INTEGER,
					TYPE.OPEN_PERENTHASIS, TYPE.CLOSED_PERENTHESIS,
					TYPE.BACKSLASH, TYPE.OR };
			int i = 0;
			while (sc.hasNext()) {
				Token t = sc.getNextToken();
				if (t.getToken_type() == answers[i]) {
					i++;
					System.out.println(t);
				} else {
					System.out.println("Error");
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
