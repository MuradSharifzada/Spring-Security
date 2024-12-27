package com.spring.exception;

import com.spring.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameAlreadyTakenException.class)
    public ResponseEntity<ErrorDto> handleUsernameAlreadyTakenException(UsernameAlreadyTakenException ex) {
        ErrorDto errorDTO = new ErrorDto("Username Already Taken", ex.getMessage());
        return new ResponseEntity<>(errorDTO, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDto> handleUserNotFoundException(UserNotFoundException ex) {
        ErrorDto errorDTO = new ErrorDto("User Not Found", ex.getMessage());
        return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorDto> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        ErrorDto errorDTO = new ErrorDto("Invalid Credentials", ex.getMessage());
        return new ResponseEntity<>(errorDTO, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleGenericException(Exception ex) {
        ErrorDto errorDTO = new ErrorDto("Internal Server Error", ex.getMessage());
        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
