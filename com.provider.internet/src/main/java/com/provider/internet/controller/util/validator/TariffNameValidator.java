package com.provider.internet.controller.util.validator;

public class TariffNameValidator extends RegexValidator {
    private final static String INVALID_LOGIN_KEY = "invalid.tariff";

    private final static int MAX_LENGTH = 50;

    /**
     * Regex used to perform validation of data.
     */
    private static final String EMAIL_REGEX = "^(.+)$";

    public TariffNameValidator() {
        super(EMAIL_REGEX, MAX_LENGTH, INVALID_LOGIN_KEY);
    }
}
