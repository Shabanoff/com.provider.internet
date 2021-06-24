package com.provider.internet.controller.util;


import com.provider.internet.controller.util.validator.Validator;

import java.util.List;

public class Util {
    /**
     * Performs validation of given field with provided validator.
     * If error occurs add error message to list of errors.
     *
     * @param <T> type of field for validation
     */
    public static <T> void validateField(Validator<T> validator,
                                         T field,
                                         List<String> errors) {
        if (!validator.isValid(field)) {
            errors.add(validator.getErrorKey());
        }
    }

}
