package com.example.user_register.util;

import com.example.user_register.model.dto.request.NewUserRequestDto;
import com.example.user_register.model.enums.Language;
import com.example.user_register.model.enums.Region;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class NewUserRequestDtoFactory {

    public static final String SIMPLE = "Simple";
    public static final String VALID = "Valid";
    public static final String UNDER_AGE = "Under age";
    public static final String OVER_AGE = "Over age";

    /**
     * Create a NewUserRequestDto object according to the specified type.
     * The 4 types are:
     *  - SIMPLE:       only mandatory data with valid formatting.
     *  - VALID:        SIMPLE + optional fields.
     *  - UNDER_AGE:    VALID with a birthDate lower than 18 years old.
     *  - OVER_AGE:     VALID with a birthDate older than 150 years old.
     *
     * @param type One of the static string of the current class.
     *             Possible values are: SIMPLE, VALID, UNDER_AGE, OVER_AGE.
     * @return a NewUserRequestDto according to the specified type.
     */
    public NewUserRequestDto getNewUserRequestDto(String type){
        if(type == null){
            return null;
        }

        return switch (type) {
            case VALID -> this.getValid();
            case UNDER_AGE -> this.getUnderAge();
            case OVER_AGE -> this.getOverAge();
            case SIMPLE -> this.getSimple();
            default -> null;
        };

    }

    /**
     * Create a NewUserRequestDto with only the required fields:
     *  - Email
     *  - Firstname
     *  - Lastname
     *  - BirthDate
     *
     * @return The NewUserRequestDto created.
     */
    private NewUserRequestDto getSimple(){
        NewUserRequestDto newUserRequestDto = new NewUserRequestDto();
        newUserRequestDto.setEmail("jhon.doe@gmail.com");
        newUserRequestDto.setFirstname("Jhon");
        newUserRequestDto.setLastname("Doe");
        newUserRequestDto.setBirthDate("1990-10-08");
        newUserRequestDto.setPassword("Ab1!cdef");
        return newUserRequestDto;
    }

    /**
     * Get a valid user from the getSimple function and add:
     *  - PreferredLanguage
     *  - Region
     *
     * @return The NewUserRequestDto created.
     */
    private NewUserRequestDto getValid(){
        NewUserRequestDto newUserRequestDto = this.getSimple();
        newUserRequestDto.setPreferredLanguage(Language.ENGLISH);
        newUserRequestDto.setRegion(Region.PROVENCE_ALPES_COTE_D_AZUR);

        return  newUserRequestDto;
    }

    /**
     * Get a valid user and set birthDate to today.
     *
     * @return The NewUserRequestDto created.
     */
    private NewUserRequestDto getUnderAge(){
        NewUserRequestDto newUserRequestDto = this.getValid();
        newUserRequestDto.setBirthDate(LocalDate.now().toString());
        return  newUserRequestDto;
    }

    /**
     * Get a valid user and set birthDate to 200 years ago.
     *
     * @return The NewUserRequestDto created.
     */
    private NewUserRequestDto getOverAge(){
        NewUserRequestDto newUserRequestDto = this.getValid();

        LocalDate today = LocalDate.now();
        LocalDate oldAge = LocalDate
                .of(today.getYear()-200, today.getMonthValue(), today.getDayOfMonth());

        newUserRequestDto.setBirthDate(oldAge.toString());

        return  newUserRequestDto;
    }
}
