package huce.edu.vn.appdocsach.dto.auth;

import huce.edu.vn.appdocsach.exception.ValidationError;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SignupDto {

    private String fullName;
    
    @NotBlank(message = ValidationError.USERNAME_MISSING)
    @Pattern(regexp = ValidationError.REGEX_CHECK_USERNAME, message = ValidationError.USERNAME_INVALID)
    private String username;

    @NotBlank(message = ValidationError.EMAIL_MISSING)
    @Pattern(regexp = ValidationError.REGEX_CHECK_EMAIL, message = ValidationError.EMAIL_INVALID)
    private String email;

    @NotBlank(message = ValidationError.PASSWORD_MISSING)
    @Pattern(regexp = ValidationError.REGEX_CHECK_PASSWORD, message = ValidationError.PASSWORD_INVALID)
    private String password;
}
