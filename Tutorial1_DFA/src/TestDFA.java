import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;


public class TestDFA {

	@Test
	public void test1() {
		DFASimulator dfas = new DFASimulator();
		try {
			dfas.setup("test1");	
			assertEquals("reject", dfas.simulateDFA(""));
		} catch (IOException | IllegalTokenException
				| IllegalDFAFormatException | IllegalInputException  e) {
			// TODO Auto-generated catch block
			assertFalse("Expected reject but found : " + e, true);
		}
		
	}
	
	@Test
	public void test2() {
		DFASimulator dfas = new DFASimulator();
		try {
			dfas.setup("test2");	
			assertEquals("accept", dfas.simulateDFA(""));
		} catch (IOException | IllegalTokenException
				| IllegalDFAFormatException | IllegalInputException  e) {
			// TODO Auto-generated catch block
			assertFalse("Expected accept but found : " + e, true);
		}
	}
	
	@Test
	public void test3() {
		
		DFASimulator dfas = new DFASimulator();
		try {
			dfas.setup("test3");	
			assertEquals("accept", dfas.simulateDFA("bbaabab"));
		} catch (IOException | IllegalTokenException
				| IllegalDFAFormatException | IllegalInputException  e) {
			// TODO Auto-generated catch block
			assertFalse("Expected accept but found : " + e, true);
		}
	}
	
	@Test
	public void test4() {
		;
		DFASimulator dfas = new DFASimulator();
		try {
			dfas.setup("test4");	
			dfas.simulateDFA("100101");
		} catch (IOException | IllegalTokenException
				 | IllegalInputException  e) {
			// TODO Auto-generated catch block
			assertFalse("Expected IllegalDFAFormatException but found : " + e, true);
		}catch (IllegalDFAFormatException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void test5() {
		DFASimulator dfas = new DFASimulator();
		try {
			dfas.setup("test5");	
			dfas.simulateDFA("100101");
			assertFalse("Expected IllegalDFAFormatException", true);
		} catch (IOException | IllegalTokenException
				 | IllegalInputException  e) {
			// TODO Auto-generated catch block
			assertFalse("Expected IllegalDFAFormatException but found : " + e, true);
		}catch (IllegalDFAFormatException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void test6() {
		DFASimulator dfas = new DFASimulator();
		try {
			dfas.setup("test6");	
			dfas.simulateDFA("123");
			assertFalse("Expected IllegalInputException", true);
		} catch (IOException | IllegalTokenException
				| IllegalDFAFormatException    e) {
			// TODO Auto-generated catch block
			assertFalse("Expected IllegalInputException but found : " + e, true);
		} catch (IllegalInputException e) {
			assertTrue(true);
		}
		
	}
	
	@Test
	public void test7() {
		DFASimulator dfas = new DFASimulator();
		try {
			dfas.setup("test7");	
			dfas.simulateDFA("10010101");
		} catch (IOException | IllegalTokenException
				 | IllegalInputException  e) {
			// TODO Auto-generated catch block
			assertFalse("Expected IllegalDFAFormatException but found : " + e, true);
		}catch (IllegalDFAFormatException e) {
			assertTrue(true);
		}
	}

}
