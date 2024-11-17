package br.com.araujomarcos.order.infrastructure.config;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import br.com.araujomarcos.order.domain.exception.BusinessException;

@ControllerAdvice
public class ControlExceptionHandler {

	public static final String X_RD_TRACEID = "X-rd-traceid";
	public static final String CONSTRAINT_VALIDATION_FAILED = "Constraint validation failed";

	@ExceptionHandler(value = { BusinessException.class})
	protected ResponseEntity<Object> handleConflict(BusinessException ex, WebRequest request) {
		return ResponseEntity.status(ex.getHttpStatusCode()).body(ex.getOnlyBody());

	}

	@ExceptionHandler({ Throwable.class })
	public ResponseEntity<Object> handleException(Throwable eThrowable) {

		BusinessException ex = BusinessException.builder()
				.httpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR)
				.message(Optional.ofNullable(eThrowable.getMessage()).orElse(eThrowable.toString()))
				.build();
	
		return ResponseEntity.status(ex.getHttpStatusCode()).body(ex.getOnlyBody());

	}

	@ExceptionHandler({ MissingServletRequestParameterException.class })
	public ResponseEntity<Object> handleException(MissingServletRequestParameterException e) {

		BusinessException ex = BusinessException.builder()
				.httpStatusCode(HttpStatus.BAD_REQUEST)
				.message(Optional.ofNullable(e.getMessage()).orElse(e.toString()))
				.build();

		return ResponseEntity.status(ex.getHttpStatusCode()).body(ex.getOnlyBody());

	}

	@ExceptionHandler({ HttpRequestMethodNotSupportedException.class })
	public ResponseEntity<Object> handleException(HttpRequestMethodNotSupportedException  e) {

		BusinessException ex = BusinessException.builder()
				.httpStatusCode(HttpStatus.METHOD_NOT_ALLOWED)
				.message(Optional.ofNullable(e.getMessage()).orElse(e.toString()))
				.build();

		return ResponseEntity.status(ex.getHttpStatusCode()).body(ex.getOnlyBody());
	}

}
