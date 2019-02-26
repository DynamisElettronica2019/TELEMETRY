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
	 * Read and return the len of the packets of type type
	 * type -> "channels","states","debug"
	 * On reading error log and return 0
	 */
	public static long getPacketLen(String type){
		try {
			JSONObject obj = readJSONObject();
			JSONObject data = (JSONObject)obj.get("data");
			JSONObject ty = (JSONObject)data.get(type);
			return (long)ty.get("packetLen");
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
			return (long)rec.get("CarBaudRate");
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
			return (String)rec.get("CarCommPort");
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
			return (long)rec.get("LapBaudRate");
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
			return (String)rec.get("LapCommPort");
		} catch (Exception e) {
			System.err.println("Config file reading error. Return COM6 as receiver lap port");
			return "COM5";
		}
	}
	
	/*
	 * Read and return command board
	 * On reading error log and return "D"
	 */
	public static char getCommandBoard(){
		try {
			JSONObject obj = readJSONObject();
			JSONObject rec = (JSONObject)obj.get("command");
			return ((String)rec.get("board")).charAt(0);
		} catch (Exception e) {
			System.err.println("Config file reading error. Return D as default command board");
			return 'D';
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
}
