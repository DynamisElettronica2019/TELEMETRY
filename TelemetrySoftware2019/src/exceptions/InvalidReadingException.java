package exceptions;

public class InvalidReadingException extends Exception {
	private static final long serialVersionUID = 7802809916810716256L;

	/*
	 * Print exception timestamp and error type to console
	 */
	
	public InvalidReadingException(String Errore) {
		log(Errore);
	}
	
	private void log(String type) {
		//Testing
		System.err.println(type);
	}
	
	
}
