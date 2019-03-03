package front_end.gui_ground;

import back_end.Channel;
import back_end.Command;
import back_end.Debug;
import back_end.Error;
import back_end.LapTimer;
import back_end.State;
import back_end.Threshold;

public class DebugScreenController extends Controller {

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

}
