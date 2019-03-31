package front_end.gui_ground;

import java.net.URL;
import java.time.LocalDateTime;
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
	ObservableList<Integer> elementNumberList;
	@FXML
	private ComboBox<Integer> exhaustTempValues, pressValues, oilTempValues, waterTempValues;
	@FXML
	private LineChart<String, Double> oilTempChart, waterTempChart, exhaustTempChart, pressChart;
	@FXML
	private Label oilTempInLabel, oilTempOutLabel, waterTempInLLabel, waterTempOutLLabel, exhaust1TempLabel, exhaust2TempLabel, oilPressLabel;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		setChart();
		elementNumberList = FXCollections.observableArrayList(10, 50, 100, 500, 1000);
		exhaustTempValues.setItems(elementNumberList);
		pressValues.setItems(elementNumberList);
		oilTempValues.setItems(elementNumberList);
		waterTempValues.setItems(elementNumberList);
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
			    	if(chartChannelMap.get(channel.getName()) == null) {
			    		
			    	}
			    	else {
			    		if(chartChannelMap.get(channel.getName()).getData().size() > 20) {
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
}
