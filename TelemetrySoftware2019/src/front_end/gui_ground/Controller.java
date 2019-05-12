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
	
	protected GuiView view;
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
    }
	
	//Setting the view for accessing his controller managing functions
	public void SetView(GuiView guiView) {
		this.view = guiView;
	}
	
	/*
	 *  From here abstract function declarations
	 */
	public abstract void editDebug(Debug debug);
	public abstract void editState(State state);
	public abstract void editChannel(Channel channel);
	public abstract void editCommand(Command command);
	public abstract void editError(Error error);
	public abstract void editLap(LapTimer lapTimer);
	public abstract void editTS(Threshold thresholdState);
	public abstract void setPause();
}
