package simpleParser;

public class ParserDriver {

	public static void main(String[] args) {
		new SILParser(System.in);
		try {
			System.out.println("Enter your phrase: ");
			SILParser.command();
        	System.out.println("input is parsed successfully");
		} catch (Throwable e) {
			System.out.println("Unfortunately parsing is unsuccessful");
			e.printStackTrace();
      }
	}
}
