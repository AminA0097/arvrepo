package com.webapp.arvand.arvandback.Annotaion;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.annotation.Annotation;

public class ValidPasswordValidator implements ConstraintValidator<ValidPassword, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return false;
        }
        boolean hasMinLength = s.length() >= 8;
        boolean hasDigit = s.matches(".*\\d.*");
        boolean hasLower = s.matches(".*[a-z].*");
        boolean hasUpper = s.matches(".*[A-Z].*");
        boolean hasSpecial = s.matches(".*[@#$%^&+=!].*");
        boolean noSpace = !s.contains(" ");
        String lower = s.toLowerCase();
        boolean containsQueryScript = lower.contains("select") ||
                lower.contains("insert") ||
                lower.contains("update") ||
                lower.contains("delete") ||
                lower.contains("drop") ||
                lower.contains("alter") ||
                lower.contains("create") ||
                lower.contains("exec") ||
                lower.contains("script");
        return hasMinLength && hasDigit && hasLower && hasUpper && hasSpecial && noSpace && !containsQueryScript;
    }

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
