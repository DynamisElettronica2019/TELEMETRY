package back_end;

public class Debug extends DataElem {
	
	private Double value;

	/*
	 * Call superclass constructor and initialize value
	 */
	public Debug(String name) {
		super(name);
		value = 0.0;
	}
	
	/*
	 * Set value to val
	 */
	public void setValue(Double val){
		value = val;
	}

	/*
	 * Get state value
	 */
	public Double getValue(){
		return value;
	}
	
	/*
	 * Reset state value
	 */
	public void reset(){
		value = 0.0;
	}

}
