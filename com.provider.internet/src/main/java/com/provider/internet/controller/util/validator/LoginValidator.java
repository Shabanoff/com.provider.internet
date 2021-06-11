package com.provider.internet.controller.util.validator;

public class LoginValidator extends RegexValidator {
    private final static String INVALID_LOGIN_KEY = "invalid.login";

    private final static int MAX_LENGTH = 50;

    /**
     * Regex used to perform validation of data.
     */
    private static final String EMAIL_REGEX = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";

    public LoginValidator() {
        super(EMAIL_REGEX, MAX_LENGTH, INVALID_LOGIN_KEY);
    }
}
