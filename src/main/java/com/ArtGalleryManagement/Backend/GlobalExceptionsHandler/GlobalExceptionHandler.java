package com.ArtGalleryManagement.Backend.GlobalExceptionsHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(NoProductFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ResponseEntity<String> handleNoProductFoundException(NoProductFoundException ex) {
		return new ResponseEntity<>("Product not found",HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(OutOfStockException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ResponseEntity<String> handleOutOfStockException(OutOfStockException ex) {
		return new ResponseEntity<>("Product is out of stock",HttpStatus.NOT_FOUND);
    }
	@ExceptionHandler(NoMessageFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ResponseEntity<String> handleNoMessageFoundException(NoMessageFoundException ex) {
		return new ResponseEntity<>("Message not found",HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler(NoPaymentInfoFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ResponseEntity<String> handleNoPaymentInfoFoundException(NoPaymentInfoFoundException ex) {
		return new ResponseEntity<>("Payment Information is missing",HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler(UserEmailNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ResponseEntity<String> handleUserEmailNotFoundException(UserEmailNotFoundException ex) {
		return new ResponseEntity<>("User Email is missing",HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler(ReviewException.class)
	@ResponseStatus(HttpStatus.FOUND)
	@ResponseBody
	public ResponseEntity<String> handleReviewException(ReviewException ex) {
		return new ResponseEntity<>("Review is already exist",HttpStatus.FOUND);
    }
	
	@ExceptionHandler(MethodNotAllowedException.class)
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@ResponseBody
	public ResponseEntity<String> handleMethodNotAllowedException(MethodNotAllowedException ex) {
		return new ResponseEntity<>("Admin is only allowed",HttpStatus.METHOD_NOT_ALLOWED);
    }
	@ExceptionHandler(NoProductFoundOrNotCheckedException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ResponseEntity<String> handleNoProductFoundOrNotCheckedException(NoProductFoundOrNotCheckedException ex) {
		return new ResponseEntity<>("Product does not exist or not checked out by user",HttpStatus.NOT_FOUND);
    }
	@ExceptionHandler(ReturnProductException.class)
	@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
	@ResponseBody
	public ResponseEntity<String> handleReturnProductException(ReturnProductException ex) {
		return new ResponseEntity<>("Can be returned after 1 day!",HttpStatus.NOT_ACCEPTABLE);
    }
}
