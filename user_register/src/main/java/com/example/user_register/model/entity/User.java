package com.example.user_register.model.entity;

import com.example.user_register.model.enums.Language;
import com.example.user_register.model.enums.Region;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @Setter(AccessLevel.PROTECTED)
    private String id;

    @NotNull
    @Size(min = 2, max = 30)
    private String firstname;

    @NotNull
    @Size(min = 3, max = 30)
    private String lastname;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Min(1900)
    private Integer birthYear;

    @NotNull
    @Min(1)
    @Max(12)
    private Integer birthMonth;

    @NotNull
    @Min(1)
    @Max(31)
    private Integer birthDay;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Language preferredLanguage;

    @Enumerated(EnumType.STRING)
    private Region region;
}
