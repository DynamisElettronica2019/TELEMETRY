package back_end;

import java.util.ArrayList;

import front_end.View;

public class ThresholdChannel extends Channel {
	
	private Threshold threshold;

	/*
	 * Channel with a threshold object (th is of the type <500.0)
	 */
	public ThresholdChannel(String name, Data data, ArrayList<View> myViews, String th) {
		super(name, data, myViews);
		if(th.charAt(0)=='<') threshold = new MinThreshold(Double.parseDouble(th.substring(1)), myViews, this);
		else threshold = new MaxThreshold(Double.parseDouble(th.substring(1)), myViews, this);
	}
	
	/*
	 * Add element to channel and update threshold value
	 */
	@Override
	public void addElem(Double elem) {
		super.addElem(elem);
		threshold.update(elem);
	}
	
	/*
	 * Reset channel and threshold
	 */
	@Override
	public void reset() {
		super.reset();
		threshold.reset();
	}
	
	/*
	 * Notify view
	 */
	@Override
	public void load() {
		super.load();
		threshold.load();
	}

}
