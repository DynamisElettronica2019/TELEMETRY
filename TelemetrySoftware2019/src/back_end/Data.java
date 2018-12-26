package back_end;


import java.time.*;
import java.util.ArrayList;

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
		// TODO 
	}
	
	/*
	 * Update channels and timestamps adding the new elements
	 */
	public void update(ParsedData data) throws InvalidUpdateException {
		// TODO
	}
	
	/*
	 * Update states with new values
	 */
	public void update(ParsedState data) throws InvalidUpdateException {
		// TODO
	}
	
	/*
	 * Update debug with new values
	 */
	public void update(ParsedDebug data) throws InvalidUpdateException {
		// TODO
	}
	
	/*
	 * Return last n timestamps
	 */
	public ArrayList<LocalDateTime> getLastTs(int n){
		//TODO
	}
	
	/*
	 * Return last n elements of channel ch
	 */
	public ArrayList<Double> getLastElemsChannel(int ch,int n){
		//TODO
	}
	
	/*
	 * Return current value of state n
	 */
	public boolean getState(int n) {
		//TODO
	}
	
	/*
	 * Return current debug of state n
	 */
	public Double getDebug(int n) {
		//TODO
	}
	
	/*
	 * Reset data attributes
	 */
	public void reset(){
		//TODO
	}

}
