import java.util.ArrayList;

public class LadoxTemplate extends Template {

	public LadoxTemplate(boolean isDoxygen) {
		ArrayList<Rule> rules = new ArrayList<>();
		
		rules.add(new SignRule(isDoxygen));
		this.setRules(rules);
	}

	@Override
	public String translate(String input) {
		int		firstSpace = input.indexOf(' ');
		Rule	rule;
		String 	ruleName = input.substring(0, firstSpace);

		if (this.rules.containsKey(ruleName)) {
			String	token = input.substring(firstSpace+1);
			String	translation = "";
			
			rule = this.rules.get(ruleName);
			translation += rule.enter(token);
			translation += rule.exit(token);
			
			return translation;
		}
		return input;
	}
}
