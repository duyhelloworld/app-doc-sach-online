package huce.edu.vn.appdocsach.annotations.valid;

import huce.edu.vn.appdocsach.constants.ValidConstants;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidEmailValidator implements ConstraintValidator<ValidEmail, String>{
    
    private boolean nullable;

    @Override
    public void initialize(ValidEmail constraintAnnotation) {
        this.nullable = constraintAnnotation.nullable();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (nullable && value == null) {
            return true;
        }

        if (value == null || value.trim().isBlank()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("EMAIL_MISSING");
            return false;
        }
        
        if (!value.matches(ValidConstants.REGEX_CHECK_EMAIL)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("EMAIL_INVALID");
            return false;
        }
        
        return true;
    }
}
