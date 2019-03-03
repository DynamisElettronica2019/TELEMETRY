package back_end;

import configuration.ConfReader;
import exceptions.InvalidCodeException;
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
	private char recogniserLapAccMode;
	private char recogniserLapEndMode;
	private char recogniserLapIntType;
	private char recogniserLapLapType;
	private int lenLap;

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
		recogniserDcuErr = ConfReader.getRecogniser("error");
		recogniserAckComm = ConfReader.getRecogniser("command");
		recogniserLapAccMode = ConfReader.getLapTimerRecogniser("accMode");
		recogniserLapEndMode = ConfReader.getLapTimerRecogniser("endMode");
		recogniserLapIntType = ConfReader.getLapTimerRecogniser("intType");
		recogniserLapLapType = ConfReader.getLapTimerRecogniser("lapType");
		lenLap = (int)ConfReader.getPacketLen("lap");
	}

	/*
	 * Check input string validity and decide what to do. Throw
	 * exception if the input string is not valid.
	 */
	public void parseString(String stringToParse) throws InvalidReadingException, InvalidUpdateException {
		if (stringToParse.charAt(0) == recogniserData && stringToParse.charAt(1) == ';') {
			if (stringToParse.length() != lenData) {
				throw new InvalidReadingException("Message lenght Error ("+recogniserData+")");
			} else {
				data.update(new ParsedData(stringToParse.substring(2)));
			}
		} else if (stringToParse.charAt(0) == recogniserState && stringToParse.charAt(1) == ';') {
			if (stringToParse.length() != lenState) {
				throw new InvalidReadingException("Message lenght Error ("+recogniserState+")");
			} else {
				data.update(new ParsedState(stringToParse.substring(2)));
			}
		} else if (stringToParse.charAt(0) == recogniserDebug && stringToParse.charAt(1) == ';') {
			if (stringToParse.length() != lenDebug) {
				throw new InvalidReadingException("Message lenght Error ("+recogniserDebug+")");
			} else {
				data.update(new ParsedDebug(stringToParse.substring(2)));
			}
		} else if (stringToParse.charAt(0) == recogniserDcuErr) {
			if (stringToParse.length() != 2) {
				throw new InvalidReadingException("Message lenght Error ("+recogniserDcuErr+")");
			} else {
				try {
					data.setDcuError(stringToParse.charAt(1));
				} catch (InvalidCodeException e) {
					e.log();
				}
			}
		//Modify the following code if some ack accept parameters
		} else if (stringToParse.charAt(0) == recogniserAckComm) {
			if (stringToParse.charAt(1)!='R') {
				if (stringToParse.length() != 2) {
					throw new InvalidReadingException("Message lenght Error ("+recogniserAckComm+")");
				} else {
					try {
						data.delTimer(stringToParse.charAt(1));
					} catch (InvalidCodeException e) {
						e.log();
					}
				}
			}
			else {
				if (stringToParse.length() != 22) {
					throw new InvalidReadingException("Message lenght Error ("+recogniserAckComm+"R)");
				} else {
					try {
						data.delTimer(stringToParse.charAt(1));
						//TODO COSA FARE CON IL PARAMETRO RTC (CARATTERI DA 2 A 21)
					} catch (InvalidCodeException e) {
						e.log();
					}
				}
			}
		//Laptimer
		} else if ((stringToParse.charAt(0) == recogniserLapAccMode || stringToParse.charAt(0) == recogniserLapEndMode) && stringToParse.charAt(1) == ';') {
			if (stringToParse.length() != lenLap) {
				throw new InvalidReadingException("Message lenght Error ("+stringToParse.charAt(0)+")");
			} else {
				if(stringToParse.charAt(0) == recogniserLapAccMode && stringToParse.charAt(2) == recogniserLapIntType) 
					data.newLap(new ParsedLap(stringToParse.substring(4),LapMode.ACC,LapType.INT));
				else if(stringToParse.charAt(0) == recogniserLapAccMode && stringToParse.charAt(2) == recogniserLapLapType) 
					data.newLap(new ParsedLap(stringToParse.substring(4),LapMode.ACC,LapType.LAP));
				else if(stringToParse.charAt(0) == recogniserLapEndMode && stringToParse.charAt(2) == recogniserLapIntType) 
					data.newLap(new ParsedLap(stringToParse.substring(4),LapMode.END,LapType.INT));
				else if(stringToParse.charAt(0) == recogniserLapEndMode && stringToParse.charAt(2) == recogniserLapLapType) 
					data.newLap(new ParsedLap(stringToParse.substring(4),LapMode.END,LapType.LAP));
				else 
					throw new InvalidReadingException("Laptimer second letter reading Error");
			}
		} else {
			throw new InvalidReadingException("First letter reading Error");
		}
	}
	
}
