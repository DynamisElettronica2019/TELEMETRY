package front_end.gui_ground;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import back_end.Channel;
import back_end.Command;
//import back_end.Data;
import back_end.Debug;
import back_end.Error;
import back_end.LapTimer;
import back_end.State;
import back_end.Threshold;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.chart.XYChart.Data;

public class EngineScreenController extends Controller {
	private Map<String, Label> chartLabelMap = new HashMap<>();
	private Map<String, Series<String, Double>> chartChannelMap = new HashMap<>();
	private Series<String, Double> oilTempIn, oilTempOut;
	private Series<String, Double> waterTempLIN, waterTempLOut;
	private Series<String, Double> exhaust1Temp, exhaust2Temp;
	private Series<String, Double> oilPress;
	private ObservableList<XYChart.Series<String,Double>> waterTempChartData, oiltempChartData, exhaustTempChartData, pressChartData;
	private ObservableList<Integer> elementNumberList;
	private Boolean[] toLoadArray = {false, false, false, false, false, false, false};
	private Map<String, Integer> loadArrayMap = new HashMap<>();
	@FXML
	private ComboBox<Integer> numberValues;
	@FXML
	private LineChart<String, Double> oilTempChart, waterTempChart, exhaustTempChart, pressChart;
	@FXML
	private Label oilTempInLabel, oilTempOutLabel, waterTempInLLabel, waterTempOutLLabel, exhaust1TempLabel, exhaust2TempLabel, oilPressLabel;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		setChart();
		elementNumberList = FXCollections.observableArrayList(10, 50, 100, 500, 1000);
		numberValues.setItems(elementNumberList);
		numberValues.getSelectionModel().select(1);
		
		loadArrayMap.put("tOil_In", 0);
		loadArrayMap.put("tOil_Out", 1);
		loadArrayMap.put("tWaterL_In", 2);
		loadArrayMap.put("tWaterL_Out", 3);
		loadArrayMap.put("tExhaust_1", 4);
		loadArrayMap.put("tExhaust_2", 5);
		loadArrayMap.put("pOil", 6);
		
		numberValues.valueProperty().addListener(new ChangeListener<Integer>() {
			@Override
			public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {		
				while (newValue < oilTempIn.getData().size()) {
					oilTempIn.getData().remove(0);
				}
				while (newValue < oilTempOut.getData().size()) {
					oilTempOut.getData().remove(0);
				}
				while (newValue < waterTempLIN.getData().size()) {
					waterTempLIN.getData().remove(0);
				}
				while (newValue < waterTempLOut.getData().size()) {
					waterTempLOut.getData().remove(0);
				}
				while (newValue < exhaust1Temp.getData().size()) {
					exhaust1Temp.getData().remove(0);
				}
				while (newValue < exhaust2Temp.getData().size()) {
					exhaust2Temp.getData().remove(0);
				}
				while (newValue < oilPress.getData().size()) {
					oilPress.getData().remove(0);
				}
				for (int i=0; i<7; i++) {
					toLoadArray[i] = true;
				}
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
			Platform.runLater(new Runnable() {
			    @Override
			    public void run() {
			    	if(chartChannelMap.get(channel.getName()) != null) {
			    		if (toLoadArray[loadArrayMap.get(channel.getName())]) {
			    			chartChannelMap.get(channel.getName()).setData(getLastnChartElem(channel));	    			
			    			toLoadArray[loadArrayMap.get(channel.getName())] = false;
			    		}
			    		if(chartChannelMap.get(channel.getName()).getData().size() > numberValues.getValue()) {
					    	chartChannelMap.get(channel.getName()).getData().remove(0);
					    }
				    	chartChannelMap.get(channel.getName()).getData().add(getLastChartElem(channel));
					    chartLabelMap.get(channel.getName()).setText(Double.toString(channel.getLastElems(1).get(0)));	
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
	
	private void setChart() {
		waterTempChartData = FXCollections.observableArrayList();
		oiltempChartData = FXCollections.observableArrayList();
		exhaustTempChartData = FXCollections.observableArrayList();
		pressChartData = FXCollections.observableArrayList();
		
		oilTempIn = new Series<>();
		oilTempOut = new Series<>();
		waterTempLIN = new Series<>();
		waterTempLOut = new Series<>();
		exhaust1Temp = new Series<>();
		exhaust2Temp = new Series<>();
		oilPress = new Series<>();
		
		oilTempIn.setName("Oil Temp In");
		oilTempOut.setName("Oil Temp Out");
		waterTempLIN.setName("Water temp left In");
		waterTempLOut.setName("Water temp left Out");
		exhaust1Temp.setName("Exhaust 1 Temp");
		exhaust2Temp.setName("Exhaust 2 Temp");
		oilPress.setName("Oil Pression");
		
		waterTempChartData.add(waterTempLIN);
		waterTempChartData.add(waterTempLOut);
		oiltempChartData.add(oilTempIn);
		oiltempChartData.add(oilTempOut);
		exhaustTempChartData.add(exhaust1Temp);
		exhaustTempChartData.add(exhaust2Temp);
		pressChartData.add(oilPress);
		
		waterTempChart.setData(waterTempChartData);
		oilTempChart.setData(oiltempChartData);
		exhaustTempChart.setData(exhaustTempChartData);
		pressChart.setData(pressChartData);
		
		chartLabelMap.put("tOil_In", oilTempInLabel);
		chartLabelMap.put("tOil_Out", oilTempOutLabel);
		chartLabelMap.put("tWaterL_In", waterTempInLLabel);
		chartLabelMap.put("tWaterL_Out", waterTempOutLLabel);
		chartLabelMap.put("tExhaust_1", exhaust1TempLabel);
		chartLabelMap.put("tExhaust_2", exhaust2TempLabel);
		chartLabelMap.put("pOil", oilPressLabel);
		
		chartChannelMap.put("tOil_In", oilTempIn);
		chartChannelMap.put("tOil_Out", oilTempOut);
		chartChannelMap.put("tWaterL_In", waterTempLIN);
		chartChannelMap.put("tWaterL_Out", waterTempLOut);
		chartChannelMap.put("tExhaust_1", exhaust1Temp);
		chartChannelMap.put("tExhaust_2", exhaust2Temp);
		chartChannelMap.put("pOil", oilPress);
	}
	
	private Data<String, Double> getLastChartElem(Channel channel) {
		LocalDateTime ts = channel.getLastTs();
		return new Data<String, Double>(ts.getHour()+":"+ts.getMinute()+":"+ts.getSecond()+":"+ts.getNano()/1000000, channel.getLastElems());
	}
	
	private ObservableList<Data<String, Double>> getLastnChartElem(Channel channel) {
		ObservableList<Data<String, Double>> newDataList = FXCollections.observableArrayList();
		ArrayList<Double> channelDataList = channel.getLastElems(numberValues.getValue());
		for (int i=0; i<channelDataList.size(); i++) {
			newDataList.add(new Data<String, Double>(Integer.toString(i), channelDataList.get(i)));
		}
		return newDataList;
	}
}
