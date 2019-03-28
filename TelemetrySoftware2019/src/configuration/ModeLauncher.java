package configuration;

import java.io.IOException;
import java.util.ArrayList;

import back_end.Data;
import back_end.Parser;
import back_end.Receiver;
import front_end.View;
import front_end.cli.CommandLineView;
import front_end.gui_ground.GuiGroundView;
import front_end.gui_row.GUIRowView;
import front_end.server_adapter.ServerAdapterView;

public class ModeLauncher {
	
	public static ArrayList<View> createViews() throws IOException{
		ArrayList<View> myViews = new ArrayList<>();
		String mode = ConfReader.getLaunchMode();
		//Cli mode
		if(mode.equals("CLI")){
			myViews.add(new CommandLineView());
		}
		//Gui_Row mode
		else if(mode.equals("GUI_ROW")){
			try {
				myViews.add(new GUIRowView());
			} catch (IOException e) { e.printStackTrace(); }
		}
		//ADD HERE NEW MODE
		//Ground mode
		else{
			if(!mode.equals("GROUND")) System.err.println("Launch mode "+mode+" not found. Launch in GROUND mode");
			myViews.add(new ServerAdapterView());
			myViews.add(new GuiGroundView());
		}
		return myViews;
	}
	
	public static void launchReceivers(ArrayList<View> myViews,Data data,Parser parser){
		String mode = ConfReader.getLaunchMode();
		//Cli mode
		if(mode.equals("CLI")){
			startCarReceiver(myViews, data, parser);
			startLapReceiver(myViews, data, parser);
		}
		//Gui_Row mode
		else if(mode.equals("GUI_ROW")){
			startCarReceiver(myViews, data, parser);
		}
		//ADD HERE NEW MODE
		//Ground mode
		else{
			startCarReceiver(myViews, data, parser);
			startLapReceiver(myViews, data, parser);
		}
	}
	
	private static void startCarReceiver(ArrayList<View> myViews,Data data,Parser parser){
		//Car Receiver launch
		new Thread(new Runnable() {
			@Override
			public void run() {
				new Receiver(myViews,data,parser,'C').Reader();
			}
		}).start();
	}
	
	private static void startLapReceiver(ArrayList<View> myViews,Data data,Parser parser){
		//Lap Receiver launch
		new Thread(new Runnable() {
			@Override
			public void run() {
				new Receiver(myViews,data,parser,'L').Reader();
			}
		}).start();
	}

}
