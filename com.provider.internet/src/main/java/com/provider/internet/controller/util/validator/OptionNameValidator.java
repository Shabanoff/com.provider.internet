package com.provider.internet.controller.util.validator;

public class OptionNameValidator extends RegexValidator {
    private final static String INVALID_LOGIN_KEY = "invalid.service.name";

    private final static int MAX_LENGTH = 50;

    /**
     * Regex used to perform validation of data.
     */
    private static final String SERVICE_NAME_REGEX = "^(.+)$";

    public OptionNameValidator() {
        super(SERVICE_NAME_REGEX, MAX_LENGTH, INVALID_LOGIN_KEY);
    }
}
