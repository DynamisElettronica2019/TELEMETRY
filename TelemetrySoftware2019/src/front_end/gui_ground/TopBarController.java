package front_end.gui_ground;

import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import back_end.Channel;
import back_end.Command;
import back_end.Debug;
import back_end.State;
import back_end.Threshold;
import back_end.Error;
import back_end.LapTimer;
import configuration.ConfReader;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class TopBarController extends Controller {
	private final int STATES = 6;
	private final String MAP_CHANNEL = "GCU_AUTOX_FB";
	private final String TRACTION_CHANNEL = "GCU_TRACTION_FB";
	private final String ACQUISITION_ON_STATE = "Acquisition ON";
	private Map<String, Circle> stateMap = new HashMap<>();
	private ArrayList<String> stateList;
	private Circle[] circleList;
	private Label[] labelList;
	private char side;
	
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SS");
	
	@FXML
	private Circle circle1, circle2, circle3, circle4, circle5, circle6, circle7, circle8, circle9, circle10, circle11;
	@FXML
	private Button commandsPage, debugPage, dynamicsPage, enginePage, rawPage, laptimerPage, errorPage;
	@FXML
	private Label label1, label2, label3, label4, label5, label6, label7, label8, label9, label10, label11;
	@FXML
	private Label map, traction, lastLap;
	@FXML
	private Button loadCsvButton, resetButton;
	@FXML
	private ToggleButton saveCsvButton;
	@FXML
	private HBox buttonBox;
	@FXML
	private TextField errorBar;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		initState();
		initTS();
		
		/*
		 * Set reset all button for double click
		 */
		resetButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent mouseEvent) {
		        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
		            if(mouseEvent.getClickCount() == 2){
		                view.getCommandSender().ResetAll();
		                view.getViewLoader().load();
		            }
		        }
		    }
		});
	}

	@Override
	public void editDebug(Debug debug) {
		// TODO Auto-generated method stub
		
	}

	//Initzialize the top bar states
	private void initState() { 
		stateList = ConfReader.getNames("states");
		circleList = new Circle[11];
		labelList = new Label[11];
		circleList[0] = circle1;
		circleList[1] = circle2;
		circleList[2] = circle3;
		circleList[3] = circle4;
		circleList[4] = circle5;
		circleList[5] = circle6;
		labelList[0] = label1;
		labelList[1] = label2;
		labelList[2] = label3;
		labelList[3] = label4;
		labelList[4] = label5;
		labelList[5] = label6;
				
		for(int i=0; i<STATES; i++) {
			stateMap.put(stateList.get(i), circleList[i]);
			labelList[i].setText(stateList.get(i));
		}
	}

	@Override
	public void editState(State state) {
		if(state.getValue() == true) {
			stateMap.get(state.getName()).setFill(Color.GREEN);
		}
		if(state.getValue() == false) {
			stateMap.get(state.getName()).setFill(Color.RED);
		}
		if((state.getName().equals(ACQUISITION_ON_STATE))&&(state.getValue())) {
			if(!saveCsvButton.isSelected()) {
				saveCsvButton.fire();
			}	
		}
	}

	//If detects a new map/traction mode changes the value in the top bar
	@Override
	public void editChannel(Channel channel) {
		if(!channel.isEmpty()){
			if (channel.getName().equals(MAP_CHANNEL)) {
				Platform.runLater(new Runnable() {
				    @Override
				    public void run() {
				    	map.setText(channel.getLastElems(1).get(0).toString());
				    }
				});
			}
			else if (channel.getName().equals(TRACTION_CHANNEL)) {
				Platform.runLater(new Runnable() {
				    @Override
				    public void run() {
				    	traction.setText(channel.getLastElems(1).get(0).toString());
				    }
				});
			}
		}
		else {
			if (channel.getName().equals(MAP_CHANNEL)) {
				Platform.runLater(new Runnable() {
				    @Override
				    public void run() {
				    	map.setText("/");
				    }
				});
			}
			else if (channel.getName().equals(TRACTION_CHANNEL)) {
				Platform.runLater(new Runnable() {
				    @Override
				    public void run() {
				    	traction.setText("/");
				    }
				});
			}
		}
	}

	@Override
	public void editCommand(Command command) {
		// TODO Auto-generated method stub
	}

	@Override
	public void editError(Error error) {
		if(error!=null) {
			if((errorBar!=null) && (error.getLastOcc() != null)) {
				errorBar.setText(error.getLastOcc().format(formatter)+" "+error.getName());
			}
		}
	}

	//Set last lap time on label, second top bar
	@Override
	public void editLap(LapTimer lapTimer) {
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
		    	if (lapTimer.getLastTime()==null)
		    	{
		    		
		    	}
		    	else {
		    		lastLap.setText(Integer.toString(lapTimer.getLastTime().getMinutes())+":"+Integer.toString(lapTimer.getLastTime().getSeconds())+":"+Integer.toString(lapTimer.getLastTime().getmSeconds()));
		    	}
		    }
		});
	}

	//Set labels, you cannot have more than 5 Threshold states
	private void initTS() {
		int element = STATES+1;
		ArrayList<String> chNames = ConfReader.getNames("channels");
		circleList[6] = circle7;
		circleList[7] = circle8;
		circleList[8] = circle9;
		circleList[9] = circle10;
		circleList[10] = circle11;
		labelList[6] = label7;
		labelList[7] = label7;
		labelList[8] = label8;
		labelList[9] = label9;
		labelList[10] = label10;
		for(int i=0;i<chNames.size();i++) {
			String[] NaTh = ConfReader.haveThresholdAndServer(chNames.get(i));
			if (NaTh.length>2) {
				stateMap.put(NaTh[1], circleList[element-1]);
				labelList[element].setText(chNames.get(i));
				element++;
			}
			if (element > 11) {
				break;
			}
		}
	}

	@Override
	public void editTS(Threshold thresholdState) {
		if (thresholdState.isError()) {
			stateMap.get(thresholdState.getChName()).setFill(Color.RED);
		}
		else {
			stateMap.get(thresholdState.getChName()).setFill(Color.GREEN);
		}
	}
	
	/*
	 *  From here functions for managing the button clicks, calling view functions for loading new fxml
	 */
	
	@FXML
	private void commandClick() throws IOException {
		view.SetScreen("CommandScreen.fxml", side);
	}
	
	@FXML
	private void engClick() throws IOException {
		view.SetScreen("EngineScreen.fxml", side);
	}
	
	@FXML
	private void dynClick() throws IOException {
		view.SetScreen("DynamicsScreen.fxml", side);
	}
	
	@FXML
	private void rawClick() throws IOException {
		view.SetScreen("RawScreen.fxml", side);
	}
	@FXML
	private void debugClick() throws IOException {
		view.SetScreen("DebugScreen.fxml", side);
	}
	@FXML
	public void laptimerClick() throws IOException {
		view.SetScreen("LaptimerScreen.fxml", side);
	}
	@FXML
	public void errorClick() throws IOException {
		view.SetScreen("ErrorScreen.fxml", side);
	}
	@FXML
	public void errorBarClick() {
		errorBar.setText("");
	}
	@FXML
	public void LoadCsvClick() throws IOException {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
		fileChooser.setTitle("Select file to load");
		fileChooser.getExtensionFilters().add(extFilter);
		File fileToLoad = fileChooser.showOpenDialog(view.GetStage());
		if(fileToLoad!=null){
			view.getCommandSender().ResetChannels();
			view.getCommandSender().LoadFile(fileToLoad.getAbsolutePath());
			view.getViewLoader().load();
			view.SetPause();
		}
	}
	@FXML
	public void SaveCsvClick() throws IOException {
		if(saveCsvButton.isSelected()) {
			view.getCommandSender().SaveFile();
		}
		else {
			view.getCommandSender().CloseFile();
		}
	}
	
	
	/*
	 * Set if the top bar is on the left or right side of the screen
	 * Make load/save button, errorBar visible only on right side
	 */
	public void SetSide(char side) {
		this.side = side;
		if (side == 'd') {
			buttonBox.getChildren().remove(loadCsvButton);
			buttonBox.getChildren().remove(saveCsvButton);
			errorBar.setVisible(false);
		}
	}

	@Override
	public void setPause() {
		// TODO Auto-generated method stub
		
	}
	
	
}
