package front_end.gui_ground;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import back_end.State;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class SxController implements Initializable {
	public TableView<StateList> stateTable;
	public TableColumn<StateList, String> stateColumn;
	public TableColumn<StateList, Boolean> valueColumn;
	public ObservableList<StateList> states = FXCollections.observableArrayList();
	private StateList[] stateArray;
	private Map<String, Integer> indexMap = new HashMap<>();
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("View is now loaded!");
    }
	
	public void AddState(State state) {
		String newName = state.getName();
		Boolean newValue = state.getValue();
		states.set(indexMap.get(newName), new StateList(newName, newValue));
		stateTable.refresh();
	}
	
	public void SetState() {
		stateTable.setItems(states);
		stateColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
		indexMap.put("A", 0);
		indexMap.put("B", 1);
		indexMap.put("C", 2);
		indexMap.put("D", 3);
		indexMap.put("E", 4);
		indexMap.put("F", 5);
		indexMap.put("G", 6);
		indexMap.put("H", 7);
		stateTable.getItems().add(new StateList("A", false));
		stateTable.getItems().add(new StateList("B", false));
		stateTable.getItems().add(new StateList("C", false));
		stateTable.getItems().add(new StateList("D", false));
		stateTable.getItems().add(new StateList("E", false));
		stateTable.getItems().add(new StateList("F", false));
		stateTable.getItems().add(new StateList("G", false));
		stateTable.getItems().add(new StateList("H", false));
	}
	
	public void ButtonClick()
	{
		System.err.println("Pollo");
	}
}
