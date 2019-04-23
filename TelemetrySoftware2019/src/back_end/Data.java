package back_end;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.ParsePosition;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import configuration.ConfReader;
import exceptions.InvalidCodeException;
import exceptions.InvalidUpdateException;
import front_end.View;

public class Data {
	
	private ArrayList<LocalDateTime> timestamps; //Common ts for all channels
	private Channel[] channels;
	private State[] states;
	private Debug[] debug;
	private Command[] dcuCommands;
	private Error[] dcuErrors;
	private LapTimer lapTimer;
	private Reader reader;
	private CSVParser csvParser;
	private CSVPrinter csvPrinter;
	private BufferedWriter writer;
	
	/*
	 * Create channels,states,debug,dcuCommands,dcuErrors,lapTimer through ConfReader and initialize timeStamps
	 * Set viewLoader for all views
	 */
	public Data(ArrayList<View> myViews) throws IOException {
		timestamps = new ArrayList<>();
		
		ArrayList<String> chNames = ConfReader.getNames("channels");
		channels = new Channel[chNames.size()];
		for(int i=0;i<channels.length;i++) {
			String[] NaTh = ConfReader.haveThresholdAndServer(chNames.get(i));
			if(NaTh.length==2) channels[i] = new Channel(NaTh[1], NaTh[0], this, myViews);
			else channels[i] = new ThresholdChannel(NaTh[1], NaTh[0], this, myViews, NaTh[2]);
		}
		
		ArrayList<String> stNames = ConfReader.getNames("states");
		states = new State[stNames.size()];
		for(int i=0;i<states.length;i++) states[i] = new State(stNames.get(i), myViews);
		
		ArrayList<String> deNames = ConfReader.getNames("debug");
		debug = new Debug[deNames.size()];
		for(int i=0;i<debug.length;i++) debug[i] = new Debug(deNames.get(i), myViews);
		
		ArrayList<String> erNames = ConfReader.getErrorNames("error");
		ArrayList<Character> erCodes = ConfReader.getErrorCode("error");
		dcuErrors = new Error[erNames.size()];
		for(int i=0;i<dcuErrors.length;i++) dcuErrors[i] = new Error(erNames.get(i), erCodes.get(i), myViews);
		
		ArrayList<String> coNames = ConfReader.getErrorNames("command");
		ArrayList<Character> coCodes = ConfReader.getErrorCode("command");
		ArrayList<Character> coBoards = ConfReader.getCommandBoardCode();
		ArrayList<Boolean> coParams = ConfReader.getCommandParams();
		dcuCommands = new Command[coNames.size()];
		for(int i=0;i<dcuCommands.length;i++) dcuCommands[i] = new Command(coNames.get(i), coCodes.get(i), coBoards.get(i), coParams.get(i), myViews);
		
		lapTimer = new LapTimer(myViews);
		
		ViewLoader vl = new ViewLoader(this);
		for(View v : myViews) v.setViewLoader(vl);
		
		
		/*
		 * Csv write
		 */
		String[] strArray = new String[chNames.size()+1];
		strArray[0] = "ts";
		for (int i=0; i<chNames.size(); i++) {
			strArray[i+1] = ConfReader.haveThresholdAndServer(chNames.get(i))[1];
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
		LocalDateTime dateTime = LocalDateTime.now();
		writer = Files.newBufferedWriter(Paths.get(dateTime.format(formatter) + ".csv"), 
                StandardOpenOption.CREATE);
		csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
				.withDelimiter(';')
                .withHeader(strArray));
		csvPrinter.flush();
	}
	
