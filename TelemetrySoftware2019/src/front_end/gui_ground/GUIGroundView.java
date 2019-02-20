package front_end.gui_ground;

import java.io.IOException;

import back_end.Channel;
import back_end.Command;
import back_end.Debug;
import back_end.Error;
import back_end.State;
import front_end.View;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class GUIGroundView extends View {
	
	private final int SCREEN_OFFSET_X = -8;
	private final int SCREEN_OFFSET_Y = 0;
	
	private Stage stageSX;
	private Stage stageDX;
	private Scene firstSceneSX;
	private Parent firstPaneSX;
	private Parent firstPaneDX;
	private SxController SXControl;
	private DxController DXControl;
	private FXMLLoader SXLoader;
	private FXMLLoader DXLoader;

	
	public GUIGroundView() throws IOException {
		System.out.println("Starting GUI view (Ground Station Version)..");
		setDoubleScreen();
		
		//TODO
	}
	
	private void setDoubleScreen() throws IOException{
		//Set stage SX
		SXLoader = new FXMLLoader(getClass().getResource("SxStage.fxml"));
		firstPaneSX = SXLoader.load();
		SXControl = SXLoader.getController();
		stageSX = new Stage();
		stageSX.setTitle("Telemetry2019_SX");
		Screen mainScreen = Screen.getScreens().get(0); 
        Rectangle2D bounds = mainScreen.getVisualBounds();
        stageSX.setX(bounds.getMinX()+SCREEN_OFFSET_X);
        stageSX.setY(bounds.getMinY()+SCREEN_OFFSET_Y);
        stageSX.setMaximized(true);
        //stageSX.setOnCloseRequest( event -> {stageDX.close();} );
        firstSceneSX = new Scene(firstPaneSX);
        stageSX.setScene(firstSceneSX);
        
        
        //Initialize sxstage
        SXControl.SetState();
        SXControl.passCommandSender(commandSender);
       
        stageSX.show();
        /*
        //Set stage DX
        DXLoader = new FXMLLoader(getClass().getResource("DxStage.fxml"));
        firstPaneDX = DXLoader.load();
        DXControl = DXLoader.getController();
        stageDX = new Stage();
        stageDX.setTitle("Telemetry2019_DX");
        Screen secondScreen = Screen.getScreens().get(0);
        if(Screen.getScreens().size()>1) secondScreen = Screen.getScreens().get(1);
        bounds = secondScreen.getVisualBounds();
        stageDX.setX(bounds.getMinX()+SCREEN_OFFSET_X);
        stageDX.setY(bounds.getMinY()+SCREEN_OFFSET_Y);
        stageDX.setMaximized(true);
        stageDX.setOnCloseRequest( event -> {stageSX.close();} );
        stageSX.setScene(new Scene(firstPaneDX));
        //stageDX.show(); //uncomment to run second stage
         */
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
		SXControl.AddState(state);
	}
}
