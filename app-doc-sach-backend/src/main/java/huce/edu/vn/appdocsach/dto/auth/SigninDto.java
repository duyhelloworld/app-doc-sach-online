package huce.edu.vn.appdocsach.dto.auth;

import huce.edu.vn.appdocsach.annotations.valid.ValidPassword;
import huce.edu.vn.appdocsach.annotations.valid.ValidUsername;
import lombok.Data;
import lombok.ToString;

@Data
public class SigninDto {
    
    @ValidUsername
    private String username;

    @ToString.Exclude
    @ValidPassword
    private String password;
}
