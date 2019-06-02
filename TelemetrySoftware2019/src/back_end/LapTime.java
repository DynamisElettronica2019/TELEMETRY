package back_end;

import java.time.LocalDateTime;

public class LapTime {
	
	private LapMode mode;
	private LapType type;
	private int lapNumber;
	private int minutes;
	private int seconds;
	private int mSeconds;
	private LocalDateTime ts;
	
	/*
	 * Create a new LapTime on the base of a ParsedLap
	 */
	public LapTime(ParsedLap pl) {
		mode = pl.getMode();
		type = pl.getType();
		lapNumber = pl.convertLapNumber();
		minutes = pl.convertLapTime()[0];
		seconds = pl.convertLapTime()[1];
		mSeconds = pl.convertLapTime()[2];
		ts = LocalDateTime.now();
	}
	
	public LapMode getMode() {
		return mode;
	}
	
	public LapType getType() {
		return type;
	}
	
	public int getLapNumber() {
		return lapNumber;
	}
	
	public int getMinutes() {
		return minutes;
	}
	
	public int getSeconds() {
		return seconds;
	}
	
	public int getmSeconds() {
		return mSeconds;
	}
	
	public LocalDateTime getTs() {
		return ts;
	}

}
