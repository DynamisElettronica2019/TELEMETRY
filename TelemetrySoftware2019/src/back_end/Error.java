package back_end;

import java.time.LocalDateTime;

public class Error {
	
	private String name;
	private char code;
	private LocalDateTime lastOcc;
	private int numbOcc;
	
	/*
	 * Create an error with a given name and a given identificator
	 */
	public Error(String name, char code) {
		this.name = name;
		this.code = code;
		numbOcc = 0;
		lastOcc = null;
	}
	
	/*
	 * Set a new occurence for this error updating lastOcc to now and incrementing numbOcc
	 */
	public void setOcc() {
		numbOcc++;
		lastOcc = LocalDateTime.now();
	}

}
