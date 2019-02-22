package front_end.gui_row;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import back_end.Channel;
import back_end.Debug;
import configuration.ConfReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

public class DxController implements Initializable {
	public TableView<ChannelList> mainTable;
	public TableColumn<ChannelList, String> channelColumn;
	public TableColumn<ChannelList, Double> valueColumn;
	public ObservableList<ChannelList> channels = FXCollections.observableArrayList();
	public ObservableList<ChannelList> debug = FXCollections.observableArrayList();
	private Map<String, Integer> indexMap = new HashMap<>();
	public ToggleGroup radio;
	public RadioMenuItem channelsButton, debugButton;
	private ArrayList<String> channelList;
	private ArrayList<String> debugList;
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("View dx is now loaded!");
    }
    
    public void EditChannel(Channel chan) {
		channels.set(indexMap.get(chan.getName()), new ChannelList(chan.getName(), chan.getLastElems(1).get(0)));
		mainTable.refresh();
	}
    
    public void SetChannel() {
		mainTable.setItems(channels);
		channelColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
		
		channelList = ConfReader.getNames("channels");
		for (int i = 0; i < channelList.size(); i++) {
			indexMap.put(channelList.get(i), i);
			mainTable.getItems().add(new ChannelList(channelList.get(i), 0));
		}	
	}
    
    public void EditDebug(Debug deb) {
    	debug.set(indexMap.get(deb.getName()), new ChannelList(deb.getName(), deb.getValue()));
		mainTable.refresh();
	}
    
    public void SetDebug() {
    	debugList = ConfReader.getNames("debug");
		for (int i = 0; i < debugList.size(); i++) {
			indexMap.put(debugList.get(i), i);
			debug.add(new ChannelList(debugList.get(i), 0));
		}	
	}
    
    public void channelButtonClick() {
    	mainTable.setItems(channels);
    	channelColumn.setText("Channels");
	}
    
    public void debugButtonClick() {
    	mainTable.setItems(debug);
    	channelColumn.setText("Debug");
	}
}