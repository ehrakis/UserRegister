package com.example.user_register.controller;

import com.example.user_register.Util.Utility;
import com.example.user_register.exception.UserNotFoundException;
import com.example.user_register.model.dto.request.NewUserRequestDto;
import com.example.user_register.model.dto.response.UserResponseDto;
import com.example.user_register.service.UserService;
import com.example.user_register.Util.NewUserRequestDtoFactory;
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
                .andExpect(jsonPath("$.birthDate").value("User must be between 18 and 150 years old."));
    }

    @Test
    public void whenCreateUser_givenOverAgeNewRequestUserDto_return400() throws Exception {
        NewUserRequestDto newUserRequestDto = newUserRequestDtoFactory.getNewUserRequestDto(NewUserRequestDtoFactory.OVER_AGE);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                .content(Utility.asJsonString(newUserRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.birthDate").value("User must be between 18 and 150 years old."));
    }

    @Test
    public void whenCreateUser_givenBadFormatBirthDateUserDto_return400() throws Exception {
        NewUserRequestDto newUserRequestDto = newUserRequestDtoFactory.getNewUserRequestDto(NewUserRequestDtoFactory.VALID);

        given(userService.registerUser(any(NewUserRequestDto.class))).willThrow(new DateTimeParseException("", "", 0));

        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                .content(Utility.asJsonString(newUserRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.birthDate").value("The date should be in the format: yyyy-mm-dd"));
    }

    @Test
    public void whenCreateUser_givenNullFirstnameUserDto_return400() throws Exception {
        NewUserRequestDto newUserRequestDto = newUserRequestDtoFactory.getNewUserRequestDto(NewUserRequestDtoFactory.VALID);
        newUserRequestDto.setFirstname(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                .content(Utility.asJsonString(newUserRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.firstname").value("must not be null"));
    }

    @Test
    public void whenCreateUser_givenShortFirstnameUserDto_return400() throws Exception {
        NewUserRequestDto newUserRequestDto = newUserRequestDtoFactory.getNewUserRequestDto(NewUserRequestDtoFactory.VALID);
        newUserRequestDto.setFirstname("A");

        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                .content(Utility.asJsonString(newUserRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.firstname").value("size must be between 2 and 30"));
    }

    @Test
    public void whenCreateUser_givenLongFirstnameUserDto_return400() throws Exception {
        NewUserRequestDto newUserRequestDto = newUserRequestDtoFactory.getNewUserRequestDto(NewUserRequestDtoFactory.VALID);
        newUserRequestDto.setFirstname("Abcdefghijklmnopqrstuvwxyz12345");

        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                .content(Utility.asJsonString(newUserRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.firstname").value("size must be between 2 and 30"));
    }

    @Test
    public void whenCreateUser_givenNullLastnameUserDto_return400() throws Exception {
        NewUserRequestDto newUserRequestDto = newUserRequestDtoFactory.getNewUserRequestDto(NewUserRequestDtoFactory.VALID);
        newUserRequestDto.setLastname(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                .content(Utility.asJsonString(newUserRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.lastname").value("must not be null"));
    }

    @Test
    public void whenCreateUser_givenShortLastnameUserDto_return400() throws Exception {
        NewUserRequestDto newUserRequestDto = newUserRequestDtoFactory.getNewUserRequestDto(NewUserRequestDtoFactory.VALID);
        newUserRequestDto.setLastname("A");

        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                .content(Utility.asJsonString(newUserRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.lastname").value("size must be between 2 and 30"));
    }

    @Test
    public void whenCreateUser_givenLongLastnameUserDto_return400() throws Exception {
        NewUserRequestDto newUserRequestDto = newUserRequestDtoFactory.getNewUserRequestDto(NewUserRequestDtoFactory.VALID);
        newUserRequestDto.setLastname("Abcdefghijklmnopqrstuvwxyz12345");

        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                .content(Utility.asJsonString(newUserRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.lastname").value("size must be between 2 and 30"));
    }

    @Test
    public void whenCreateUser_givenNullEmailUserDto_return400() throws Exception {
        NewUserRequestDto newUserRequestDto = newUserRequestDtoFactory.getNewUserRequestDto(NewUserRequestDtoFactory.VALID);
        newUserRequestDto.setEmail(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                .content(Utility.asJsonString(newUserRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email").value("must not be null"));
    }

    @Test
    public void whenCreateUser_givenInvalidEmailUserDto_return400() throws Exception {
        NewUserRequestDto newUserRequestDto = newUserRequestDtoFactory.getNewUserRequestDto(NewUserRequestDtoFactory.VALID);
        newUserRequestDto.setEmail("aaaaaaaaaaa");

        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                .content(Utility.asJsonString(newUserRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email").value("must be a well-formed email address"));
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
                .andExpect(jsonPath("$.email").value("This email is already used"));
    }

    @Test
    public void whenCreateUser_givenBadPassword_return400() throws Exception {
        NewUserRequestDto newUserRequestDto = newUserRequestDtoFactory.getNewUserRequestDto(NewUserRequestDtoFactory.SIMPLE);
        newUserRequestDto.setPassword("");

        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                .content(Utility.asJsonString(newUserRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.password").value(containsString("Password must contain 1 or more uppercase characters.")))
                .andExpect(jsonPath("$.password").value(containsString("Password must contain 1 or more lowercase characters.")))
                .andExpect(jsonPath("$.password").value(containsString("Password must contain 1 or more special characters.")))
                .andExpect(jsonPath("$.password").value(containsString("Password must contain 1 or more digit characters.")))
                .andExpect(jsonPath("$.password").value(containsString("Password must be 8 or more characters in length.")))
                .andExpect(jsonPath("$.password").value(containsString("Password matches 0 of 4 character rules, but 4 are required.")));
    }

    @Test
    public void whenGetUser_givenValidId_returnUser() throws Exception {
        String id = "aaa";
        given(userService.getUser(id)).willReturn(new UserResponseDto());

        mockMvc.perform(MockMvcRequestBuilders.get("/user/register")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetUser_givenInvalidId_return404() throws Exception {
        String id = "aaa";
        given(userService.getUser(id)).willThrow(new UserNotFoundException("User not Found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/user/register")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("User not found"));
    }

}
