package model.exception;

public class UserAlreadyException extends Exception {

	private static final long serialVersionUID = 667293769445488669L;

	public UserAlreadyException() {
    }

    public UserAlreadyException(String message) {
        super(message);
    }
}
