package com.example.domain_models.models.device;

import com.example.domain_models.validators.ValueValidator;

public record DeviceId(String value) {
    private static final int MIN_LENGTH = 15;
    private static final int MAX_LENGTH = 50;

    public DeviceId {
        ValueValidator.assertWithinRange(getClass(), value, MIN_LENGTH, MAX_LENGTH);
    }

    public static DeviceId create(String value) {
        return new DeviceId(value);
    }

}
