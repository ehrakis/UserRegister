package com.example.user_register.model.dto.response;

import com.example.user_register.model.enums.Language;
import com.example.user_register.model.enums.Region;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserResponseDto {
    private String id;

    private String firstname;
    private String lastname;
    private String email;

    private LocalDate birthDate;

    private Language preferredLanguage = Language.FRENCH;

    private Region region;
}
