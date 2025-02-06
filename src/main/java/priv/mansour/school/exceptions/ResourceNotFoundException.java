package priv.mansour.school.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class ResourceNotFoundException extends RuntimeException {

	private static final Logger logger = LoggerFactory.getLogger(ResourceNotFoundException.class);

	public ResourceNotFoundException(String message) {
		super(message);
		logger.error("Resource not found with message: {}", message);
	}

	public ResourceNotFoundException(String message, Throwable cause) {
		super(message, cause);
		logger.error("Resource not found with messag: {}", message, cause);
	}
}
