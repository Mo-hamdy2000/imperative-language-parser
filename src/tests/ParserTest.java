package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import simpleParser.SILParser;

class ParserTest {

	static SILParser parser;
	
	@BeforeAll
	static void SetUp() {
		parser = new SILParser(System.in);
	}
	
	@Test
	void testString() {
		String initialString = "if tt then a := 2 else a := 4";
	    SILParser.ReInit(new ByteArrayInputStream(initialString.getBytes()));
	    assertDoesNotThrow(() -> {
	        parser.Input();
	      });
	}
	
	@Test
	void testFile1() throws FileNotFoundException {
		FileReader fileReader = new FileReader("src/resources/test1.txt");
    	SILParser.ReInit(fileReader);
	    assertDoesNotThrow(() -> {
	        parser.Input();
	      });
	      
	}
	
	@Test
	void testFile2() throws FileNotFoundException {
		FileReader fileReader = new FileReader("src/resources/test2.txt");
    	SILParser.ReInit(fileReader);
	    assertDoesNotThrow(() -> {
	        parser.Input();
	      });
	      
	}

	@Test
	void testStringCommandSemicolon() {
		String initialString = "b := 7; a := ((2+6) - (12-b))";
	    SILParser.ReInit(new ByteArrayInputStream(initialString.getBytes()));
	    assertDoesNotThrow(() -> {
	        parser.Input();
	      });
	}
	
	@Test
	void testInvalidVariables() {
	    assertAll("Test invalid variables",
	    		() -> assertDoesNotThrow(()-> {
	            	String initialString = "abcede := 1";
	            	SILParser.ReInit(new ByteArrayInputStream(initialString.getBytes()));
	            	parser.Input();
	            }),
	            () -> assertThrows(simpleParser.TokenMgrError.class, ()-> {
	            	String initialString = "gadbb := 1";
	            	SILParser.ReInit(new ByteArrayInputStream(initialString.getBytes()));
	            	parser.Input();
	            }),
	            () -> assertThrows(simpleParser.TokenMgrError.class, ()-> {
	            	String initialString = "g := 1";
	            	SILParser.ReInit(new ByteArrayInputStream(initialString.getBytes()));
	            	parser.Input();
	            }),
	            () -> assertThrows(simpleParser.TokenMgrError.class, ()-> {
	            	String initialString = "abcedes := 1";
	            	SILParser.ReInit(new ByteArrayInputStream(initialString.getBytes()));
	            	parser.Input();
	            })
	    );
	}
	    @Test
		void testInvalidDigits() {
		    assertAll("Test invalid digits",
		    		() -> assertDoesNotThrow(()-> {
		            	String initialString = "a := 1";
		            	SILParser.ReInit(new ByteArrayInputStream(initialString.getBytes()));
		            	parser.Input();
		            }),
		            () -> assertThrows(simpleParser.ParseException.class, ()-> {
		            	String initialString = "a := -1";
		            	SILParser.ReInit(new ByteArrayInputStream(initialString.getBytes()));
		            	parser.Input();
		            }),
		            () -> assertThrows(simpleParser.ParseException.class, ()-> {
		            	String initialString = "a := -20";
		            	SILParser.ReInit(new ByteArrayInputStream(initialString.getBytes()));
		            	parser.Input();
		            })
		    );
	}
	
	
}
