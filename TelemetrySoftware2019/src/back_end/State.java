package back_end;

public class State extends DataElem {

	private boolean value;
	
	/*
	 * Call superclass constructor and initialize value
	 */
	public State(String name) {
		super(name);
		value = false;
	}
	
	/*
	 * Set value to val
	 */
	public void setValue(boolean val){
		value = val;
	}

	/*
	 * Get state value
	 */
	public boolean getValue(){
		return value;
	}
	
	/*
	 * Reset state value
	 */
	public void reset(){
		value = false;
	}
}
