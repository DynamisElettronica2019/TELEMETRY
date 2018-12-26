package back_end;

import java.util.ArrayList;

public class Channel extends DataElem {

	private ArrayList<Double> serie;
	
	/*
	 * Call superclass constructor and initialize serie
	 */
	public Channel(String name) {
		super(name);
		serie = new ArrayList<>();
	}
	
	/*
	 * Add an element to serie
	 */
	public void addElem(Double elem) {
		serie.add(elem);
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
