package back_end;

import java.util.ArrayList;

import exceptions.InvalidUpdateException;

public class ParsedState extends Parsed {
	
	/*
	 * Create parsedInfo and set string to parsed information
	 */
	public ParsedState(String strToParse) {
		super(strToParse);
	}
	
	/*
	 * Convert String ArrayList in boolean ArrayList
	 * On error do not update (expected sintax: "0" "1")
	 */
	public ArrayList<Boolean> convert() throws InvalidUpdateException{
		ArrayList<Boolean> temp = new ArrayList<>();
		for(int i=0;i<parsedInfo.length;i++){
			if(parsedInfo[i].equals("0")||parsedInfo[i].equals("1")) temp.add("1".equals(parsedInfo[i]));
			else throw new InvalidUpdateException("States -> Cannot convert received strings to boolean");
		}
		return temp;
	}

}
