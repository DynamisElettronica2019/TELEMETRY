package back_end;

import java.time.LocalDateTime;
import java.util.ArrayList;

import front_end.View;

public class Channel extends DataElem {

	private ArrayList<Double> serie;
	private Data data;
	private int serverNumb;
	
	/*
	 * Call superclass constructor and initialize serie
	 */
	public Channel(String name, String serverNumber, Data data, ArrayList<View> myViews) {
		super(name, myViews);
		serie = new ArrayList<>();
		this.serverNumb = Integer.parseInt(serverNumber); //=0 if not to send
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
	 * Get last serie element
	 */
	public Double getLastElems(){
		if(serie.size()>0)
			return serie.get(serie.size()-1);
		else
			return null;
	}
	
	/*
	 * Get last ts element
	 */
	public LocalDateTime getLastTs(){
		return data.getLastTs();
	}
	
	public int getServerNumb() {
		return serverNumb;
	}
	
	/*
	 * Verify if there are data in serie
	 */
	public boolean isEmpty(){
		if(serie.size()>0) return false;
		return true;
	}

	/*
	 * Reset serie
	 */
	public void reset() {
		serie.clear();
	}
	
}
