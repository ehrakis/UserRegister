package com.example.user_register.model.dto.request;

import com.example.user_register.model.dto.constraint.age.ValidAge;
import com.example.user_register.model.dto.constraint.password.ValidPassword;
import com.example.user_register.model.enums.Language;
import com.example.user_register.model.enums.Region;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
public class NewUserRequestDto {
    @NotNull
    @Size(min = 2, max = 30)
    private String firstname;

    @NotNull
    @Size(min = 2, max = 30)
    private String lastname;

    @NotNull
    @Email
    private String email;

    @NotNull
    @ValidAge
    private String birthDate;

    @NotNull
    @ValidPassword
    private String password;

    @Enumerated(EnumType.STRING)
    private Language preferredLanguage = Language.FRENCH;

    @Enumerated(EnumType.STRING)
    private Region region;

    @Override
    public String toString() {
        String string = "NewUserRequestDto: "
                .concat(" firstname: ")
                .concat(firstname)
                .concat(", lastname: ")
                .concat(lastname)
                .concat(", email: ")
                .concat(email)
                .concat(", birthDate: ")
                .concat(birthDate)
                .concat(", preferredLanguage: ")
                .concat(preferredLanguage.toString());

        if(region != null) {
            string = string
                .concat(", region: ")
                .concat(region.toString());
        } else {
            string = string
                    .concat(", region: null");
        }

        return string;
    }
}
