package front_end.gui_ground;

import java.net.URL;
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
import eu.hansolo.medusa.Gauge;
import javafx.fxml.FXML;

public class DebugScreenController extends Controller {
	
	private Map<String, Gauge> stateMap = new HashMap<>();
	
	@FXML
	private Gauge dcuTemp, dcuCurr, dauFLTemp, dauFLCurr, dauFRTemp, dauFRCurr, dauRearTemp, dauRearCurr;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		initMap();
	}

	private void initMap() {
		stateMap.put("DCU_TEMP", dcuTemp);
		stateMap.put("DCU_CURRENT", dcuCurr);
		stateMap.put("DAU_FL_TEMP", dauFLTemp);
		stateMap.put("DAU_FL_CURRENT", dauFLCurr);
		stateMap.put("DAU_FR_TEMP", dauFRTemp);
		stateMap.put("DAU_FR_CURRENT", dauFRCurr);
		stateMap.put("DAU_REAR_TEMP", dauRearTemp);
		stateMap.put("DAU_REAR_CURRENT", dauRearCurr);
	}

	@Override
	public void editDebug(Debug debug) {
		if(stateMap.get(debug.getName()) != null) stateMap.get(debug.getName()).setValue(debug.getValue());
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
