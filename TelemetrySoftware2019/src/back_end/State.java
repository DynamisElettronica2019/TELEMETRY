package back_end;

import java.util.ArrayList;

import front_end.View;

public class State extends DataElem {

	private boolean value;
	
	/*
	 * Call superclass constructor and initialize value
	 */
	public State(String name, ArrayList<View> myViews) {
		super(name, myViews);
		value = false;
		for(View v: myViews) this.addObserver(v.getStObs());
	}
	
	/*
	 * Set value to val
	 */
	public void setValue(boolean val){
		if(value!=val){
			value=val;
			
			setChanged();
			notifyObservers();
		}
	}

	/*
	 * Get state value
	 */
	public boolean getValue(){
		return value;
	}
	
	/*
	 * Reset state value
	 */
	public void reset(){
		value = false;
	}
}
