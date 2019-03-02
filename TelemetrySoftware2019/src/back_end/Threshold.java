package back_end;

import java.util.ArrayList;
import java.util.Observable;

import front_end.View;

public abstract class Threshold extends Observable{
	
	protected double threshold;
	protected boolean error; //True if threshold is exceeded
	private ThresholdChannel channel;
	
	/*
	 * Create a threshold with a certain threshold value and error state set to default
	 */
	public Threshold(double threshold, ArrayList<View> myViews, ThresholdChannel channel) {
		this.threshold = threshold;
		error = false;
		for(View v: myViews) this.addObserver(v.getTsObs());
		this.channel = channel;
	}
	
	/*
	 * Reset threshold state
	 */
	public void reset(){
		error = false;
	}
	
	public String getChName(){
		return channel.getName();
	}
	
	/*
	 * Update error based on threshold and value. Notify observers if changed.
	 */
	public abstract void update(double value);
	
	public boolean isError() {
		return error;
	}
	
	/*
	 * Notify view
	 */
	public void load() {
		setChanged();
		notifyObservers();
	}

}
