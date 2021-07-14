package com.example.user_register.service;

import com.example.user_register.aop.annotation.ExecutionTime;
import com.example.user_register.aop.annotation.LogInputOutput;
import com.example.user_register.exception.user.UserNotFoundException;
import com.example.user_register.model.converter.UserDtoConverter;
import com.example.user_register.model.dto.request.NewUserRequestDto;
import com.example.user_register.model.dto.response.UserResponseDto;
import com.example.user_register.model.entity.User;
import com.example.user_register.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.user_register.exception.ErrorMessage.USER_NOT_FOUND;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserDtoConverter userDtoConverter;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       UserDtoConverter userDtoConverter,
                       PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.userDtoConverter = userDtoConverter;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Create a User from the newUserRequestDto in parameter.
     * Hash the user password and save the user in the database.
     * It is then convert to a UserResponseDto and returned.
     *
     * @param newUserRequestDto The NewUserRequestDto to user to register a new user.
     * @return a UserResponseDto containing user's information.
     */
    @LogInputOutput
    @ExecutionTime
    public UserResponseDto registerUser(NewUserRequestDto newUserRequestDto){
        User user = this.userDtoConverter.newUserRequestDtoToEntity(newUserRequestDto);
        user.setPassword(passwordEncoder.encode(newUserRequestDto.getPassword()));

        return this.userDtoConverter.userToUserResponseDto(
                this.userRepository.save(user)
        );
    }

    /**
     * Find a user by its id and return the user as a UserResponseDto.
     * If the user can't be found then throw a UserNotFoundException.
     *
     * @param userId The userId to look for.
     * @return a UserResponseDto containing the user parameters.
     */
    @LogInputOutput
    @ExecutionTime
    public UserResponseDto getUser(String userId){
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            return this.userDtoConverter.userToUserResponseDto(user.get());
        } else {
            throw new UserNotFoundException(USER_NOT_FOUND);
        }
    }
}
