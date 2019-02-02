package back_end;

import java.util.ArrayList;

import exceptions.InvalidUpdateException;

public class ParsedDebug extends Parsed {
	
	/*
	 * Create parsedInfo and set string to parsed information
	 */
	public ParsedDebug(String strToParse) {
		super(strToParse);
	}
	
	/*
	 * Convert String ArrayList in double ArrayList
	 * On error do not update (expected sintax: "123.45" "456.00")
	 */
	public ArrayList<Double> convert() throws InvalidUpdateException{
		try {
			ArrayList<Double> temp = new ArrayList<>();
			for(int i=0;i<parsedInfo.length;i++) temp.add(Double.parseDouble(parsedInfo[i]));
			return temp;
		} catch (NumberFormatException e) {
			throw new InvalidUpdateException("Debug -> Cannot convert received strings to double");
		}
	}

}
