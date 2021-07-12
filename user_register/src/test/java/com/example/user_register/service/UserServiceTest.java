package com.example.user_register.service;

import com.example.user_register.model.dto.request.NewUserRequestDto;
import com.example.user_register.model.dto.response.UserResponseDto;
import com.example.user_register.model.entity.User;
import com.example.user_register.repository.UserRepository;
import com.example.user_register.Util.NewUserRequestDtoFactory;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private NewUserRequestDtoFactory newUserRequestDtoFactory;

    @Test
    public void whenRegisterUser_givenValidNewUserRequestDto_returnValidUserResponseDto(){
        // Return the same user object as provided in parameter.
        given(userRepository.save(any(User.class))).willAnswer((Answer<User>) invocation -> {
            Object[] arguments = invocation.getArguments();
            return (User) arguments[0];
        });

        NewUserRequestDto newUserRequestDto = newUserRequestDtoFactory.getNewUserRequestDto(NewUserRequestDtoFactory.VALID);

        UserResponseDto userResponseDto = userService.registerUser(newUserRequestDto);

        assertThat(userResponseDto).isNotNull();
        assertThat(userResponseDto.getEmail()).isEqualTo(newUserRequestDto.getEmail());
        assertThat(userResponseDto.getFirstname()).isEqualTo(newUserRequestDto.getFirstname());
        assertThat(userResponseDto.getLastname()).isEqualTo(newUserRequestDto.getLastname());
        assertThat(userResponseDto.getBirthDate()).isEqualTo(newUserRequestDto.getBirthDate());
        assertThat(userResponseDto.getPreferredLanguage()).isEqualTo(newUserRequestDto.getPreferredLanguage());
        assertThat(userResponseDto.getRegion()).isEqualTo(newUserRequestDto.getRegion());
    }

}
