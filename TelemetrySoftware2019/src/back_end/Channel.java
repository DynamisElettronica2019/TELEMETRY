package back_end;

import java.util.ArrayList;

import front_end.View;

public class Channel extends DataElem {

	private ArrayList<Double> serie;
	
	/*
	 * Call superclass constructor and initialize serie
	 */
	public Channel(String name, ArrayList<View> myViews) {
		super(name, myViews);
		serie = new ArrayList<>();
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
	 * Get n last serie elements
	 */
	public ArrayList<Double> getLastElems(int n){
		ArrayList<Double> temp = new ArrayList<>();
		for(int i=serie.size()-n;i<serie.size();i++) temp.add(serie.get(i));
		return temp;
	}

	/*
	 * Reset serie
	 */
	public void reset() {
		serie.clear();
	}
}
