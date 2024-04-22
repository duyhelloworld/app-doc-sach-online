package huce.edu.vn.appdocsach.dto.auth;

import huce.edu.vn.appdocsach.constants.AppConst;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.ToString;

@Data
public class SigninDto {
    
    @NotBlank(message = "USERNAME_MISSING")
    @Pattern(regexp = AppConst.REGEX_CHECK_USERNAME, message = "USERNAME_INVALID")
    private String username;

    @ToString.Exclude
    @NotBlank(message = "PASSWORD_MISSING")
    @Pattern(regexp = AppConst.REGEX_CHECK_PASSWORD, message = "PASSWORD_INVALID")
    private String password;
}
