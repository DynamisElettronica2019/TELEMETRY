package front_end.gui_ground;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import back_end.Channel;
import back_end.Command;
import back_end.Debug;
import back_end.Error;
import back_end.LapTimer;
import back_end.State;
import back_end.Threshold;
import configuration.ConfReader;
import front_end.gui_ground.DynamicsScreenController.HoveredThresholdNode;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.chart.XYChart.Data;

public class EngineScreenController extends Controller {
	private Map<String, Label> chartLabelMap = new HashMap<>();
	private Map<String, Series<String, Double>> chartChannelMap = new HashMap<>();
	private Series<String, Double> oilTempIn, oilTempOut;
	private Series<String, Double> waterTempLIN, waterTempLOut;
	private Series<String, Double> exhaust1Temp, exhaust2Temp;
	private Series<String, Double> oilPress;
	private ObservableList<XYChart.Series<String,Double>> waterTempChartData, oiltempChartData, exhaustTempChartData, pressChartData;
	private ObservableList<Integer> elementNumberList;
	private ArrayList<Boolean> toLoadList = new ArrayList<Boolean>();
	private ArrayList<String> channelList;
	private Map<String, Integer> loadArrayMap = new HashMap<>();
	private String timePattern;
	private DateTimeFormatter timeColonFormatter;
	private Integer size, offset;
	@FXML
	private ComboBox<Integer> numberValues;
	@FXML
	private LineChart<String, Double> oilTempChart, waterTempChart, exhaustTempChart, pressChart;
	@FXML
	private Label oilTempInLabel, oilTempOutLabel, waterTempInLLabel, waterTempOutLLabel, exhaust1TempLabel, exhaust2TempLabel, oilPressLabel;
	@FXML
	private ToggleButton pauseButton;
	@FXML
	private Slider slider;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		setChart();
		elementNumberList = FXCollections.observableArrayList(10, 50, 100, 500, 1000);
		numberValues.setItems(elementNumberList);
		numberValues.getSelectionModel().select(1);
		
		/*
		 * Time formatting
		 */
		timePattern = "HH:mm:ss.SSS";
		timeColonFormatter = DateTimeFormatter.ofPattern(timePattern);
		
		/*
		 * Load the channel names and map them to the update list
		 */
		channelList = ConfReader.getNames("channels");
		for (int i=0; i<channelList.size(); i++) {
			toLoadList.add(true);
			loadArrayMap.put(channelList.get(i), i);
		}
		
		/*
		 * Listener on the number of values to display
		 * Removes the unnecessary data if the newValue is lower than before
		 * Set to update the channel load list
		 */
		numberValues.valueProperty().addListener(new ChangeListener<Integer>() {
			@Override
			public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {	
				for (int i=0; i<toLoadList.size(); i++) {
					toLoadList.set(i, true);
				}
				view.getViewLoader().load();
			}
		 });
		
		/*
		 * Slider init
		 */
		slider.setVisible(false);
		slider.setMin(0);
		slider.setMax(100);
		slider.setValue(100);
		
