package back_end;

import java.util.ArrayList;
import java.util.Observable;

import front_end.View;

public class DataElem extends Observable{
	
	protected String name;
	
	/*
	 * Create an element with a given name, adding all views as observers
	 */
	public DataElem(String name, ArrayList<View> myViews) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	/*
	 * Notify view
	 */
	public void load() {
		setChanged();
		notifyObservers(true);
	}

}
