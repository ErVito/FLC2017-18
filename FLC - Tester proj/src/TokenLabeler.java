import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

public class TokenLabeler {
	// TokenLabeler is a singleton
	public static final TokenLabeler instance = new TokenLabeler();
	
	private TokenLabeler() {
	}
	public Map<Integer, String> labelsMap(String tokensFile) throws IOException {
		Map<Integer, String> map = null;
		
		try {
			BufferedReader 	file = new BufferedReader(new FileReader(tokensFile));
			int				equal, token;
			String			label, line;
			
			map = new HashMap<Integer, String>(0);
			do {
				line = file.readLine();
				if (line != null && ! line.startsWith("T_")) {
					equal = line.indexOf('=');
					label = line.substring(0, equal);
					token = Integer.parseInt(line.substring(equal+1));
					map.put(token, label);
				}
			} while (line != null);
			try {
				file.close();
			}
			catch (IOException exception) {
				System.out.println("Error: file can't be closed.");
				throw(exception);
			}
		}
		catch (FileNotFoundException exception) {
			System.out.println("Error: can't open '"+tokensFile+"'.");
		}

		return map;
	}
}
