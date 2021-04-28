package egtry.hello;

public class tester {

	public static void main(String[] args) {
		try {
			new egtry.hello.Hello(System.in);
			Hello.words();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
