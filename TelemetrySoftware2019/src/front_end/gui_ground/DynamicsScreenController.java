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
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;

public class DynamicsScreenController extends Controller {
	private Boolean isPaused = false;
	private Series<String, Double> rpm, speed, sw, gear;
	private ObservableList<XYChart.Series<String,Double>> rpmChartData, speedChartData, swChartData, gearChartData;
	private ObservableList<Integer> elementNumberList;
	private String timePattern;
	private DateTimeFormatter timeColonFormatter;
	private ArrayList<String> channelList;
	private ArrayList<Boolean> toLoadList = new ArrayList<Boolean>();
	private Map<String, Integer> loadArrayMap = new HashMap<>();
	private Map<String, Series<String, Double>> chartChannelMap = new HashMap<>();
	private Map<String, Label> chartLabelMap = new HashMap<>();
	@FXML
	private LineChart<String, Double> rpmChart, speedChart, swChart, gearChart;
	@FXML
	private ComboBox<Integer> numberValues;
	@FXML
	private Label rpmLabel, speedLabel, swLabel, gearLabel;
	@FXML
	private ToggleButton pauseButton;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		
		/*
		 * N. data to visualize
		 */
		elementNumberList = FXCollections.observableArrayList(10, 50, 100, 500, 1000);
		numberValues.setItems(elementNumberList);
		numberValues.getSelectionModel().select(1);
		
		/*
		 * Time formatting
		 */
		timePattern = "HH:mm:ss.SSS";
		timeColonFormatter = DateTimeFormatter.ofPattern(timePattern);
		
		/*
		 * Load the channel names and map them to the update list
		 */
		channelList = ConfReader.getNames("channels");
		for (int i=0; i<channelList.size(); i++) {
			toLoadList.add(true);
			loadArrayMap.put(channelList.get(i), i);
		}
		
		/*
		 * Do not show x axis
		 */
		rpmChart.getXAxis().setTickLabelsVisible(false);
		rpmChart.getXAxis().setTickMarkVisible(false);
		speedChart.getXAxis().setTickLabelsVisible(false);
		speedChart.getXAxis().setTickMarkVisible(false);
		swChart.getXAxis().setTickLabelsVisible(false);
		swChart.getXAxis().setTickMarkVisible(false);
		
		/*
		 *  Chart initialization
		 */
		rpmChartData = FXCollections.observableArrayList();
		speedChartData = FXCollections.observableArrayList();
		swChartData = FXCollections.observableArrayList();
		gearChartData = FXCollections.observableArrayList();
		
		rpm = new Series<>();
		speed = new Series<>();
		sw = new Series<>();
		gear = new Series<>();
		
		rpm.setName("RPM");
		speed.setName("Speed");
		sw.setName("Steering Wheel Angle");
		gear.setName("Gear");
		
		rpmChartData.add(rpm);
		speedChartData.add(speed);
		swChartData.add(sw);
		gearChartData.add(gear);
		
		rpmChart.setData(rpmChartData);
		speedChart.setData(speedChartData);
		swChart.setData(swChartData);
		gearChart.setData(gearChartData);
		
		chartLabelMap.put("nRPM", rpmLabel);
		chartLabelMap.put("vCar", speedLabel);
		chartLabelMap.put("aSteering", swLabel);
		chartLabelMap.put("nGear", gearLabel);
		
		chartChannelMap.put("nRPM", rpm);
		chartChannelMap.put("vCar", speed);
		chartChannelMap.put("aSteering", sw);
		chartChannelMap.put("nGear", gear);
		
		/*
		 * Listener on the number of values to display
		 * Removes the unnecessary data if the newValue is lower than before
		 * Set to update the channel load list
		 */
		numberValues.valueProperty().addListener(new ChangeListener<Integer>() {
			@Override
			public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {	
				for (int i=0; i<toLoadList.size(); i++) {
					toLoadList.set(i, true);
				}
				view.getViewLoader().load();
			}
		 });  
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
		if(!channel.isEmpty()){
			if (!pauseButton.isSelected()) {
					Platform.runLater(new Runnable() {
					    @Override
					    public void run() {
					    	if(chartChannelMap.get(channel.getName()) != null) {
					    		if (toLoadList.get(loadArrayMap.get(channel.getName()))) {
					    			chartChannelMap.get(channel.getName()).setData(getLastnChartElem(channel));	    			
					    			toLoadList.set(loadArrayMap.get(channel.getName()), false);
					    		}
					    		else {
					    			chartChannelMap.get(channel.getName()).getData().add(getLastChartElem(channel));
								    chartLabelMap.get(channel.getName()).setText(Double.toString(channel.getLastElems(1).get(0)));	
					    		}
					    		if(chartChannelMap.get(channel.getName()).getData().size() > numberValues.getValue()) {
							    	chartChannelMap.get(channel.getName()).getData().remove(0);
							    }
					    	}	    	
					    }
					});
			}
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

	@FXML
	private void PressButtonClick() {
	}
	
	/*
	 *  Create a new data element ready for getting added to the chart
	 *  Timestamp on the x axis, value on the y
	 */
	private Data<String, Double> getLastChartElem(Channel channel) {
		LocalDateTime ts = channel.getLastTs();
		return new Data<String, Double>(ts.format(timeColonFormatter), channel.getLastElems());
	}
	
	/*
	 *  Create a new list of data for replacing the old list when value of numbers to display is changed
	 *  Timestamp is on the x axis and value on the y
	 */
	private ObservableList<Data<String, Double>> getLastnChartElem(Channel channel) {
		ObservableList<Data<String, Double>> newDataList = FXCollections.observableArrayList();
		ArrayList<Double> channelDataList = channel.getLastElems(numberValues.getValue());
		ArrayList<LocalDateTime> tsList = channel.getLastTs(numberValues.getValue());
		for (int i=0; i<channelDataList.size(); i++) {
			newDataList.add(new Data<String, Double>(tsList.get(i).format(timeColonFormatter), channelDataList.get(i)));
		}
		return newDataList;
	}
}
