package front_end.gui_ground;

import javafx.scene.shape.Circle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import back_end.Channel;
import back_end.State;
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
	private static final int STATES = 11;
	private static final String MAP_CHANNEL = "GCU_AUTOX_FB";
	private static final String TRACTION_CHANNEL = "GCU_TRACTION_FB";
	private Map<String, Circle> stateMap = new HashMap<>();
	private ArrayList<String> stateList;
	private Circle[] circleList;
	private Label[] labelList;
	private BorderPane rootPane;
	
	private Parent MainPane;
	private Controller MainController;
	private FXMLLoader MainLoader;
	
	@FXML
	private Circle circle1, circle2, circle3, circle4, circle5, circle6, circle7, circle8, circle9, circle10, circle11;
	@FXML
	private Button commandsPage, debugPage, dynamicsPage, enginePage, rawPage;
	@FXML
	private Label label1, label2, label3, label4, label5, label6, label7, label8, label9, label10, label11;
	@FXML
	private Label map, traction;
	@FXML
	private Button button;
	
	@Override
	public void SetDebug() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void EditDebug() {
		// TODO Auto-generated method stub
		
	}

	//Updates the top bar states
	@Override
	public void SetState() { 
		stateList = ConfReader.getNames("states");
		circleList = new Circle[STATES];
		labelList = new Label[STATES];
		circleList[0] = circle1;
		circleList[1] = circle2;
		circleList[2] = circle3;
		circleList[3] = circle4;
		circleList[4] = circle5;
		circleList[5] = circle6;
		circleList[6] = circle7;
		circleList[7] = circle8;
		circleList[8] = circle9;
		circleList[9] = circle10;
		circleList[10] = circle11;
		labelList[0] = label1;
		labelList[1] = label2;
		labelList[2] = label3;
		labelList[3] = label4;
		labelList[4] = label5;
		labelList[5] = label6;
		labelList[6] = label7;
		labelList[7] = label8;
		labelList[8] = label9;
		labelList[9] = label10;
		labelList[10] = label11;
				
		for(int i=0; i<7; i++) {
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

	@Override
	public void SetCommand() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void EditCommand() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void SetError() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void EditError() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void SetLap() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void EditLap() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void SetTS() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void EditTS() {
		// TODO Auto-generated method stub
		
	}
	
	@FXML
	private void commandClick() throws IOException {
		MainLoader = new FXMLLoader();
        MainPane = MainLoader.load(getClass().getResource("CommandScreen.fxml").openStream());
        MainController = MainLoader.getController();
        rootPane.setCenter(MainPane);
	}
	
	@FXML
	private void engClick() throws IOException {
		MainLoader = new FXMLLoader();
        MainPane = MainLoader.load(getClass().getResource("EngineScreen.fxml").openStream());
        MainController = MainLoader.getController();
        rootPane.setCenter(MainPane);
	}
	
	@FXML
	private void dynClick() throws IOException {
		MainLoader = new FXMLLoader();
        MainPane = MainLoader.load(getClass().getResource("DynamicsScreen.fxml").openStream());
        MainController = MainLoader.getController();
        rootPane.setCenter(MainPane);
	}
	
	@FXML
	private void rawClick() throws IOException {
		MainLoader = new FXMLLoader();
        MainPane = MainLoader.load(getClass().getResource("RawScreen.fxml").openStream());
        MainController = MainLoader.getController();
        rootPane.setCenter(MainPane);
	}
	@FXML
	private void debugClick() throws IOException {
		MainLoader = new FXMLLoader();
        MainPane = MainLoader.load(getClass().getResource("DebugScreen.fxml").openStream());
        MainController = MainLoader.getController();
        rootPane.setCenter(MainPane);
	}
	
	public void SetParent(BorderPane borderPane) {
		rootPane = borderPane;
	}
	
	
}
