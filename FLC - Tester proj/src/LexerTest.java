import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.antlr.v4.runtime.*;

import generated.*;

public class LexerTest {
	public static void main(String[] args) {
	  	int 				  i, labelPadding=0, ntest=0, tokenPadding=0;
	  	Map<Integer, String>  labels;
		String tester		= "/home/marco/Desktop/Progetto LFC/ProgettoLFCTester";
		String tokens		= tester+"/src/generated/SimplifiedLatexLexer.tokens";
		String testsPath	= tester+"/tests";
	  	File   testsDir		= new File(testsPath);
		File[] tests 		= testsDir.listFiles();

		if (tests == null) {
			System.out.println("Error: cannot open '"+testsPath+"'");
			return;
		}
		String				  output;
	  	Token 				  tk;
	  	TokenLabeler 		  labeler = TokenLabeler.instance;
	  	
	  	try {
	  		labels = labeler.labelsMap(tokens);
	  	}
	  	catch (IOException exception) {
			System.out.println("Error: cannot open '"+tokens+"'");
	  		return;
	  	}
		System.out.println("=== Lexer test started ===");
		for(File test: tests) {
			ntest++;
			try {
				SimplifiedLatexLexer lexer = new SimplifiedLatexLexer(new ANTLRFileStream(test.getPath()));
				System.out.println(">> Test "+ntest);
				i = 0;
				while ((tk = lexer.nextToken()).getType() != SimplifiedLatexLexer.EOF) {
				
					// Formats the output
					if (labels.get(tk.getType()).length() > labelPadding)
						labelPadding = labels.get(tk.getType()).length();
					output = "%" + labelPadding + "s" + ":%";
					if (tk.getText().length() > tokenPadding)
						tokenPadding = tk.getText().length() + 4;
					output += tokenPadding + "s\n";
					System.out.format("%3s:[%3s,%3s] ", ++i, tk.getLine(), tk.getCharPositionInLine());
					System.out.format(output, labels.get(tk.getType()), getText(tk));
				}
			} catch (Exception e) {
				System.out.println("Error: cannot open '"+test+"'");
				e.printStackTrace();
			}
		}
	}
	public static String getText(Token tk) {
		String text = tk.getText();
		
		if (text.equals("\n"))
			return "\\n";
		return text;
	}
}