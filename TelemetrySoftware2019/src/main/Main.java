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
		
		Data data = new Data(myViews);
		Parser parser = new Parser(data);
		
		//Receiver launch
		Receiver rec = new Receiver(myViews,data,parser,'C');
		Thread threadLettura = new Thread(new Runnable() {
			@Override
			public void run() {
				rec.Reader();
			}
		});
		threadLettura.start();
		
	}

}
