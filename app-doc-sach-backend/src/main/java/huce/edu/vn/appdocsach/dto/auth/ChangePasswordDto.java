package huce.edu.vn.appdocsach.dto.auth;

import huce.edu.vn.appdocsach.constants.AppConst;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Valid
public class ChangePasswordDto {
    
    @Pattern(regexp = AppConst.REGEX_CHECK_PASSWORD, message = "PASSWORD_INVALID")
    private String oldPassword;

    @Pattern(regexp = AppConst.REGEX_CHECK_PASSWORD, message = "PASSWORD_INVALID")
    private String newPassword;
}
