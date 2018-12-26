package exceptions;

public class InvalidUpdateException extends Exception {
	private static final long serialVersionUID = 1L;
	private static final String type = "[Update error]";
	private String msg;
	
	public InvalidUpdateException(String err) {
		msg = err;
	}
	
	public void log() {
		//TODO print error ts
		System.err.println(type+" "+msg);
	}
}
