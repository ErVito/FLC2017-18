import java.util.ArrayList;
import java.util.HashMap;

public abstract class Template {
	// There isn't any advantage to use an HashSet (it's backed by an HashMap)
	protected final HashMap<String, Rule> rules;
	
	public Template() {
		this.rules = new HashMap<String, Rule>();
	}

	protected void setRules(ArrayList<Rule> rules) {
		for(Rule rule: rules)
			this.rules.put(rule.getName(), rule);
	}

	public abstract String translate(String input);
}