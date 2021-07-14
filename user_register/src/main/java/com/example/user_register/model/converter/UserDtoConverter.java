package com.example.user_register.model.converter;

import com.example.user_register.aop.annotation.ExecutionTime;
import com.example.user_register.aop.annotation.LogInputOutput;
import com.example.user_register.model.dto.request.NewUserRequestDto;
import com.example.user_register.model.dto.response.UserResponseDto;
import com.example.user_register.model.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class UserDtoConverter {

    private final ModelMapper modelMapper;

    public UserDtoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Convert a NewUserRequestDto to a User object.
     *
     * @param newUserRequestDto The NewUserRequestDto to convert.
     * @return a User object.
     */
    @LogInputOutput
    @ExecutionTime
    public User newUserRequestDtoToEntity(NewUserRequestDto newUserRequestDto) {
        User user = modelMapper.map(newUserRequestDto, User.class);
        user.setBirthDate(LocalDate.parse(newUserRequestDto.getBirthDate()));
        return user;
    }

    /**
     * Convert a User object to a UserResponseDto.
     *
     * @param user The User to convert.
     * @return a UserResponseDto object.
     */
    @LogInputOutput
    @ExecutionTime
    public UserResponseDto userToUserResponseDto(User user) {
        return modelMapper.map(user, UserResponseDto.class);
    }
}
