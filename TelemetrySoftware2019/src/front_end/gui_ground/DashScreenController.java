package front_end.gui_ground;

import java.net.URL;
import java.util.ResourceBundle;

import back_end.Channel;
import back_end.Command;
import back_end.Debug;
import back_end.Error;
import back_end.LapTimer;
import back_end.State;
import back_end.Threshold;
import configuration.ConfReader;
import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.Section;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.ValueAxis;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class DashScreenController extends Controller {
	private final String RPM_CHANNEL = "RPM";
	private final String SPEED_CHANNEL = "VH SPEED";
	private final String FUEL_CHANNEL = "L FUEL";
	private final String WATER_CHANNEL = "T WATER ENGINE";
	private final String SW_ANGLE_CHANNEL = "SW ANGLE";
	private final String THROTTLE_CHANNEL = "TPS";
	private final String BRAKE_CHANNEL = "BPS FRONT";
	private final String CLUTCH_CHANNEL = "CLUTCH";
	private final String GPS_LATITUDE = "GPS LATITUDE MINUTES";
	private final String GPS_LONGITUDE = "GPS LONGITUDE MINUTES";
	private final String GEAR_CHANNEL = "GEAR";
	
	Series<Double, Double> series = new Series<Double, Double>();
	Double latLastValue;
	Double latOffset, lonOffset;
	Boolean toCalibrate = true;
	
	@FXML
	Gauge rpm, speed, fuel, water;
	@FXML
	ProgressBar throttle, brake, clutch;
	@FXML
	ImageView wheel;
	@FXML
	LineChart<Double, Double> gps;
	@FXML
	Pane wheelRot;
	@FXML
	Label gear;
	@FXML
	Circle circle1, circle2, circle3, circle4, circle5, circle6, circle7, circle8, circle9, circle10, circle11, circle12;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		
		rpm.addSection(new Section(10, 12, Color.web("0xc80000ff")));
		water.addSection(new Section(95, 120, Color.web("0xc80000ff")));
		fuel.addSection(new Section(0, 1, Color.web("0xc80000ff")));
		
		gps.getXAxis().setAutoRanging(false);
		gps.getYAxis().setAutoRanging(false);
		((ValueAxis<Double>) gps.getXAxis()).setUpperBound(5000);
		((ValueAxis<Double>) gps.getXAxis()).setLowerBound(-5000);
		((ValueAxis<Double>) gps.getYAxis()).setUpperBound(5000);
		((ValueAxis<Double>) gps.getYAxis()).setLowerBound(-5000);
		
		((ValueAxis<Double>) gps.getXAxis()).setTickLabelsVisible(false);
		((ValueAxis<Double>) gps.getXAxis()).setMinorTickVisible(false);
		((ValueAxis<Double>) gps.getXAxis()).setTickMarkVisible(false);
		((ValueAxis<Double>) gps.getYAxis()).setTickLabelsVisible(false);
		((ValueAxis<Double>) gps.getYAxis()).setMinorTickVisible(false);
		((ValueAxis<Double>) gps.getYAxis()).setTickMarkVisible(false);
		
		gps.getData().add(series);
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
			if (channel.getName().equals(RPM_CHANNEL)) {
				Platform.runLater(new Runnable() {
				    @Override
				    public void run() {
				    	rpm.setValue(channel.getLastElems(1).get(0)/1000);
				    	if(channel.getLastElems(1).get(0)>0) {
				    		circle1.setVisible(true);
				    	}
				    	else {
				    		circle1.setVisible(false);
				    	}
				    	if(channel.getLastElems(1).get(0)>=1000) {
				    		circle2.setVisible(true);
				    	}
				    	else {
				    		circle2.setVisible(false);
				    	}
				    	if(channel.getLastElems(1).get(0)>=2000) {
				    		circle3.setVisible(true);
				    	}
				    	else {
				    		circle3.setVisible(false);
				    	}
				    	if(channel.getLastElems(1).get(0)>=3000) {
				    		circle4.setVisible(true);
				    	}
				    	else {
				    		circle4.setVisible(false);
				    	}
				    	if(channel.getLastElems(1).get(0)>=4000) {
				    		circle5.setVisible(true);
				    	}
				    	else {
				    		circle5.setVisible(false);
				    	}
				    	if(channel.getLastElems(1).get(0)>=5000) {
				    		circle6.setVisible(true);
				    	}
				    	else {
				    		circle6.setVisible(false);
				    	}
				    	if(channel.getLastElems(1).get(0)>=6000) {
				    		circle7.setVisible(true);
				    	}
				    	else {
				    		circle7.setVisible(false);
				    	}
				    	if(channel.getLastElems(1).get(0)>=7000) {
				    		circle8.setVisible(true);
				    	}
				    	else {
				    		circle8.setVisible(false);
				    	}
				    	if(channel.getLastElems(1).get(0)>=8000) {
				    		circle9.setVisible(true);
				    	}
				    	else {
				    		circle9.setVisible(false);
				    	}
				    	if(channel.getLastElems(1).get(0)>=9000) {
				    		circle10.setVisible(true);
				    	}
				    	else {
				    		circle10.setVisible(false);
				    	}
				    	if(channel.getLastElems(1).get(0)>=10000) {
				    		circle11.setVisible(true);
				    	}
				    	else {
				    		circle11.setVisible(false);
				    	}
				    	if(channel.getLastElems(1).get(0)>=11000) {
				    		circle12.setVisible(true);
				    	}
				    	else {
				    		circle12.setVisible(false);
				    	}
				    }
				});
			}
			else if (channel.getName().equals(SPEED_CHANNEL)) {
				Platform.runLater(new Runnable() {
				    @Override
				    public void run() {
				    	speed.setValue(channel.getLastElems(1).get(0));
				    }
				});
			}
			else if (channel.getName().equals(FUEL_CHANNEL)) {
				Platform.runLater(new Runnable() {
				    @Override
				    public void run() {
				    	fuel.setValue(channel.getLastElems(1).get(0));
				    }
				});
			}
			else if (channel.getName().equals(WATER_CHANNEL)) {
				Platform.runLater(new Runnable() {
				    @Override
				    public void run() {
				    	water.setValue(channel.getLastElems(1).get(0));
				    }
				});
			}
			else if (channel.getName().equals(SW_ANGLE_CHANNEL)) {
				Platform.runLater(new Runnable() {
				    @Override
				    public void run() {
				    	wheelRot.setRotate(channel.getLastElems(1).get(0));
				    }
				});
			}
			else if (channel.getName().equals(THROTTLE_CHANNEL)) {
				Platform.runLater(new Runnable() {
				    @Override
				    public void run() {
				    	throttle.setProgress(channel.getLastElems(1).get(0)/100);
				    }
				});
			}
			else if (channel.getName().equals(BRAKE_CHANNEL)) {
				Platform.runLater(new Runnable() {
				    @Override
				    public void run() {
				    	brake.setProgress(channel.getLastElems(1).get(0)/40);
				    }
				});
			}
			else if (channel.getName().equals(CLUTCH_CHANNEL)) {
				Platform.runLater(new Runnable() {
				    @Override
				    public void run() {
				    	clutch.setProgress(channel.getLastElems(1).get(0)/100);
				    }
				});
			}
			else if (channel.getName().equals(GEAR_CHANNEL)) {
				Platform.runLater(new Runnable() {
				    @Override
				    public void run() {
				    	gear.setText(Integer.toString(((int)Math.round(channel.getLastElems(1).get(0)))));
				    	if(channel.getLastElems(1).get(0) == 0) {
				    		gear.setText("N");
				    	}
				    }
				});
			}
			else if (channel.getName().equals(GPS_LATITUDE)) {
				Platform.runLater(new Runnable() {
				    @Override
				    public void run() {
				    	if(toCalibrate) {
				    		if(channel.getLastElems(1).get(0) > 0) {
				    			latOffset = channel.getLastElems(1).get(0);
				    		}
				    	}
				    	else {
				    		latLastValue = channel.getLastElems(1).get(0);
				    	}
				    }
				});
			}
			else if (channel.getName().equals(GPS_LONGITUDE)) {
				Platform.runLater(new Runnable() {
				    @Override
				    public void run() {
				    	if(toCalibrate) {
				    		if(channel.getLastElems(1).get(0) > 0) {
				    			lonOffset = channel.getLastElems(1).get(0);
				    			toCalibrate = false;
				    		}
				    	}
				    	else {
				    		series.getData().add(new Data<Double, Double>(latLastValue - latOffset, channel.getLastElems(1).get(0) - lonOffset));	
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

	@Override
	public void setPause() {
		// TODO Auto-generated method stub

	}

}
