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
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.chart.XYChart.Data;

public class EngineScreenController extends Controller {
	private final String IN_OIL_TEMP = "tOil_In";
	private final String OUT_OIL_TEMP = "tOil_Out";
	private final String IN_WATER_L_TEMP = "tWaterL_In";
	private final String OUT_WATER_L_TEMP = "tWaterL_Out";
	private final String LAST_TIME = "time";
	private Series<String, Double> oilTempIn, oilTempOut;
	private Series<String, Double> waterTempLIN, waterTempLOut;
	private ObservableList<XYChart.Series<String,Double>> waterTempChartData, oiltempChartData;
	private double lastTime;
	private int elCount = 0;
	private int element = 0;
	@FXML
	private LineChart<String, Double> oilTempChart, waterTempChart;
	@FXML
	private Label oilTempInLabel, oilTempOutLabel, waterTempInLLabel, waterTempOutLLabel;
	@FXML
	private TableView table;
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
		SetDebug();
		SetState();
		SetChannel();
		SetCommand();
		SetError();
		SetLap();
		SetTS();
		SetChart();
    }
	
	@Override
	public void SetDebug() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void EditDebug(Debug debug) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void SetState() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void EditState(State state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void SetChannel() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void EditChannel(Channel channel) {
		// TODO Auto-generated method stub
		if (channel.getLastElems(1).size()==0) {
			//No channel data present, exit the function
		}
		else if(channel.getName().equals(LAST_TIME)) {
			Platform.runLater(new Runnable() {
			    @Override
			    public void run() {
			    	lastTime = channel.getLastElems(1).get(0);
			    	element++;
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
			    	//oilTempIn.getData().add(new Data<>(Double.toString(lastTime), channel.getLastElems(1).get(0)));
			    	oilTempIn.getData().add(new Data<>(Integer.toString(element), channel.getLastElems(1).get(0)));
			    	oilTempInLabel.setText(Double.toString(channel.getLastElems(1).get(0)));
			    }
			});
		}
		else if(channel.getName().equals(OUT_OIL_TEMP)) {
			Platform.runLater(new Runnable() {
			    @Override
			    public void run() {
			    	//oilTempOut.getData().add(new Data<>(Double.toString(lastTime), channel.getLastElems(1).get(0)));
			    	oilTempOut.getData().add(new Data<>(Integer.toString(element), channel.getLastElems(1).get(0)));
			    	oilTempOutLabel.setText(Double.toString(channel.getLastElems(1).get(0)));
			    }
			});
		}
		else if(channel.getName().equals(IN_WATER_L_TEMP)) {
			Platform.runLater(new Runnable() {
			    @Override
			    public void run() {
			    	//waterTempLIN.getData().add(new Data<>(Double.toString(lastTime), channel.getLastElems(1).get(0)));
			    	waterTempLIN.getData().add(new Data<>(Integer.toString(element), channel.getLastElems(1).get(0)));
			    	waterTempInLLabel.setText(Double.toString(channel.getLastElems(1).get(0)));
			    }
			});
		}
		else if(channel.getName().equals(OUT_WATER_L_TEMP)) {
			Platform.runLater(new Runnable() {
			    @Override
			    public void run() {
			    	//waterTempLOut.getData().add(new Data<>(Double.toString(lastTime), channel.getLastElems(1).get(0)));
			    	waterTempLOut.getData().add(new Data<>(Integer.toString(element), channel.getLastElems(1).get(0)));
			    	waterTempOutLLabel.setText(Double.toString(channel.getLastElems(1).get(0)));
			    }
			});
		}
	}

	@Override
	public void SetCommand() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void EditCommand(Command command) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void SetError() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void EditError(Error error) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void SetLap() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void EditLap(LapTimer lapTimer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void SetTS() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void EditTS(Threshold thresholdState) {
		// TODO Auto-generated method stub
		
	}
	
	private void SetChart() {
		waterTempChartData = FXCollections.observableArrayList();
		oiltempChartData = FXCollections.observableArrayList();
		oilTempIn = new Series<>();
		oilTempOut = new Series<>();
		waterTempLIN = new Series<>();
		waterTempLOut = new Series<>();
		oilTempIn.setName("Oil Temp In");
		oilTempOut.setName("Oil Temp Out");
		waterTempLIN.setName("Water temp left In");
		waterTempLOut.setName("Water temp left Out");
		waterTempChartData.add(waterTempLIN);
		waterTempChartData.add(waterTempLOut);
		oiltempChartData.add(oilTempIn);
		oiltempChartData.add(oilTempOut);
		waterTempChart.setData(waterTempChartData);
		oilTempChart.setData(oiltempChartData);
	}
}
