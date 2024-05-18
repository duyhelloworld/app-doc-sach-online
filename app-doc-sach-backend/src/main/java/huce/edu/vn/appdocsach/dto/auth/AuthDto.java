package huce.edu.vn.appdocsach.dto.auth;

import lombok.Data;

@Data
public class AuthDto {
    private String jwt;
    private String fullname;
}