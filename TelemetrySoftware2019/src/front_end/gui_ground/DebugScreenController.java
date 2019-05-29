package front_end.gui_ground;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.controlsfx.control.CheckComboBox;

import back_end.Channel;
import back_end.Command;
import back_end.Debug;
import back_end.Error;
import back_end.LapTimer;
import back_end.State;
import back_end.Threshold;
import configuration.ConfReader;
import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.Gauge.SkinType;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;

public class DebugScreenController extends Controller {
	
	private Map<String, Gauge> debugMap = new HashMap<>();
	
	@FXML
	private CheckComboBox<String> allTempList, allCurrentList;
	@FXML
	private FlowPane gaugePane;
	
	private ArrayList<String> allDebugStringList;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
	
		allDebugStringList = ConfReader.getNames("debug");
		for (String s : allDebugStringList){
			if(isTemp(s)) allTempList.getItems().add(subTemp(s));
			else if(isCurrent(s)) allCurrentList.getItems().add(subCurrent(s));
			else System.err.println("[Configuration error] Invalid debug name "+s+". Must end with _TEMP or _CURRENT");
		}
		
		allTempList.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
		     public void onChanged(ListChangeListener.Change<? extends String> c) {
		    	 int index = Integer.parseInt(c.toString().split(" ")[4].split(",")[0]);
		    	 String name = c.toString().split(" ")[1].substring(1, c.toString().split(" ")[1].length()-1)+"_TEMP";
		    	 String addRem = c.toString().split(" ")[2];
		         if(addRem.equals("added") && debugMap.get(name)==null){
		        	 Gauge temp = new Gauge(SkinType.LINEAR); 
					 temp.setId(name);
					 temp.setTitle(subTemp(name));
					 gaugePane.getChildren().add(temp); //POSITION??
					 debugMap.put(name, temp);
		         }
		         else if(addRem.equals("removed")){
		        	 gaugePane.getChildren().remove(((Gauge)gaugePane.lookup("#"+name)));
		        	 debugMap.remove(name);
		         }
		     }
		 });
		
		allCurrentList.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
			public void onChanged(ListChangeListener.Change<? extends String> c) {
		    	 int index = Integer.parseInt(c.toString().split(" ")[4].split(",")[0]);
		    	 String name = c.toString().split(" ")[1].substring(1, c.toString().split(" ")[1].length()-1)+"_CURRENT";
		    	 String addRem = c.toString().split(" ")[2];
		    	 if(addRem.equals("added") && debugMap.get(name)==null){
		        	 Gauge temp = new Gauge(SkinType.FLAT);
					 temp.setId(name);
					 temp.setTitle(subCurrent(name));
					 gaugePane.getChildren().add(temp); //POSITION??
					 debugMap.put(name, temp);
		         }
		    	 else if(addRem.equals("removed")){
		        	 gaugePane.getChildren().remove(((Gauge)gaugePane.lookup("#"+name)));
		        	 debugMap.remove(name);
		         }
		     }
		 });

		allTempList.getCheckModel().checkAll();
		allCurrentList.getCheckModel().checkAll();
		
	}
	
	/*
	 * String parse functions
	 */
	private boolean isTemp(String name){
		return name.split("_")[name.split("_").length-1].equals("TEMP");
	}
	private boolean isCurrent(String name){
		return name.split("_")[name.split("_").length-1].equals("CURRENT");
	}
	private String subTemp(String name){
		if(name.split("_")[name.split("_").length-1].equals("TEMP")) return name.substring(0,name.length()-5);
		else return null;
	}
	private String subCurrent(String name){
		if(name.split("_")[name.split("_").length-1].equals("CURRENT")) return name.substring(0,name.length()-8);
		else return null;
	}


	@Override
	public void editDebug(Debug debug) {
		if(debugMap.get(debug.getName())!=null) debugMap.get(debug.getName()).setValue(debug.getValue());
	}

	@Override
	public void editState(State state) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void editChannel(Channel channel) {
		// TODO Auto-generated method stub
	}

	@Override
	public void editCommand(Command command) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editError(Error error) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editLap(LapTimer lapTimer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editTS(Threshold thresholdState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPause() {
		// TODO Auto-generated method stub
		
	}

}
