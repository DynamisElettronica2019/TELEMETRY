package front_end.gui_ground;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import back_end.Channel;
import back_end.Command;
import back_end.Debug;
import back_end.Error;
import back_end.LapTimer;
import back_end.State;
import back_end.Threshold;
import configuration.ConfReader;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class RawScreenController extends Controller {
	private ArrayList<String> channelList;
	private ArrayList<String> debugList;
	private ArrayList<String> stateList;
	private ObservableList<TableList> channelObsList = FXCollections.observableArrayList();
	private ObservableList<TableList> debugObsList = FXCollections.observableArrayList();
	private ObservableList<TableList> stateObsList = FXCollections.observableArrayList();
	private Map<String, Integer> channelMap = new HashMap<>();
	private Map<String, Integer> debugMap = new HashMap<>();
	private Map<String, Integer> stateMap = new HashMap<>();
	@FXML
	private TableView<TableList> channelTable, stateTable, debugTable;
	@FXML
	private TableColumn<TableList, String> channelNameColumn, debugNameColumn, stateNameColumn;
	@FXML
	private TableColumn<TableList, Double> channelValueColumn, debugValueColumn, stateValueColumn;
	@FXML
	private CheckBox channelsCheckbox, debugCheckbox, statesCheckbox;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		
		//Initialize channels
		channelTable.setItems(channelObsList);
		channelNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		channelValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
		channelList = ConfReader.getNames("channels");
		for (int i = 0; i < channelList.size(); i++) {
			channelMap.put(ConfReader.haveThresholdAndServer(channelList.get(i))[1], i);
			channelTable.getItems().add(new TableList(ConfReader.haveThresholdAndServer(channelList.get(i))[1], 0));
		}	
		
		//Initialize debug
		debugTable.setItems(debugObsList);
		debugNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		debugValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
		debugList = ConfReader.getNames("debug");
		for (int i = 0; i < debugList.size(); i++) {
			debugMap.put(debugList.get(i), i);
			debugTable.getItems().add(new TableList(debugList.get(i), 0));
		}
		
		//Initialize states
		stateTable.setItems(stateObsList);
		stateNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		stateValueColumn.setCellValueFactory(new PropertyValueFactory<>("valueBool"));
		stateList = ConfReader.getNames("states");
		for (int i = 0; i < stateList.size(); i++) {
			stateMap.put(stateList.get(i), i);
			stateTable.getItems().add(new TableList(stateList.get(i), false));
		}
		
		//Checkbox listeners
		channelsCheckbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		    	if(channelsCheckbox.isSelected()) {
		    		channelTable.setVisible(true);
		    	}
		    	else {
		    		channelTable.setVisible(false);
		    	}
		    }
		});

		debugCheckbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		    	if(debugCheckbox.isSelected()) {
		    		debugTable.setVisible(true);
		    	}
		    	else {
		    		debugTable.setVisible(false);
		    	}
		    }
		});
		
		statesCheckbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		    	if(statesCheckbox.isSelected()) {
		    		stateTable.setVisible(true);
		    	}
		    	else {
		    		stateTable.setVisible(false);
		    	}
		    }
		});
	}
	
	@Override
	public void editDebug(Debug debug) {
		debugObsList.set(debugMap.get(debug.getName()), new TableList(debug.getName(), debug.getValue()));
		debugTable.refresh();
	}

	@Override
	public void editState(State state) {
		if(stateMap.get(state.getName()) != null) {
			stateObsList.set(stateMap.get(state.getName()), new TableList(state.getName(), state.getValue()));	
			stateTable.refresh();
		}
	}

	@Override
	public void editChannel(Channel channel) {
		if(!channel.isEmpty()){
			//This is required for being sure that the object is created before assignment and not genning null pointer exception
			Platform.runLater(new Runnable() {
			    @Override
			    public void run() {
			    	if (channelMap.get(channel.getName()) != null) {
						channelObsList.set(channelMap.get(channel.getName()), new TableList(channel.getName(), channel.getLastElems(1).get(0)));
						channelTable.refresh();
					}	    	
			    }
			});	
		}
	}

	@Override
	public void editCommand(Command command) {
		// TODO Auto-generated method stub
		
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
