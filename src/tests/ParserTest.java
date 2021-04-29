package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import simpleParser.ParseException;
import simpleParser.SILParser;

class ParserTest {

	static SILParser parser;
	
	@BeforeAll
	static void SetUp() {
		parser = new SILParser(System.in);
	}
	
	@Test
	void testString() throws FileNotFoundException {
		String initialString = "if tt then a := 2 else a := 4";
	    SILParser.ReInit(new ByteArrayInputStream(initialString.getBytes()));
	    Assertions.assertDoesNotThrow(() -> {
	        parser.Input();
	      });
	}
	
	@Test
	void testFile() throws FileNotFoundException {
		FileReader fileReader = new FileReader("src/resources/test1.txt");
    	SILParser.ReInit(fileReader);
	    Assertions.assertDoesNotThrow(() -> {
	        parser.Input();
	      });
	      
	}

}
