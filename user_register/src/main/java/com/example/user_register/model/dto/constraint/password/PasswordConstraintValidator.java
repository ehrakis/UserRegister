package com.example.user_register.model.dto.constraint.password;

import com.google.common.base.Joiner;
import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {
    @Override
    public void initialize(ValidPassword arg0) {
    }

    /**
     * Custom validator for the password field.
     * Password must contain:
     *  - One lowercase letter
     *  - One uppercase letter
     *  - One digit
     *  - One special character
     * And be at least 8 character long.
     *
     * @param password The password to check.
     * @param context The context of the application.
     * @return a boolean true if the password is valid and false otherwise.
     */
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {

        CharacterCharacteristicsRule characterCharacteristicsRule = new CharacterCharacteristicsRule();
        characterCharacteristicsRule.setNumberOfCharacteristics(4);
        characterCharacteristicsRule.getRules().add(new CharacterRule(EnglishCharacterData.UpperCase, 1));
        characterCharacteristicsRule.getRules().add(new CharacterRule(EnglishCharacterData.LowerCase, 1));
        characterCharacteristicsRule.getRules().add(new CharacterRule(EnglishCharacterData.Digit, 1));
        characterCharacteristicsRule.getRules().add(new CharacterRule(EnglishCharacterData.Special, 1));

        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                characterCharacteristicsRule,
                new LengthRule(8, 30)));

        RuleResult result = validator.validate(new PasswordData(password));
        if (result.isValid()) {
            return true;
        }
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(
                Joiner.on(" ").join(validator.getMessages(result)))
                .addConstraintViolation();
        return false;
    }
}
