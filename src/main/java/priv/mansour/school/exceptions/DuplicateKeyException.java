package priv.mansour.school.exceptions;

@SuppressWarnings("serial")
public class DuplicateKeyException  extends RuntimeException {

	public DuplicateKeyException(String message) {
		super(message);
	}

}
