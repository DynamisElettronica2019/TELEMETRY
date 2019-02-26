package back_end;

import java.util.ArrayList;

import configuration.ConfReader;
import exceptions.InvalidCodeException;
import front_end.View;

public class CommandSender {
	
	private Receiver receiver;
	private Data data;
	private char boardChar;
	
	/*
	 * Create a new command sender associated with a specific 'receiver' and a specific 'data'. Set the command sender as command sender of all views
	 */
	public CommandSender(Receiver receiver, Data data, ArrayList<View> myViews) {
		this.receiver=receiver;
		this.data=data;
		boardChar=ConfReader.getCommandBoard();
		for(View v: myViews){
			v.setCommandSender(this);
		}
	}
	
	/*
	 * Directly called from view. Check if 'comm' is a legal command, extract the correspondent code and check if 'params' are legal fot this command.
	 * If legal create a string accepted from DCU and send it through receiver. Start also the timer for 'comm'
	 */
	public void sendCommand(String comm,String params) {
		try {
			char myCode = data.authorizedNameAndParams(comm, params);
			data.startTimer(myCode);
			receiver.send(""+boardChar+myCode);
		} catch (InvalidCodeException e) {
			e.log();
		}
	}

}
