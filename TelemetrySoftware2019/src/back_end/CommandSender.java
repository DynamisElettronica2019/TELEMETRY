package back_end;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import exceptions.InvalidCodeException;
import front_end.View;

public class CommandSender {
	
	private Receiver receiver;
	private Data data;
	
	/*
	 * Create a new command sender associated with a specific 'receiver' and a specific 'data'. Set the command sender as command sender of all views
	 */
	public CommandSender(Receiver receiver, Data data, ArrayList<View> myViews) {
		this.receiver=receiver;
		this.data=data;
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
			if(params.equals("")) {
				receiver.send(""+data.getBoard(myCode)+myCode);
			}
			else {
				receiver.send(""+data.getBoard(myCode)+myCode+';'+params);
			}
		} catch (InvalidCodeException e) {
			e.log();
		}
	}
	
	/*
	 *  Call function in data to load file
	 */
	public void LoadFile(String pathStr) throws IOException {
		data.LoadFile(pathStr);
	}
	
	/*
	 *  Call function in data to save file
	 */
	public void SaveFile() throws IOException {
		data.SaveFile();
	}
	
	/*
	 *  Call function in data to close file
	 */
	public void CloseFile() throws IOException {
		data.CloseFile();
	}
	
	/*
	 *  Reset channel data
	 */
	public void ResetChannels() {
		data.ResetChannels();
	}
	
	/*
	 *  Reset all data
	 */
	public void ResetAll() {
		data.reset();
	}
	
	/*
	 * Return an array list of laps
	 */
	public ArrayList<LapTime> LoadLaps() {
		return data.getLapTimer().getLapTimes();
	}

}
