package front_end;

import java.io.IOException;

import back_end.Channel;
import back_end.Command;
import back_end.CommandSender;
import back_end.Error;
import back_end.LapTimer;
import back_end.Debug;
import back_end.State;
import back_end.Threshold;
import back_end.ViewLoader;

public abstract class View {
	
	protected CommandSender commandSender; //Not available before setCommandSender call
	protected ViewLoader viewLoader; //Not available before setViewLoader call
	private ChannelObserver chObs;
	private StateObserver stObs;
	private DebugObserver deObs;
	private ErrorObserver erObs;
	private CommandObserver coObs;
	private LapObserver lapObs;
	private ThStateObserver tsObs;
	
	public View() {
		chObs = new ChannelObserver(this);
		stObs = new StateObserver(this);
		deObs = new DebugObserver(this);
		erObs = new ErrorObserver(this);
		coObs = new CommandObserver(this);
		lapObs = new LapObserver(this);
		tsObs = new ThStateObserver(this);
	}
	
	public void setCommandSender(CommandSender commandSender) {
		this.commandSender = commandSender;
	}
	
	public void setViewLoader(ViewLoader viewLoader) throws IOException {
		this.viewLoader = viewLoader;
	}
	
	public ChannelObserver getChObs() {
		return chObs;
	}
	
	/*
	 * Implement for update of last element of a channel
	 */
	public abstract void UpdateChannel(Channel channel);
	
	public CommandObserver getCoObs() {
		return coObs;
	}
	
	/*
	 * Implement for update of the state sending value of a command
	 */
	public abstract void UpdateCommand(Command command);
	
	public DebugObserver getDeObs() {
		return deObs;
	}
	
	/*
	 * Implement for update of a debug value
	 */
	public abstract void UpdateDebug(Debug debug);
	
	public ErrorObserver getErObs() {
		return erObs;
	}
	
	/*
	 * Implement for new error event
	 */
	public abstract void UpdateError(Error error);
	
	public StateObserver getStObs() {
		return stObs;
	}
	
	/*
	 * Implement for update of a state value
	 */
	public abstract void UpdateState(State state);
	
	public LapObserver getLapObs() {
		return lapObs;
	}
	
	/*
	 * Implement for lap timer modified
	 */
	public abstract void UpdateLap(LapTimer lapTimer);
	
	public ThStateObserver getTsObs() {
		return tsObs;
	}
	
	/*
	 * Implement for update of a threshold state value
	 */
	public abstract void UpdateTS(Threshold thresholdState);
	
	public CommandSender getCommandSender() {
		return commandSender;
	}
	
	public ViewLoader getViewLoader() {
		return viewLoader;
	}

}
