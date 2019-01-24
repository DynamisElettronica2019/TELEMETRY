package back_end;

import java.util.TimerTask;

public class CommandTimerTask extends TimerTask {

	private Command command;
	
	public CommandTimerTask(Command command) {
		this.command=command;
	}
	
	@Override
	public void run() {
		command.setOcc();
	}

}
