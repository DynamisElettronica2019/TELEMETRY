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
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class DynamicsScreenController extends Controller {
	private Boolean isPaused = false;
	private Series<String, Double> rpm, speed, sw, gear;
	private ObservableList<XYChart.Series<String,Double>> rpmChartData, speedChartData, swChartData, gearChartData;
	private ObservableList<Integer> elementNumberList;
	private String timePattern;
	private DateTimeFormatter timeColonFormatter;
	private ArrayList<String> channelList;
	private ArrayList<Boolean> toLoadList = new ArrayList<Boolean>();
	private Map<String, Integer> loadArrayMap = new HashMap<>();
	private Map<String, Series<String, Double>> chartChannelMap = new HashMap<>();
	private Map<String, Label> chartLabelMap = new HashMap<>();
	private Integer size, offset;
	private Boolean dragDone;
	@FXML
	private LineChart<String, Double> rpmChart, speedChart, swChart, gearChart;
	@FXML
	private ComboBox<Integer> numberValues;
	@FXML
	private Label rpmLabel, speedLabel, swLabel, gearLabel;
	@FXML
	private ToggleButton pauseButton;
	@FXML
	private Slider slider;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		
		/*
		 * N. data to visualize
		 */
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
		 * Do not show x axis
		 */
		rpmChart.getXAxis().setTickLabelsVisible(false);
		rpmChart.getXAxis().setTickMarkVisible(false);
		speedChart.getXAxis().setTickLabelsVisible(false);
		speedChart.getXAxis().setTickMarkVisible(false);
		swChart.getXAxis().setTickLabelsVisible(false);
		swChart.getXAxis().setTickMarkVisible(false);
		
		/*
		 *  Chart initialization
		 */
		rpmChartData = FXCollections.observableArrayList();
		speedChartData = FXCollections.observableArrayList();
		swChartData = FXCollections.observableArrayList();
		gearChartData = FXCollections.observableArrayList();
		
		rpm = new Series<>();
		speed = new Series<>();
		sw = new Series<>();
		gear = new Series<>();
		
		rpm.setName("RPM");
		speed.setName("Speed");
		sw.setName("Steering Wheel Angle");
		gear.setName("Gear");
		
		rpmChartData.add(rpm);
		speedChartData.add(speed);
		swChartData.add(sw);
		gearChartData.add(gear);
		
		rpmChart.setData(rpmChartData);
		speedChart.setData(speedChartData);
		swChart.setData(swChartData);
		gearChart.setData(gearChartData);
		
		chartLabelMap.put("nRPM", rpmLabel);
		chartLabelMap.put("vCar", speedLabel);
		chartLabelMap.put("aSteering", swLabel);
		chartLabelMap.put("nGear", gearLabel);
		
		chartChannelMap.put("nRPM", rpm);
		chartChannelMap.put("vCar", speed);
		chartChannelMap.put("aSteering", sw);
		chartChannelMap.put("nGear", gear);
		
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
		dragDone = false;
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
			if(chartChannelMap.get(channel.getName()) != null) {
				if (toLoadList.get(loadArrayMap.get(channel.getName()))) {
					Platform.runLater(new Runnable() {
					    @Override
					    public void run() {
			    			chartChannelMap.get(channel.getName()).setData(getLastnChartElemOffset(channel, offset));	    			
			    			toLoadList.set(loadArrayMap.get(channel.getName()), false);	    	
					    }
					});
	    		}
				else {
					if (!pauseButton.isSelected()) {
						Platform.runLater(new Runnable() {
						    @Override
						    public void run() {
						    	if(chartChannelMap.get(channel.getName()) != null) {
						    		chartChannelMap.get(channel.getName()).getData().add(getLastChartElem(channel));
									chartLabelMap.get(channel.getName()).setText(Double.toString(channel.getLastElems(1).get(0)));	
						    		if(chartChannelMap.get(channel.getName()).getData().size() > numberValues.getValue()) {
								    	chartChannelMap.get(channel.getName()).getData().remove(0);
								    }
						    	}	    	
						    }
						});
					}
					else {
						size = channel.getSize();
						slider.setMax(size);
					}
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
	
	/*
	 *  Create a new data element ready for getting added to the chart
	 *  Timestamp on the x axis, value on the y
	 */
	private Data<String, Double> getLastChartElem(Channel channel) {
		LocalDateTime ts = channel.getLastTs();
		Data<String, Double> data = new Data<String, Double>(ts.format(timeColonFormatter), channel.getLastElems());
		data.setNode(new HoveredThresholdNode(ts.format(timeColonFormatter), channel.getLastElems()));
		return data;
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
	 *  a node which displays a value on hover, but is otherwise empty 
	 */
	class HoveredThresholdNode extends StackPane {
		private Label label;
		private Integer index;
	    HoveredThresholdNode(String ts, double value) {
	      label = createDataThresholdLabel(value);
	      setOnMouseEntered(new EventHandler<MouseEvent>() {
	        @Override public void handle(MouseEvent mouseEvent) {
	        	for(int i=0;i<rpm.getData().size();i++){
	              if(ts.equals(rpm.getData().get(i).getXValue())){
	            	  ((HoveredThresholdNode) rpm.getData().get(i).getNode()).getChildren().setAll(((HoveredThresholdNode) rpm.getData().get(i).getNode()).getLabel());
	            	  ((HoveredThresholdNode) rpm.getData().get(i).getNode()).getLabel().toFront();
	            	  ((HoveredThresholdNode) sw.getData().get(i).getNode()).getChildren().setAll(((HoveredThresholdNode) sw.getData().get(i).getNode()).getLabel());
	            	  ((HoveredThresholdNode) sw.getData().get(i).getNode()).getLabel().toFront();
	            	  ((HoveredThresholdNode) gear.getData().get(i).getNode()).getChildren().setAll(((HoveredThresholdNode) gear.getData().get(i).getNode()).getLabel());
	            	  ((HoveredThresholdNode) gear.getData().get(i).getNode()).getLabel().toFront();
	            	  ((HoveredThresholdNode) speed.getData().get(i).getNode()).getChildren().setAll(((HoveredThresholdNode) speed.getData().get(i).getNode()).getLabel());
	            	  ((HoveredThresholdNode) speed.getData().get(i).getNode()).getLabel().toFront();
	            	  index = i;  
	              }
	          }
	          setCursor(Cursor.NONE);
	        }
	      });
	      setOnMouseExited(new EventHandler<MouseEvent>() {
	        @Override public void handle(MouseEvent mouseEvent) {
	          ((HoveredThresholdNode) rpm.getData().get(index).getNode()).getChildren().clear();
	          ((HoveredThresholdNode) sw.getData().get(index).getNode()).getChildren().clear();
	          ((HoveredThresholdNode) gear.getData().get(index).getNode()).getChildren().clear();
	          ((HoveredThresholdNode) speed.getData().get(index).getNode()).getChildren().clear();
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
}
