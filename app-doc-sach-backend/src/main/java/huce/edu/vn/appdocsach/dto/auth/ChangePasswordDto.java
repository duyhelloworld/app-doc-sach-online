package huce.edu.vn.appdocsach.dto.auth;

import huce.edu.vn.appdocsach.annotations.valid.ValidPassword;
import jakarta.validation.Valid;
import lombok.Data;

@Data
@Valid
public class ChangePasswordDto {
    
    @ValidPassword
    private String oldPassword;

    @ValidPassword
    private String newPassword;
}
