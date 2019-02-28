package back_end;

import java.util.ArrayList;

import front_end.View;

public class MinThreshold extends Threshold {

	/*
	 * Threshold with a min threshold value
	 */
	public MinThreshold(double threshold, ArrayList<View> myViews, ThresholdChannel channel) {
		super(threshold, myViews, channel);
	}

	@Override
	public void update(double value) {
		if(value<threshold && !error){
			error = true;
			
			setChanged();
			notifyObservers();
		}
		else if(value>=threshold && error){
			error = false;
			
			setChanged();
			notifyObservers();
		}
	}

}
