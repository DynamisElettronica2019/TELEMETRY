package exceptions;

import java.time.LocalDateTime;

public class InvalidCodeException extends MyException {
	private static final long serialVersionUID = 1L;
	private static final String type = "[Error_Code error]";
	
	public InvalidCodeException(String err) {
		super(err);
	}
	
	public void log() {
		System.err.println("["+LocalDateTime.now()+" "+type+"] "+msg);
	}
}