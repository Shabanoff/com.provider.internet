package com.provider.internet.controller.util.validator;

public class OptionNameValidator extends RegexValidator {
    private final static String INVALID_OPTION = "invalid.option";

    private final static int MAX_LENGTH = 150;

    /**
     * Regex used to perform validation of data.
     */
    private static final String OPTION_REGEX = "^[ \\w \\d \\s \\. \\& \\+ \\- \\, \\! \\@ \\# \\$ \\% \\^ \\* \\( \\) \\; \\\\ \\/ \\| \\< \\> \\\" \\' \\? \\= \\: \\[ \\] ]*$";

    public OptionNameValidator() {
        super(OPTION_REGEX, MAX_LENGTH, INVALID_OPTION);
    }
}
