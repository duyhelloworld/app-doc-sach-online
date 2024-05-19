package huce.edu.vn.appdocsach.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfoDto {
    private String fullname;
    private String avatar;
    private String username;
}
