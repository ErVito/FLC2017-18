public class SignRule extends Rule {
	private int 		 errorOccurred;
	private final String tagPrefix;

	public SignRule(boolean isDoxygen) {
		super("sign");
		
		if (isDoxygen)
			this.tagPrefix = "\\textbackslash ";
		else
			this.tagPrefix = "@";
	}

	@Override
	public String enter(String token) {
		boolean  translationEnded = false;
		String 	 line = token;
		String 	 parameter, parametersTags = "", returnTag;
		String[] tokens;

		this.errorOccurred = 0;
		tokens = strtok(token, ' ');
		if (tokens == null)
			this.errorOccurred = 1;
		else {
			if (tokens[0].contains("("))
				this.errorOccurred = 2;
		}
		if (this.errorOccurred > 0)
			return line;
		returnTag = tagPrefix+"return "+tokens[0]+" \\\\\n";
		token = tokens[1];

		// Skip the name of the function
		tokens = strtok(token, '(');
		if (tokens == null) {
			this.errorOccurred = 3;

			return line;
		}
		token = tokens[1];
		
		// There are some parameters
		if (!token.equals(")"))
			do {
				tokens = strtok(token, ',');
				if (tokens != null) {
					parameter = tokens[0];
					token	  = tokens[1];
					parametersTags += translateParameter(parameter);
				} else {

					// Remains only one parameter
					tokens = strtok(token, ')');
					parameter = tokens[0];
					token	  = tokens[1];
					if (token.equals("")) {
						parametersTags += translateParameter(parameter);
						translationEnded = true;
					} else
						this.errorOccurred = 4;
				}
			} while(!translationEnded && this.errorOccurred == 0);
		if (this.errorOccurred > 0)
			return line;
		else
			return parametersTags+returnTag+line;
	}

	@Override
	public String exit(String token) {
		return "";
	}
	
	private String[] strtok(String input, char delimiter) {
		int		 tail = input.indexOf(delimiter)+1;
		String[] ret  = null;
		
		if (tail > 0) {
			ret = new String[2];
			ret[0] = input.substring(0, tail-1);
			ret[1] = input.substring(tail);	
		}
		return ret;
	}

	private String translateParameter(String parameter) {
		String[] parameters;

		parameter  = parameter.trim();
		parameters = strtok(parameter, ' ');
		if (parameters == null) {
			this.errorOccurred = 5;
			
			return "";
		}
		parameter  = parameters[1];
		parameters = strtok(parameter, ' ');
		if (parameters != null) {
			this.errorOccurred = 6;
			
			return "";
		}
		return tagPrefix + "param " + parameter + " \\\\\n";
	}
	
	public int getError() {
		return this.errorOccurred;
	}
}
