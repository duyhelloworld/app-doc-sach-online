package huce.edu.vn.appdocsach.dto.auth;

import huce.edu.vn.appdocsach.constants.AppConst;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Valid
public class UpdateProfileDto {

    private String fullname;
    
    @Pattern(regexp = AppConst.REGEX_CHECK_EMAIL, message = "EMAIL_INVALID")
    private String email;
}
