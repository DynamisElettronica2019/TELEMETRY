package front_end.gui_ground;

import java.net.URL;
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
import front_end.View;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class CommandScreenController extends Controller {
	private Map<String, String> driverMessageMap = new HashMap<>();
	private Map<String, Button> buttonMap = new HashMap<>();
	private CommandSender commandSender;
	ObservableList<String> driverMessages;
	@FXML
	private Button StartAcqButton, StopAcqButton, StartTelemButton, StopTelemButton;
	@FXML
	private ComboBox<String> PilotMessageList;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		driverMessages = FXCollections.observableArrayList("Stop the car", "Keep going", "Pit");
		PilotMessageList.setItems(driverMessages);
		
		driverMessageMap.put("Stop the car", "CAR_STOP");
		driverMessageMap.put("Keep going", "GO");
		driverMessageMap.put("Pit", "PIT");
		
		buttonMap.put("START_ACQUISITION", StartAcqButton);
		buttonMap.put("STOP_ACQUISITION", StopAcqButton);
		buttonMap.put("START_TELEMETRY", StartTelemButton);
		buttonMap.put("STOP_TELEMETRY", StopTelemButton);
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
			buttonMap.get(command.getName()).setDisable(false);
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

	/*
	 * From here button actions
	 * Command sender get is here because the view does not have it when you initialize the screen
	 */
	
	@FXML
	private void StartAcqAction() {
		if(commandSender == null) {
			commandSender = view.getCommandSender();
		}	
		commandSender.sendCommand("START_ACQUISITION", "");
		StartAcqButton.setDisable(true);
	}
	
	@FXML
	private void StopAcqAction() {
		if(commandSender == null) {
			commandSender = view.getCommandSender();
		}	
		commandSender.sendCommand("STOP_ACQUISITION", "");
		StopAcqButton.setDisable(true);
	}
	
	@FXML
	private void StartTelemAction() {
		if(commandSender == null) {
			commandSender = view.getCommandSender();
		}	
		commandSender.sendCommand("START_TELEMETRY", "");
		StartTelemButton.setDisable(true);
	}
	
	@FXML
	private void StopTelemAction() {
		if(commandSender == null) {
			commandSender = view.getCommandSender();
		}	
		commandSender.sendCommand("STOP_TELEMETRY", "");
		StopTelemButton.setDisable(true);
	}
	
	@FXML
	private void SendToDriverAction() {
		if(commandSender == null) {
			commandSender = view.getCommandSender();
		}	
		commandSender.sendCommand(driverMessageMap.get(PilotMessageList.getValue()), "");
	}
}
