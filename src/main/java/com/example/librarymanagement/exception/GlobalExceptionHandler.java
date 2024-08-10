package com.example.librarymanagement.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<String> handleBookNotFoundException(BookNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookAlreadyExistsException.class)
    public ResponseEntity<String> handleBookAlreadyExistException(BookAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NoPatronFoundException.class)
    public ResponseEntity<String> handleNoPatronException(NoPatronFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PatronAlreadyExistsException.class)
    public ResponseEntity<String> handlePatronAlreadyExistsException(PatronAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidReturnDateException.class)
    public ResponseEntity<String> handleInvalidDateReturnException(InvalidReturnDateException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ProblemDetail> handleBadCredentialsException(BadCredentialsException ex) {
        return createProblemDetailResponse(HttpStatus.UNAUTHORIZED, ex.getMessage(), "The username or password is incorrect");
    }

    @ExceptionHandler(AccountStatusException.class)
    public ResponseEntity<ProblemDetail> handleAccountStatusException(AccountStatusException ex) {
        return createProblemDetailResponse(HttpStatus.FORBIDDEN, ex.getMessage(), "The account is locked");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ProblemDetail> handleAccessDeniedException(AccessDeniedException ex) {
        return createProblemDetailResponse(HttpStatus.FORBIDDEN, ex.getMessage(), "You are not authorized to access this resource");
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ProblemDetail> handleSignatureException(SignatureException ex) {
        return createProblemDetailResponse(HttpStatus.FORBIDDEN, ex.getMessage(), "The JWT signature is invalid");
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ProblemDetail> handleExpiredJwtException(ExpiredJwtException ex) {
        return createProblemDetailResponse(HttpStatus.FORBIDDEN, ex.getMessage(), "The JWT token has expired");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleGenericException(Exception ex) {
        logger.error("An unexpected error occurred", ex);
        return createProblemDetailResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), "Unknown internal server error.");
    }

    private ResponseEntity<ProblemDetail> createProblemDetailResponse(HttpStatus status, String message, String description) {
        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(status, message);
        errorDetail.setProperty("description", description);
        return new ResponseEntity<>(errorDetail, status);
    }
}
