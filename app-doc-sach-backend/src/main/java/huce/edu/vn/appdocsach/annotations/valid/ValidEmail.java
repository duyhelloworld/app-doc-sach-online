package huce.edu.vn.appdocsach.annotations.valid;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import huce.edu.vn.appdocsach.constants.AppConst;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Retention(RetentionPolicy.RUNTIME)
@NotBlank(message = "EMAIL_MISSING")
@Pattern(regexp = AppConst.REGEX_CHECK_EMAIL, message = "EMAIL_INVALID")
public @interface ValidEmail {
    
}