		slider.valueProperty().addListener((obs, oldValue, newValue) -> {
			offset = size - (int) slider.getValue();
			for (int i=0; i<toLoadList.size(); i++) {
				toLoadList.set(i, true);
			}
			view.getViewLoader().load();
        });
		offset = 0;
	}

	@Override
	public void editDebug(Debug debug) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editState(State state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editChannel(Channel channel) {
		if(!channel.isEmpty()){
			size = channel.getSize();
			slider.setMax(size);
			Platform.runLater(new Runnable() {
			    @Override
			    public void run() {
			    	if(chartChannelMap.get(channel.getName()) != null) {
			    		if (toLoadList.get(loadArrayMap.get(channel.getName()))) {
			    			chartChannelMap.get(channel.getName()).setData(getLastnChartElemOffset(channel, offset));		    			
			    			toLoadList.set(loadArrayMap.get(channel.getName()), false);
			    		}
			    		else {
			    			if (!pauseButton.isSelected()) {
			    				if(chartChannelMap.get(channel.getName()) != null) {
			    					chartChannelMap.get(channel.getName()).getData().add(getLastChartElem(channel));
			    					chartLabelMap.get(channel.getName()).setText(Double.toString(channel.getLastElems(1).get(0)));
			    					if(chartChannelMap.get(channel.getName()).getData().size() > numberValues.getValue()) {
			    						chartChannelMap.get(channel.getName()).getData().remove(0);
			    					}
			    				}
			    			}
			    		}
			    	}	    	
			    }
			});
		}
		else {
			if(channel != null) {
				if(chartChannelMap.get(channel.getName()) != null) {
					chartChannelMap.get(channel.getName()).getData().clear();
				}
				if(chartLabelMap.get(channel.getName()) != null) {
					chartLabelMap.get(channel.getName()).setText("No value");
				}
			}
		}
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
	
	/*
	 *  Chart initzialization
	 *  Create new series and map channel names to them
	 *  link charts to series
	 */
	private void setChart() {
		waterTempChartData = FXCollections.observableArrayList();
		oiltempChartData = FXCollections.observableArrayList();
		exhaustTempChartData = FXCollections.observableArrayList();
		pressChartData = FXCollections.observableArrayList();
		
		oilTempIn = new Series<>();
		oilTempOut = new Series<>();
		waterTempLIN = new Series<>();
		waterTempLOut = new Series<>();
		exhaust1Temp = new Series<>();
		exhaust2Temp = new Series<>();
		oilPress = new Series<>();
		
		oilTempIn.setName("Oil Temp In");
		oilTempOut.setName("Oil Temp Out");
		waterTempLIN.setName("Water temp left In");
		waterTempLOut.setName("Water temp left Out");
		exhaust1Temp.setName("Exhaust 1 Temp");
		exhaust2Temp.setName("Exhaust 2 Temp");
		oilPress.setName("Oil Pression");
		
		waterTempChartData.add(waterTempLIN);
		waterTempChartData.add(waterTempLOut);
		oiltempChartData.add(oilTempIn);
		oiltempChartData.add(oilTempOut);
		exhaustTempChartData.add(exhaust1Temp);
		exhaustTempChartData.add(exhaust2Temp);
		pressChartData.add(oilPress);
		
		waterTempChart.setData(waterTempChartData);
		oilTempChart.setData(oiltempChartData);
		exhaustTempChart.setData(exhaustTempChartData);
		pressChart.setData(pressChartData);
		
		chartLabelMap.put("tOil_In", oilTempInLabel);
		chartLabelMap.put("tOil_Out", oilTempOutLabel);
		chartLabelMap.put("tWaterL_In", waterTempInLLabel);
		chartLabelMap.put("tWaterL_Out", waterTempOutLLabel);
		chartLabelMap.put("tExhaust_1", exhaust1TempLabel);
		chartLabelMap.put("tExhaust_2", exhaust2TempLabel);
		chartLabelMap.put("pOil", oilPressLabel);
		
		chartChannelMap.put("tOil_In", oilTempIn);
		chartChannelMap.put("tOil_Out", oilTempOut);
		chartChannelMap.put("tWaterL_In", waterTempLIN);
		chartChannelMap.put("tWaterL_Out", waterTempLOut);
		chartChannelMap.put("tExhaust_1", exhaust1Temp);
		chartChannelMap.put("tExhaust_2", exhaust2Temp);
		chartChannelMap.put("pOil", oilPress);
	}
	
	/*
	 *  Create a new data element ready for getting added to the chart
	 *  Timestamp on the x axis, value on the y
	 */
	private Data<String, Double> getLastChartElem(Channel channel) {
		LocalDateTime ts = channel.getLastTs();
		return new Data<String, Double>(ts.format(timeColonFormatter), channel.getLastElems());
	}
	
	/*
	 *  Create a new list of data for replacing the old list when value of numbers to display is changed
	 *  Timestamp is on the x axis and value on the y
	 */
	private ObservableList<Data<String, Double>> getLastnChartElemOffset(Channel channel, Integer offset) {
		ObservableList<Data<String, Double>> newDataList = FXCollections.observableArrayList();
		ArrayList<Double> channelDataList = channel.getLastElemsOffset(numberValues.getValue(), offset);
		ArrayList<LocalDateTime> tsList = channel.getLastTsOffset(numberValues.getValue(), offset);
		for (int i=0; i<channelDataList.size(); i++) {
			Data<String, Double> data = new Data<String, Double>(tsList.get(i).format(timeColonFormatter), channelDataList.get(i));
			data.setNode(new HoveredThresholdNode(tsList.get(i).format(timeColonFormatter), channelDataList.get(i)));
			newDataList.add(data);
		}
		return newDataList;
	}
	
	/*
	 *  Displays a value on hover, set as node on creation
	 */
	class HoveredThresholdNode extends StackPane {
		private Label label;
		private Integer index;
	    HoveredThresholdNode(String ts, double value) {
	      label = createDataThresholdLabel(value);
	      setOnMouseEntered(new EventHandler<MouseEvent>() {
	        @Override public void handle(MouseEvent mouseEvent) {
	        	for(int i=0;i<oilTempOut.getData().size();i++){
	              if(ts.equals(oilTempOut.getData().get(i).getXValue())){
	            	  ((HoveredThresholdNode) oilTempIn.getData().get(i).getNode()).getChildren().setAll(((HoveredThresholdNode) oilTempIn.getData().get(i).getNode()).getLabel());
	            	  ((HoveredThresholdNode) oilTempIn.getData().get(i).getNode()).getLabel().toFront();
	            	  ((HoveredThresholdNode) oilTempOut.getData().get(i).getNode()).getChildren().setAll(((HoveredThresholdNode) oilTempOut.getData().get(i).getNode()).getLabel());
	            	  ((HoveredThresholdNode) oilTempOut.getData().get(i).getNode()).getLabel().toFront();
	            	  ((HoveredThresholdNode) waterTempLIN.getData().get(i).getNode()).getChildren().setAll(((HoveredThresholdNode) waterTempLIN.getData().get(i).getNode()).getLabel());
	            	  ((HoveredThresholdNode) waterTempLIN.getData().get(i).getNode()).getLabel().toFront();
	            	  ((HoveredThresholdNode) waterTempLOut.getData().get(i).getNode()).getChildren().setAll(((HoveredThresholdNode) waterTempLOut.getData().get(i).getNode()).getLabel());
	            	  ((HoveredThresholdNode) waterTempLOut.getData().get(i).getNode()).getLabel().toFront();
	            	  ((HoveredThresholdNode) exhaust1Temp.getData().get(i).getNode()).getChildren().setAll(((HoveredThresholdNode) exhaust1Temp.getData().get(i).getNode()).getLabel());
	            	  ((HoveredThresholdNode) exhaust1Temp.getData().get(i).getNode()).getLabel().toFront();
	            	  ((HoveredThresholdNode) exhaust2Temp.getData().get(i).getNode()).getChildren().setAll(((HoveredThresholdNode) exhaust2Temp.getData().get(i).getNode()).getLabel());
	            	  ((HoveredThresholdNode) exhaust2Temp.getData().get(i).getNode()).getLabel().toFront();
	            	  ((HoveredThresholdNode) oilPress.getData().get(i).getNode()).getChildren().setAll(((HoveredThresholdNode) oilPress.getData().get(i).getNode()).getLabel());
	            	  ((HoveredThresholdNode) oilPress.getData().get(i).getNode()).getLabel().toFront();
	            	  index = i;  
	              }
	          }
	          setCursor(Cursor.NONE);
	        }
	      });
	      setOnMouseExited(new EventHandler<MouseEvent>() {
	        @Override public void handle(MouseEvent mouseEvent) {
	          ((HoveredThresholdNode) oilTempIn.getData().get(index).getNode()).getChildren().clear();
	          ((HoveredThresholdNode) oilTempOut.getData().get(index).getNode()).getChildren().clear();
	          ((HoveredThresholdNode) waterTempLIN.getData().get(index).getNode()).getChildren().clear();
	          ((HoveredThresholdNode) waterTempLOut.getData().get(index).getNode()).getChildren().clear();
	          ((HoveredThresholdNode) exhaust1Temp.getData().get(index).getNode()).getChildren().clear();
	          ((HoveredThresholdNode) exhaust2Temp.getData().get(index).getNode()).getChildren().clear();
	          ((HoveredThresholdNode) oilPress.getData().get(index).getNode()).getChildren().clear();
	          setCursor(Cursor.CROSSHAIR);
	        }
	      });
	    }
	    
	    private Label createDataThresholdLabel(double value) {
	        final Label label = new Label(value + "");
	        label.getStyleClass().addAll("default-color0", "chart-line-symbol", "chart-series-line");
	        label.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");
	        label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
	        return label;
	    }
	    
	    public Label getLabel() {
	    	return label;
	    }
	}
	
	/*
	 * Manage click of the pause button
	 */
	@FXML
	private void PressButtonClick() {
		if (pauseButton.isSelected()) {
			
			for (int i=0; i<toLoadList.size(); i++) {
				toLoadList.set(i, true);
			}
			view.getViewLoader().load();
			slider.setVisible(true);
		}
		else {
			for (int i=0; i<toLoadList.size(); i++) {
				toLoadList.set(i, true);
			}
			view.getViewLoader().load();
			offset = 0;
			slider.setVisible(false);
		}
	}
}
