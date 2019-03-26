package front_end.server_adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import configuration.ConfReader;

public class Downsample implements Runnable {
	
	private Map<Integer, Boolean> toSendMap = new HashMap<>();
	private long timeOut;
	
	/*
	 * Init timeOut from configurations and toSendMap at true
	 */
	public Downsample() {
		timeOut = ConfReader.getServerFreq();
		ArrayList<String> chNames = ConfReader.getNames("channels");
		for(int i=0;i<chNames.size();i++) {
			Integer tempNum = Integer.parseInt(ConfReader.haveThresholdAndServer(chNames.get(i))[0]);
			if(tempNum>0) toSendMap.put(tempNum, new Boolean(true));
		}
	}

	/*
	 * Every timeOut set all channel to send (true)
	 */
	@Override
	public void run() {
		while(true){
			try {
				TimeUnit.MILLISECONDS.sleep(timeOut);
			} catch (InterruptedException e) {}
			for (Integer key : toSendMap.keySet()) {
				toSendMap.put(key, new Boolean(true));
			}
		}
	}
	
	/*
	 * Get hashmap value at serverNumber
	 */
	public boolean isToSend(int serverNumber){
		return toSendMap.get(serverNumber);
	}
	
	/*
	 * Set hashmap value at serverNumber to false
	 */
	public void setSended(int serverNumber){
		toSendMap.put(serverNumber, new Boolean(false));
	}

}
