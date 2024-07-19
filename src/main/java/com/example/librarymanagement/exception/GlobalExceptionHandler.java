package com.example.librarymanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {BookNotFoundException.class})
    public ResponseEntity<String> handleBookNotFoundException(BookNotFoundException e){
        return new ResponseEntity<>(  e.getMessage() , HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = {BookAlreadyExistsException.class})
    public ResponseEntity<String> handeBookAlreadyExistException(BookAlreadyExistsException ex){
                return new ResponseEntity<>(ex.getMessage() , HttpStatus.CONFLICT);
    }
    @ExceptionHandler(value = {NoPatronFoundException.class})
    public ResponseEntity<String> handleNoPatronException(NoPatronFoundException ex){
        return new ResponseEntity<>(ex.getMessage() , HttpStatus.NOT_FOUND);

    }
    @ExceptionHandler(value = {PatronAlreadyExistsException.class})
    public ResponseEntity<String> handlePatronAlreadyExistsException (PatronAlreadyExistsException ex){
        return new ResponseEntity<>(ex.getMessage() , HttpStatus.CONFLICT);
    }
    @ExceptionHandler(value = {InvalidReturnDateException.class})
    public ResponseEntity<String> handleInvalidDateReturnException(InvalidReturnDateException ex){
        return new ResponseEntity<>(ex.getMessage() , HttpStatus.BAD_REQUEST);

    }

}
