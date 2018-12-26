package exceptions;

public abstract class MyException extends Exception{
	private static final long serialVersionUID = 1L;
	protected String msg;
	
	public MyException(String err) {
		msg = err;
	}
	
	public abstract void log();

}
