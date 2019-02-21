package front_end.gui_ground;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import back_end.CommandSender;
import back_end.State;
import configuration.ConfReader;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class SxController implements Initializable {
	public TextField commandField;
	public TableView<StateList> stateTable;
	public TableColumn<StateList, String> stateColumn;
	public TableColumn<StateList, Boolean> valueColumn;
	public ObservableList<StateList> states = FXCollections.observableArrayList();
	private Map<String, Integer> indexMap = new HashMap<>();
	private CommandSender commandSender;
	private ArrayList<String> stateList;
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("View sx is now loaded!");
    }
	
	public void AddState(State state) {
		String newName = state.getName();
		Boolean newValue = state.getValue();
		states.set(indexMap.get(newName), new StateList(newName, newValue));
		stateTable.refresh();
	}
	
	public void passCommandSender(CommandSender commandSender) {
		this.commandSender = commandSender;
	}
	
	public void SetState() {
		stateTable.setItems(states);
		stateColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
		stateList = ConfReader.getNames("states");
		for (int i = 0; i < stateList.size(); i++) {
			indexMap.put(stateList.get(i), i);
			stateTable.getItems().add(new StateList(stateList.get(i), false));
		}	
	}
	
	public void ButtonClick()
	{
		commandSender.sendCommand(commandField.getText(), "");
	}
}
