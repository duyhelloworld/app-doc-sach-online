package huce.edu.vn.appdocsach.dto.auth;

import lombok.Data;

@Data
public class SignupResponseDto {
    
    private String fullname;

    private String avatar;
    
    private String username;
    
    private String email;

    private String accessToken;

    private String refreshToken;
}
