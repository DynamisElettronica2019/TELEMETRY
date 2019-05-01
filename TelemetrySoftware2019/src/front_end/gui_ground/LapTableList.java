package front_end.gui_ground;

public class LapTableList {
	int lapNumber;
	String lapTime;
	String intTime;
	
	public LapTableList(int lapNumber,  String lapTime, String intTime) {
		this.setLapNumber(lapNumber);
		this.setLapTime(lapTime);
		this.setIntTime(intTime);
	}
	
	public int getLapNumber() {
		return lapNumber;
	}
	public void setLapNumber(int lapNumber) {
		this.lapNumber = lapNumber;
	}
	public String getLapTime() {
		return lapTime;
	}
	public void setLapTime(String lapTime) {
		this.lapTime = lapTime;
	}
	public String getIntTime() {
		return intTime;
	}
	public void setIntTime(String intTime) {
		this.intTime = intTime;
	}
	
	
}
