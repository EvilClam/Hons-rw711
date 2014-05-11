import static org.junit.Assert.*;

import org.junit.Test;

public class Test_Scanner {

	/**
	 * Tests the literals
	 */
	Scanner sc;

	public void After() {
		sc = null;
	}

	/**
	 * Test literals.
	 */
	@Test
	public void test1_Literals_single() {
		sc = new Scanner(new StringBuilder("a"));
		expectLiteral(TYPE.LITTERAL, "a");
		expect(TYPE.END);
	}
	
	/**
	 * Test literals.
	 */
	@Test
	public void test2_Literals_Multiple() {
		sc = new Scanner(new StringBuilder("abc"));
		expectLiteral(TYPE.LITTERAL, "a");
		expectLiteral(TYPE.LITTERAL, "b");
		expectLiteral(TYPE.LITTERAL, "c");
		expect(TYPE.END);
	}
	
	/**
	 * Test literals.
	 */
	@Test
	public void test3_BackSlash() {
		sc = new Scanner(new StringBuilder("\\"));
		expect(TYPE.BACKSLASH);
		expect(TYPE.END);
	}
	
	/**
	 * Test literals.
	 */
	@Test
	public void test4_Literals_single_BackSlash() {
		sc = new Scanner(new StringBuilder("a\\"));
		expectLiteral(TYPE.LITTERAL, "a");
		expect(TYPE.BACKSLASH);
		expect(TYPE.END);
		
		sc = new Scanner(new StringBuilder("\\a"));
		expect(TYPE.BACKSLASH);
		expectLiteral(TYPE.LITTERAL, "a");
		expect(TYPE.END);
	}
	
	/**
	 * Test literals.
	 */
	@Test
	public void test5_Literals_multiple_BackSlashs() {
		sc = new Scanner(new StringBuilder("\\\\abcdefg\\\\hijklmn\\opqrstuvwxyz\\\\b\\AZ"));
		expect(TYPE.BACKSLASH);
		expect(TYPE.BACKSLASH);
		expectLiteral(TYPE.LITTERAL, "a");
		expectLiteral(TYPE.LITTERAL, "b");
		expectLiteral(TYPE.LITTERAL, "c");
		expectLiteral(TYPE.LITTERAL, "d");
		expectLiteral(TYPE.LITTERAL, "e");
		expectLiteral(TYPE.LITTERAL, "f");
		expectLiteral(TYPE.LITTERAL, "g");
		expect(TYPE.BACKSLASH);
		expect(TYPE.BACKSLASH);
		expectLiteral(TYPE.LITTERAL, "h");
		expectLiteral(TYPE.LITTERAL, "i");
		expectLiteral(TYPE.LITTERAL, "j");
		expectLiteral(TYPE.LITTERAL, "k");
		expectLiteral(TYPE.LITTERAL, "l");
		expectLiteral(TYPE.LITTERAL, "m");
		expectLiteral(TYPE.LITTERAL, "n");
		expect(TYPE.BACKSLASH);
		expectLiteral(TYPE.LITTERAL, "o");
		expectLiteral(TYPE.LITTERAL, "p");
		expectLiteral(TYPE.LITTERAL, "q");
		expectLiteral(TYPE.LITTERAL, "r");
		expectLiteral(TYPE.LITTERAL, "s");
		expectLiteral(TYPE.LITTERAL, "t");
		expectLiteral(TYPE.LITTERAL, "u");
		expectLiteral(TYPE.LITTERAL, "v");
		expectLiteral(TYPE.LITTERAL, "w");
		expectLiteral(TYPE.LITTERAL, "x");
		expectLiteral(TYPE.LITTERAL, "y");
		expectLiteral(TYPE.LITTERAL, "z");
		expect(TYPE.BACKSLASH);
		expect(TYPE.BACKSLASH);
		expectLiteral(TYPE.LITTERAL, "b");
		expect(TYPE.BACKSLASH);
		expectLiteral(TYPE.LITTERAL, "A");
		expectLiteral(TYPE.LITTERAL, "Z");	
		expect(TYPE.END);
	}
	
	/**
	 * Test wildcard
	 */
	@Test
	public void test6_wildcard_single() {
		sc = new Scanner(new StringBuilder("."));
		expect(TYPE.FULLSTOP);
		expect(TYPE.END);
	}
	
	/**
	 * Test wildcard
	 */
	@Test
	public void test7_wildcard_multiple() {
		sc = new Scanner(new StringBuilder("....."));
		expect(TYPE.FULLSTOP);
		expect(TYPE.FULLSTOP);
		expect(TYPE.FULLSTOP);
		expect(TYPE.FULLSTOP);
		expect(TYPE.FULLSTOP);
		expect(TYPE.END);
	}
	
	/**
	 * Test wildcard
	 */
	@Test
	public void test8_wildcard_multiple_Literals() {
		sc = new Scanner(new StringBuilder("..abc.defg..xyz"));
		expect(TYPE.FULLSTOP);
		expect(TYPE.FULLSTOP);
		expectLiteral(TYPE.LITTERAL, "a");
		expectLiteral(TYPE.LITTERAL, "b");
		expectLiteral(TYPE.LITTERAL, "c");
		expect(TYPE.FULLSTOP);
		expectLiteral(TYPE.LITTERAL, "d");
		expectLiteral(TYPE.LITTERAL, "e");
		expectLiteral(TYPE.LITTERAL, "f");
		expectLiteral(TYPE.LITTERAL, "g");
		expect(TYPE.FULLSTOP);
		expect(TYPE.FULLSTOP);
		expectLiteral(TYPE.LITTERAL, "x");
		expectLiteral(TYPE.LITTERAL, "y");
		expectLiteral(TYPE.LITTERAL, "z");
		expect(TYPE.END);
	}
	
	private void expect(TYPE expect) {
		assertEquals(sc.getNextToken(), new Token(expect));
	}
	
	private void expectLiteral(TYPE expect, String actualS) {
		assertEquals((new Token(expect, actualS)),sc.getNextToken());
	}
	
	private void expectInteger(TYPE expect, Integer actualI) {
		assertEquals((new Token(expect, actualI)),sc.getNextToken());
	}

}
