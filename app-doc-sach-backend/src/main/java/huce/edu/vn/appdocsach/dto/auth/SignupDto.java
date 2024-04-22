package huce.edu.vn.appdocsach.dto.auth;

import huce.edu.vn.appdocsach.annotations.valid.ValidEmail;
import huce.edu.vn.appdocsach.annotations.valid.ValidPassword;
import huce.edu.vn.appdocsach.annotations.valid.ValidUsername;
import lombok.Data;

@Data
public class SignupDto {

    private String fullName;
    
    @ValidUsername
    private String username;

    @ValidEmail
    private String email;

    @ValidPassword
    private String password;
}
