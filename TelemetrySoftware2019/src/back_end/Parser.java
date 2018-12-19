package back_end;

import exceptions.InvalidReadingException;

public class Parser {
	
	private int lenData;
	private char recogniserData;
	private int lenState;
	private char recogniserState;
	private int lenDebug;
	private char recogniserDebug;
	
	/*
	 * Set class attributes through ConfReader
	 */
	public Parser() {
		// TODO
	}
	
	/*
	 * Check input string validity and create corresponding Parsed object.
	 * Throw exception if the input string is not valid.
	 */
	Parsed parseString(String stringToParse) throws InvalidReadingException {
		//TODO
	}
	
}
