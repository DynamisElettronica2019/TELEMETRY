package back_end;

import configuration.ConfReader;
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
		lenData = (int)ConfReader.getPacketLen("channels");
		recogniserData = ConfReader.getRecogniser("channels");
		lenState = (int)ConfReader.getPacketLen("states");
		recogniserState = ConfReader.getRecogniser("states");
		lenDebug = (int)ConfReader.getPacketLen("debug");
		recogniserDebug = ConfReader.getRecogniser("debug");
	}

	/*
	 * Check input string validity and create corresponding Parsed object. Throw
	 * exception if the input string is not valid.
	 */
	Parsed parseString(String stringToParse) throws InvalidReadingException {
		char recogniser;
		recogniser = stringToParse.charAt(0);
		if (recogniser == recogniserData) {
			if (stringToParse.length() != lenData) {
				throw new InvalidReadingException("Message lenght Error");
			} else {
				return new ParsedData(stringToParse);
			}
		} else if (recogniser == recogniserState) {
			if (stringToParse.length() != lenState) {
				throw new InvalidReadingException("Message lenght Error");
			} else {
				return new ParsedState(stringToParse);

			}
		} else if (recogniser == recogniserDebug) {
			if (stringToParse.length() != lenDebug) {
				throw new InvalidReadingException("Message lenght Error");
			} else {
				return new ParsedDebug(stringToParse);
			}
		} else {
			throw new InvalidReadingException("First letter reading Error");
		}
	}
}
