package com.shortenUrl.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.shortenUrl.dto.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<String> handleRunTimeException(RuntimeException ex){
		
		return new ResponseEntity<>(" Error: "+ex.getMessage(), HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGenericException(Exception ex){
		
		return new ResponseEntity<>(" Something went wrong: "+ex.getMessage(), HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(URLException.class)
	public ResponseEntity<ErrorResponse> shortUrlNotFoundException(URLException ex,HttpServletRequest request) {
		
		ErrorResponse error = new ErrorResponse(
                LocalDateTime.now().toString(),
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                request.getRequestURI()
        );
		
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request){
		
		String message = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
		
		ErrorResponse error = new ErrorResponse(
				LocalDateTime.now().toString(),
                HttpStatus.NOT_FOUND.value(),
                message,
                request.getRequestURI()
        );
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		
	}
	

	@ExceptionHandler(InvalidUrlException.class)
	public ResponseEntity<ErrorResponse> invalidUrlException(InvalidUrlException ex,HttpServletRequest request) {
		
		ErrorResponse error = new ErrorResponse(
                LocalDateTime.now().toString(),
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                request.getRequestURI()
        );
		
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
		
	}

}
