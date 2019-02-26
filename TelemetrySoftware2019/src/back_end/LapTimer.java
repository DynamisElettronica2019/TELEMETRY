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
		LapTime newTime = new LapTime(pl);
		lapTimes.add(newTime);
		
		setChanged();
		notifyObservers(newTime);
	}
	
	/*
	 * Reset lapTimer
	 */
	public void reset(){
		lapTimes = new ArrayList<>();
	}

}
