package front_end;

import back_end.Channel;
import back_end.Command;
import back_end.Debug;
import back_end.Error;
import back_end.State;

public class DebugView extends View {
	
	public DebugView() {
		System.out.println("Starting DebugView..");
	}

	@Override
	public void UpdateChannel(Channel channel) {
		System.out.println("[Channel] "+channel.getName()+" @"+channel.getLastTs(1).get(0).toString()
							+" -> "+channel.getLastElems(1).get(0).toString());
	}

	@Override
	public void UpdateCommand(Command command) {
		if(command.isSending()) System.out.println("[Command sended] "+command.getName());
		else System.out.println("[Command acked] "+command.getName());
	}

	@Override
	public void UpdateDebug(Debug debug) {
		System.out.println("[Debug] "+debug.getName()+" -> "+debug.getValue().toString());
	}

	@Override
	public void UpdateError(Error error) {
		System.out.println("[Error] "+error.getName()+" @"+error.getLastOcc().toString());
	}

	@Override
	public void UpdateState(State state) {
		System.out.println("[State] "+state.getName()+" -> "+state.getValue());
	}

}
