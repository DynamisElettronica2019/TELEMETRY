package front_end.gui_row;

import java.io.IOException;

import back_end.Channel;
import back_end.Command;
import back_end.CommandSender;
import back_end.Debug;
import back_end.Error;
import back_end.LapTime;
import back_end.State;
import front_end.View;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class GUIRowView extends View {
	
	private final int SCREEN_OFFSET_X = -8;
	private final int SCREEN_OFFSET_Y = 0;
	
	private Stage stageSX;
	private Stage stageDX;
	private Scene firstSceneSX;
	private Scene firstSceneDX;
	private Parent firstPaneSX;
	private Parent firstPaneDX;
	private SxController SXControl;
	private DxController DXControl;
	private FXMLLoader SXLoader;
	private FXMLLoader DXLoader;

	
	public GUIRowView() throws IOException {
		System.out.println("Starting GUI view (Ground Station Version)..");
		
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
        stageSX.setOnCloseRequest( event -> {stageDX.close();} );
        firstSceneSX = new Scene(firstPaneSX);
        stageSX.setScene(firstSceneSX);
        //Initialize sxstage
        SXControl.SetState();
        stageSX.show();
        
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
        firstSceneDX = new Scene(firstPaneDX);
        stageDX.setScene(firstSceneDX);
        //Initialize dxstage
        DXControl.SetChannel();
        DXControl.SetDebug();
        stageDX.show(); 
	}

	@Override
	public void UpdateChannel(Channel channel) {
		DXControl.EditChannel(channel);
	}

	@Override
	public void UpdateCommand(Command command) {
	}

	@Override
	public void UpdateDebug(Debug debug) {
		DXControl.EditDebug(debug);

	}

	@Override
	public void UpdateError(Error error) {
	}

	@Override
	public void UpdateState(State state) {
		SXControl.AddState(state);
	}
	
	@Override
	public void UpdateLap(LapTime lapTime) {
	}
	
	@Override
	public void setCommandSender(CommandSender commandSender) {
		System.out.println("Command Sender set");
		super.setCommandSender(commandSender);
		SXControl.passCommandSender(commandSender);	
	}
}
