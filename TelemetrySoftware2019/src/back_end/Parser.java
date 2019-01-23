package back_end;

import configuration.ConfReader;
import exceptions.InvalidReadingException;
import exceptions.InvalidUpdateException;

public class Parser {

	private Data data;
	private int lenData;
	private char recogniserData;
	private int lenState;
	private char recogniserState;
	private int lenDebug;
	private char recogniserDebug;
	private char recogniserDcuErr;
	private char recogniserAckComm;

	/*
	 * Set class attributes through ConfReader
	 */
	public Parser(Data data) {
		this.data = data;
		lenData = (int)ConfReader.getPacketLen("channels");
		recogniserData = ConfReader.getRecogniser("channels");
		lenState = (int)ConfReader.getPacketLen("states");
		recogniserState = ConfReader.getRecogniser("states");
		lenDebug = (int)ConfReader.getPacketLen("debug");
		recogniserDebug = ConfReader.getRecogniser("debug");
		
		//TODO SETTARE QUI I NUOVI PARAMETRI DA CONF READER
	}

	/*
	 * Check input string validity and create corresponding Parsed object. Throw
	 * exception if the input string is not valid.
	 */
	public void parseString(String stringToParse) throws InvalidReadingException, InvalidUpdateException {
		if (stringToParse.charAt(0) == recogniserData && stringToParse.charAt(1) == ';') {
			if (stringToParse.length() != lenData) {
				throw new InvalidReadingException("Message lenght Error");
			} else {
				data.update(new ParsedData(stringToParse.substring(2)));
			}
		} else if (stringToParse.charAt(0) == recogniserState && stringToParse.charAt(1) == ';') {
			if (stringToParse.length() != lenState) {
				throw new InvalidReadingException("Message lenght Error");
			} else {
				data.update(new ParsedState(stringToParse.substring(2)));
			}
		} else if (stringToParse.charAt(0) == recogniserDebug && stringToParse.charAt(1) == ';') {
			if (stringToParse.length() != lenDebug) {
				throw new InvalidReadingException("Message lenght Error");
			} else {
				data.update(new ParsedDebug(stringToParse.substring(2)));
			}
			
		//TODO AGGIUNGERE QUI I NUOVI CASI PER LA GESTIONE DI COMANDI ED ERRORI
			
		} else {
			throw new InvalidReadingException("First letter reading Error");
		}
		
	}
	
}
