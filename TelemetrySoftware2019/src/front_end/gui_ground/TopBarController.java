package front_end.gui_ground;

import javafx.scene.shape.Circle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import back_end.Channel;
import back_end.Command;
import back_end.Debug;
import back_end.State;
import back_end.Threshold;
import back_end.ThresholdChannel;
import back_end.Error;
import back_end.LapTimer;
import back_end.ViewLoader;
import configuration.ConfReader;
import front_end.gui_row.StateList;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class TopBarController extends Controller {
	private static final int STATES = 6;
	private static final String MAP_CHANNEL = "GCU_AUTOX_FB";
	private static final String TRACTION_CHANNEL = "GCU_TRACTION_FB";
	private Map<String, Circle> stateMap = new HashMap<>();
	private ArrayList<String> stateList;
	private Circle[] circleList;
	private Label[] labelList;
	private char side;
	
	@FXML
	private Circle circle1, circle2, circle3, circle4, circle5, circle6, circle7, circle8, circle9, circle10, circle11;
	@FXML
	private Button commandsPage, debugPage, dynamicsPage, enginePage, rawPage;
	@FXML
	private Label label1, label2, label3, label4, label5, label6, label7, label8, label9, label10, label11;
	@FXML
	private Label map, traction, lastLap;
	@FXML
	private Button button;
	
	@Override
	public void SetDebug() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void EditDebug(Debug debug) {
		// TODO Auto-generated method stub
		
	}

	//Initzialize the top bar states
	@Override
	public void SetState() { 
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
	public void EditState(State state) {
		if(state.getValue() == true) {
			stateMap.get(state.getName()).setFill(Color.GREEN);
		}
		if(state.getValue() == false) {
			stateMap.get(state.getName()).setFill(Color.RED);
		}
	}

	@Override
	public void SetChannel() {
	}

	//If detects a new map/traction mode changes the value in the top bar
	@Override
	public void EditChannel(Channel channel) {
		if (channel.getLastElems(1).size()==0) {
			//No channel data present, exit the function
		}
		else if (channel.getName().equals(MAP_CHANNEL)) {
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

	@Override
	public void SetCommand() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void EditCommand(Command command) {
		// TODO Auto-generated method stub
	}

	@Override
	public void SetError() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void EditError(Error error) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void SetLap() {
		// TODO Auto-generated method stub
		
	}

	//Set last lap time on label, second top bar
	@Override
	public void EditLap(LapTimer lapTimer) {
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
	@Override
	public void SetTS() {
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
			String[] NaTh = ConfReader.haveThreshold(chNames.get(i));
			if (NaTh.length>1) {
				stateMap.put(NaTh[0], circleList[element-1]);
				labelList[element].setText(chNames.get(i));
				element++;
			}
			if (element > 11) {
				break;
			}
		}
	}

	@Override
	public void EditTS(Threshold thresholdState) {
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
		if(side == 's') {
			view.CommandScreenSX();
		}
		else if(side =='d')
		{
			view.CommandScreenDX();
		}
	}
	
	@FXML
	private void engClick() throws IOException {
		if(side == 's') {
			view.EngineScreenSX();
		}
		else if(side =='d')
		{
			view.EngineScreenDX();
		}
	}
	
	@FXML
	private void dynClick() throws IOException {
		if(side == 's') {
			view.DynamicsScreenSX();
		}
		else if(side =='d')
		{
			view.DynamicsScreenDX();
		}
	}
	
	@FXML
	private void rawClick() throws IOException {
		if(side == 's') {
			view.RawScreenSX();
		}
		else if(side =='d')
		{
			view.RawScreenDX();
		}
	}
	@FXML
	private void debugClick() throws IOException {
		if(side == 's') {
			view.DebugScreenSX();
		}
		else if(side =='d')
		{
			view.DebugScreenDX();
		}
	}
	
	//Set if the top bar is on the left or right side of the screen
	public void SetSide(char side) {
		this.side = side;
	}
}
