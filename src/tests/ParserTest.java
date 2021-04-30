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
	void testWrongString() {
		String initialString = "if ttt then a := 2 else a := 4";
	    SILParser.ReInit(new ByteArrayInputStream(initialString.getBytes()));
	    assertThrows(simpleParser.TokenMgrError.class, () -> {
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
	void testFile3() throws FileNotFoundException {
		FileReader fileReader = new FileReader("src/resources/test3.txt");
    	SILParser.ReInit(fileReader);
	    assertDoesNotThrow(() -> {
	        parser.Input();
	      });
	      
	}

	@Test
	void testWrongFile() throws FileNotFoundException {
		FileReader fileReader = new FileReader("src/resources/wrongTest.txt");
    	SILParser.ReInit(fileReader);
	    assertThrows(Throwable.class, () -> {
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
	
    @Test
	void testWhile() {
	    assertAll("Test while",
	    		() -> assertDoesNotThrow(()-> {
	            	String initialString = "while (a == 1) do skip";
	            	SILParser.ReInit(new ByteArrayInputStream(initialString.getBytes()));
	            	parser.Input();
	            }),
	            () -> assertThrows(simpleParser.ParseException.class, ()-> {
	            	String initialString = "while a == 1 do skip";
	            	SILParser.ReInit(new ByteArrayInputStream(initialString.getBytes()));
	            	parser.Input();
	            }),
	            () -> assertThrows(simpleParser.ParseException.class, ()-> {
	            	String initialString = "while (a == 1) do skip;";
	            	SILParser.ReInit(new ByteArrayInputStream(initialString.getBytes()));
	            	parser.Input();
	            })
	    );
    }
    
    @Test
	void testIf() {
	    assertAll("Test if",
	    		() -> assertDoesNotThrow(()-> {
	            	String initialString = "if (a == 1) then a := (a + 1) else skip";
	            	SILParser.ReInit(new ByteArrayInputStream(initialString.getBytes()));
	            	parser.Input();
	            }),
	            () -> assertThrows(simpleParser.ParseException.class, ()-> {
	            	String initialString = "if (a == 1) then skip";
	            	SILParser.ReInit(new ByteArrayInputStream(initialString.getBytes()));
	            	parser.Input();
	            }),
	            () -> assertThrows(simpleParser.ParseException.class, ()-> {
	            	String initialString = "if (a == 1) then skip else;";
	            	SILParser.ReInit(new ByteArrayInputStream(initialString.getBytes()));
	            	parser.Input();
	            })
	    );
    }
    
    @Test
	void testCmd() {
	    assertAll("Test command",
	    		() -> assertDoesNotThrow(()-> {
	            	String initialString = "a := 2; if (a == 1) then a := (a + 1); b := b else skip; c := 2";
	            	SILParser.ReInit(new ByteArrayInputStream(initialString.getBytes()));
	            	parser.Input();
	            }),
	            () -> assertThrows(simpleParser.ParseException.class, ()-> {
	            	String initialString = "skip ; skip;";
	            	SILParser.ReInit(new ByteArrayInputStream(initialString.getBytes()));
	            	parser.Input();
	            }),
	            () -> assertThrows(simpleParser.ParseException.class, ()-> {
	            	String initialString = ";";
	            	SILParser.ReInit(new ByteArrayInputStream(initialString.getBytes()));
	            	parser.Input();
	            })
	    );
    }
    
    @Test
	void testBool() {
	    assertAll("Test boolean expression",
	    		() -> assertDoesNotThrow(()-> {
	            	String initialString = "a := 2; if (tt ^ !(a==2)) then if (tt ^ ff) then skip else skip else skip";
	            	SILParser.ReInit(new ByteArrayInputStream(initialString.getBytes()));
	            	parser.Input();
	            }),
	            () -> assertThrows(simpleParser.ParseException.class, ()-> {
	            	String initialString = "if (tt) then skip else skip";
	            	SILParser.ReInit(new ByteArrayInputStream(initialString.getBytes()));
	            	parser.Input();
	            }),
	            () -> assertThrows(simpleParser.ParseException.class, ()-> {
	            	String initialString = "if (!tt) then skip else skip";
	            	SILParser.ReInit(new ByteArrayInputStream(initialString.getBytes()));
	            	parser.Input();
	            })
	    );
    }
	
    @Test
	void testArth() {
	    assertAll("Test arithmetic",
	    		() -> assertDoesNotThrow(()-> {
	            	String initialString = "a := (a + 2)";
	            	SILParser.ReInit(new ByteArrayInputStream(initialString.getBytes()));
	            	parser.Input();
	            }),
	            () -> assertThrows(RuntimeException.class, ()-> {
	            	String initialString = "a := (0 - 1)";
	            	SILParser.ReInit(new ByteArrayInputStream(initialString.getBytes()));
	            	parser.Input();
	            }),
	            () -> assertThrows(simpleParser.ParseException.class, ()-> {
	            	String initialString = "a := -1";
	            	SILParser.ReInit(new ByteArrayInputStream(initialString.getBytes()));
	            	parser.Input();
	            })
	    );
    }
    
    @Test
	void testAssign() {
	    assertAll("Test assignment",
	    		() -> assertDoesNotThrow(()-> {
	            	String initialString = "a := 2";
	            	SILParser.ReInit(new ByteArrayInputStream(initialString.getBytes()));
	            	parser.Input();
	            }),
	            () -> assertThrows(simpleParser.TokenMgrError.class, ()-> {
	            	String initialString = "a = 3";
	            	SILParser.ReInit(new ByteArrayInputStream(initialString.getBytes()));
	            	parser.Input();
	            }),
	            () -> assertThrows(simpleParser.ParseException.class, ()-> {
	            	String initialString = "a == 3";
	            	SILParser.ReInit(new ByteArrayInputStream(initialString.getBytes()));
	            	parser.Input();
	            })
	    );
    }
}
