package dev.sayem.selis.exceptions.handler;

import dev.sayem.selis.exceptions.NonExistentException;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

@ControllerAdvice
public class ExHandler {

	private final Environment env;

	public ExHandler(Environment env) {
		this.env = env;
	}

	@ExceptionHandler(NonExistentException.class)
	ResponseEntity<ErrorResponse> handleNotExistsException(NonExistentException ex) {
		return buildResponse(
				HttpStatus.NOT_FOUND,
				ex,
				null
		);
	}

	@ExceptionHandler(NonExistentException.class)
	ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
		return buildResponse(
				HttpStatus.INTERNAL_SERVER_ERROR,
				ex,
				null
		);
	}

	Boolean debug() {
		var profiles = this.env.getActiveProfiles();
		return Arrays.stream(profiles).noneMatch(s -> s.equals("prod"));
	}

	public ResponseEntity<ErrorResponse> buildResponse(
			HttpStatus status,
			Throwable ex,
			Map<String, Set<String>> customHeaders
	) {
		if (customHeaders == null) {
			customHeaders = Map.of(); // Use an empty map if customHeaders is not provided
		}

		ErrorResponse response = this.debug()
				? new ErrorResponse(status.value(), status.name(), ex.getMessage() != null ? ex.getMessage() : "", ex)
				: new ErrorResponse(status.value(), status.name(), ex.getMessage() != null ? ex.getMessage() : "");

		HttpHeaders headers = new HttpHeaders();

		return ResponseEntity
				.status(status)
				.headers(headers)
				.body(response);
	}

}

