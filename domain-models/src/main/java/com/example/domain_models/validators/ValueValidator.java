package com.example.domain_models.validators;


import com.example.domain_models.exceptions.AppException;

import java.util.regex.Pattern;

public class ValueValidator {

    public static void assertNotEmpty(String value, Class<?> aClass) {
        if (value == null || value.trim().isEmpty()) {
            throw new AppException.BadRequestException(aClass.getSimpleName() + " cannot be empty");
        }
    }

    public static void assertWithinRange(Class<?> aClass, String value, int minLength, int maxLength) {
        if (value.length() < minLength || value.length() > maxLength) {
            throw new AppException.BadRequestException(aClass.getSimpleName() + " must be between " + minLength + " and " + maxLength + " characters");
        }
    }

    public static void assertValidFormat(String value, Pattern pattern, Class<?> aClass) {
        if (!pattern.matcher(value).matches()) {
            throw new AppException.BadRequestException(aClass.getSimpleName() + " has an invalid format");
        }
    }
}
