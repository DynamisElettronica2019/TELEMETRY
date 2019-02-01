package back_end;

import java.util.ArrayList;

import front_end.View;

public class Debug extends DataElem {
	
	private Double value;

	/*
	 * Call superclass constructor and initialize value
	 */
	public Debug(String name, ArrayList<View> myViews) {
		super(name, myViews);
		value = 0.0;
		for(View v: myViews) this.addObserver(v.getDeObs());
	}
	
	/*
	 * Set value to val
	 */
	public void setValue(Double val){
		if(value!=val){
			value = val;
			
			setChanged();
			notifyObservers();
		}
	}

	/*
	 * Get state value
	 */
	public Double getValue(){
		return value;
	}
	
	/*
	 * Reset state value
	 */
	public void reset(){
		value = 0.0;
	}

}
