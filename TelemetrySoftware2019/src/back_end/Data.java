package back_end;

import java.time.*;
import java.util.ArrayList;

import configuration.ConfReader;
import exceptions.InvalidUpdateException;

public class Data {
	
	private ArrayList<LocalDateTime> timestamps; //Common ts for all channels
	private Channel[] channels;
	private State[] states;
	private Debug[] debug;
	
	/*
	 * Create channels,states,debug through ConfReader and initialize timeStamps
	 */
	public Data() {
		timestamps = new ArrayList<>();
		
		ArrayList<String> chNames = ConfReader.getNames("channels");
		channels = new Channel[chNames.size()];
		for(int i=0;i<channels.length;i++) channels[i] = new Channel(chNames.get(i));
		
		ArrayList<String> stNames = ConfReader.getNames("states");
		states = new State[stNames.size()];
		for(int i=0;i<states.length;i++) states[i] = new State(stNames.get(i));
		
		ArrayList<String> deNames = ConfReader.getNames("debug");
		debug = new Debug[deNames.size()];
		for(int i=0;i<debug.length;i++) debug[i] = new Debug(deNames.get(i));
	}
	
	/*
	 * Check validity and update channels and timestamps adding the new elements
	 */
	public void update(ParsedData data) throws InvalidUpdateException {
		ArrayList<Double> dbList = data.convert();
		for(int i=0;i<channels.length;i++) channels[i].addElem(dbList.get(i));
		timestamps.add(LocalDateTime.now());
	}
	
	/*
	 * Check validity and update states with new values
	 */
	public void update(ParsedState data) throws InvalidUpdateException {
		ArrayList<Boolean> blList = data.convert();
		for(int i=0;i<states.length;i++) states[i].setValue(blList.get(i));
	}
	
	/*
	 * Check validity and update debug with new values
	 */
	public void update(ParsedDebug data) throws InvalidUpdateException {
		ArrayList<Double> dbList = data.convert();
		for(int i=0;i<debug.length;i++) debug[i].setValue(dbList.get(i));
	}
	
	/*
	 * Return last n timestamps
	 */
	public ArrayList<LocalDateTime> getLastTs(int n){
		ArrayList<LocalDateTime> temp = new ArrayList<>();
		for(int i=timestamps.size()-n;i<timestamps.size();i++) temp.add(timestamps.get(i));
		return temp;
	}
	
	/*
	 * Return last n elements of channel ch
	 */
	public ArrayList<Double> getLastElemsChannel(int ch,int n){
		return channels[ch].getLastElems(n);
	}
	
	/*
	 * Return current value of state n
	 */
	public boolean getState(int n) {
		return states[n].getValue();
	}
	
	/*
	 * Return current debug of state n
	 */
	public Double getDebug(int n) {
		return debug[n].getValue();
	}
	
	/*
	 * Reset data attributes
	 */
	public void reset(){
		timestamps.clear();
		for(Channel c : channels) c.reset();
		for(State s : states) s.reset();
		for(Debug d : debug) d.reset();
	}
	
}
