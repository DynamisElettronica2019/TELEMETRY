package back_end;

import java.util.Timer;

public class Command extends Error {
	
	private boolean sending;
	private Timer timer;
	private Boolean params;

	/*
	 * Create a command with a given name and a given identificator, spcifying if it is allowed to receive parameters
	 */
	public Command(String name, char code, boolean params) {
		super(name, code);
		//TODO
	}
	
	/*
	 * Verify if the command is on sending state
	 */
	public boolean isSending(){
		//TODO
	}
	
	/*
	 * Startn timer and set this command to sending
	 */
	public void startSending(){
		//TODO
	}

}
