import java.io.File;

import org.antlr.v4.runtime.*;

import generated.*;

public class ParserTest {
	public static void main(String[] args) {
		CommonTokenStream tokens;
		int	   ntest = 0;
		String tester	= "/home/marco/Desktop/FLC project/FLC - Tester proj";
	  	String testsPath= tester+"/tests";
	  	File   testsDir	= new File(testsPath);
		File[] tests 	= testsDir.listFiles();
		
		if (tests == null) {
			System.out.println("Error: cannot open '"+testsPath+"'");
			return;
		}
		System.out.println("=== Parser test started ===");
		for(File test: tests) {
			ntest++;
			try {
				SimplifiedLatexLexer lexer = new SimplifiedLatexLexer(new ANTLRFileStream(test.getPath()));
				System.out.println(">> Test "+ntest); 
				tokens = new CommonTokenStream(lexer);
			
				SimplifiedLatexParser parser = new SimplifiedLatexParser(tokens);

				parser.axiom();

			} catch (Exception e) {
				System.out.println("Error: cannot open '"+test+"'");
				e.printStackTrace();
			}
		}
	}
}
