package back_end;

import java.util.ArrayList;
import java.util.Timer;

import configuration.ConfReader;
import front_end.View;

public class Command extends Error {
	
	private boolean sending;
	private Timer timer;
	private long timerLen;
	private Boolean params;

	/*
	 * Create a command with a given name and a given identificator, spcifying if it is allowed to receive parameters
	 */
	public Command(String name, char code, boolean params, ArrayList<View> myViews) {
		super(name, code, myViews);
		this.params = params;
		timer = new Timer();
		timerLen=ConfReader.getTimerLen();
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
		sending = false;
		
		setChanged();
		notifyObservers();
	}
	
	public boolean haveParams(){
		return params;
	}
	
	public void setSending(boolean sending) {
		this.sending = sending;
	}

}
