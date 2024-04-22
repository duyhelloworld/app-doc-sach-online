package huce.edu.vn.appdocsach.annotations.valid;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import huce.edu.vn.appdocsach.constants.AppConst;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Retention(RetentionPolicy.RUNTIME)
@NotBlank(message = "PASSWORD_MISSING")
@Pattern(regexp = AppConst.REGEX_CHECK_PASSWORD, message = "PASSWORD_INVALID")
public @interface ValidPassword {
    
}
