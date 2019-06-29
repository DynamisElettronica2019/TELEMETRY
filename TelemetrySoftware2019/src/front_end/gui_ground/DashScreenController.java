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
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class DashScreenController extends Controller {
	@FXML
	Gauge rpm, speed, fuel, water;
	@FXML
	ProgressBar throttle, brake, clutch;
	@FXML
	ImageView wheel;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		
		rpm.addSection(new Section(10, 12, Color.web("0xc80000ff")));
		water.addSection(new Section(95, 120, Color.web("0xc80000ff")));
		fuel.addSection(new Section(0, 1, Color.web("0xc80000ff")));
		
		throttle.setProgress(0.5);
		brake.setProgress(0.4);
		clutch.setProgress(0.3);
		wheel.setRotate(50);
		rpm.setValue(3.5);
		speed.setValue(70);
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
