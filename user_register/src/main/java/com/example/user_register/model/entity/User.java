package com.example.user_register.model.entity;

import com.example.user_register.model.enums.Language;
import com.example.user_register.model.enums.Region;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class User {

    @Id
    @Setter(AccessLevel.PROTECTED)
    private String id;

    @NotNull
    @Size(min = 2, max = 30)
    private String firstname;

    @NotNull
    @Size(min = 2, max = 30)
    private String lastname;

    @NotNull
    @Getter(AccessLevel.PROTECTED)
    private String password;

    @NotNull
    @Email
    @Indexed(unique=true)
    private String email;

    @NotNull
    private LocalDate birthDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Language preferredLanguage;

    @Enumerated(EnumType.STRING)
    private Region region;

    @Override
    public String toString(){
        String string = "User: "
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
