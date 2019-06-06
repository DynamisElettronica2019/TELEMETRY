package front_end.gui_ground;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import front_end.gui_ground.DynamicsScreenController.HoveredThresholdNode;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.chart.XYChart.Data;

public class EngineScreenController extends Controller {
	private Map<String, Label> topLeftLabelMap = new HashMap<>();
	private Map<String, Label> topRightLabelMap = new HashMap<>();
	private Map<String, Label> bottomLeftLabelMap = new HashMap<>();
	private Map<String, Label> bottomRightLabelMap = new HashMap<>();
	private Map<String, Series<String, Double>> topLeftChannelMap = new HashMap<>();
	private Map<String, Series<String, Double>> topRightChannelMap = new HashMap<>();
	private Map<String, Series<String, Double>> bottomLeftChannelMap = new HashMap<>();
	private Map<String, Series<String, Double>> bottomRightChannelMap = new HashMap<>();
	private ObservableList<XYChart.Series<String,Double>> bottomRightChartData, bottomLeftChartData, topLeftChartData, topRightChartData;
	private ObservableList<Integer> elementNumberList;
	private ArrayList<Boolean> toLoadList = new ArrayList<Boolean>();
	private ArrayList<String> channelList;
	private Map<String, Integer> loadArrayMap = new HashMap<>();
	private String timePattern;
	private DateTimeFormatter timeColonFormatter;
	private Integer size, offset;
	private ArrayList<Series<String, Double>> topLeftSeries = new ArrayList<Series<String, Double>>();
	private ArrayList<Series<String, Double>> topRightSeries = new ArrayList<Series<String, Double>>();
	private ArrayList<Series<String, Double>> bottomLeftSeries = new ArrayList<Series<String, Double>>();
	private ArrayList<Series<String, Double>> bottomRightSeries = new ArrayList<Series<String, Double>>();
	private ArrayList<Label> topLeftLabels = new ArrayList<Label>();
	private ArrayList<Label> topRightLabels = new ArrayList<Label>();
	private ArrayList<Label> bottomLeftLabels = new ArrayList<Label>();
	private ArrayList<Label> bottomRightLabels = new ArrayList<Label>();
	@FXML
	private ComboBox<Integer> numberValues;
	@FXML
	private CheckComboBox<String> topLeftSelList, topRightSelList, bottomLeftSelList, bottomRightSelList;
	@FXML
	private LineChart<String, Double> bottomLeftChart, bottomRightChart, topLeftChart, topRightChart;
	@FXML
	private Label oilTempInLabel, oilTempOutLabel, waterTempInLLabel, waterTempOutLLabel, exhaust1TempLabel, exhaust2TempLabel, oilPressLabel;
	@FXML
	private ToggleButton pauseButton;
	@FXML
	private Slider slider;
	@FXML
	private VBox topLeftLabelBox, topRightLabelBox, bottomLeftLabelBox, bottomRightLabelBox;
	
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
			topLeftSelList.getItems().add(channelList.get(i));
			topRightSelList.getItems().add(channelList.get(i));
			bottomLeftSelList.getItems().add(channelList.get(i));
			bottomRightSelList.getItems().add(channelList.get(i));
		}
		
		/*
		 * Add listeners for CheckComboBoxes
		 */
		topLeftSelList.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
		    public void onChanged(ListChangeListener.Change<? extends String> c) {
		    	/*
		    	 * Cleanup older series
		    	 */
		    	topLeftSeries.clear();
		    	topLeftChartData.clear();
		    	topLeftChannelMap.clear();
		    	topLeftLabels.clear();
		    	topLeftLabelBox.getChildren().clear();
		    	topLeftLabelMap.clear();
		    	/*
		    	 * Add new series
		    	 */
		        for(int i=0; i< topLeftSelList.getCheckModel().getCheckedItems().size(); i++) {
		        	topLeftSeries.add(new Series<>());
		        	topLeftSeries.get(i).setName(topLeftSelList.getCheckModel().getCheckedItems().get(i));
		        	topLeftChartData.add(topLeftSeries.get(i));
		        	topLeftChannelMap.put(topLeftSelList.getCheckModel().getCheckedItems().get(i), topLeftSeries.get(i));
		        	
		        	/*
		        	 * Cleanup labels and add new to map
		        	 */
		        	HBox labelBox = new HBox();
		        	Label name = new Label(topLeftSelList.getCheckModel().getCheckedItems().get(i) + ": ");
		        	name.getStyleClass().add("chart-labels-bold");
		        	labelBox.getChildren().add(name);
		        	topLeftLabels.add(new Label("No Value"));
		        	topLeftLabels.get(i).getStyleClass().add("chart-labels");
		        	topLeftLabelMap.put(topLeftSelList.getCheckModel().getCheckedItems().get(i), topLeftLabels.get(i));
		        	labelBox.getChildren().add(topLeftLabels.get(i));
		        	topLeftLabelBox.getChildren().add(labelBox);
		        }
		        for (int i=0; i<toLoadList.size(); i++) {
					toLoadList.set(i, true);
				}
		    }
		});
		
		topRightSelList.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
		    public void onChanged(ListChangeListener.Change<? extends String> c) {
		    	topRightSeries.clear();
		    	topRightChartData.clear();
		    	topRightChannelMap.clear();
		    	topRightLabels.clear();
		    	topRightLabelBox.getChildren().clear();
		    	topRightLabelMap.clear();
		        for(int i=0; i< topRightSelList.getCheckModel().getCheckedItems().size(); i++) {
		        	topRightSeries.add(new Series<>());
		        	topRightSeries.get(i).setName(topRightSelList.getCheckModel().getCheckedItems().get(i));
		        	topRightChartData.add(topRightSeries.get(i));
		        	topRightChannelMap.put(topRightSelList.getCheckModel().getCheckedItems().get(i), topRightSeries.get(i));
		        	
		        	/*
		        	 * Cleanup labels and add new to map
		        	 */
		        	HBox labelBox = new HBox();
		        	Label name = new Label(topRightSelList.getCheckModel().getCheckedItems().get(i) + ": ");
		        	name.getStyleClass().add("chart-labels-bold");
		        	labelBox.getChildren().add(name);
		        	topRightLabels.add(new Label("No Value"));
		        	topRightLabels.get(i).getStyleClass().add("chart-labels");
		        	topRightLabelMap.put(topRightSelList.getCheckModel().getCheckedItems().get(i), topRightLabels.get(i));
		        	labelBox.getChildren().add(topRightLabels.get(i));
		        	topRightLabelBox.getChildren().add(labelBox);
		        }
		        for (int i=0; i<toLoadList.size(); i++) {
					toLoadList.set(i, true);
				}
		    }
		});
		
		bottomLeftSelList.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
		    public void onChanged(ListChangeListener.Change<? extends String> c) {
		    	bottomLeftSeries.clear();
		    	bottomLeftChartData.clear();
		    	bottomLeftChannelMap.clear();
		    	bottomLeftLabels.clear();
		    	bottomLeftLabelBox.getChildren().clear();
		    	bottomLeftLabelMap.clear();
		        for(int i=0; i< bottomLeftSelList.getCheckModel().getCheckedItems().size(); i++) {
		        	bottomLeftSeries.add(new Series<>());
		        	bottomLeftSeries.get(i).setName(bottomLeftSelList.getCheckModel().getCheckedItems().get(i));
		        	bottomLeftChartData.add(bottomLeftSeries.get(i));
		        	bottomLeftChannelMap.put(bottomLeftSelList.getCheckModel().getCheckedItems().get(i), bottomLeftSeries.get(i));
		        	
		        	/*
		        	 * Cleanup labels and add new to map
		        	 */
		        	HBox labelBox = new HBox();
		        	Label name = new Label(bottomLeftSelList.getCheckModel().getCheckedItems().get(i) + ": ");
		        	name.getStyleClass().add("chart-labels-bold");
		        	labelBox.getChildren().add(name);
		        	bottomLeftLabels.add(new Label("No Value"));
		        	bottomLeftLabels.get(i).getStyleClass().add("chart-labels");
		        	bottomLeftLabelMap.put(bottomLeftSelList.getCheckModel().getCheckedItems().get(i), bottomLeftLabels.get(i));
		        	labelBox.getChildren().add(bottomLeftLabels.get(i));
		        	bottomLeftLabelBox.getChildren().add(labelBox);
		        }
		        for (int i=0; i<toLoadList.size(); i++) {
					toLoadList.set(i, true);
				}
		    }
		});
		
		bottomRightSelList.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
		    public void onChanged(ListChangeListener.Change<? extends String> c) {
		    	bottomRightSeries.clear();
		    	bottomRightChartData.clear();
		    	bottomRightChannelMap.clear();
		    	bottomRightLabels.clear();
		    	bottomRightLabelBox.getChildren().clear();
		    	bottomRightLabelMap.clear();
		        for(int i=0; i< bottomRightSelList.getCheckModel().getCheckedItems().size(); i++) {
		        	bottomRightSeries.add(new Series<>());
		        	bottomRightSeries.get(i).setName(bottomRightSelList.getCheckModel().getCheckedItems().get(i));
		        	bottomRightChartData.add(bottomRightSeries.get(i));
		        	bottomRightChannelMap.put(bottomRightSelList.getCheckModel().getCheckedItems().get(i), bottomRightSeries.get(i));
		        	
		        	/*
		        	 * Cleanup labels and add new to map
		        	 */
		        	HBox labelBox = new HBox();
		        	Label name = new Label(bottomRightSelList.getCheckModel().getCheckedItems().get(i) + ": ");
		        	name.getStyleClass().add("chart-labels-bold");
		        	labelBox.getChildren().add(name);
		        	bottomRightLabels.add(new Label("No Value"));
		        	bottomRightLabels.get(i).getStyleClass().add("chart-labels");
		        	bottomRightLabelMap.put(bottomRightSelList.getCheckModel().getCheckedItems().get(i), bottomRightLabels.get(i));
		        	labelBox.getChildren().add(bottomRightLabels.get(i));
		        	bottomRightLabelBox.getChildren().add(labelBox);
		        }
		        for (int i=0; i<toLoadList.size(); i++) {
					toLoadList.set(i, true);
				}
		    }
		});
		
		/*
		 * Initialize channels
		 */
		topLeftSelList.getCheckModel().check("T EXHAUST 1");
		topLeftSelList.getCheckModel().check("T EXHAUST 2");
		topRightSelList.getCheckModel().check("P OIL[<3500]");
		bottomLeftSelList.getCheckModel().check("T OIL IN");
		bottomLeftSelList.getCheckModel().check("T OIL OUT");
		bottomRightSelList.getCheckModel().check("T H20 SX IN");
		bottomRightSelList.getCheckModel().check("T H20 SX OUT");
		bottomRightSelList.getCheckModel().check("T H20 DX IN");
		bottomRightSelList.getCheckModel().check("T H20 DX OUT");
		bottomRightSelList.getCheckModel().check("T WATER ENGINE[>95]");
		
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
			    	if(topLeftChannelMap.get(channel.getName()) != null) {
			    		if (toLoadList.get(loadArrayMap.get(channel.getName()))) {
			    			topLeftChannelMap.get(channel.getName()).setData(getLastnChartElemOffset(channel, offset));		    			
			    			toLoadList.set(loadArrayMap.get(channel.getName()), false);
			    		}
			    		else {
			    			if (!pauseButton.isSelected()) {
			    				if(topLeftChannelMap.get(channel.getName()) != null) {
			    					topLeftChannelMap.get(channel.getName()).getData().add(getLastChartElem(channel));
			    					topLeftLabelMap.get(channel.getName()).setText(Double.toString(channel.getLastElems(1).get(0)));
			    					if(topLeftChannelMap.get(channel.getName()).getData().size() > numberValues.getValue()) {
			    						topLeftChannelMap.get(channel.getName()).getData().remove(0);
			    					}
			    				}
			    			}
			    		}
			    	}
			    	else if(topRightChannelMap.get(channel.getName()) != null) {
			    		if (toLoadList.get(loadArrayMap.get(channel.getName()))) {
			    			topRightChannelMap.get(channel.getName()).setData(getLastnChartElemOffset(channel, offset));		    			
			    			toLoadList.set(loadArrayMap.get(channel.getName()), false);
			    		}
			    		else {
			    			if (!pauseButton.isSelected()) {
			    				if(topRightChannelMap.get(channel.getName()) != null) {
			    					topRightChannelMap.get(channel.getName()).getData().add(getLastChartElem(channel));
			    					topRightLabelMap.get(channel.getName()).setText(Double.toString(channel.getLastElems(1).get(0)));
			    					if(topRightChannelMap.get(channel.getName()).getData().size() > numberValues.getValue()) {
			    						topRightChannelMap.get(channel.getName()).getData().remove(0);
			    					}
			    				}
			    			}
			    		}
			    	}
			    	else if(bottomLeftChannelMap.get(channel.getName()) != null) {
			    		if (toLoadList.get(loadArrayMap.get(channel.getName()))) {
			    			bottomLeftChannelMap.get(channel.getName()).setData(getLastnChartElemOffset(channel, offset));		    			
			    			toLoadList.set(loadArrayMap.get(channel.getName()), false);
			    		}
			    		else {
			    			if (!pauseButton.isSelected()) {
			    				if(bottomLeftChannelMap.get(channel.getName()) != null) {
			    					bottomLeftChannelMap.get(channel.getName()).getData().add(getLastChartElem(channel));
			    					bottomLeftLabelMap.get(channel.getName()).setText(Double.toString(channel.getLastElems(1).get(0)));
			    					if(bottomLeftChannelMap.get(channel.getName()).getData().size() > numberValues.getValue()) {
			    						bottomLeftChannelMap.get(channel.getName()).getData().remove(0);
			    					}
			    				}
			    			}
			    		}
			    	}
			    	else if(bottomRightChannelMap.get(channel.getName()) != null) {
			    		if (toLoadList.get(loadArrayMap.get(channel.getName()))) {
			    			bottomRightChannelMap.get(channel.getName()).setData(getLastnChartElemOffset(channel, offset));		    			
			    			toLoadList.set(loadArrayMap.get(channel.getName()), false);
			    		}
			    		else {
			    			if (!pauseButton.isSelected()) {
			    				if(bottomRightChannelMap.get(channel.getName()) != null) {
			    					bottomRightChannelMap.get(channel.getName()).getData().add(getLastChartElem(channel));
			    					bottomRightLabelMap.get(channel.getName()).setText(Double.toString(channel.getLastElems(1).get(0)));
			    					if(bottomRightChannelMap.get(channel.getName()).getData().size() > numberValues.getValue()) {
			    						bottomRightChannelMap.get(channel.getName()).getData().remove(0);
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
				if(topLeftChannelMap.get(channel.getName()) != null) {
					topLeftChannelMap.get(channel.getName()).getData().clear();
				}
				else if(topRightChannelMap.get(channel.getName()) != null) {
					topRightChannelMap.get(channel.getName()).getData().clear();
				}
				else if(bottomLeftChannelMap.get(channel.getName()) != null) {
					bottomLeftChannelMap.get(channel.getName()).getData().clear();
				}
				else if(bottomRightChannelMap.get(channel.getName()) != null) {
					bottomRightChannelMap.get(channel.getName()).getData().clear();
				}
				if(topLeftLabelMap.get(channel.getName()) != null) {
					topLeftLabelMap.get(channel.getName()).setText("No value");
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
		bottomRightChartData = FXCollections.observableArrayList();
		bottomLeftChartData = FXCollections.observableArrayList();
		topLeftChartData = FXCollections.observableArrayList();
		topRightChartData = FXCollections.observableArrayList();
		
		bottomRightChart.setData(bottomRightChartData);
		bottomLeftChart.setData(bottomLeftChartData);
		topLeftChart.setData(topLeftChartData);
		topRightChart.setData(topRightChartData);
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
	        	if (pauseButton.isSelected()) {
		        		for(int i=0;i<topLeftSeries.get(0).getData().size();i++){
			              if(ts.equals(topLeftSeries.get(0).getData().get(i).getXValue())){
			            	  for(Series<String, Double> serie: topLeftSeries) {
			            		  ((HoveredThresholdNode) serie.getData().get(i).getNode()).getChildren().setAll(((HoveredThresholdNode) serie.getData().get(i).getNode()).getLabel());
				            	  ((HoveredThresholdNode) serie.getData().get(i).getNode()).getLabel().toFront();
			            	  }
			            	  for(Series<String, Double> serie: topRightSeries) {
			            		  ((HoveredThresholdNode) serie.getData().get(i).getNode()).getChildren().setAll(((HoveredThresholdNode) serie.getData().get(i).getNode()).getLabel());
				            	  ((HoveredThresholdNode) serie.getData().get(i).getNode()).getLabel().toFront();
			            	  }
			            	  for(Series<String, Double> serie: bottomLeftSeries) {
			            		  ((HoveredThresholdNode) serie.getData().get(i).getNode()).getChildren().setAll(((HoveredThresholdNode) serie.getData().get(i).getNode()).getLabel());
				            	  ((HoveredThresholdNode) serie.getData().get(i).getNode()).getLabel().toFront();
			            	  }
			            	  for(Series<String, Double> serie: bottomRightSeries) {
			            		  ((HoveredThresholdNode) serie.getData().get(i).getNode()).getChildren().setAll(((HoveredThresholdNode) serie.getData().get(i).getNode()).getLabel());
				            	  ((HoveredThresholdNode) serie.getData().get(i).getNode()).getLabel().toFront();
			            	  }
			            	  index = i;  
			              }
		        		}
		        	setCursor(Cursor.NONE);
	        	}
	        }
	      });
	      setOnMouseExited(new EventHandler<MouseEvent>() {
	        @Override public void handle(MouseEvent mouseEvent) {
	        	if(pauseButton.isSelected()) {
	            	  for(Series<String, Double> serie: topLeftSeries) {
	                		((HoveredThresholdNode) serie.getData().get(index).getNode()).getChildren().clear();
	              	  }
	              	  for(Series<String, Double> serie: topRightSeries) {
	              		  ((HoveredThresholdNode) serie.getData().get(index).getNode()).getChildren().clear();
	              	  }
	              	  for(Series<String, Double> serie: bottomLeftSeries) {
	              		  ((HoveredThresholdNode) serie.getData().get(index).getNode()).getChildren().clear();
	              	  }
	              	  for(Series<String, Double> serie: bottomRightSeries) {
	              		  ((HoveredThresholdNode) serie.getData().get(index).getNode()).getChildren().clear();
	              	  }
	      	          setCursor(Cursor.CROSSHAIR);
	        	}
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
	
	/*
	 * Fire pause button on request
	 */
	@Override
	public void setPause() {
		pauseButton.setSelected(true);
	}
}
