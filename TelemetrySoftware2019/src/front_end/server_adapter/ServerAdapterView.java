package front_end.server_adapter;

import java.io.File;
import java.io.IOException;

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
	
	public ServerAdapterView() {
		//Start server
		try {
			startServer();
		} catch (IOException e) {}
		
		//Web socket connect
		wsc = new WebSocketClient();
		wsc.Connect("ws://127.0.0.1:"+ConfReader.getServerPort()+"/");
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
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void UpdateTS(Threshold thresholdState) {
		// TODO Auto-generated method stub
		
	}

}
