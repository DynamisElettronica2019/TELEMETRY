package back_end;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Timer;

import configuration.ConfReader;
import front_end.View;

public class Command extends Observable{
	
	private String name;
	private char code;
	private boolean sending;
	private Timer timer;
	private long timerLen;
	private Boolean params;
	private Error commandError;

	/*
	 * Create a command with a given name and a given identificator, spcifying if it is allowed to receive parameters
	 */
	public Command(String name, char code, boolean params, ArrayList<View> myViews) {
		this.name = name;
		this.code = code;
		sending = false;
		timer = new Timer();
		timerLen=ConfReader.getTimerLen();
		this.params = params;
		commandError = new Error("ACK '"+name+"' NOT RECEIVED", code, myViews);
		for(View v: myViews) this.addObserver(v.getCoObs());
	}
	
	/*
	 * Verify if the command is on sending state
	 */
	public boolean isSending(){
		return sending;
	}
	
	/*
	 * Start timer and set this command to sending
	 */
	public void startSending(){
		sending = true;
		timer.schedule(new CommandTimerTask(this), timerLen);
		
		setChanged();
		notifyObservers();
	}
	
	/*
	 * Stop the timer and set sending to false
	 */
	public void stopTimer(){
		timer.cancel();
		timer = new Timer();
		sending = false;
		
		setChanged();
		notifyObservers();
	}
	
	/*
	 * Set an occurrence for the error of this command and notify for command not acked
	 */
	public void notAcked() {
		sending=false;
		commandError.setOcc();
	}
	
	/*
	 * Reset command and related error
	 */
	public void reset() {
		commandError.reset();
		timer.cancel();
		timer = new Timer();
		sending = false;
	}
	
	public boolean haveParams(){
		return params;
	}
	
	public String getName() {
		return name;
	}
	
	public char getCode() {
		return code;
	}
}
