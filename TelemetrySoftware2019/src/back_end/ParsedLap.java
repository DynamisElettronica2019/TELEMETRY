package back_end;

public class ParsedLap extends Parsed {
	
	private LapMode mode;
	private LapType type;

	/*
	 * Create parsedLap and set string to parsed information. Add mode and type attributes
	 */
	public ParsedLap(String strToParse, LapMode mode, LapType type) {
		super(strToParse.replaceAll(":", ";"));
		this.mode = mode;
		this.type = type;
	}
	
	/*
	 * Convert lap number string to int
	 */
	public int convertLapNumber() {
		return Integer.parseInt(parsedInfo[0]);
	}
	
	/*
	 * Conver lap time in an array of int [m,s,ms]
	 */
	public int[] convertLapTime(){
		return new int[]{Integer.parseInt(parsedInfo[1]),Integer.parseInt(parsedInfo[2]),Integer.parseInt(parsedInfo[3])};
	}
	
	public LapMode getMode() {
		return mode;
	}
	
	public LapType getType() {
		return type;
	}

}
