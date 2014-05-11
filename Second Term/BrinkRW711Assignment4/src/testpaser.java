

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class testpaser {
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	
	@Before
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	}

	@After
	public void cleanUpStreams() {
	    System.setOut(null);
	    System.setErr(null);
	}
	
	@Test(timeout=10)
	public void parser_test1() {
		NFAPattern.compile("a");
		assertEquals("a", outContent.toString());
	}
	
	@Test(timeout=10)
	public void parser_test2() {
		NFAPattern.compile("aaaaaaaaaaa");
		assertEquals("aaaaaaaaaaa", outContent.toString());
	}
	
	@Test(timeout=10)
	public void parser_test3() {
		NFAPattern.compile("a(a)");
		assertEquals("a(a)", outContent.toString());
	}
	
	@Test(timeout=10)
	public void parser_test4() {
		NFAPattern.compile("((a((a)))(a))");
		assertEquals("((a((a)))(a))", outContent.toString());
	}
	
	@Test(timeout=10)
	public void parser_test5() {
		NFAPattern.compile("a*");
		assertEquals("a*", outContent.toString());
	}
	
	@Test(timeout=10)
	public void parser_test6() {
		NFAPattern.compile("a*a*(a*)*");
		assertEquals("a*a*(a*)*", outContent.toString());
	}
	
	@Test(timeout=10)
	public void parser_test7() {
		NFAPattern.compile("a*a*?(a*)*?");
		assertEquals("a*a*?(a*)*?", outContent.toString());
	}

	@Test(timeout=10)
	public void parser_test8() {
		NFAPattern.compile("a*a*?(a*)*?\\1");
		assertEquals("a*a*?(a*)*?\\1", outContent.toString());
	}
	
	@Test(timeout=10)
	public void parser_test9() {
		NFAPattern.compile("a|a");
		assertEquals("a|a", outContent.toString());
	}
	
	@Test(timeout=10)
	public void parser_test10() {
		NFAPattern.compile("a|a|b(a|b)*");
		assertEquals("a|a|b(a|b)*", outContent.toString());
	}
	
	@Test(timeout=10)
	public void parser_test11() {
		NFAPattern.compile("a|a|b(a|b*?)*?(aa*?|a*a*b*z*f*)*");
		assertEquals("a|a|b(a|b*?)*?(aa*?|a*a*b*z*f*)*", outContent.toString());
	}
	
	@Test(timeout=10)
	public void cat_macher_test1() {
		NFAPattern temp = NFAPattern.compile("a");
		NFAMatcher temp1 = temp.matcher("a");
		assertEquals(true,temp1.matches());
	}
	
	@Test(timeout=10)
	public void cat_macher_test2() {
		NFAPattern temp = NFAPattern.compile("aa");
		NFAMatcher temp1 = temp.matcher("a");
		assertEquals(false,temp1.matches());
	}
	@Test(timeout=10)
	public void cat_macher_test3() {
		NFAPattern temp = NFAPattern.compile("ab");
		NFAMatcher temp1 = temp.matcher("ab");
		assertEquals(true,temp1.matches());
	}
	@Test(timeout=10)
	public void cat_macher_test4() {
		NFAPattern temp = NFAPattern.compile("ab");
		NFAMatcher temp1 = temp.matcher("aa");
		assertEquals(false,temp1.matches());
	}
	@Test//(timeout=10)
	public void cat_macher_test5() {
		NFAPattern temp = NFAPattern.compile("aaaaaaaaaaaaaaaaaaaabaaaaaaaaaaaaaabbbbbbbbbbbbbbbbbbbbaaaaaaaaaa");
		NFAMatcher temp1 = temp.matcher("aaaaaaaaaaaaaaaaaaaabaaaaaaaaaaaaaabbbbbbbbbbbbbbbbbbbbaaaaaaaaaa");
		assertEquals(true, temp1.matches());
	}
	@Test//(timeout=10)
	public void cat_macher_test6() {
		NFAPattern temp = NFAPattern.compile("aaaaaaaaaaaaaaaaaaaabaaaaaaaaaaaaaabbbbbbbbbbbbbbbbbbbbaaaaaaaaa");
		NFAMatcher temp1 = temp.matcher("aaaaaaaaaaaaaaaaaaaabaaaaaaaaaaaaaabbbbbbbbbbbbbbbbbbbbaaaaaaaaaa");
		assertEquals(false, temp1.matches());
	}
	
	@Test(timeout=10)
	public void or_macher_test1() {
		NFAPattern temp = NFAPattern.compile("a|a");
		NFAMatcher temp1 = temp.matcher("a");
		assertEquals(true, temp1.matches());
	}
	
	@Test(timeout=10)
	public void or_macher_test2() {
		NFAPattern temp = NFAPattern.compile("a|b");
		NFAMatcher temp1 = temp.matcher("a");
		assertEquals(true, temp1.matches());
	}
	
	@Test(timeout=10)
	public void or_macher_test3() {
		NFAPattern temp = NFAPattern.compile("a|b");
		NFAMatcher temp1 = temp.matcher("ab");
		assertEquals(false, temp1.matches());
	}
	
	@Test(timeout=10)
	public void or_macher_test4() {
		NFAPattern temp = NFAPattern.compile("a|b");
		NFAMatcher temp1 = temp.matcher("b");
		assertEquals(true,temp1.matches());
	}
	
	@Test(timeout=10)
	public void or_macher_test5() {
		NFAPattern temp = NFAPattern.compile("a|E|K|S|F|G|H|J|K");
		NFAMatcher temp1 = temp.matcher("H");
		assertEquals(true,temp1.matches());
	}
	
	@Test(timeout=10)
	public void or_macher_test6() {
		NFAPattern temp = NFAPattern.compile("a|E|K|S|F|G|H|J|K");
		NFAMatcher temp1 = temp.matcher("K");
		assertEquals(true,temp1.matches());
	}
	
	@Test(timeout=10)
	public void brace_macher_test1() {
		NFAPattern temp = NFAPattern.compile("(a)");
		NFAMatcher temp1 = temp.matcher("a");
		assertEquals(true, temp1.matches());
	}
	
	@Test(timeout=10)
	public void brace_macher_test2() {
		NFAPattern temp = NFAPattern.compile("(a)|b");
		NFAMatcher temp1 = temp.matcher("a");
		assertEquals(true, temp1.matches());
	}
	
	@Test(timeout=10)
	public void brace_macher_test3() {
		NFAPattern temp = NFAPattern.compile("(a)|(b)");
		NFAMatcher temp1 = temp.matcher("ab");
		assertEquals(false, temp1.matches());
	}
	
	@Test(timeout=10)
	public void brace_macher_test4() {
		NFAPattern temp = NFAPattern.compile("((a)|(b))");
		NFAMatcher temp1 = temp.matcher("b");
		assertEquals(true,temp1.matches());
	}
	
	@Test(timeout=10)
	public void brace_macher_test5() {
		NFAPattern temp = NFAPattern.compile("(a|(E|K|S)|(F)|(G|H)|(J|K))");
		NFAMatcher temp1 = temp.matcher("H");
		assertEquals(true,temp1.matches());
	}
	
	@Test(timeout=10)
	public void brace_macher_test6() {
		NFAPattern temp = NFAPattern.compile("a|(E|K|S|F|G|H|J)|K");
		NFAMatcher temp1 = temp.matcher("K");
		assertEquals(true,temp1.matches());
	}
	
	@Test(timeout=10)
	public void star_macher_test1() {
		NFAPattern temp = NFAPattern.compile("a*");
		NFAMatcher temp1 = temp.matcher("a");
		assertEquals(true, temp1.matches());
	}
	
	@Test(timeout=10)
	public void star_macher_test2() {
		NFAPattern temp = NFAPattern.compile("a*");
		NFAMatcher temp1 = temp.matcher("aaaaaaa");
		assertEquals(true, temp1.matches());
	}
	
	@Test(timeout=10)
	public void star_macher_test3() {
		NFAPattern temp = NFAPattern.compile("a*");
		NFAMatcher temp1 = temp.matcher("aaaaab");
		assertEquals(false, temp1.matches());
	}
	
	@Test(timeout=10)
	public void star_macher_test4() {
		NFAPattern temp = NFAPattern.compile("a*b*");
		NFAMatcher temp1 = temp.matcher("b");
		assertEquals(true,temp1.matches());
	}
	
	@Test(timeout=10)
	public void star_macher_test5() {
		NFAPattern temp = NFAPattern.compile("a*b*");
		NFAMatcher temp1 = temp.matcher("aaaaaa");
		assertEquals(true,temp1.matches());
	}
	
	@Test(timeout=10)
	public void star_macher_test6() {
		NFAPattern temp = NFAPattern.compile("a*b*");
		NFAMatcher temp1 = temp.matcher("");
		assertEquals(true,temp1.matches());
	}
	
	@Test(timeout=10)
	public void star_macher_test7() {
		NFAPattern temp = NFAPattern.compile("a*b*");
		NFAMatcher temp1 = temp.matcher("aaaaaaaaaaaaaabbbbbbb");
		assertEquals(true,temp1.matches());
	}
	
	@Test(timeout=10)
	public void reluctant_star_macher_test1() {
		NFAPattern temp = NFAPattern.compile("a*?");
		NFAMatcher temp1 = temp.matcher("a");
		assertEquals(true, temp1.matches());
	}
	
	@Test(timeout=10)
	public void reluctant_star_macher_test2() {
		NFAPattern temp = NFAPattern.compile("a*?");
		NFAMatcher temp1 = temp.matcher("aaaaaaa");
		assertEquals(true, temp1.matches());
	}
	
	@Test(timeout=10)
	public void reluctant_star_macher_test3() {
		NFAPattern temp = NFAPattern.compile("a*?");
		NFAMatcher temp1 = temp.matcher("aaaaab");
		assertEquals(false, temp1.matches());
	}
	
	@Test(timeout=10)
	public void reluctant_star_macher_test4() {
		NFAPattern temp = NFAPattern.compile("a*?b*?");
		NFAMatcher temp1 = temp.matcher("b");
		assertEquals(true,temp1.matches());
	}
	
	@Test(timeout=10)
	public void reluctant_star_macher_test5() {
		NFAPattern temp = NFAPattern.compile("a*?b*?");
		NFAMatcher temp1 = temp.matcher("aaaaaa");
		assertEquals(true,temp1.matches());
	}
	
	@Test(timeout=10)
	public void reluctant_star_macher_test6() {
		NFAPattern temp = NFAPattern.compile("a*?b*?");
		NFAMatcher temp1 = temp.matcher("");
		assertEquals(true,temp1.matches());
	}
	
	@Test(timeout=10)
	public void reluctant_star_macher_test7() {
		NFAPattern temp = NFAPattern.compile("a*?b*?");
		NFAMatcher temp1 = temp.matcher("aaaaaaaaaaaaaabbbbbbb");
		assertEquals(true,temp1.matches());
	}
	
	@Test(timeout=1000)
	public void hard_macher_test1() {
		NFAPattern temp = NFAPattern.compile("(a|b)*");
		NFAMatcher temp1 = temp.matcher("aaabbbbbbbbbbbbbbbbbbbbbb");
		assertEquals(true, temp1.matches());
	}
	
	@Test(timeout=1000)
	public void hard_macher_test2() {
		NFAPattern temp = NFAPattern.compile("(a*b*)*");
		NFAMatcher temp1 = temp.matcher("abb");
		assertEquals(true, temp1.matches());
	}
		
	@Test(timeout=1000)
	public void hard_macher_test3() {
		//Pattern s = Pattern.compile(".");
		//assertTrue(s.matcher("").matches());
		NFAPattern temp = NFAPattern.compile("(X*)(((aa|b)*)*)|.*(A)*(ASDASD(ASDASD))|(a*?|(X*)(((aa|b)*)*)|.*(A)*(ASDASD(ASDASD)))((((((((as)*)*)*)*)*)*)*)*");
		NFAMatcher temp1 = temp.matcher("XXX");
		assertEquals(true, temp1.matches());
	}
	
	@Test(timeout=1000)
	public void hard_macher_test4() {
		//Pattern s = Pattern.compile(".");
		//assertTrue(s.matcher("").matches());
		NFAPattern temp = NFAPattern.compile("(ASDASD(ASDASD))");
		NFAMatcher temp1 = temp.matcher("ASDASDASDASD");
		assertEquals(true, temp1.matches());
	}
	
	@Test(timeout=1000)
	public void hard_macher_test5() {
		// The problem is because the A is after the A* is breaks it
		NFAPattern temp = NFAPattern.compile("A*ASDASDASDASD.*");
		NFAMatcher temp1 = temp.matcher("ASDASDASDASD");
		assertEquals(true, temp1.matches());
	}
}
