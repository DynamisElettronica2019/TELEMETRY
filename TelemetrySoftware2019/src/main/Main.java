package main;

import java.util.ArrayList;

import back_end.Data;
import back_end.Parser;
import back_end.Receiver;
import front_end.cli.CommandLineView;
import front_end.gui_row.GUIRowView;
import front_end.View;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		
		//View construction
		ArrayList<View> myViews = new ArrayList<>();
		myViews.add(new CommandLineView());
		myViews.add(new GUIRowView());
		
		//Data and Parser creation
		Data data = new Data(myViews);
		Parser parser = new Parser(data);
		
		//Car Receiver launch
		new Thread(new Runnable() {
			@Override
			public void run() {
				new Receiver(myViews,data,parser,'C').Reader();
			}
		}).start();
		
		//Lap Receiver launch
		new Thread(new Runnable() {
			@Override
			public void run() {
				new Receiver(myViews,data,parser,'L').Reader();
			}
		}).start();
		
	}

}
