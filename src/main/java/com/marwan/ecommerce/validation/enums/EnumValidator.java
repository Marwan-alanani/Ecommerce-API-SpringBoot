package com.marwan.ecommerce.validation.enums;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class EnumValidator implements ConstraintValidator<EnumConstraint, String>
{

    private final Set<String> allowedValues = new HashSet<>();
    private boolean ignoreCase;

    @Override
    public void initialize(EnumConstraint constraint)
    {
        ignoreCase = constraint.ignoreCase();

        Arrays.stream(constraint.enumClass().getEnumConstants())
                .map(Enum::name)
                .forEach(allowedValues::add);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context)
    {
        if (value == null)
            return true; // let @NotNull handle nulls

        return ignoreCase
                ? allowedValues.stream().anyMatch(v -> v.equalsIgnoreCase(value))
                : allowedValues.contains(value);
    }
}
