package huce.edu.vn.appdocsach.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthDto {

    private String accessToken;

    private String refreshToken;

    private UserInfoDto userInfo;
}