package front_end;

import back_end.Channel;
import back_end.Command;
import back_end.Debug;
import back_end.Error;
import back_end.State;

public class DebugView extends View {

	@Override
	public void UpdateChannel(Channel channel) {
		// TODO CHANNEL UPDATE
		
	}

	@Override
	public void UpdateCommand(Command command) {
		// TODO COMMAND START SENDING OR ACK ARRIVED
		
	}

	@Override
	public void UpdateDebug(Debug debug) {
		// TODO DEBUG UPDATE
		
	}

	@Override
	public void UpdateError(Error error) {
		// TODO ERROR RISE (ERROR FROM DCU OR ACK NOT ARRIVED)
		
	}

	@Override
	public void UpdateState(State state) {
		// TODO STATE UPDATE
		
	}

}
