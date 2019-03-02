package front_end.gui_ground;

import java.io.IOException;

import back_end.Channel;
import back_end.Command;
import back_end.CommandSender;
import back_end.Debug;
import back_end.Error;
import back_end.LapTimer;
import back_end.State;
import back_end.Threshold;
import back_end.ViewLoader;
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
	//private Parent SXMainPane;
	//private Parent DXMainPane;
	private TopBarController SXTopBarController;
	private TopBarController DXTopBarController;
	//private Controller SXMainController;
	//private Controller DXMainController;
	private FXMLLoader SXTopBarLoader;
	private FXMLLoader DXTopBarLoader;
	//private FXMLLoader SXMainLoader;
	//private FXMLLoader DXMainLoader;
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
        SXTopBarController = SXTopBarLoader.getController();
        //SXMainLoader = new FXMLLoader();
        //SXMainPane = SXMainLoader.load(getClass().getResource("EngineScreen.fxml").openStream());
        //SXMainController = SXMainLoader.getController();
        borderPaneSX.setTop(SXTopBarPane);
        //borderPaneSX.setCenter(SXMainPane);
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
        DXTopBarController = DXTopBarLoader.getController();
        //DXMainLoader = new FXMLLoader();
        //DXMainPane = DXMainLoader.load(getClass().getResource("CommandScreen.fxml").openStream());
        //DXMainController = DXMainLoader.getController();
        borderPaneDX.setTop(DXTopBarPane);
        //borderPaneDX.setCenter(DXMainPane);
        sceneDX = new Scene(borderPaneDX);
        stageDX.setScene(sceneDX);
        stageDX.show();
        
        //Pass borderpane
        SXTopBarController.SetParent(borderPaneSX);
        DXTopBarController.SetParent(borderPaneDX);
	}
	
	//Update functions call controller relative functions, always defined in controlelr
	@Override
	public void UpdateChannel(Channel channel) {
		SXTopBarController.EditChannel(channel);
		DXTopBarController.EditChannel(channel);
	}

	@Override
	public void UpdateCommand(Command command) {
		SXTopBarController.EditCommand();
		DXTopBarController.EditCommand();
	}

	@Override
	public void UpdateDebug(Debug debug) {
		SXTopBarController.EditDebug();
		DXTopBarController.EditDebug();
	}

	@Override
	public void UpdateError(Error error) {
		SXTopBarController.EditError();
		DXTopBarController.EditError();
	}

	@Override
	public void UpdateState(State state) {
		SXTopBarController.EditState(state);
		DXTopBarController.EditState(state);
	}

	@Override
	public void UpdateLap(LapTimer lapTimer) {
		SXTopBarController.EditLap();
		DXTopBarController.EditLap();
	}

	@Override
	public void UpdateTS(Threshold thresholdState) {
		SXTopBarController.EditTS();
		DXTopBarController.EditTS();
	}
	
	
	@Override
	public void setCommandSender(CommandSender commandSender) {
		super.setCommandSender(commandSender);
		// TODO Auto-generated method stub
	}
	
	@Override
	public void setViewLoader(ViewLoader viewLoader) {
		super.setViewLoader(viewLoader);
		// TODO CREARE QUI I CONTROLLER DELLE PRIME DUE SCHERMATE DISPONIBILI, CHE SI PRENDONO I DATI CHIAMANDO viewLoader.load()
	}

}
