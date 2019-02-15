package front_end;

import back_end.Channel;
import back_end.Command;
import back_end.Debug;
import back_end.Error;
import back_end.State;
import javafx.stage.Stage;

public class GUIView extends View {
	
	private Stage stageA;
	private Stage stageB;
	
	public GUIView(Stage arg0) {
		System.out.println("Starting GUI view..");
		
		//TODO
		this.stageA = arg0;
		stageA.show();
	}

	@Override
	public void UpdateChannel(Channel channel) {
		// TODO Auto-generated method stub

	}

	@Override
	public void UpdateCommand(Command command) {
		// TODO Auto-generated method stub

	}

	@Override
	public void UpdateDebug(Debug debug) {
		// TODO Auto-generated method stub

	}

	@Override
	public void UpdateError(Error error) {
		// TODO Auto-generated method stub

	}

	@Override
	public void UpdateState(State state) {
		// TODO Auto-generated method stub

	}

}
