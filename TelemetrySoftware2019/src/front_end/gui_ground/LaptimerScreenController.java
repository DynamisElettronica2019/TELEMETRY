package front_end.gui_ground;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import back_end.Channel;
import back_end.Command;
import back_end.Debug;
import back_end.Error;
import back_end.LapTime;
import back_end.LapTimer;
import back_end.LapType;
import back_end.State;
import back_end.Threshold;
import configuration.ConfReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class LaptimerScreenController extends Controller {
	private ObservableList<LapTableList> lapObsList = FXCollections.observableArrayList();
	@FXML
	private TableView<LapTableList> lapTable;
	@FXML
	private TableColumn<LapTableList, Integer> lapColumn;
	@FXML
	private TableColumn<LapTableList, String> lapTimeColumn, intTimeColumn;

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		
		//Table init
		lapTable.setItems(lapObsList);
		lapColumn.setCellValueFactory(new PropertyValueFactory<>("lapNumber"));
		lapTimeColumn.setCellValueFactory(new PropertyValueFactory<>("lapTime"));
		intTimeColumn.setCellValueFactory(new PropertyValueFactory<>("intTime"));
		
		//Loading previous laps
		ArrayList<LapTime> laps = view.getCommandSender().LoadLaps();
		for(int i=0; i<laps.size(); i++) {
			if(laps.get(i).getType() == LapType.LAP) {
				if(laps.get(i-1).getType() == LapType.INT) {
					lapTable.getItems().add(new LapTableList(
						laps.get(i).getLapNumber(),
						Integer.toString(laps.get(i).getMinutes())+":"+Integer.toString(laps.get(i).getSeconds())+":"+Integer.toString(laps.get(i).getmSeconds()), 
						Integer.toString(laps.get(i-1).getMinutes())+":"+Integer.toString(laps.get(i-1).getSeconds())+":"+Integer.toString(laps.get(i-1).getmSeconds())
					));
				}
				else {
					lapTable.getItems().add(new LapTableList(
						laps.get(i).getLapNumber(),
						Integer.toString(laps.get(i).getMinutes())+":"+Integer.toString(laps.get(i).getSeconds())+":"+Integer.toString(laps.get(i).getmSeconds()), 
						"No intermediate"
					));
				}
			}
		}
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
		// TODO Auto-generated method stub

	}

	@Override
	public void editCommand(Command command) {
		// TODO Auto-generated method stub

	}

	@Override
	public void editError(Error error) {
		// TODO Auto-generated method stub

	}

	/*
	 * When you receive new lap add it to the table, check for int
	 */
	@Override
	public void editLap(LapTimer lapTimer) {
		if((lapTimer != null) && (lapTimer.getLastTime() != null)) {
			if (lapTimer.getLastTime().getType() == LapType.LAP) {
				if (lapTimer.getLastIntTime() != null) {
					lapTable.getItems().add(new LapTableList(
							lapTimer.getLastTime().getLapNumber(), 
							Integer.toString(lapTimer.getLastTime().getMinutes())+":"+Integer.toString(lapTimer.getLastTime().getSeconds())+":"+Integer.toString(lapTimer.getLastTime().getmSeconds()), 
							Integer.toString(lapTimer.getLastIntTime().getMinutes())+":"+Integer.toString(lapTimer.getLastIntTime().getSeconds())+":"+Integer.toString(lapTimer.getLastTime().getmSeconds())
							));
				}
				else {
					lapTable.getItems().add(new LapTableList(
							lapTimer.getLastTime().getLapNumber(), 
							Integer.toString(lapTimer.getLastTime().getMinutes())+":"+Integer.toString(lapTimer.getLastTime().getSeconds())+":"+Integer.toString(lapTimer.getLastTime().getmSeconds()),
							"No intermediate"
							));
				}
			}
		}
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
