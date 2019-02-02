package front_end;

import back_end.Channel;
import back_end.Command;
import back_end.CommandSender;
import back_end.Error;
import back_end.Debug;
import back_end.State;

public abstract class View {
	
	private CommandSender commandSender;
	private ChannelObserver chObs;
	private StateObserver stObs;
	private DebugObserver deObs;
	private ErrorObserver erObs;
	private CommandObserver coObs;
	
	public View() {
		chObs = new ChannelObserver(this);
		stObs = new StateObserver(this);
		deObs = new DebugObserver(this);
		erObs = new ErrorObserver(this);
		coObs = new CommandObserver(this);
	}
	
	public void setCommandSender(CommandSender commandSender) {
		this.commandSender = commandSender;
	}
	
	public ChannelObserver getChObs() {
		return chObs;
	}
	
	public abstract void UpdateChannel(Channel channel);
	
	public CommandObserver getCoObs() {
		return coObs;
	}
	
	public abstract void UpdateCommand(Command command);
	
	public DebugObserver getDeObs() {
		return deObs;
	}
	
	public abstract void UpdateDebug(Debug debug);
	
	public ErrorObserver getErObs() {
		return erObs;
	}
	
	public abstract void UpdateError(Error error);
	
	public StateObserver getStObs() {
		return stObs;
	}
	
	public abstract void UpdateState(State state);

}
