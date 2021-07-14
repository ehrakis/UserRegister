package com.example.user_register.exception;

import com.example.user_register.exception.user.UserNotFoundException;
import com.mongodb.MongoWriteException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.format.DateTimeParseException;
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
    @ExceptionHandler(DateTimeParseException.class)
    @ResponseBody
    public Map<String, String> handleImpossibleAgeExceptions(DateTimeParseException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put(BIRTH_DATE_FIELD, INVALID_DATE_FORMAT);
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MongoWriteException.class)
    @ResponseBody
    public Map<String, String> handleImpossibleAgeExceptions(
            MongoWriteException ex) {
        Map<String, String> errors = new HashMap<>();
        if(ex.getMessage().contains("email dup key")) {
            errors.put(EMAIL_FIELD, EMAIL_ALREADY_USED);
        } else {
            errors.put(ERROR_FIELD, "Error during user creation");
        }
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    public Map<String, String> handleImpossibleAgeExceptions(
            UserNotFoundException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put(ERROR_FIELD, USER_NOT_FOUND);
        return errors;
    }
}
