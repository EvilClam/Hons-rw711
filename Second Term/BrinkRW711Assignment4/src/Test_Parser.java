import static org.junit.Assert.*;

import org.junit.Test;


public class Test_Parser {
	
	private Parser pc;
	
	
	public void After() {
		pc = null;
	}
	
	@Test
	public void test1_test_signle_literal() {
		pc = new Parser(new StringBuilder("a"));
		assertTrue(pc.parse());
	}
	
	@Test
	public void test2_test_multiple_literal() {
		pc = new Parser(new StringBuilder("aAbBBBAalsdkfjlasdfnqwerjk"));
		assertTrue(pc.parse());
	}
	
	@Test
	public void test3_test_signle_OR() {
		pc = new Parser(new StringBuilder("aadsf|aasfsadfasdf"));
		assertTrue(pc.parse());
	}
	@Test
	public void test4_test_multiple_OR() {
		pc = new Parser(new StringBuilder("aadsf|a|SF|FS|fasf|asd|asd|asdvvv"));
		assertTrue(pc.parse());
	}

	@Test
	public void test6_test_backSlash() {
		pc = new Parser(new StringBuilder("\\2"));
		assertTrue(pc.parse());
	}

	@Test
	public void test5_test_single_expression() {
		pc = new Parser(new StringBuilder("(A)*"));
		assertTrue(pc.parse());
	}
	
	@Test
	public void test6_test_multiple() {
		pc = new Parser(new StringBuilder("a*?|(X*)(((aa|b)*)*)|.*(A)*(ASDASD(ASDASD))|(a*?|(X*)(((aa|b)*)*)|.*(A)*(ASDASD(ASDASD)))((((((((as)*)*)*)*)*)*)*)*"));
		assertTrue(pc.parse());
	}
	
}
