package com.example.user_register.controller;

import com.example.user_register.util.Utility;
import com.example.user_register.exception.user.UserNotFoundException;
import com.example.user_register.model.dto.request.NewUserRequestDto;
import com.example.user_register.model.dto.response.UserResponseDto;
import com.example.user_register.service.UserService;
import com.example.user_register.util.NewUserRequestDtoFactory;
import com.mongodb.MongoWriteException;
import com.mongodb.ServerAddress;
import com.mongodb.WriteError;
import org.bson.BsonDocument;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.format.DateTimeParseException;

import static com.example.user_register.exception.ErrorMessage.*;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private NewUserRequestDtoFactory newUserRequestDtoFactory;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenCreateUser_givenValidNewRequestUserDto_return201() throws Exception {
        NewUserRequestDto newUserRequestDto = newUserRequestDtoFactory.getNewUserRequestDto(NewUserRequestDtoFactory.VALID);

        given(userService.registerUser(any(NewUserRequestDto.class))).willReturn(new UserResponseDto());

        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                .content(Utility.asJsonString(newUserRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

    }

    @Test
    public void whenCreateUser_givenUnderAgeNewRequestUserDto_return400() throws Exception {
        NewUserRequestDto newUserRequestDto = newUserRequestDtoFactory.getNewUserRequestDto(NewUserRequestDtoFactory.UNDER_AGE);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                .content(Utility.asJsonString(newUserRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.".concat(BIRTH_DATE_FIELD))
                        .value(INVALID_USER_AGE));
    }

    @Test
    public void whenCreateUser_givenOverAgeNewRequestUserDto_return400() throws Exception {
        NewUserRequestDto newUserRequestDto = newUserRequestDtoFactory.getNewUserRequestDto(NewUserRequestDtoFactory.OVER_AGE);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                .content(Utility.asJsonString(newUserRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.".concat(BIRTH_DATE_FIELD))
                        .value(INVALID_USER_AGE));
    }

    @Test
    public void whenCreateUser_givenBadFormatBirthDateUserDto_return400() throws Exception {
        NewUserRequestDto newUserRequestDto = newUserRequestDtoFactory.getNewUserRequestDto(NewUserRequestDtoFactory.VALID);

        given(userService.registerUser(any(NewUserRequestDto.class))).willThrow(new DateTimeParseException("", "", 0));

        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                .content(Utility.asJsonString(newUserRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.".concat(BIRTH_DATE_FIELD))
                        .value(INVALID_DATE_FORMAT));
    }

    @Test
    public void whenCreateUser_givenNullFirstnameUserDto_return400() throws Exception {
        NewUserRequestDto newUserRequestDto = newUserRequestDtoFactory.getNewUserRequestDto(NewUserRequestDtoFactory.VALID);
        newUserRequestDto.setFirstname(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                .content(Utility.asJsonString(newUserRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.".concat(FIRSTNAME_FIELD))
                        .value(MUST_NOT_BE_NUL));
    }

    @Test
    public void whenCreateUser_givenShortFirstnameUserDto_return400() throws Exception {
        NewUserRequestDto newUserRequestDto = newUserRequestDtoFactory.getNewUserRequestDto(NewUserRequestDtoFactory.VALID);
        newUserRequestDto.setFirstname("A");

        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                .content(Utility.asJsonString(newUserRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.".concat(FIRSTNAME_FIELD))
                        .value(SIZE_BETWEEN_2_AND_30));
    }

    @Test
    public void whenCreateUser_givenLongFirstnameUserDto_return400() throws Exception {
        NewUserRequestDto newUserRequestDto = newUserRequestDtoFactory.getNewUserRequestDto(NewUserRequestDtoFactory.VALID);
        newUserRequestDto.setFirstname("Abcdefghijklmnopqrstuvwxyz12345");

        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                .content(Utility.asJsonString(newUserRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.".concat(FIRSTNAME_FIELD))
                        .value(SIZE_BETWEEN_2_AND_30));
    }

    @Test
    public void whenCreateUser_givenNullLastnameUserDto_return400() throws Exception {
        NewUserRequestDto newUserRequestDto = newUserRequestDtoFactory.getNewUserRequestDto(NewUserRequestDtoFactory.VALID);
        newUserRequestDto.setLastname(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                .content(Utility.asJsonString(newUserRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.".concat(LASTNAME_FIELD))
                        .value(MUST_NOT_BE_NUL));
    }

    @Test
    public void whenCreateUser_givenShortLastnameUserDto_return400() throws Exception {
        NewUserRequestDto newUserRequestDto = newUserRequestDtoFactory.getNewUserRequestDto(NewUserRequestDtoFactory.VALID);
        newUserRequestDto.setLastname("A");

        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                .content(Utility.asJsonString(newUserRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.".concat(LASTNAME_FIELD))
                        .value(SIZE_BETWEEN_2_AND_30));
    }

    @Test
    public void whenCreateUser_givenLongLastnameUserDto_return400() throws Exception {
        NewUserRequestDto newUserRequestDto = newUserRequestDtoFactory.getNewUserRequestDto(NewUserRequestDtoFactory.VALID);
        newUserRequestDto.setLastname("Abcdefghijklmnopqrstuvwxyz12345");

        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                .content(Utility.asJsonString(newUserRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.".concat(LASTNAME_FIELD))
                        .value(SIZE_BETWEEN_2_AND_30));
    }

    @Test
    public void whenCreateUser_givenNullEmailUserDto_return400() throws Exception {
        NewUserRequestDto newUserRequestDto = newUserRequestDtoFactory.getNewUserRequestDto(NewUserRequestDtoFactory.VALID);
        newUserRequestDto.setEmail(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                .content(Utility.asJsonString(newUserRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.".concat(EMAIL_FIELD))
                        .value(MUST_NOT_BE_NUL));
    }

    @Test
    public void whenCreateUser_givenInvalidEmailUserDto_return400() throws Exception {
        NewUserRequestDto newUserRequestDto = newUserRequestDtoFactory.getNewUserRequestDto(NewUserRequestDtoFactory.VALID);
        newUserRequestDto.setEmail("aaaaaaaaaaa");

        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                .content(Utility.asJsonString(newUserRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.".concat(EMAIL_FIELD))
                        .value(EMAIL_BADDLY_FORMATED));
    }

    @Test
    public void whenCreateUser_givenAlreadyExistEmailUserDto_return400() throws Exception {
        NewUserRequestDto newUserRequestDto = newUserRequestDtoFactory.getNewUserRequestDto(NewUserRequestDtoFactory.VALID);

        given(userService.registerUser(any(NewUserRequestDto.class))).willThrow(
                new MongoWriteException(
                        new WriteError(0, "email dup key", new BsonDocument()), new ServerAddress()));

        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                .content(Utility.asJsonString(newUserRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.".concat(EMAIL_FIELD))
                        .value(EMAIL_ALREADY_USED));
    }

    @Test
    public void whenCreateUser_givenBadPassword_return400() throws Exception {
        NewUserRequestDto newUserRequestDto = newUserRequestDtoFactory.getNewUserRequestDto(NewUserRequestDtoFactory.SIMPLE);
        newUserRequestDto.setPassword("");

        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                .content(Utility.asJsonString(newUserRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.".concat(PASSWORD_FIELD))
                        .value(containsString(PASSWORD_HAS_ONE_UPPERCASE_CHARACTER)))
                .andExpect(jsonPath("$.".concat(PASSWORD_FIELD))
                        .value(containsString(PASSWORD_HAS_ONE_LOWERCASE_CHARACTER)))
                .andExpect(jsonPath("$.".concat(PASSWORD_FIELD))
                        .value(containsString(PASSWORD_HAS_ONE_SPECIAL_CHARACTER)))
                .andExpect(jsonPath("$.".concat(PASSWORD_FIELD))
                        .value(containsString(PASSWORD_HAS_ONE_DIGIT_CHARACTER)))
                .andExpect(jsonPath("$.".concat(PASSWORD_FIELD))
                        .value(containsString(PASSWORD_SIZE_INVALID)))
                .andExpect(jsonPath("$.".concat(PASSWORD_FIELD))
                        .value(containsString(PASSWORD_RULES_NOT_MATCHING)));
    }

    @Test
    public void whenGetUser_givenValidId_returnUser() throws Exception {
        String id = "aaa";
        given(userService.getUser(id)).willReturn(new UserResponseDto());

        mockMvc.perform(MockMvcRequestBuilders.get("/user/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetUser_givenInvalidId_return404() throws Exception {
        String id = "aaa";
        given(userService.getUser(id)).willThrow(new UserNotFoundException(USER_NOT_FOUND));

        mockMvc.perform(MockMvcRequestBuilders.get("/user/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.".concat(ERROR_FIELD)).value(USER_NOT_FOUND));
    }

}
