package front_end.gui_ground;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import back_end.State;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

public class SxController implements Initializable {
	public TableView<StateList> stateTable;
	public TableColumn<StateList, String> stateColumn;
	public TableColumn<StateList, Boolean> valueColumn;
	public ObservableList<StateList> states = FXCollections.observableArrayList();
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("View is now loaded!");
    }
	
	public void AddState(State state) {
		String newName = state.getName();
		Boolean newValue = state.getValue();
		stateTable.getItems().add(new StateList(newName, newValue));
		stateTable.getItems().add(new StateList("Pollo", true));
		stateTable.refresh();
	}
	
	public void SetState() {
		stateTable.setItems(states);
		stateColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
		stateTable.getItems().add(new StateList("Pollo", true));
	}
	
	public void ButtonClick()
	{
		System.err.println("Pollo");
	}
}
