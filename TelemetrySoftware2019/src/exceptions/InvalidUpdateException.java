package exceptions;

import java.time.LocalDateTime;

public class InvalidUpdateException extends MyException {
	private static final long serialVersionUID = 1L;
	private static final String type = "[Update error]";
	
	public InvalidUpdateException(String err) {
		super(err);
	}
	
	public void log() {
		System.err.println("["+LocalDateTime.now()+" "+type+"] "+msg);
	}
}
