package exceptions;

public class InvalidReadingException extends Exception {
	private static final long serialVersionUID = 7802809916810716256L;
	private static final String type = "[Reading error]";
	private String msg;
	
	public InvalidReadingException(String err) {
		msg = err;
	}
	
	public void log() {
		//TODO print error ts
		System.err.println(type+" "+msg);
	}
	
	
}
