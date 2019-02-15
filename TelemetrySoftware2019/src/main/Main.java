package main;

import java.util.ArrayList;

import back_end.Receiver;
import front_end.CommandLineView;
import front_end.GUIView;
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
		myViews.add(new GUIView(arg0));
		
		//Receiver launch
		Receiver rec = new Receiver(myViews);
		Thread threadLettura = new Thread(new Runnable() {
			@Override
			public void run() {
				rec.Reader();
			}
		});
		threadLettura.start();
		
	}

}
