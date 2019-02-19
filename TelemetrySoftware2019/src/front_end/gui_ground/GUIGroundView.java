package front_end.gui_ground;

import back_end.Channel;
import back_end.Command;
import back_end.Debug;
import back_end.Error;
import back_end.State;
import front_end.View;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class GUIGroundView extends View {
	
	private final int SCREEN_OFFSET_X = -8;
	private final int SCREEN_OFFSET_Y = 0;
	
	private Stage stageSX;
	private Stage stageDX;
	
	public GUIGroundView() {
		System.out.println("Starting GUI view (Ground Station Version)..");
		setDoubleScreen();
		
		//TODO
	}
	
	private void setDoubleScreen(){
		//Set stage SX
		stageSX = new Stage();
		stageSX.setTitle("Telemetry2019_SX");
		Screen mainScreen = Screen.getScreens().get(0); 
        Rectangle2D bounds = mainScreen.getVisualBounds();
        stageSX.setX(bounds.getMinX()+SCREEN_OFFSET_X);
        stageSX.setY(bounds.getMinY()+SCREEN_OFFSET_Y);
        stageSX.setMaximized(true);
        stageSX.setOnCloseRequest( event -> {stageDX.close();} );
        stageSX.show();
        //Set stage DX
        stageDX = new Stage();
        stageDX.setTitle("Telemetry2019_DX");
        Screen secondScreen = Screen.getScreens().get(0);
        if(Screen.getScreens().size()>1) secondScreen = Screen.getScreens().get(1);
        bounds = secondScreen.getVisualBounds();
        stageDX.setX(bounds.getMinX()+SCREEN_OFFSET_X);
        stageDX.setY(bounds.getMinY()+SCREEN_OFFSET_Y);
        stageDX.setMaximized(true);
        stageDX.setOnCloseRequest( event -> {stageSX.close();} );
        stageDX.show();
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

}
