package exceptions;

public class InvalidReadingException extends MyException {
	private static final long serialVersionUID = 1L;
	private static final String type = "[Reading error]";
	
	public InvalidReadingException(String err) {
		super(err);
	}
	
	public void log() {
		//TODO print error ts
		System.err.println(type+" "+msg);
	}
	
	
}
