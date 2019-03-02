package back_end;

public class ViewLoader {
	
	private Data data;

	public ViewLoader(Data data) {
		this.data=data;
	}
	
	/*
	 * Call notify method for all channel(and thresholdChannel),state,debug,command,laptimer
	 */
	public void load(){
		for(Channel c : data.getChannels()) c.load();
		for(State s : data.getStates()) s.load();
		for(Debug d : data.getDebug()) d.load();
		for(Command c : data.getDcuCommands()) c.load();
		data.getLapTimer().load();
	}

}
