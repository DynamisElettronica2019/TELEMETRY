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
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.chart.XYChart.Data;

public class EngineScreenController extends Controller {
	private final String IN_OIL_TEMP = "tOil_In";
	private final String OUT_OIL_TEMP = "tOil_Out";
	private final String IN_WATER_L_TEMP = "tWaterL_In";
	private final String OUT_WATER_L_TEMP = "tWaterL_Out";
	private Series<String, Double> oilTempIn, oilTempOut;
	private Series<String, Double> waterTempLIN, waterTempLOut;
	private ObservableList<XYChart.Series<String,Double>> waterTempChartData;
	private int element = 0;
	@FXML
	private LineChart<String, Double> oilTempChart, waterTempChart;
	
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
		else if(channel.getName().equals(IN_OIL_TEMP)) {
			Platform.runLater(new Runnable() {
			    @Override
			    public void run() {
			    	oilTempIn.getData().add(new Data<String, Double>(Integer.toString(element), channel.getLastElems(1).get(0)));
			    	element++;
			    }
			});
		}
		else if(channel.getName().equals(OUT_OIL_TEMP)) {
			Platform.runLater(new Runnable() {
			    @Override
			    public void run() {
			    	oilTempOut.getData().add(new Data<String, Double>(Integer.toString(element), channel.getLastElems(1).get(0)));
			    	element++;
			    }
			});
		}
		else if(channel.getName().equals(IN_WATER_L_TEMP)) {
			Platform.runLater(new Runnable() {
			    @Override
			    public void run() {
			    	waterTempLIN.setData(value);
			    }
			});
		}
		else if(channel.getName().equals(OUT_WATER_L_TEMP)) {
			Platform.runLater(new Runnable() {
			    @Override
			    public void run() {
			    	waterTempLOut.setData(value);
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
		oilTempIn = new Series<>();
		oilTempOut = new Series<>();
		oilTempChart.getData().add(oilTempIn);
		oilTempChart.getData().add(oilTempOut);
		waterTempLIN = new Series<>();
		waterTempChartData.add(waterTempLIN);
		waterTempChartData.add(waterTempLOut);
		waterTempChart.setData(waterTempChartData);
	}
}
