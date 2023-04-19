package com.jk.exptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jk.dto.ApiResponseMessage;

@RestControllerAdvice
public class GlobalExceptionHandler {
   Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	//handler Resource Not Found Exception
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponseMessage> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
		 logger.info(" Exception Handler invoked !!");
		ApiResponseMessage response =	ApiResponseMessage.builder().message(ex.getMessage())
		.status(HttpStatus.NOT_FOUND).success(true).build();
		return new ResponseEntity(response,HttpStatus.NOT_FOUND);
	}
	
	 
	//method Arggument not valid Exception
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,Object>> handleMethodArgumentNotvalidException(MethodArgumentNotValidException ex){
		
		List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
		Map<String,Object> response = new HashMap<>();
		allErrors.stream().forEach(objectError->{
			String message= objectError.getDefaultMessage();
			String field = ((FieldError) objectError).getField();
			response.put(field, message);
		});
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
		
	}
	
	// handle bad Api Exception for extension
	@ExceptionHandler(BadApiRequestException.class)
	public ResponseEntity<ApiResponseMessage> handleBadApiRequest(BadApiRequestException ex){
		 logger.info(" Bad Api Request !!");
		ApiResponseMessage response =	ApiResponseMessage.builder().message(ex.getMessage())
		.status(HttpStatus.BAD_REQUEST).success(false).build();
		return new ResponseEntity(response,HttpStatus.BAD_REQUEST);
	}
}
