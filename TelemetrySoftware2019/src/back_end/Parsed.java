package back_end;

public class Parsed {
	
	private String[] parsedInfo;
	private int currElement;
	
	public Parsed() {
		currElement = 0;
	}
	
	public void parsedEdit(String strToAdd)
	{
		parsedInfo[currElement] = strToAdd;
		currElement++;
	}
}
