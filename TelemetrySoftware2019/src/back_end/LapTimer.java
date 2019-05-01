package back_end;

import java.util.ArrayList;
import java.util.Observable;

import front_end.View;

public class LapTimer extends Observable {
	
	private ArrayList<LapTime> lapTimes;
	
	public LapTimer(ArrayList<View> myViews) {
		lapTimes = new ArrayList<>();
		for(View v: myViews) this.addObserver(v.getLapObs());
	}
	
	/*
	 * Add a new lapTime
	 * Notify observers with the new LapTime
	 */
	public void newLap(ParsedLap pl){
		lapTimes.add(new LapTime(pl));
		
		setChanged();
		notifyObservers();
	}
	
	/*
	 * Get last time in lapTimes
	 * Return null if no times is available
	 */
	public LapTime getLastTime() {
		if(lapTimes.size()>0) return lapTimes.get(lapTimes.size()-1);
		else return null;
	}
	
	/*
	 * Get last time in lapTimes
	 * Return null if no times is available
	 */
	public LapTime getLastIntTime() {
		if((lapTimes.size()>0) && (lapTimes.get(lapTimes.size()-2).getType() == LapType.INT)) return lapTimes.get(lapTimes.size()-2);
		else return null;
	}
	
	/*
	 * Reset lapTimer
	 */
	public void reset(){
		lapTimes = new ArrayList<>();
	}
	
	public ArrayList<LapTime> getLapTimes() {
		return lapTimes;
	}
	
	/*
	 * Notify view
	 */
	public void load() {
		setChanged();
		notifyObservers();
	}

}
