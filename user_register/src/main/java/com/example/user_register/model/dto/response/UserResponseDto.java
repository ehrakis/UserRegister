package com.example.user_register.model.dto.response;

import com.example.user_register.model.enums.Language;
import com.example.user_register.model.enums.Region;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDto {
    private String id;

    private String firstname;
    private String lastname;
    private String email;

    private LocalDate birthDate;

    private Language preferredLanguage = Language.FRENCH;

    private Region region;

    @Override
    public String toString() {
        String string = "UserResponseDto: "
                .concat("id: ")
                .concat(id)
                .concat(", firstname: ")
                .concat(firstname)
                .concat(", lastname: ")
                .concat(lastname)
                .concat(", email: ")
                .concat(email)
                .concat(", birthDate: ")
                .concat(birthDate.toString())
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
