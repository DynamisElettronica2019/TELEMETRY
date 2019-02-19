package front_end.cli;

import back_end.Channel;
import back_end.Command;
import back_end.CommandSender;
import back_end.Debug;
import back_end.Error;
import back_end.State;
import front_end.View;

public class CommandLineView extends View {
	
	public CommandLineView() {
		System.out.println("Starting command line view..");
	}
	
	@Override
	public void setCommandSender(CommandSender commandSender) {
		System.out.println("Setting command sender.. (insert COMMAND_NAME or COMMAND_NAME;PARAMETERS)");
		super.setCommandSender(commandSender);
		CommandLineReader clr = new CommandLineReader(commandSender);
		Thread t = new Thread(clr);
		t.start();
	}

	@Override
	public void UpdateChannel(Channel channel) {
		System.out.println("[Channel] "+channel.getName()+" @"+channel.getLastTs(1).get(0).toString()
							+" -> "+channel.getLastElems(1).get(0).toString());
	}

	@Override
	public void UpdateCommand(Command command) {
		if(command.isSending()) System.out.println("[Command] sended "+command.getName());
		else System.out.println("[Command] ack_rec/not_acked "+command.getName());
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
