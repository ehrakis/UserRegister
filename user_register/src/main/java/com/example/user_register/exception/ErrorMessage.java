package com.example.user_register.exception;

public class ErrorMessage {
    // Fields
    public static final String BIRTH_DATE_FIELD = "birthDate";
    public static final String FIRSTNAME_FIELD = "firstname";
    public static final String LASTNAME_FIELD = "lastname";
    public static final String EMAIL_FIELD = "email";
    public static final String PASSWORD_FIELD = "password";
    public static final String ERROR_FIELD = "error";

    // Error messages
    public static final String INVALID_USER_AGE = "User must be between 18 and 150 years old.";
    public static final String INVALID_DATE_FORMAT = "The date should be in the format: yyyy-mm-dd";

    public static final String MUST_NOT_BE_NUL = "must not be null";
    public static final String SIZE_BETWEEN_2_AND_30 = "size must be between 2 and 30";

    public static final String EMAIL_ALREADY_USED = "This email is already used";
    public static final String EMAIL_BADDLY_FORMATED = "must be a well-formed email address";

    public static final String PASSWORD_HAS_ONE_UPPERCASE_CHARACTER = "Password must contain 1 or more uppercase characters.";
    public static final String PASSWORD_HAS_ONE_LOWERCASE_CHARACTER = "Password must contain 1 or more lowercase characters.";
    public static final String PASSWORD_HAS_ONE_SPECIAL_CHARACTER = "Password must contain 1 or more special characters.";
    public static final String PASSWORD_HAS_ONE_DIGIT_CHARACTER = "Password must contain 1 or more digit characters.";
    public static final String PASSWORD_SIZE_INVALID = "Password must be 8 or more characters in length.";
    public static final String PASSWORD_RULES_NOT_MATCHING = "Password matches 0 of 4 character rules, but 4 are required.";

    public static final String USER_NOT_FOUND = "User not found";
}
