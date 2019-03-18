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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
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
	private AnchorPane AnchorPaneSX, AnchorPaneDX;
	private VBox vboxSX, vboxDX;

	
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
        vboxSX = new VBox(5);
        AnchorPaneSX = new AnchorPane();
        sceneSX = new Scene(AnchorPaneSX);
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
        vboxDX = new VBox(5);
        AnchorPaneDX = new AnchorPane();
        sceneDX = new Scene(AnchorPaneDX);
        stageDX.setScene(sceneDX);
        stageDX.show();
	}
	
	//Update functions call controller relative functions, always defined in controlelr
	@Override
	public void UpdateChannel(Channel channel) {
		SXTopBarController.editChannel(channel);
		DXTopBarController.editChannel(channel);
		SXMainController.editChannel(channel);
		DXMainController.editChannel(channel);
	}

	@Override
	public void UpdateCommand(Command command) {
		SXTopBarController.editCommand(command);
		DXTopBarController.editCommand(command);
		SXMainController.editCommand(command);
		DXMainController.editCommand(command);
	}

	@Override
	public void UpdateDebug(Debug debug) {
		SXTopBarController.editDebug(debug);
		DXTopBarController.editDebug(debug);
		SXMainController.editDebug(debug);
		DXMainController.editDebug(debug);
	}

	@Override
	public void UpdateError(Error error) {
		SXTopBarController.editError(error);
		DXTopBarController.editError(error);
		SXMainController.editError(error);
		DXMainController.editError(error);
	}

	@Override
	public void UpdateState(State state) {
		SXTopBarController.editState(state);
		DXTopBarController.editState(state);
		SXMainController.editState(state);
		DXMainController.editState(state);
	}

	@Override
	public void UpdateLap(LapTimer lapTimer) {
		SXTopBarController.editLap(lapTimer);
		DXTopBarController.editLap(lapTimer);
		SXMainController.editLap(lapTimer);
		DXMainController.editLap(lapTimer);
	}

	@Override
	public void UpdateTS(Threshold thresholdState) {
		SXTopBarController.editTS(thresholdState);
		DXTopBarController.editTS(thresholdState);
		SXMainController.editTS(thresholdState);
		DXMainController.editTS(thresholdState);
	}
	
	
	@Override
	public void setCommandSender(CommandSender commandSender) {
		super.setCommandSender(commandSender);
		// TODO Auto-generated method stub
	}
	
	public CommandSender getCommandSender() {
		return commandSender;
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
	    
	    //Set SX constraints
	    AnchorPane.setTopAnchor(vboxSX, 0.0);
	    AnchorPane.setBottomAnchor(vboxSX, 0.0);
	    AnchorPane.setLeftAnchor(vboxSX, 0.0);
	    AnchorPane.setRightAnchor(vboxSX, 0.0);
	    vboxSX.getChildren().add(SXTopBarPane);
	    vboxSX.getChildren().add(SXMainPane);
	    AnchorPaneSX.getChildren().add(vboxSX);
	    
	   //Set DX constraints
	    AnchorPane.setTopAnchor(vboxDX, 0.0);
	    AnchorPane.setBottomAnchor(vboxDX, 0.0);
	    AnchorPane.setLeftAnchor(vboxDX, 0.0);
	    AnchorPane.setRightAnchor(vboxDX, 0.0);
	    vboxDX.getChildren().add(DXTopBarPane);
	    vboxDX.getChildren().add(DXMainPane);
	    AnchorPaneDX.getChildren().add(vboxDX);
	    
	    SXTopBarController.SetView(this);
	    DXTopBarController.SetView(this);
	    SXMainController.SetView(this);
	    DXMainController.SetView(this);
	    SXTopBarController.SetSide('s');
	    DXTopBarController.SetSide('d');
	    viewLoader.load();
	}
	
	/*
	 * From here functions for changing screen when pressing buttons on the top bar
	 * Called from top bar controllers
	 */
	public void SetScreen(String fxml, char side) throws IOException {
		if (side == 's') {
			SXMainLoader = new FXMLLoader();
		    SXMainPane = SXMainLoader.load(getClass().getResource(fxml).openStream());
		    SXMainController = SXMainLoader.getController();
		    vboxSX.getChildren().set(1, SXMainPane);
		}
		else if (side == 'd')
		{
			DXMainLoader = new FXMLLoader();
			DXMainPane = DXMainLoader.load(getClass().getResource(fxml).openStream());
			DXMainController = DXMainLoader.getController();
			vboxDX.getChildren().set(1, DXMainPane);
		}
		else {
			System.err.println("Side screen error");
		}
		viewLoader.load();
	}
}
