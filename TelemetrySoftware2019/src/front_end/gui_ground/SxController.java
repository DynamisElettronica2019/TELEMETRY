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
	private StateList[] stateArray;
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("View is now loaded!");
    }
	
	public void AddState(State state) {
		String newName = state.getName();
		int index = newName.charAt(0)-'A';
		char str = (char) ('A' + index);
		Boolean newValue = state.getValue();
		states.set(index, new StateList(String.valueOf(str), newValue));
		stateTable.refresh();
	}
	
	public void SetState() {
		stateTable.setItems(states);
		stateColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
		for(int i=0; i<8; i++)
		{
			char str = (char) ('A' + i);
			stateTable.getItems().add(new StateList(String.valueOf(str), false));
		}
	}
	
	public void ButtonClick()
	{
		System.err.println("Pollo");
	}
}
