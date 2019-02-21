package front_end.gui_ground;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import back_end.Channel;
import back_end.Debug;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class DxController implements Initializable {
	public TableView<ChannelList> mainTable;
	public TableColumn<ChannelList, String> channelColumn;
	public TableColumn<ChannelList, Double> valueColumn;
	public ObservableList<ChannelList> channels = FXCollections.observableArrayList();
	public ObservableList<ChannelList> debug = FXCollections.observableArrayList();
	private Map<String, Integer> indexMap = new HashMap<>();
	public ToggleGroup radio;
	public RadioMenuItem channelsButton, debugButton;
	
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
		indexMap.put("Recognizer", 0);
		indexMap.put("time", 1);
		indexMap.put("vWheelFR", 2);
		indexMap.put("vWheelFL", 3);
		indexMap.put("vWheelRR", 4);
		indexMap.put("vWheelRL", 5);
		indexMap.put("tWaterL_In", 6);
		indexMap.put("tWaterL_Out", 7);
		indexMap.put("tOil_In", 8);
		indexMap.put("tOil_Out", 9);
		indexMap.put("tWaterEngine", 10);
		indexMap.put("Vbattery", 11);
		indexMap.put("nGear", 12);
		indexMap.put("nRPM", 13);
		indexMap.put("XTPS", 14);
		indexMap.put("XPedal", 15);
		indexMap.put("vCar", 16);
		indexMap.put("XSlipTarget", 17);
		indexMap.put("XSlip", 18);
		indexMap.put("bFuel", 19);
		indexMap.put("bFan", 20);
		indexMap.put("bDutyWaterPump", 21);
		indexMap.put("bLaunch", 22);
		indexMap.put("pFuel", 23);
		indexMap.put("pOil", 24);
		indexMap.put("rLambda", 25);
		indexMap.put("FlagSMOT", 26);
		indexMap.put("bDiagIgn_1", 27);
		indexMap.put("bDiagIgn_2", 28);
		indexMap.put("tExhaust_1", 29);
		indexMap.put("tExhaust_2", 30);
		indexMap.put("xWheel_FR", 31);
		indexMap.put("fLoad_FR", 32);
		indexMap.put("pBrakeFront", 33);
		indexMap.put("xWheel_FL", 34);
		indexMap.put("fLoad_FL", 35);
		indexMap.put("pBrakeRear", 36);
		indexMap.put("aSteering", 37);

		mainTable.getItems().add(new ChannelList("Recognizer", 0));
		mainTable.getItems().add(new ChannelList("time", 0));
		mainTable.getItems().add(new ChannelList("vWheelFR", 0));
		mainTable.getItems().add(new ChannelList("vWheelFL", 0));
		mainTable.getItems().add(new ChannelList("vWheelRR", 0));
		mainTable.getItems().add(new ChannelList("vWheelRL", 0));
		mainTable.getItems().add(new ChannelList("tWaterL_In", 0));
		mainTable.getItems().add(new ChannelList("tWaterL_Out", 0));
		
	}
    
    public void EditDebug(Debug deb) {
    	debug.set(indexMap.get(deb.getName()), new ChannelList(deb.getName(), deb.getValue()));
		mainTable.refresh();
	}
    
    public void SetDebug() {
		//TODO
    	indexMap.put("A", 0);
		indexMap.put("B", 1);
		indexMap.put("C", 2);
		indexMap.put("D", 3);
		indexMap.put("E", 4);
		indexMap.put("F", 5);
		indexMap.put("G", 6);
		indexMap.put("H", 7);
		debug.add(new ChannelList("A", 0));
		debug.add(new ChannelList("B", 0));
		debug.add(new ChannelList("C", 0));
		debug.add(new ChannelList("D", 0));
		debug.add(new ChannelList("E", 0));
		debug.add(new ChannelList("F", 0));
		debug.add(new ChannelList("G", 0));
		debug.add(new ChannelList("H", 0));
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