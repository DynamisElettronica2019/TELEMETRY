package front_end.gui_ground;

public class TableList {
	private String name;
	private double value;
	private boolean valueBool;
	
	public TableList(String name, double value) {
		this.setName(name);
		this.setValue(value);
	}
	
	//Overloading constructor for boolean
	public TableList(String name, boolean valueBool) {
		this.setName(name);
		this.setValueBool(valueBool);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public boolean isValueBool() {
		return valueBool;
	}

	public void setValueBool(boolean valueBool) {
		this.valueBool = valueBool;
	}
}
