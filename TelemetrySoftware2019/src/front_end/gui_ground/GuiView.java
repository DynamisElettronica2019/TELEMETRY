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
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

/*
 * Gui view for only SX screen
 */
public class GuiView extends View {
	
	protected final int SCREEN_OFFSET_X = -8;
	protected final int SCREEN_OFFSET_Y = 0;
	
	protected Stage stageSX;
	private Scene sceneSX;
	private Parent SXTopBarPane;
	private Parent SXMainPane;
	private TopBarController SXTopBarController;
	private Controller SXMainController;
	private FXMLLoader SXTopBarLoader;
	private FXMLLoader SXMainLoader;
	private AnchorPane AnchorPaneSX;
	private VBox vboxSX;
	
	public GuiView() throws IOException {
		System.out.println("Starting gui view..");
		
		//Set stage SX
		stageSX = new Stage();
		stageSX.setTitle("Telemetry2019_SX");
		Screen mainScreen = Screen.getScreens().get(0); 
        Rectangle2D bounds = mainScreen.getVisualBounds();
        stageSX.setX(bounds.getMinX()+SCREEN_OFFSET_X);
        stageSX.setY(bounds.getMinY()+SCREEN_OFFSET_Y);
        stageSX.setMaximized(true);
        
        //Initialize stage SX
        vboxSX = new VBox(5);
        AnchorPaneSX = new AnchorPane();
        sceneSX = new Scene(AnchorPaneSX);
        stageSX.setScene(sceneSX);
        stageSX.show();
	}

	//Update functions call SX controller relative functions, always defined in controller
		@Override
		public void UpdateChannel(Channel channel) {
			SXTopBarController.editChannel(channel);
			SXMainController.editChannel(channel);
		}

		@Override
		public void UpdateCommand(Command command) {
			SXTopBarController.editCommand(command);
			SXMainController.editCommand(command);
		}

		@Override
		public void UpdateDebug(Debug debug) {
			SXTopBarController.editDebug(debug);
			SXMainController.editDebug(debug);
		}

		@Override
		public void UpdateError(Error error) {
			SXTopBarController.editError(error);
			SXMainController.editError(error);
		}

		@Override
		public void UpdateState(State state) {
			SXTopBarController.editState(state);
			SXMainController.editState(state);
		}

		@Override
		public void UpdateLap(LapTimer lapTimer) {
			SXTopBarController.editLap(lapTimer);
			SXMainController.editLap(lapTimer);
		}

		@Override
		public void UpdateTS(Threshold thresholdState) {
			SXTopBarController.editTS(thresholdState);
			SXMainController.editTS(thresholdState);
		}
		
		
		@Override
		public void setCommandSender(CommandSender commandSender) {
			super.setCommandSender(commandSender);
		}
		
		public CommandSender getCommandSender() {
			return commandSender;
		}
		
		@Override
		public void setViewLoader(ViewLoader viewLoader) throws IOException {
			//Set first two FXML and pass viewloader
	        SXTopBarLoader = new FXMLLoader();
	        SXTopBarPane = SXTopBarLoader.load(getClass().getResource("TopBar.fxml").openStream());
	        SXTopBarController = SXTopBarLoader.getController();
	        SXMainLoader = new FXMLLoader();
		    SXMainPane = SXMainLoader.load(getClass().getResource("EngineScreen.fxml").openStream());
		    SXMainController = SXMainLoader.getController();
		    
		    //Set SX constraints
		    AnchorPane.setTopAnchor(vboxSX, 0.0);
		    AnchorPane.setBottomAnchor(vboxSX, 0.0);
		    AnchorPane.setLeftAnchor(vboxSX, 0.0);
		    AnchorPane.setRightAnchor(vboxSX, 0.0);
		    vboxSX.getChildren().add(SXTopBarPane);
		    vboxSX.getChildren().add(SXMainPane);
		    AnchorPaneSX.getChildren().add(vboxSX);
		    
		    SXTopBarController.SetView(this);
		    SXMainController.SetView(this);
		    SXTopBarController.SetSide('s');
		    
			super.setViewLoader(viewLoader);
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
			    SXMainController.SetView(this);
			    vboxSX.getChildren().set(1, SXMainPane);
			}
			
			//Reload content after change screen
			viewLoader.load();
		}
}