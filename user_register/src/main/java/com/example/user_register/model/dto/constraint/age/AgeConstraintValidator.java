package com.example.user_register.model.dto.constraint.age;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

import static com.example.user_register.exception.ErrorMessage.INVALID_USER_AGE;

public class AgeConstraintValidator implements ConstraintValidator<ValidAge, String> {
    @Override
    public void initialize(ValidAge arg0) {
    }

    /**
     * Test if the birthDate set in parameter is older than 18 years old.
     *
     * @param birthDate The birthDate to test.
     * @return a boolean, True if the birthDate is older than 18, False otherwise.
     */
    private boolean isOlderThan18YearOld(LocalDate birthDate){
        LocalDate today = LocalDate.now();

        LocalDate majority = LocalDate
                .of(today.getYear()-18, today.getMonthValue(), today.getDayOfMonth())
                .plusDays(1L);

        return birthDate.isBefore(majority);
    }

    /**
     * Test if the birthDate set in parameter is older than 150 years old.
     *
     * @param birthDate The birthDate to test.
     * @return a boolean, True if the birthDate is older than 150, False otherwise.
     */
    private boolean isOlderThan150YearOld(LocalDate birthDate){
        LocalDate today = LocalDate.now();

        LocalDate oldAge = LocalDate
                .of(today.getYear()-150, today.getMonthValue(), today.getDayOfMonth());

        return birthDate.isBefore(oldAge);
    }

    /**
     * Custom validator for the birthDate field.
     * Age must be:
     *  - More than 18
     *  - Less than 150
     *
     * @param birthDate The birthDate to check.
     * @param context The context of the application.
     * @return a boolean true if the birthDate is valid and false otherwise.
     */
    @Override
    public boolean isValid(String birthDate, ConstraintValidatorContext context) {
        LocalDate date = LocalDate.parse(birthDate);

        if (!isOlderThan150YearOld(date) && isOlderThan18YearOld(date)) {
            return true;
        }

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(INVALID_USER_AGE)
                .addConstraintViolation();
        return false;
    }
}
