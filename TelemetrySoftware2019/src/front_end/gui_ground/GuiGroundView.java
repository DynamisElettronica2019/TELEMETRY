package front_end.gui_ground;

import java.io.IOException;

import back_end.Channel;
import back_end.Command;
import back_end.Debug;
import back_end.Error;
import back_end.LapTimer;
import back_end.State;
import back_end.Threshold;
import back_end.ViewLoader;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class GuiGroundView extends GuiView {
	
	private Stage stageDX;
	private Scene sceneDX;
	private Parent DXTopBarPane;
	private Parent DXMainPane;
	private TopBarController DXTopBarController;
	private Controller DXMainController;
	private FXMLLoader DXTopBarLoader;
	private FXMLLoader DXMainLoader;
	private AnchorPane AnchorPaneDX;
	private VBox vboxDX;
	
	public GuiGroundView() throws IOException {
		
        super();
        
        //Set stage DX
        stageDX = new Stage();
        stageDX.getIcons().add(new Image("front_end/gui_ground/Dynamis.png"));
        stageDX.setTitle("Telemetry2019_DX");
        Screen secondScreen = Screen.getScreens().get(0);
        if(Screen.getScreens().size()>1) secondScreen = Screen.getScreens().get(1);
        Rectangle2D bounds = secondScreen.getVisualBounds();
        stageDX.setX(bounds.getMinX()+SCREEN_OFFSET_X);
        stageDX.setY(bounds.getMinY()+SCREEN_OFFSET_Y);
        stageDX.setMaximized(true);
        stageDX.setOnCloseRequest( event -> {
        	stageSX.close();
        	try {
				commandSender.CloseFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
        } );
        stageSX.setOnCloseRequest( event -> {
        	stageDX.close();
        	try {
				commandSender.CloseFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
        } );
        
        //Initialize stage DX
        vboxDX = new VBox(0);
        AnchorPaneDX = new AnchorPane();
        sceneDX = new Scene(AnchorPaneDX);
        sceneDX.getStylesheets().add(getStylesheetString());
        stageDX.setScene(sceneDX);
        stageDX.show();
	}
	
	//Update functions call controller relative functions, always defined in controller
	@Override
	public void UpdateChannel(Channel channel, boolean loadMode) {
		super.UpdateChannel(channel, loadMode);
		DXTopBarController.editChannel(channel);
		DXMainController.editChannel(channel);
	}

	@Override
	public void UpdateCommand(Command command) {
		super.UpdateCommand(command);
		DXTopBarController.editCommand(command);
		DXMainController.editCommand(command);
	}

	@Override
	public void UpdateDebug(Debug debug) {
		super.UpdateDebug(debug);
		DXTopBarController.editDebug(debug);
		DXMainController.editDebug(debug);
	}

	@Override
	public void UpdateError(Error error) {
		super.UpdateError(error);
		DXTopBarController.editError(error);
		DXMainController.editError(error);
	}

	@Override
	public void UpdateState(State state) {
		super.UpdateState(state);
		DXTopBarController.editState(state);
		DXMainController.editState(state);
	}

	@Override
	public void UpdateLap(LapTimer lapTimer) {
		super.UpdateLap(lapTimer);
		DXTopBarController.editLap(lapTimer);
		DXMainController.editLap(lapTimer);
	}

	@Override
	public void UpdateTS(Threshold thresholdState) {
		super.UpdateTS(thresholdState);
		DXTopBarController.editTS(thresholdState);
		DXMainController.editTS(thresholdState);
	}
	
	@Override
	public void setViewLoader(ViewLoader viewLoader) throws IOException {
		//Set first two FXML and pass viewloader
        DXTopBarLoader = new FXMLLoader();
        DXTopBarPane = DXTopBarLoader.load(getClass().getResource("TopBar.fxml").openStream());
        DXTopBarController = DXTopBarLoader.getController();
	    DXMainLoader = new FXMLLoader();
	    DXMainPane = DXMainLoader.load(getClass().getResource("CommandScreen.fxml").openStream());
	    DXMainController = DXMainLoader.getController();
	    
	   //Set DX constraints
	    AnchorPane.setTopAnchor(vboxDX, 0.0);
	    AnchorPane.setBottomAnchor(vboxDX, 0.0);
	    AnchorPane.setLeftAnchor(vboxDX, 0.0);
	    AnchorPane.setRightAnchor(vboxDX, 0.0);
	    DXTopBarPane.prefWidth(200);
	    vboxDX.getChildren().add(DXTopBarPane);
	    vboxDX.getChildren().add(DXMainPane);
	    AnchorPaneDX.getChildren().add(vboxDX);
	    
	    DXTopBarController.SetView(this);
	    DXMainController.SetView(this);
	    DXTopBarController.SetSide('d');
	    
		super.setViewLoader(viewLoader);
	}
	
	/*
	 * From here functions for changing screen when pressing buttons on the top bar
	 * Called from top bar controllers
	 */
	public void SetScreen(String fxml, char side) throws IOException {
		if (side == 'd') {
			DXMainLoader = new FXMLLoader();
			DXMainPane = DXMainLoader.load(getClass().getResource(fxml).openStream());
			DXMainController = DXMainLoader.getController();
		    DXMainController.SetView(this);
			vboxDX.getChildren().set(1, DXMainPane);
		}
		
		super.SetScreen(fxml, side);
	}
	
	/*
	 * Pause both sx and dx stage if you are in ground mode
	 */
	@Override
	public void SetPause() {
		super.SetPause();
		DXMainController.setPause();
	}
}
