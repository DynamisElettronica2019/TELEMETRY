package front_end.server_adapter;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

import back_end.Channel;
import back_end.Command;
import back_end.Debug;
import back_end.Error;
import back_end.LapTimer;
import back_end.State;
import back_end.Threshold;
import configuration.ConfReader;
import front_end.View;

public class ServerAdapterView extends View{
	
	private WebSocketClient wsc;
	private Downsample downsample;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("k:m:s.SSS a");
	
	public ServerAdapterView() {
		//Start server
		try {
			startServer();
		} catch (IOException e) {}
		
		//Web socket connect
		wsc = new WebSocketClient();
		wsc.connect("ws://127.0.0.1:"+ConfReader.getServerPort()+"/");
		
		//Run downsample on thread
		downsample = new Downsample();
		Thread thread = new Thread(downsample);
		thread.start();
		
	}
	
	/*
	 * Start server from command line. Start_Server.bat must be in web_server folder.
	 */
	private void startServer() throws IOException{
		String cmd = "cmd /c start Start_Server.bat";
		File dir = new File(System.getProperty("user.dir")+"\\web_server");
		Runtime.getRuntime().exec(cmd, null, dir);
		System.out.println("Server launched");
	}

	@Override
	public void UpdateChannel(Channel channel) {
		if(channel.getServerNumb()>0){
			if(!channel.isEmpty()){
				if(downsample.isToSend(channel.getServerNumb())){
					try {
						wsc.sendMessage(createJson(channel));
					} catch (IOException e) {}
					downsample.setSended(channel.getServerNumb());
				}
			}
		}	
	}

	private String createJson(Channel channel) {
		return "{\"ch\":\""+channel.getServerNumb()+"\",\"ts\":\""+channel.getLastTs().format(formatter)+
				"\",\"val\":\""+channel.getLastElems()+"\"}";
	}

	@Override
	public void UpdateCommand(Command command) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void UpdateDebug(Debug debug) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void UpdateError(Error error) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void UpdateState(State state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void UpdateLap(LapTimer lapTimer) {
		//create json and send to server
	}

	@Override
	public void UpdateTS(Threshold thresholdState) {
		// TODO Auto-generated method stub
		
	}

}
