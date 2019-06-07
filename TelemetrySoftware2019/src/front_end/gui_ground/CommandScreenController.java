package front_end.gui_ground;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import back_end.Channel;
import back_end.Command;
import back_end.CommandSender;
import back_end.Debug;
import back_end.Error;
import back_end.LapTimer;
import back_end.State;
import back_end.Threshold;
import configuration.ConfReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToolBar;

public class CommandScreenController extends Controller {
	private Map<String, String> driverMessageMap = new HashMap<>();
	private Map<Button, String> buttonMap = new HashMap<>();
	private Map<String, Button> buttonMapReverse = new HashMap<>();
	ObservableList<String> driverMessages;
	ArrayList<String> nameButtonList;
	ArrayList<Button> commandButtons;
	@FXML
	private ToolBar buttonBar;
	@FXML
	private Button StartAcqButton, StopAcqButton, StartTelemButton, StopTelemButton;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		driverMessages = FXCollections.observableArrayList("Stop the car", "Keep going", "Pit");
		
		//Set all the buttons from config file
		nameButtonList = new ArrayList<String>();
		nameButtonList = ConfReader.getCommandNames();
		commandButtons = new ArrayList<Button>();
		
		for(int i=0; i < nameButtonList.size(); i++) {
			commandButtons.add(new Button(nameButtonList.get(i)));
			buttonMap.put(commandButtons.get(i), nameButtonList.get(i));
			buttonMapReverse.put(nameButtonList.get(i), commandButtons.get(i));
			buttonBar.getItems().add(commandButtons.get(i));
		}
		
		for(Button but : commandButtons) {
			but.setOnAction((event) -> {
				view.getCommandSender().sendCommand(buttonMap.get(but), "");
				but.setDisable(true);
			});
		}

		driverMessageMap.put("Stop the car", "CAR_STOP");
		driverMessageMap.put("Keep going", "GO");
		driverMessageMap.put("Pit", "PIT");
	}
	
	@Override
	public void editDebug(Debug debug) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editState(State state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editChannel(Channel channel) {
		if(!channel.isEmpty()){
			// TODO Auto-generated method stub
			
		}
	}

	//Enable the button again
	@Override
	public void editCommand(Command command) {
		if(command.isSending() == false) {
			buttonMapReverse.get(command.getName()).setDisable(false);
		}
	}

	@Override
	public void editError(Error error) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editLap(LapTimer lapTimer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editTS(Threshold thresholdState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPause() {
		// TODO Auto-generated method stub
		
	}
}
