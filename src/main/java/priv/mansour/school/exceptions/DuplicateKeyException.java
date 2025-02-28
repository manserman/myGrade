package priv.mansour.school.exceptions;

import lombok.extern.slf4j.Slf4j;
import priv.mansour.school.logger.GlobalLogger;

@Slf4j
public class DuplicateKeyException extends RuntimeException {

	public DuplicateKeyException(String message) {
		super(message);
	}

	public DuplicateKeyException(String message, String entity, String id) {
		super(message);
		GlobalLogger.warnDuplicate(entity, id);
	}
	public DuplicateKeyException(String message, Throwable cause) {
		super(message, cause);
		log.warn("Resource not found with message: {}", message, cause);
	}
}
