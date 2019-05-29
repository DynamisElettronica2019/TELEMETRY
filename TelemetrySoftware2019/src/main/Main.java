package main;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import back_end.Data;
import back_end.Parser;
import configuration.ModeLauncher;
import front_end.View;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		
		//Set std err to file
		//System.setErr(new PrintStream(new FileOutputStream("err.txt")));
		
		//View construction
		ArrayList<View> myViews = ModeLauncher.createViews();
		
		//Data and Parser creation
		Data data = new Data(myViews);
		Parser parser = new Parser(data);
		
		//Receiver launch
		ModeLauncher.launchReceivers(myViews, data, parser);
		
	}

}
