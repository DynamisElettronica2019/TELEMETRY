package front_end.gui_ground;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ErrorScreenController extends Controller {
	private ArrayList<String> errorList;
	private ObservableList<ErrorTableList> errorObsList = FXCollections.observableArrayList();
	private Map<String, Integer> errorMap = new HashMap<>();
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	@FXML
	private TableView<ErrorTableList> errorTable;
	@FXML
	private TableColumn<ErrorTableList, String> nameColumn, lastOccColumn, nOccColumn;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		
		//Initialize errors
		errorTable.setItems(errorObsList);
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		lastOccColumn.setCellValueFactory(new PropertyValueFactory<>("lastOcc"));
		lastOccColumn.setCellValueFactory(new PropertyValueFactory<>("nOcc"));
		errorList = ConfReader.getErrorNames("error");
		for (int i = 0; i < errorList.size(); i++) {
			errorMap.put(errorList.get(i), i);
			errorTable.getItems().add(new ErrorTableList(errorList.get(i), "No Occurrence", 0));
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
		if(errorMap.get(error.getName()) != null) {
			errorObsList.set(errorMap.get(error.getName()), new ErrorTableList(error.getName(), error.getLastOcc().format(formatter), error.getNumbOcc()));	
			errorTable.refresh();
			System.out.println("WE");
		}
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
