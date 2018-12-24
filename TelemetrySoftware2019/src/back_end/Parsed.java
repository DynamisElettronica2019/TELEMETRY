package back_end;

public class Parsed {
	
	private String[] parsedInfo;
	
	public Parsed(String strToParse) {
		parsedInfo = strToParse.split(";");
	}
}
