package configuration;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ConfReader {
	
	private final static String filePath = "Config.json";
	
	/*
	 * Return JSONObject at filePath
	 */
	private static JSONObject readJSONObject() throws FileNotFoundException, IOException, ParseException{
		JSONParser parser = new JSONParser();
		return (JSONObject)parser.parse(new FileReader(filePath));
	}
	
	/*
	 * Convert a JSONArray in an ArrayList of strings
	 */
	private static ArrayList<String> JSONToArray(JSONArray jsonArray){
		ArrayList<String> myList = new ArrayList<String>();
		for(int i=0;i<jsonArray.size();i++) myList.add((String)jsonArray.get(i));
		return myList;
	}
	
	/*
	 * Read and return names of the data of type type
	 * type -> "channels","states","debug"
	 * On reading error log and return an empty ArrayList
	 * N.B. for channels use also haveThresholdAndServer
	 */
	public static ArrayList<String> getNames(String type){
		try {
			JSONObject obj = readJSONObject();
			JSONObject data = (JSONObject)obj.get("data");
			JSONObject ty = (JSONObject)data.get(type);
			return JSONToArray((JSONArray)ty.get("names"));
		} catch (Exception e) {
			System.err.println("Config file reading error. Return empty ArrayList for "+type+" names");
			return new ArrayList<>();
		}
	}
	
	/*
	 * Define if a channel name have a threshold. 
	 * If yes return {serverNumb,name,threshold}, else return {serverNumb,name}
	 * where serverNumb is 0 if not specified
	 */
	public static String[] haveThresholdAndServer(String name){
		String serverNumb = "0";
		if(name.split("\\)").length == 2) {
			String num = name.split("\\)")[0];
			name = name.split("\\)")[1];
			if(num.toCharArray()[0]=='('){
				try {
					Integer numInt = Integer.parseInt(num.substring(1,num.length()));
					if(numInt>0) serverNumb = numInt.toString();
				} catch (NumberFormatException | NullPointerException nfe) {}
			}
		}
		if(name.split("\\[").length == 2) {
			String th = name.split("\\[")[1];
			if(th.toCharArray()[th.toCharArray().length-1]==']')
				if(th.toCharArray()[0]=='<'||th.toCharArray()[0]=='>')
					try {
				        Double.parseDouble(th.substring(1,th.length()-1));
				        return new String[]{serverNumb,name.split("\\[")[0],th.substring(0,th.length()-1)}; 
				    } catch (NumberFormatException | NullPointerException nfe) {
				        return new String[]{name.split("\\[")[0]};
				    }
		}
		return new String[]{serverNumb,name};	
	}
	
	/*
	 * Read and return the len of the packets of type type
	 * type -> "channels","states","debug","lap"
	 * On reading error log and return 0
	 */
	public static long getPacketLen(String type){
		try {
			JSONObject obj = readJSONObject();
			if(!type.equals("lap")){
				JSONObject data = (JSONObject)obj.get("data");
				JSONObject ty = (JSONObject)data.get(type);
				return (long)ty.get("packetLen");
			}
			else{
				JSONObject laptimer = (JSONObject)obj.get("laptimer");
				return (long)laptimer.get("packetLen");
			}
		} catch (Exception e) {
			System.err.println("Config file reading error. Return 0 for "+type+" packetLen");
			return 0;
		}
	}
	
	/*
	 * Read and return the len of the packets of type type
	 * type -> "channels","states","debug","error","command"
	 * On reading error log and return 0
	 */
	public static char getRecogniser(String type){
		try {
			JSONObject obj = readJSONObject();
			if(type.equals("channels") || type.equals("states") || type.equals("debug")){
				obj = (JSONObject)obj.get("data");
			}
			JSONObject ty = (JSONObject)obj.get(type);
			return ((String)ty.get("recogniser")).charAt(0);
		} catch (Exception e) {
			System.err.println("Config file reading error. Return "+type.charAt(0)+" for "+type+" recogniser");
			return type.charAt(0);
		}
	}
	
	/*
	 * Read and return car receiver baud rate
	 * On reading error log and return 115200
	 */
	public static long getCarRecBaud(){
		try {
			JSONObject obj = readJSONObject();
			JSONObject rec = (JSONObject)obj.get("receiver");
			return (long)rec.get("carBaudRate");
		} catch (Exception e) {
			System.err.println("Config file reading error. Return 115200 as receiver car baud rate");
			return 115200;
		}
	}
	
	/*
	 * Read and return car receiver port
	 * On reading error log and return "COM5"
	 */
	public static String getCarRecPort(){
		try {
			JSONObject obj = readJSONObject();
			JSONObject rec = (JSONObject)obj.get("receiver");
			return (String)rec.get("carCommPort");
		} catch (Exception e) {
			System.err.println("Config file reading error. Return COM5 as receiver car port");
			return "COM5";
		}
	}
	
	/*
	 * Read and return lap receiver baud rate
	 * On reading error log and return 115200
	 */
	public static long getLapRecBaud(){
		try {
			JSONObject obj = readJSONObject();
			JSONObject rec = (JSONObject)obj.get("receiver");
			return (long)rec.get("lapBaudRate");
		} catch (Exception e) {
			System.err.println("Config file reading error. Return 115200 as receiver lap baud rate");
			return 115200;
		}
	}
	
	/*
	 * Read and return Lap receiver port
	 * On reading error log and return "COM6"
	 */
	public static String getLapRecPort(){
		try {
			JSONObject obj = readJSONObject();
			JSONObject rec = (JSONObject)obj.get("receiver");
			return (String)rec.get("lapCommPort");
		} catch (Exception e) {
			System.err.println("Config file reading error. Return COM6 as receiver lap port");
			return "COM5";
		}
	}
	
	/*
	 * Read and return error names for type "error" or "command"
	 * On reading error log and return an empty ArrayList
	 */
	public static ArrayList<String> getErrorNames(String type){
		try {
			JSONObject obj = readJSONObject();
			JSONObject ty = (JSONObject)obj.get(type);
			JSONArray list = (JSONArray)ty.get("list");
			ArrayList<String> myList = new ArrayList<String>();
			for(int i=0;i<list.size();i++) myList.add((String)((JSONObject)list.get(i)).get("name"));
			return myList;
		} catch (Exception e) {
			System.err.println("Config file reading error. Return empty ArrayList for "+type+" names");
			return new ArrayList<>();
		}
	}
	
	/*
	 * Read and return error codes for type "error" or "command"
	 * On reading error log and return an empty ArrayList
	 */
	public static ArrayList<Character> getErrorCode(String type){
		try {
			JSONObject obj = readJSONObject();
			JSONObject ty = (JSONObject)obj.get(type);
			JSONArray list = (JSONArray)ty.get("list");
			ArrayList<Character> myList = new ArrayList<Character>();
			for(int i=0;i<list.size();i++) myList.add((Character)((String)((JSONObject)list.get(i)).get("code")).charAt(0));
			return myList;
		} catch (Exception e) {
			System.err.println("Config file reading error. Return empty ArrayList for "+type+" codes");
			return new ArrayList<>();
		}
	}
	
	/*
	 * Read and return command boards
	 * On reading error log and return an empty ArrayList
	 */
	public static ArrayList<Character> getCommandBoardCode(){
		try {
			JSONObject obj = readJSONObject();
			JSONObject ty = (JSONObject)obj.get("command");
			JSONArray list = (JSONArray)ty.get("list");
			ArrayList<Character> myList = new ArrayList<Character>();
			for(int i=0;i<list.size();i++) myList.add((Character)((String)((JSONObject)list.get(i)).get("board")).charAt(0));
			return myList;
		} catch (Exception e) {
			System.err.println("Config file reading error. Return empty ArrayList for command boards");
			return new ArrayList<>();
		}
	}
	
	/*
	 * Read and return command params boolean (allowed vs not allowed)
	 * On reading error log and return an empty ArrayList
	 */
	public static ArrayList<Boolean> getCommandParams(){
		try {
			JSONObject obj = readJSONObject();
			JSONObject ty = (JSONObject)obj.get("command");
			JSONArray list = (JSONArray)ty.get("list");
			ArrayList<Boolean> myList = new ArrayList<Boolean>();
			for(int i=0;i<list.size();i++) {
				long tempLong = (long)((JSONObject)list.get(i)).get("params");
				if(tempLong == 1) myList.add(true);
				else myList.add(false); //default return false
			}
			return myList;
		} catch (Exception e) {
			System.err.println("Config file reading error. Return empty ArrayList for commands parameters");
			return new ArrayList<>();
		}
	}
	
	/*
	 * Read and return packet start char
	 * On reading error log and return [
	 */
	public static char getPktStart(){
		try {
			JSONObject obj = readJSONObject();
			JSONObject rec = (JSONObject)obj.get("receiver");
			return ((String)rec.get("pktStart")).charAt(0);
		} catch (Exception e) {
			System.err.println("Config file reading error. Return [ as packet start char");
			return '[';
		}
	}
	
	/*
	 * Read and return packet end char
	 * On reading error log and return ]
	 */
	public static char getPktEnd(){
		try {
			JSONObject obj = readJSONObject();
			JSONObject rec = (JSONObject)obj.get("receiver");
			return ((String)rec.get("pktEnd")).charAt(0);
		} catch (Exception e) {
			System.err.println("Config file reading error. Return ] as packet end char");
			return ']';
		}
	}
	
	/*
	 * Read and return timer lenght in ms
	 * On reading error log and return 3000
	 */
	public static long getTimerLen(){
		try {
			JSONObject obj = readJSONObject();
			JSONObject rec = (JSONObject)obj.get("command");
			return (long)rec.get("timer");
		} catch (Exception e) {
			System.err.println("Config file reading error. Return 3000 as default lenght");
			return 3000;
		}
	}
	
	/*
	 * Read and return selected recogniser for lap timer
	 * modeType -> "accMode","endMode","intType","lapType"
	 * On reading error log and return a,e,i,l
	 */
	public static char getLapTimerRecogniser(String modeType){
		try {
			JSONObject obj = readJSONObject();
			JSONObject rec = (JSONObject)obj.get("laptimer");
			return ((String)rec.get(modeType+"Recogniser")).charAt(0);
		} catch (Exception e) {
			System.err.println("Config file reading error. Return "+modeType.charAt(0)+" for "+modeType+" recogniser");
			return modeType.charAt(0);
		}
	}
	
	/*
	 * Read and return selected launch mode
	 * On reading error return GROUND
	 */
	public static String getLaunchMode(){
		JSONObject obj;
		try {
			obj = readJSONObject();
			return (String)obj.get("mode");
		} catch (Exception e) {
			System.err.println("Config file reading error. Return GROUND for launch mode");
			return "GROUND";
		}
	}
	
	/*
	 * Read and return button names
	 * On reading error log and return an empty ArrayList
	 */
	public static ArrayList<String> getCommandButtons(String type){
		try {
			JSONObject obj = readJSONObject();
			JSONObject data = (JSONObject)obj.get("data");
			JSONObject ty = (JSONObject)data.get(type);
			return JSONToArray((JSONArray)ty.get("command"));
		} catch (Exception e) {
			System.err.println("Config file reading error. Return empty ArrayList for "+type+" names");
			return new ArrayList<>();
		}
	}
	
	/*
	 * Read and return selected server port
	 * On reading error return 8080
	 */
	public static long getServerPort(){
		try {
			JSONObject obj = readJSONObject();
			JSONObject server = (JSONObject)obj.get("server");
			return (long)server.get("serverPort");
		} catch (Exception e) {
			System.err.println("Config file reading error. Return 8080 for server port");
			return 8080;
		}
	}
	
	/*
	 * Read and return server downsample frequency in ms
	 * On reading error return 1000ms
	 */
	public static long getServerFreq(){
		try {
			JSONObject obj = readJSONObject();
			JSONObject server = (JSONObject)obj.get("server");
			return (long)server.get("freq");
		} catch (Exception e) {
			System.err.println("Config file reading error. Return 1000ms for server downsample frequency");
			return 8080;
		}
	}
	
}
