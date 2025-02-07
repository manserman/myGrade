package priv.mansour.school.exceptions;

import priv.mansour.school.logger.GlobalLogger;

@SuppressWarnings("serial")
public class DuplicateKeyException extends RuntimeException {

	public DuplicateKeyException(String message) {
		super(message);
	}

	public DuplicateKeyException(String message, String entity, String id) {
		super(message);
		GlobalLogger.warnDuplicate(entity, id);
	}
}
