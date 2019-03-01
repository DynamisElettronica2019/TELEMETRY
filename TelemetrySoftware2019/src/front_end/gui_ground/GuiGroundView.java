package front_end.gui_ground;

import java.io.IOException;

import back_end.Channel;
import back_end.Command;
import back_end.CommandSender;
import back_end.Debug;
import back_end.Error;
import back_end.LapTime;
import back_end.State;
import back_end.Threshold;
import front_end.View;
import front_end.gui_row.DxController;
import front_end.gui_row.SxController;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class GuiGroundView extends View {
	private final int SCREEN_OFFSET_X = -8;
	private final int SCREEN_OFFSET_Y = 0;
	
	private Stage stageSX;
	private Stage stageDX;
	private Scene sceneSX;
	private Scene sceneDX;
	private Parent SXTopBarPane;
	private Parent DXTopBarPane;
	private Controller SXController;
	private Controller DXController;
	private FXMLLoader SXTopBarLoader;
	private FXMLLoader DXTopBarLoader;
	private BorderPane borderPaneSX, borderPaneDX;

	
	public GuiGroundView() throws IOException {
		System.out.println("Starting row gui view..");
		
		//Set stage SX
		stageSX = new Stage();
		stageSX.setTitle("Telemetry2019_SX");
		Screen mainScreen = Screen.getScreens().get(0); 
        Rectangle2D bounds = mainScreen.getVisualBounds();
        stageSX.setX(bounds.getMinX()+SCREEN_OFFSET_X);
        stageSX.setY(bounds.getMinY()+SCREEN_OFFSET_Y);
        stageSX.setMaximized(true);
        stageSX.setOnCloseRequest( event -> {stageDX.close();} );
        
        //Initialize stage SX
        borderPaneSX = new BorderPane();
        SXTopBarLoader = new FXMLLoader();
        SXTopBarPane = SXTopBarLoader.load(getClass().getResource("TopBar.fxml").openStream());
        SXController = SXTopBarLoader.getController();
        borderPaneSX.setTop(SXTopBarPane);
        sceneSX = new Scene(borderPaneSX);
        stageSX.setScene(sceneSX);
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
        
        //Initialize stage DX
        borderPaneDX = new BorderPane();
        DXTopBarLoader = new FXMLLoader();
        DXTopBarPane = DXTopBarLoader.load(getClass().getResource("TopBar.fxml").openStream());
        DXController = DXTopBarLoader.getController();
        borderPaneDX.setTop(DXTopBarPane);
        sceneDX = new Scene(borderPaneDX);
        stageDX.setScene(sceneDX);
        stageDX.show();
        SXController.SetState();
	}
	
	//Update functions call controller relative functions, always defined in controlelr
	@Override
	public void UpdateChannel(Channel channel) {
		SXController.EditChannel();
		DXController.EditChannel();
	}

	@Override
	public void UpdateCommand(Command command) {
		SXController.EditCommand();
		DXController.EditCommand();
	}

	@Override
	public void UpdateDebug(Debug debug) {
		SXController.EditDebug();
		DXController.EditDebug();
	}

	@Override
	public void UpdateError(Error error) {
		SXController.EditError();
		DXController.EditError();
	}

	@Override
	public void UpdateState(State state) {
		SXController.EditState();
		DXController.EditState();
	}

	@Override
	public void UpdateLap(LapTime lapTime) {
		SXController.EditLap();
		DXController.EditLap();
	}

	@Override
	public void UpdateTS(Threshold thresholdState) {
		SXController.EditTS();
		DXController.EditTS();
	}
	
	@Override
	public void setCommandSender(CommandSender commandSender) {
		super.setCommandSender(commandSender);
		// TODO Auto-generated method stub
		
	}

}
