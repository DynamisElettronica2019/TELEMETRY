package exceptions;

public class InvalidUpdateException extends MyException {
	private static final long serialVersionUID = 1L;
	private static final String type = "[Update error]";
	
	public InvalidUpdateException(String err) {
		super(err);
	}
	
	public void log() {
		//TODO print error ts
		System.err.println(type+" "+msg);
	}
}
