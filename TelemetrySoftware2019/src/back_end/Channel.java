package back_end;

import java.time.LocalDateTime;
import java.util.ArrayList;

import front_end.View;

public class Channel extends DataElem {

	private ArrayList<Double> serie;
	private Data data;
	
	/*
	 * Call superclass constructor and initialize serie
	 */
	public Channel(String name, Data data, ArrayList<View> myViews) {
		super(name, myViews);
		serie = new ArrayList<>();
		this.data=data;
		for(View v: myViews) this.addObserver(v.getChObs());
	}
	
	/*
	 * Add an element to serie
	 */
	public void addElem(Double elem) {
		serie.add(elem);
		
		setChanged();
		notifyObservers();
	}
	
	/*
	 * Get n (or less) last serie elements
	 */
	public ArrayList<Double> getLastElems(int n){
		ArrayList<Double> temp = new ArrayList<>();
		for(int i=Math.max(serie.size()-n,0);i<serie.size();i++) temp.add(serie.get(i));
		return temp;
	}
	
	/*
	 * Get n last serie timestamps
	 */
	public ArrayList<LocalDateTime> getLastTs(int n){
		return data.getLastTs(n);
	}

	/*
	 * Reset serie
	 */
	public void reset() {
		serie.clear();
	}
	
	public javafx.scene.chart.XYChart.Data<String, Double> getLastChartEl() {
		return new javafx.scene.chart.XYChart.Data<String, Double>(getLastTs(1).get(0).toString(), serie.get(serie.size()));
	}
}
