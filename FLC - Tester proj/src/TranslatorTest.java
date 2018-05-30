import java.io.File;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import generated.SimplifiedLatexLexer;
import generated.SimplifiedLatexParser;

public class TranslatorTest {
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
		System.out.println("=== Translator test started ===");
		for(File test: tests) {
			ntest++;
			try {
				SimplifiedLatexLexer lexer = new SimplifiedLatexLexer(new ANTLRFileStream(test.getPath()));
				System.out.println(">> Test "+ntest);
				tokens = new CommonTokenStream(lexer);
			
				SimplifiedLatexParser parser = new SimplifiedLatexParser(tokens);
				ParseTree			  tree = parser.axiom();
				ParseTreeWalker		  walker = new ParseTreeWalker();
			
				walker.walk(new SimplifiedLatexTranslator(), tree);
			} catch (Exception e) {
				System.out.print("Error: cannot open '"+test+"'");
				e.printStackTrace();
			}
			System.out.print("\n\n");
		}
	}
}
