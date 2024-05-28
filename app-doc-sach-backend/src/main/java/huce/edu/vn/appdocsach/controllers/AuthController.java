package huce.edu.vn.appdocsach.controllers;

import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import huce.edu.vn.appdocsach.annotations.auth.IsAuthenticated;
import huce.edu.vn.appdocsach.annotations.auth.IsUser;
import huce.edu.vn.appdocsach.dto.auth.AuthDto;
import huce.edu.vn.appdocsach.dto.auth.ChangePasswordDto;
import huce.edu.vn.appdocsach.dto.auth.SigninDto;
import huce.edu.vn.appdocsach.dto.auth.SignupDto;
import huce.edu.vn.appdocsach.dto.auth.UpdateProfileDto;
import huce.edu.vn.appdocsach.dto.auth.UserInfoDto;
import huce.edu.vn.appdocsach.services.abstracts.auth.IAuthService;
import huce.edu.vn.appdocsach.services.impl.auth.users.AuthUser;
import huce.edu.vn.appdocsach.utils.Mapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {
    
    IAuthService authService;

    Mapper mapper; 

    @Operation(summary = "Lấy thông tin user")
    @GetMapping("info")
    @IsAuthenticated
    public UserInfoDto getInfo(@AuthenticationPrincipal AuthUser authUser) {
        return authService.getUserInfo(authUser);
    }

    @Operation(summary = "Đăng kí 1 tài khoản với ảnh avatar cung cấp")
    @PostMapping(path = "signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public AuthDto signUp(
        @Parameter(description = "{ \"fullName\":\"\", \"username\": \"\", \"password\": \"\", \"email\":\"\"}")
            @RequestPart String jsonModel,
            @RequestPart(required = false) MultipartFile avatar) {
        SignupDto signupDto = mapper.getInstance(jsonModel,  SignupDto.class);
        return authService.signUp(signupDto, avatar);
    }

    @Operation(summary = "Đăng nhập tài khoản")
    @PostMapping("signin")
    public AuthDto signIn(@RequestBody SigninDto signinDto) {
        return authService.signIn(signinDto);
    }

    @Operation(summary = "Đăng xuất")
    @IsAuthenticated
    @PostMapping("signout")
    public void signOut(@AuthenticationPrincipal AuthUser authUser, HttpServletRequest request) {
        authService.signOut(authUser.getUser(), request);
    }

    @Operation(summary = "Đổi mật khẩu")
    @IsAuthenticated
    @PutMapping("change-pass")
    public void changePass(@AuthenticationPrincipal AuthUser authUser, @RequestBody ChangePasswordDto changePasswordDto) {
        authService.changePassword(authUser.getUser(), changePasswordDto);
    }

    @Operation(summary = "Cập nhật thông tin")
    @IsUser
    @PutMapping(path = "profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void updateProfile(@AuthenticationPrincipal AuthUser authUser,
        @Parameter(description = "{ \"fullname\": \"\", \"email\": \"\" }") 
        @RequestPart String jsonDto, 
        @RequestPart(required = false) MultipartFile avatar) {
        authService.updateProfile(authUser.getUser(), mapper.getInstance(jsonDto, UpdateProfileDto.class), avatar);
    }
}
