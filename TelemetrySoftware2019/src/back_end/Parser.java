package back_end;

import exceptions.InvalidReadingException;

public class Parser {

	private int lenData;
	private char recogniserData;
	private int lenState;
	private char recogniserState;
	private int lenDebug;
	private char recogniserDebug;
	private int currElement;

	/*
	 * Set class attributes through ConfReader
	 */
	public Parser() {
		// TODO
		currElement = 0;
	}

	/*
	 * Check input string validity and create corresponding Parsed object. Throw
	 * exception if the input string is not valid.
	 */
	Parsed parseString(String stringToParse) throws InvalidReadingException {
		char recogniser;
		Parsed parse = new Parsed();
		recogniser = stringToParse.charAt(0);
		if (recogniser == recogniserData) {
			if (stringToParse.length() != lenData) {
				throw new InvalidReadingException("Message lenght Error");
			} else {
				int startIndex = 0;
				for (int i = 0; i < stringToParse.length(); i++) {
					if (stringToParse.charAt(i) == ';') {
						String strToAdd = stringToParse.substring(startIndex, i - 1);
						startIndex = i + 1;
						parse.parsedInfo[currElement] = strToAdd;
						currElement++;
					}
				}
			}
		} else if (recogniser == recogniserState) {
			if (stringToParse.length() != lenState) {
				throw new InvalidReadingException("Message lenght Error");
			} else {
				int startIndex = 0;
				for (int i = 0; i < stringToParse.length(); i++) {
					if (stringToParse.charAt(i) == ';') {
						String strToAdd = stringToParse.substring(startIndex, i - 1);
						startIndex = i + 1;
						parse.parsedInfo[currElement] = strToAdd;
						currElement++;
					}
				}

			}
		} else if (recogniser == recogniserDebug) {
			if (stringToParse.length() != lenDebug) {
				throw new InvalidReadingException("Message lenght Error");
			} else {
				int startIndex = 0;
				for (int i = 0; i < stringToParse.length(); i++) {
					if (stringToParse.charAt(i) == ';') {
						String strToAdd = stringToParse.substring(startIndex, i - 1);
						startIndex = i + 1;
						parse.parsedInfo[currElement] = strToAdd;
						currElement++;
					}
				}
			}
		} else {
			throw new InvalidReadingException("First letter reading Error");
		}
		return parse;
	}
}
