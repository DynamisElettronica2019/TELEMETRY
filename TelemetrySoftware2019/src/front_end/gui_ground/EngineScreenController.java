package front_end.gui_ground;

import java.net.URL;
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
import javafx.scene.control.Label;

public class EngineScreenController extends Controller {
	private final String IN_OIL_TEMP = "tOil_In";
	private final String OUT_OIL_TEMP = "tOil_Out";
	private final String IN_WATER_L_TEMP = "tWaterL_In";
	private final String OUT_WATER_L_TEMP = "tWaterL_Out";
	private final String EXHAUST_TEMP_1 = "tExhaust_1";
	private final String EXHAUST_TEMP_2 = "tExhaust_2";
	private final String OIL_PRESS = "pOil";
	private final String LAST_TIME = "time";
	private Series<String, Double> oilTempIn, oilTempOut;
	private Series<String, Double> waterTempLIN, waterTempLOut;
	private Series<String, Double> exhaust1Temp, exhaust2Temp;
	private Series<String, Double> oilPress;
	private ObservableList<XYChart.Series<String,Double>> waterTempChartData, oiltempChartData, exhaustTempChartData, pressChartData;
	private int elCount = 0;
	@FXML
	private LineChart<String, Double> oilTempChart, waterTempChart, exhaustTempChart, pressChart;
	@FXML
	private Label oilTempInLabel, oilTempOutLabel, waterTempInLLabel, waterTempOutLLabel, exhaust1TempLabel, exhaust2TempLabel, oilPressLabel;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		setChart();
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
		if (channel.getLastElems(1).size()==0) {
			//No channel data present, exit the function
		}
		else if(channel.getName().equals(LAST_TIME)) {
			Platform.runLater(new Runnable() {
			    @Override
			    public void run() {
			    	if(elCount == 8) {
			    		oilTempIn.getData().remove(0);
			    		oilTempOut.getData().remove(0);
			    		//waterTempLIN.getData().remove(0);
			    		//waterTempLOut.getData().remove(0);
			    	}
			    	else {
			    		elCount++;
			    	}
			    }
			});
		}
		else if(channel.getName().equals(IN_OIL_TEMP)) {
			Platform.runLater(new Runnable() {
			    @Override
			    public void run() {
			    	oilTempIn.getData().add(channel.getLastChartEl());
			    	oilTempInLabel.setText(Double.toString(channel.getLastElems(1).get(0)));
			    }
			});
		}
		else if(channel.getName().equals(OUT_OIL_TEMP)) {
			Platform.runLater(new Runnable() {
			    @Override
			    public void run() {
			    	oilTempOut.getData().add(channel.getLastChartEl());
			    	oilTempOutLabel.setText(Double.toString(channel.getLastElems(1).get(0)));
			    }
			});
		}
		else if(channel.getName().equals(IN_WATER_L_TEMP)) {
			Platform.runLater(new Runnable() {
			    @Override
			    public void run() {
			    	waterTempLIN.getData().add(channel.getLastChartEl());
			    	waterTempInLLabel.setText(Double.toString(channel.getLastElems(1).get(0)));
			    }
			});
		}
		else if(channel.getName().equals(OUT_WATER_L_TEMP)) {
			Platform.runLater(new Runnable() {
			    @Override
			    public void run() {
			    	waterTempLOut.getData().add(channel.getLastChartEl());
			    	waterTempOutLLabel.setText(Double.toString(channel.getLastElems(1).get(0)));
			    }
			});
		}
		else if(channel.getName().equals(EXHAUST_TEMP_1)) {
			Platform.runLater(new Runnable() {
			    @Override
			    public void run() {
			    	exhaust1Temp.getData().add(channel.getLastChartEl());
			    	exhaust1TempLabel.setText(Double.toString(channel.getLastElems(1).get(0)));
			    }
			});
		}
		else if(channel.getName().equals(EXHAUST_TEMP_2)) {
			Platform.runLater(new Runnable() {
			    @Override
			    public void run() {
			    	exhaust2Temp.getData().add(channel.getLastChartEl());
			    	exhaust2TempLabel.setText(Double.toString(channel.getLastElems(1).get(0)));
			    }
			});
		}
		else if(channel.getName().equals(OIL_PRESS)) {
			Platform.runLater(new Runnable() {
			    @Override
			    public void run() {
			    	oilPress.getData().add(channel.getLastChartEl());
			    	oilPressLabel.setText(Double.toString(channel.getLastElems(1).get(0)));
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
	}
}
