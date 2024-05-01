package huce.edu.vn.appdocsach.services.abstracts.auth;

import org.springframework.web.multipart.MultipartFile;

import huce.edu.vn.appdocsach.dto.auth.AuthDto;
import huce.edu.vn.appdocsach.dto.auth.SigninDto;
import huce.edu.vn.appdocsach.dto.auth.SignupDto;
import huce.edu.vn.appdocsach.services.impl.auth.users.AuthUser;
import jakarta.servlet.http.HttpServletRequest;

public interface IAuthService {
    
    AuthDto signIn(SigninDto signinDto);
    
    AuthDto signUp(SignupDto signupDto, MultipartFile avatar);
    
    void signOut(AuthUser authUser, HttpServletRequest request);
}
