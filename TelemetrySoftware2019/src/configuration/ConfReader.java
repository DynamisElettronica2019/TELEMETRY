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
	
	private final static String filePath = "src/configuration/Config.json";
	
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
	 * type -> "channels","states","debug"
	 * On reading error log and return 0
	 */
	public static char getRecogniser(String type){
		try {
			JSONObject obj = readJSONObject();
			JSONObject data = (JSONObject)obj.get("data");
			JSONObject ty = (JSONObject)data.get(type);
			return ((String)ty.get("recogniser")).charAt(0);
		} catch (Exception e) {
			System.err.println("Config file reading error. Return "+type.charAt(0)+" for "+type+" recogniser");
			return type.charAt(0);
		}
	}
	
	/*
	 * Read and return receiver baud rate
	 * On reading error log and return 115200
	 */
	public static long getRecBaud(){
		try {
			JSONObject obj = readJSONObject();
			JSONObject rec = (JSONObject)obj.get("receiver");
			return (long)rec.get("baudRate");
		} catch (Exception e) {
			System.err.println("Config file reading error. Return 115200 as receiver baud rate");
			return 115200;
		}
	}
	
	/*
	 * Read and return receiver port
	 * On reading error log and return "COM5"
	 */
	public static String getRecPort(){
		try {
			JSONObject obj = readJSONObject();
			JSONObject rec = (JSONObject)obj.get("receiver");
			return (String)rec.get("commPort");
		} catch (Exception e) {
			System.err.println("Config file reading error. Return 115200 as receiver baud rate");
			return "COM5";
		}
	}

}
