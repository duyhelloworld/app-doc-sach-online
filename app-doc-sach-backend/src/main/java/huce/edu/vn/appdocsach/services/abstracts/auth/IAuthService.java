package huce.edu.vn.appdocsach.services.abstracts.auth;

import org.springframework.web.multipart.MultipartFile;

import huce.edu.vn.appdocsach.dto.auth.AuthDto;
import huce.edu.vn.appdocsach.dto.auth.ChangePasswordDto;
import huce.edu.vn.appdocsach.dto.auth.SigninDto;
import huce.edu.vn.appdocsach.dto.auth.SignupDto;
import huce.edu.vn.appdocsach.dto.auth.UpdateProfileDto;
import huce.edu.vn.appdocsach.dto.auth.UserInfoDto;
import huce.edu.vn.appdocsach.entities.User;
import huce.edu.vn.appdocsach.services.impl.auth.users.AuthUser;
import jakarta.servlet.http.HttpServletRequest;

public interface IAuthService {
    
    AuthDto signIn(SigninDto signinDto);
    
    AuthDto signUp(SignupDto signupDto, MultipartFile avatar);
    
    void signOut(User user, HttpServletRequest request);
    
    void changePassword(User user, ChangePasswordDto changePasswordDto);

    void updateProfile(User user, UpdateProfileDto updateProfileDto, MultipartFile avatar);

    UserInfoDto getUserInfo(AuthUser authUser);
}
