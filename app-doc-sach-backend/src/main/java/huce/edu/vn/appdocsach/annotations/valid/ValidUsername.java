package huce.edu.vn.appdocsach.annotations.valid;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import huce.edu.vn.appdocsach.constants.ValidConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


@Retention(RetentionPolicy.RUNTIME)
@NotBlank(message = "USERNAME_MISSING")
@Pattern(regexp = ValidConstants.REGEX_CHECK_USERNAME, 
    message = "USERNAME_INVALID")
public @interface ValidUsername {
    
}
