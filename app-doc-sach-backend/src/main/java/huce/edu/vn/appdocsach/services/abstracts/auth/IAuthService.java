package huce.edu.vn.appdocsach.services.abstracts.auth;

import org.springframework.web.multipart.MultipartFile;

import huce.edu.vn.appdocsach.dto.auth.AuthDto;
import huce.edu.vn.appdocsach.dto.auth.ChangePasswordDto;
import huce.edu.vn.appdocsach.dto.auth.SigninDto;
import huce.edu.vn.appdocsach.dto.auth.SignupDto;
import huce.edu.vn.appdocsach.dto.auth.UpdateProfileDto;
import huce.edu.vn.appdocsach.dto.auth.UserInfoDto;
import huce.edu.vn.appdocsach.entities.User;

public interface IAuthService {
    
    /**
     * Đọc thông tin người dùng
     * @param user
     * @return {@link UserInfoDto}
     */
    UserInfoDto getUserInfo(User user);

    /**
     * Đăng nhập với thông tin đã cung cấp
     * @param signinDto
     * @return {@link AuthDto}
     */
    AuthDto signIn(SigninDto signinDto);
    
    /**
     * Đăng kí tài khoản mới
     * @param signupDto {@link SignupDto}
     * @param avatar {@link MultipartFile}
     * @return {@link AuthDto}
     */
    AuthDto signUp(SignupDto signupDto, MultipartFile avatar);

    /**
     * Làm mới refresh token
     * @param currentToken refresh token hiện tại
     * @return {@link AuthDto}
     */
    AuthDto renewRefreshToken(String currentToken);
    
    /**
     * Đăng xuất người dùng
     * @param user {@link User} 
     * @param currentToken 
     */
    void signOut(User user, String currentToken);
    
    /**
     * Đổi mật khẩu
     * @param user {@link User} 
     * @param changePasswordDto {@link ChangePasswordDto}
     */
    void changePassword(User user, ChangePasswordDto changePasswordDto);

    /**
     * Cập nhật thông tin người dùng
     * @param user {@link User} 
     * @param updateProfileDto {@link UpdateProfileDto}
     * @param avatar {@link MultipartFile}
     */
    void updateProfile(User user, UpdateProfileDto updateProfileDto, MultipartFile avatar);

    /**
     * Khóa tài khoản tạm thời
     * @param user {@link User}
     */
    void disableAccount(User user);

    /**
     * Xóa vĩnh viễn người dùng
     * @param user
     */
    void remove(User user);
}
