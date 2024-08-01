package huce.edu.vn.appdocsach.annotations.valid;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import huce.edu.vn.appdocsach.constants.ValidConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@NotBlank(message = "PASSWORD_MISSING")
@Pattern(regexp = ValidConstants.REGEX_CHECK_PASSWORD, message = "PASSWORD_INVALID")
public @interface ValidPassword {
    
}
