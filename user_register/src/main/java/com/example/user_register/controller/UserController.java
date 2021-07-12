package com.example.user_register.controller;

import com.example.user_register.exception.UserNotFoundException;
import com.example.user_register.model.dto.request.NewUserRequestDto;
import com.example.user_register.model.dto.response.UserResponseDto;
import com.example.user_register.service.UserService;
import com.mongodb.MongoWriteException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    /**
     * Endpoint to create a new user.
     * The request must contain a valid NewUserRequestDto object.
     * Possible status returned:
     *  - 201: User successfully created.
     *  - 400: Request badly formatted.
     *
     * @param newUserRequestDto the parameter of the requests.
     * @return a UserResponseDto if the user is successfully created, or an error otherwise.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("register")
    public @ResponseBody
    UserResponseDto createUser(@Valid @RequestBody NewUserRequestDto newUserRequestDto){
        return userService.registerUser(newUserRequestDto);
    }

    /**
     * Endpoint to retrieve a user by its id.
     * Possible status returned
     *  - 200: User successfully returned.
     *  - 404: User not found.
     *
     * @param userId The is of the user.
     * @return a UserResponseDto
     */
    @GetMapping("{userId}")
    public @ResponseBody
    UserResponseDto getUser(@PathVariable String userId){
        return userService.getUser(userId);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
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
    public Map<String, String> handleImpossibleAgeExceptions(
            DateTimeParseException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("birthDate", "The date should be in the format: yyyy-mm-dd");
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MongoWriteException.class)
    public Map<String, String> handleImpossibleAgeExceptions(
            MongoWriteException ex) {
        Map<String, String> errors = new HashMap<>();
        if(ex.getMessage().contains("email dup key")) {
            errors.put("email", "This email is already used");
        } else {
            errors.put("Bad request", "Please check the documentation");
        }
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public Map<String, String> handleImpossibleAgeExceptions(
            UserNotFoundException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", "User not found");
        return errors;
    }
}
