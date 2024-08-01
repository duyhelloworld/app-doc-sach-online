package huce.edu.vn.appdocsach.dto.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@JsonInclude(value = Include.NON_EMPTY)
public class UserInfoDto {

    private String fullname;

    private String avatar;
    
    private String username;
    
    private String email;

}
