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
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;

public class DynamicsScreenController extends Controller {
	private Boolean isPaused = false;
	private ToggleButton pauseButton;
	@FXML
	private LineChart<String, Double> graph1, graph2, graph3, graph4;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		graph1.getXAxis().setTickLabelsVisible(false);
		graph1.getXAxis().setTickMarkVisible(false);
		graph2.getXAxis().setTickLabelsVisible(false);
		graph2.getXAxis().setTickMarkVisible(false);
		graph3.getXAxis().setTickLabelsVisible(false);
		graph3.getXAxis().setTickMarkVisible(false);
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
			// TODO Auto-generated method stub
			if (!pauseButton.isSelected()) {
				
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
}
