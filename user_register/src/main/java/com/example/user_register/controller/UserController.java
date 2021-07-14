package com.example.user_register.controller;

import com.example.user_register.aop.annotation.ExecutionTime;
import com.example.user_register.aop.annotation.LogInputOutput;
import com.example.user_register.model.dto.request.NewUserRequestDto;
import com.example.user_register.model.dto.response.UserResponseDto;
import com.example.user_register.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Endpoint to create a new user.
     * The request must contain a valid NewUserRequestDto object.
     * Possible status returned:
     * - 201: User successfully created.
     * - 400: Request badly formatted.
     *
     * @param newUserRequestDto the parameter of the requests.
     * @return a UserResponseDto if the user is successfully created, or an error otherwise.
     */
    @ExecutionTime
    @LogInputOutput
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("register")
    public @ResponseBody
    UserResponseDto createUser(@Valid @RequestBody NewUserRequestDto newUserRequestDto) {
        return userService.registerUser(newUserRequestDto);
    }

    /**
     * Endpoint to retrieve a user by its id.
     * Possible status returned
     * - 200: User successfully returned.
     * - 404: User not found.
     *
     * @param userId The is of the user.
     * @return a UserResponseDto
     */
    @ExecutionTime
    @LogInputOutput
    @GetMapping("{userId}")
    public @ResponseBody
    UserResponseDto getUser(@PathVariable String userId) {
        return userService.getUser(userId);
    }
}
