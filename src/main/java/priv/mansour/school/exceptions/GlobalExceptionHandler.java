package priv.mansour.school.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.put(error.getField(), error.getDefaultMessage());
		}
		return ResponseEntity.badRequest().body(errors);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Map<String, String>> handleResourceNotFoundException(ResourceNotFoundException ex) {
		Map<String, String> response = new HashMap<>();
		response.put("error", "Resource Not Found");
		response.put("message", ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public ResponseEntity<Map<String, String>> handleDuplicateResourceException(DuplicateKeyException ex) {
		Map<String, String> response = new HashMap<>();
		response.put("error", "Duplicate Key");
		response.put("message", ex.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, String>> handleGlobalException(Exception ex) {
		Map<String, String> response = new HashMap<>();
		response.put("error", "Internal Server Error");
		response.put("message", "Erreur interne : " + ex.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}
}
