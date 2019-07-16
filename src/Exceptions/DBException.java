package Exceptions;

public class DBException extends Exception {
	public DBException(String message, Throwable throwable) {
		super(message,throwable);
	}
}
