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
	private Parent SXMainPane;
	private Parent DXMainPane;
	private TopBarController SXTopBarController;
	private TopBarController DXTopBarController;
	private Controller SXMainController;
	private Controller DXMainController;
	private FXMLLoader SXTopBarLoader;
	private FXMLLoader DXTopBarLoader;
	private FXMLLoader SXMainLoader;
	private FXMLLoader DXMainLoader;
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
        sceneDX = new Scene(borderPaneDX);
        stageDX.setScene(sceneDX);
        stageDX.show();
	}
	
	//Update functions call controller relative functions, always defined in controlelr
	@Override
	public void UpdateChannel(Channel channel) {
		SXTopBarController.EditChannel(channel);
		DXTopBarController.EditChannel(channel);
		SXMainController.EditChannel(channel);
		DXMainController.EditChannel(channel);
	}

	@Override
	public void UpdateCommand(Command command) {
		SXTopBarController.EditCommand(command);
		DXTopBarController.EditCommand(command);
		SXMainController.EditCommand(command);
		DXMainController.EditCommand(command);
	}

	@Override
	public void UpdateDebug(Debug debug) {
		SXTopBarController.EditDebug(debug);
		DXTopBarController.EditDebug(debug);
		SXMainController.EditDebug(debug);
		DXMainController.EditDebug(debug);
	}

	@Override
	public void UpdateError(Error error) {
		SXTopBarController.EditError(error);
		DXTopBarController.EditError(error);
		SXMainController.EditError(error);
		DXMainController.EditError(error);
	}

	@Override
	public void UpdateState(State state) {
		SXTopBarController.EditState(state);
		DXTopBarController.EditState(state);
		SXMainController.EditState(state);
		DXMainController.EditState(state);
	}

	@Override
	public void UpdateLap(LapTimer lapTimer) {
		SXTopBarController.EditLap(lapTimer);
		DXTopBarController.EditLap(lapTimer);
		SXMainController.EditLap(lapTimer);
		DXMainController.EditLap(lapTimer);
	}

	@Override
	public void UpdateTS(Threshold thresholdState) {
		SXTopBarController.EditTS(thresholdState);
		DXTopBarController.EditTS(thresholdState);
		SXMainController.EditTS(thresholdState);
		DXMainController.EditTS(thresholdState);
	}
	
	
	@Override
	public void setCommandSender(CommandSender commandSender) {
		super.setCommandSender(commandSender);
		// TODO Auto-generated method stub
	}
	
	@Override
	public void setViewLoader(ViewLoader viewLoader) throws IOException {
		super.setViewLoader(viewLoader);
		//Set first two FXML and pass viewloader
        SXTopBarLoader = new FXMLLoader();
        SXTopBarPane = SXTopBarLoader.load(getClass().getResource("TopBar.fxml").openStream());
        SXTopBarController = SXTopBarLoader.getController();
        DXTopBarLoader = new FXMLLoader();
        DXTopBarPane = DXTopBarLoader.load(getClass().getResource("TopBar.fxml").openStream());
        DXTopBarController = DXTopBarLoader.getController();
		SXMainLoader = new FXMLLoader();
	    SXMainPane = SXMainLoader.load(getClass().getResource("EngineScreen.fxml").openStream());
	    SXMainController = SXMainLoader.getController();
	    DXMainLoader = new FXMLLoader();
	    DXMainPane = DXMainLoader.load(getClass().getResource("CommandScreen.fxml").openStream());
	    DXMainController = DXMainLoader.getController();
        borderPaneSX.setTop(SXTopBarPane);
        borderPaneDX.setTop(DXTopBarPane);
	    borderPaneSX.setCenter(SXMainPane);
	    borderPaneDX.setCenter(DXMainPane);
	    SXTopBarController.SetView(this);
	    DXTopBarController.SetView(this);
	    SXMainController.SetView(this);
	    DXMainController.SetView(this);
	    SXTopBarController.SetViewLoader(viewLoader);
	    DXTopBarController.SetViewLoader(viewLoader);
	    SXMainController.SetViewLoader(viewLoader);
	    DXMainController.SetViewLoader(viewLoader);
	    SXTopBarController.SetSide('s');
	    DXTopBarController.SetSide('d');
	}

	//Function for getting the viewloader from controller
	public ViewLoader GetViewLoader() {
		return this.viewLoader;
	}
	
	//Function for getting the commandsender from controller
	public CommandSender GetCommandSender() {
		return this.commandSender;
	}
	
	/*
	 * From here functions for changing screen when pressing buttons on the top bar
	 * Called from top bar controllers
	 */
	
	public void EngineScreenSX() throws IOException {
		SXMainLoader = new FXMLLoader();
	    SXMainPane = SXMainLoader.load(getClass().getResource("EngineScreen.fxml").openStream());
	    SXMainController = SXMainLoader.getController();
	    borderPaneSX.setCenter(SXMainPane);
	}
	
	public void EngineScreenDX() throws IOException {
		DXMainLoader = new FXMLLoader();
	    DXMainPane = DXMainLoader.load(getClass().getResource("EngineScreen.fxml").openStream());
	    DXMainController = DXMainLoader.getController();
	    borderPaneDX.setCenter(DXMainPane);
	}
	
	public void RawScreenSX() throws IOException {
		SXMainLoader = new FXMLLoader();
	    SXMainPane = SXMainLoader.load(getClass().getResource("RawScreen.fxml").openStream());
	    SXMainController = SXMainLoader.getController();
	    borderPaneSX.setCenter(SXMainPane);
	}
	
	public void RawScreenDX() throws IOException {
		DXMainLoader = new FXMLLoader();
		DXMainPane = DXMainLoader.load(getClass().getResource("RawScreen.fxml").openStream());
		DXMainController = DXMainLoader.getController();
		borderPaneDX.setCenter(DXMainPane);
	}
	
	public void DynamicsScreenSX() throws IOException {
		SXMainLoader = new FXMLLoader();
	    SXMainPane = SXMainLoader.load(getClass().getResource("DynamicsScreen.fxml").openStream());
	    SXMainController = SXMainLoader.getController();
	    borderPaneSX.setCenter(SXMainPane);
	}
	
	public void DynamicsScreenDX() throws IOException {
		DXMainLoader = new FXMLLoader();
		DXMainPane = DXMainLoader.load(getClass().getResource("DynamicsScreen.fxml").openStream());
		DXMainController = DXMainLoader.getController();
		borderPaneDX.setCenter(DXMainPane);
	}
	
	public void DebugScreenSX() throws IOException {
		SXMainLoader = new FXMLLoader();
	    SXMainPane = SXMainLoader.load(getClass().getResource("DebugScreen.fxml").openStream());
	    SXMainController = SXMainLoader.getController();
	    borderPaneSX.setCenter(SXMainPane);
	}
	
	public void DebugScreenDX() throws IOException {
		DXMainLoader = new FXMLLoader();
		DXMainPane = DXMainLoader.load(getClass().getResource("DebugScreen.fxml").openStream());
		DXMainController = DXMainLoader.getController();
		borderPaneDX.setCenter(DXMainPane);
	}
	
	public void CommandScreenSX() throws IOException {
		SXMainLoader = new FXMLLoader();
	    SXMainPane = SXMainLoader.load(getClass().getResource("CommandScreen.fxml").openStream());
	    DXMainController = SXMainLoader.getController();
	    borderPaneSX.setCenter(SXMainPane);
	}
	
	public void CommandScreenDX() throws IOException {
		DXMainLoader = new FXMLLoader();
		DXMainPane = DXMainLoader.load(getClass().getResource("CommandScreen.fxml").openStream());
		DXMainController = DXMainLoader.getController();
		borderPaneDX.setCenter(DXMainPane);
	}
}
