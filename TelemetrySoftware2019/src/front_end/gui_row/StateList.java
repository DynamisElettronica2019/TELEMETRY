package front_end.gui_row;

public class StateList {
	private String name;
	private boolean value;
	
	public StateList(String name, boolean value) {
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean getValue() {
		return value;
	}
	public void setValue(boolean value) {
		this.value = value;
	}
}
