package front_end.gui_ground;

import java.net.URL;
import java.util.ResourceBundle;

import back_end.Channel;
import back_end.Command;
import back_end.Debug;
import back_end.State;
import back_end.Threshold;
import back_end.Error;
import back_end.LapTimer;
import javafx.fxml.Initializable;

public abstract class Controller implements Initializable {
	
	protected GuiGroundView view;
	
	//Call setter before loading viewLoader
	@Override
    public void initialize(URL location, ResourceBundle resources) {
		SetDebug();
		SetState();
		SetChannel();
		SetCommand();
		SetError();
		SetLap();
		SetTS();
    }
	
	//Setting the view for accessing his controller managing functions
	public void SetView(GuiGroundView view) {
		this.view = view;
	}
	
	/*
	 *  From here abstract function declarations
	 */
	
	public abstract void SetDebug();
	public abstract void EditDebug(Debug debug);
	
	public abstract void SetState();
	public abstract void EditState(State state);
	
	public abstract void SetChannel();
	public abstract void EditChannel(Channel channel);
	
	public abstract void SetCommand();
	public abstract void EditCommand(Command command);
	
	public abstract void SetError();
	public abstract void EditError(Error error);
	
	public abstract void SetLap();
	public abstract void EditLap(LapTimer lapTimer);
	
	public abstract void SetTS();
	public abstract void EditTS(Threshold thresholdState);
}
