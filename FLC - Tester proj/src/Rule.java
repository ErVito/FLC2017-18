public abstract class Rule {
	private final String name;

	public Rule(String name) {
		this.name = name;
	}

	abstract public String enter(String token);
	abstract public String exit(String token);
	
	public String getName() {
		return this.name;
	}
}