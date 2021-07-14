package com.example.user_register.exception;

import com.example.user_register.exception.user.EmailAlreadyExistException;
import com.example.user_register.exception.user.InvalidBirthDateFormatException;
import com.example.user_register.exception.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

import static com.example.user_register.exception.ErrorMessage.*;

@ControllerAdvice
public class UserExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidBirthDateFormatException.class)
    @ResponseBody
    public Map<String, String> handleInvalidBirthDateFormatExceptions(InvalidBirthDateFormatException e) {
        Map<String, String> errors = new HashMap<>();

        errors.put(BIRTH_DATE_FIELD, e.getMessage());
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmailAlreadyExistException.class)
    @ResponseBody
    public Map<String, String> handleMongoWriteException(
            EmailAlreadyExistException e) {
        Map<String, String> errors = new HashMap<>();
        errors.put(EMAIL_FIELD, e.getMessage());
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    public Map<String, String> handleUserNotFoundExceptions(
            UserNotFoundException e) {
        Map<String, String> errors = new HashMap<>();
        errors.put(ERROR_FIELD, e.getMessage());
        return errors;
    }
}
