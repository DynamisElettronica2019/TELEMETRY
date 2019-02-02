package back_end;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Observable;

import front_end.View;

public class Error extends Observable{
	
	private String name;
	private char code;
	private LocalDateTime lastOcc;
	private int numbOcc;
	
	/*
	 * Create an error with a given name and a given identificator, adding all views as observers
	 */
	public Error(String name, char code, ArrayList<View> myViews) {
		this.name = name;
		this.code = code;
		numbOcc = 0;
		lastOcc = null;
		for(View v: myViews) this.addObserver(v.getErObs());
	}
	
	/*
	 * Set a new occurence for this error updating lastOcc to now and incrementing numbOcc
	 */
	public void setOcc() {
		numbOcc++;
		lastOcc = LocalDateTime.now();
		
		setChanged();
		notifyObservers();
	}

	/*
	 * Reset error value
	 */
	public void reset() {
		lastOcc = null;
		numbOcc = 0;
	}
	
	public String getName() {
		return name;
	}
	
	public char getCode() {
		return code;
	}
	
	public LocalDateTime getLastOcc() {
		return lastOcc;
	}
}
