import java.util.HashMap;

import generated.SimplifiedLatexBaseListener;
import generated.SimplifiedLatexParser.*;

public class SimplifiedLatexTranslator extends SimplifiedLatexBaseListener {
	private boolean 				  applyCustomRules, trim;
	private HashMap<String, Template> availTemplates;
	private Template				  customTemplate = null;

	public SimplifiedLatexTranslator() {
		Template ladox = new LadoxTemplate(false);
		
		this.availTemplates = new HashMap<>();
		this.availTemplates.put("ladox", ladox);
	}
	@Override
	public void enterAuthor(AuthorContext ctx) {
		this.trim = true;
		System.out.print("\\author{");
	}

	@Override
	public void exitAuthor(AuthorContext ctx) {
		System.out.println("}");
	}

	@Override
	public void enterComment(CommentContext ctx) {
		this.trim = false;
		System.out.print("%");
	}

	@Override
	public void exitContent(ContentContext ctx) {
		System.out.print("\n\\end{document}");
	}

	@Override
	public void enterTemplate(TemplateContext ctx) {
		System.out.print("\\documentclass{");
	}

	@Override
	public void enterCustom(CustomContext ctx) {
		String templateName = ctx.getText();

		if (this.availTemplates.containsKey(templateName)) {
			this.customTemplate = this.availTemplates.get(templateName);
			System.out.print(templateName);
		}
	}

	@Override
	public void exitTemplate(TemplateContext ctx) {
		System.out.println("}\n\\begin{document}");
	}

	@Override
	public void enterCustomRule(CustomRuleContext ctx) {
		if (this.customTemplate != null) {
			this.applyCustomRules = true;
			this.trim			  = true;
		}
	}
	@Override
	public void enterTitle(TitleContext ctx) {
		this.trim = true;
		System.out.print("\\title{");
	}

	@Override
	public void exitTitle(TitleContext ctx) {
		System.out.println("}");
		System.out.println("\\maketitle");
	}

	@Override
	public void enterArticle(ArticleContext ctx) {
		System.out.print("article");
	}

	@Override
	public void enterLetter(LetterContext ctx) {
		System.out.print("letter");
	}

	@Override
	public void enterLine(LineContext ctx) {
		String line = ctx.getText();
		
		if (this.trim)
			line = line.trim();
		if (this.applyCustomRules) {
			line = this.customTemplate.translate(line);
			this.applyCustomRules = false;
		}
		System.out.print(line);
	}

	@Override
	public void enterCv(CvContext ctx) {
		System.out.print("cv");
	}

	@Override
	public void enterThesis(ThesisContext ctx) {
		System.out.print("thesis");
	}
}