	/*
	 * Check validity and update channels and timestamps adding the new elements
	 */
	public void update(ParsedData data) throws InvalidUpdateException {
		ArrayList<Double> dbList = data.convert();
		String[] valArray = new String[channels.length+1];
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyyHH:mm:ss.SSS");
		LocalDateTime dateTime = LocalDateTime.now();
		timestamps.add(dateTime);
		valArray[0] = dateTime.format(formatter);
		for(int i=0;i<channels.length;i++) {
			valArray[i+1] = Double.toString(dbList.get(i));
			channels[i].addElem(dbList.get(i));
		}
		try {
			csvPrinter.printRecord(valArray);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	 * Return last n (or less) timestamps
	 */
	public ArrayList<LocalDateTime> getLastTs(int n){
		ArrayList<LocalDateTime> temp = new ArrayList<>();
		for(int i=Math.max(timestamps.size()-n,0);i<timestamps.size();i++) temp.add(timestamps.get(i));
		return temp;
	}
	
	/*
	 * Return last timestamp
	 */
	public LocalDateTime getLastTs(){
		if(timestamps.size()>0)
			return timestamps.get(timestamps.size()-1);
		else
			return null;
	}
	
	/*
	 * Return last timestamp
	 */
	public ArrayList<LocalDateTime> getLastTsOffset(int n, int offset){
		ArrayList<LocalDateTime> temp = new ArrayList<>();
		for(int i=Math.max(timestamps.size()-n-offset,0);i<timestamps.size()-offset;i++) temp.add(timestamps.get(i));
		return temp;
	}
	
	/*
	 * Return last n (or less) elements of channel ch
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
		for(Command c: dcuCommands) c.reset();
		for(Error e: dcuErrors) e.reset();
		lapTimer.reset();
	}
	
	/*
	 * Verify if exist a Command with name 'name' and that accept 'params', return the corresponding code or throws an Exception.
	 * No control about parameters syntax is performed
	 */
	public char authorizedNameAndParams(String name, String params) throws InvalidCodeException {
		for(Command c: dcuCommands){
			if(c.getName().equals(name) && !c.isSending()){
				if(params.length()==0 && !c.haveParams()){
					return c.getCode();
				}
				if(params.length()>0 && c.haveParams()){
					return c.getCode();
				}
			}
		}
		throw new InvalidCodeException("Invalid call for '"+name+"' command with '"+params+"' parameters");
	}
	
	/*
	 * Start timer for command with code 'code' and set the command sending. Throws an exception if doesn't exist
	 */
	public void startTimer(char code) throws InvalidCodeException {
		for(Command c: dcuCommands){
			if(c.getCode()==code){
				c.startSending();
				return;
			}
		}
		throw new InvalidCodeException("Not able to start timer for command with code "+code);
	}
	
	/*
	 * Stop timer for command with code 'code'. Throws an exception if doesn't exist
	 */
	public void delTimer(char code) throws InvalidCodeException {
		for(Command c: dcuCommands){
			if(c.getCode()==code){
				c.stopTimer();
				return;
			}
		}
		throw new InvalidCodeException("Not valid ack code received: "+code);
	}
	
	/*
	 * Set a new occurence for dcu error with code 'code'. Throws an exception if doesn't exist
	 */
	public void setDcuError(char code) throws InvalidCodeException {
		for(Error e: dcuErrors){
			if(e.getCode()==code){
				e.setOcc();
				return;
			}
		}
		throw new InvalidCodeException("Not valid error code received: "+code);
	}

	/*
	 * Set a new lap time
	 */
	public void newLap(ParsedLap parsedLap) {
		lapTimer.newLap(parsedLap);
	}

	/*
	 * Get board for command with code myCode
	 */
	public char getBoard(char myCode) throws InvalidCodeException {
		for(Command c: dcuCommands){
			if(c.getCode()==myCode) return c.getBoard();
		}
		throw new InvalidCodeException("Not valid command code received: "+myCode);
	}
	
	public Channel[] getChannels() {
		return channels;
	}
	
	public State[] getStates() {
		return states;
	}
	
	public Debug[] getDebug(){
		return debug;
	}
	
	public Command[] getDcuCommands() {
		return dcuCommands;
	}
	
	public LapTimer getLapTimer() {
		return lapTimer;
	}
	
	/*
	 *  Csv loading
	 */
	public void LoadFile(String pathStr) throws IOException {
		ArrayList<String> chNames = ConfReader.getNames("channels");
		String[] strArray = new String[chNames.size()+1];
		strArray[0] = "ts";
		for (int i=0; i<chNames.size(); i++) {
			strArray[i+1] = ConfReader.haveThresholdAndServer(chNames.get(i))[1];
		}
		reader = Files.newBufferedReader(Paths.get(pathStr));
		csvParser = new CSVParser(reader, CSVFormat.DEFAULT
				.withSkipHeaderRecord()
				.withDelimiter(';') 
				.withHeader(strArray)
	            .withIgnoreHeaderCase()
	            .withTrim());
		for (CSVRecord csvRecord : csvParser) {
			for (int i=0; i<channels.length; i++) {
				channels[i].addElem(Double.parseDouble(csvRecord.get(channels[i].getName())));
			}
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyyHH:mm:ss.SSS");
			LocalDateTime dateTime = LocalDateTime.parse(csvRecord.get("ts"), formatter);
			timestamps.add(dateTime);
		}
	}
}
