package front_end.gui_ground;

import java.net.URL;
import java.util.ResourceBundle;

import back_end.Channel;
import back_end.State;
import javafx.fxml.Initializable;

public abstract class Controller implements Initializable {
	@Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("");
		SetDebug();
		SetState();
		SetChannel();
		SetCommand();
		SetError();
		SetLap();
		SetTS();
    }
	
	public abstract void SetDebug();
	public abstract void EditDebug();
	
	public abstract void SetState();
	public abstract void EditState(State state);
	
	public abstract void SetChannel();
	public abstract void EditChannel(Channel channel);
	
	public abstract void SetCommand();
	public abstract void EditCommand();
	
	public abstract void SetError();
	public abstract void EditError();
	
	public abstract void SetLap();
	public abstract void EditLap();
	
	public abstract void SetTS();
	public abstract void EditTS();
}
