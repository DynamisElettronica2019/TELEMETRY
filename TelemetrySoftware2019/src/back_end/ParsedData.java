package back_end;

import java.util.ArrayList;

import exceptions.InvalidUpdateException;

public class ParsedData extends Parsed {
	
	/*
	 * Create parsedInfo and set strings to parsed and computed informations
	 */
	public ParsedData(String strToParse) {
		super(strToParse);
	}
	
	/*
	 * Compute complex data and add them to parsedInfo.
	 * This part is not configurable by configuration file
	 */
	private void compute(String strToParse) {
		//TODO
	}
	
	/*
	 * Convert String ArrayList in double ArrayList
	 * On error do not update (expected sintax: "123.45" "456.00")
	 */
	public ArrayList<Double> convert() throws InvalidUpdateException{
		try {
			ArrayList<Double> temp = new ArrayList<>();
			for(int i=1;i<parsedInfo.length;i++) temp.add(Double.parseDouble(parsedInfo[i]));
			return temp;
		} catch (NumberFormatException e) {
			throw new InvalidUpdateException("Channels -> Cannot convert received strings to double");
		}
	}

}
