package priv.mansour.school.exceptions;


import lombok.extern.slf4j.Slf4j;
import priv.mansour.school.logger.GlobalLogger;
@Slf4j
@SuppressWarnings("serial")
public class ResourceNotFoundException extends RuntimeException {



	public ResourceNotFoundException(String message) {
		super(message);
		log.warn("Resource not found with message: {}", message);
	}

	public ResourceNotFoundException(String message, Throwable cause) {
		super(message, cause);
		log.warn("Resource not found with message: {}", message, cause);
	}

	public ResourceNotFoundException(String message, String entity, String id) {
		super(message);
		GlobalLogger.warnNotFound(entity, id);
	}

}
