package huce.edu.vn.appdocsach.dto.auth;

import huce.edu.vn.appdocsach.constants.AppConst;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SignupDto {

    private String fullName;
    
    @NotBlank(message = "USERNAME_MISSING")
    @Pattern(regexp = AppConst.REGEX_CHECK_USERNAME, message = "USERNAME_INVALID")
    private String username;

    @NotBlank(message = "EMAIL_MISSING")
    @Pattern(regexp = AppConst.REGEX_CHECK_EMAIL, message = "EMAIL_INVALID")
    private String email;

    @NotBlank(message = "PASSWORD_MISSING")
    @Pattern(regexp = AppConst.REGEX_CHECK_PASSWORD, message = "PASSWORD_INVALID")
    private String password;
}
