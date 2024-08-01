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
import huce.edu.vn.appdocsach.configs.AppObjectMapper;
import huce.edu.vn.appdocsach.dto.auth.AuthDto;
import huce.edu.vn.appdocsach.dto.auth.ChangePasswordDto;
import huce.edu.vn.appdocsach.dto.auth.SigninDto;
import huce.edu.vn.appdocsach.dto.auth.SignupDto;
import huce.edu.vn.appdocsach.dto.auth.UpdateProfileDto;
import huce.edu.vn.appdocsach.dto.auth.UserInfoDto;
import huce.edu.vn.appdocsach.services.abstracts.auth.IAuthService;
import huce.edu.vn.appdocsach.services.abstracts.auth.IRefreshTokenService;
import huce.edu.vn.appdocsach.services.impl.auth.users.AuthenticatedUser;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final IAuthService authService;

    private final IRefreshTokenService refreshTokenService;

    private final AppObjectMapper mapper; 

    @Operation(summary = "Lấy thông tin user")
    @GetMapping("info")
    @IsAuthenticated
    public UserInfoDto getInfo(@AuthenticationPrincipal AuthenticatedUser authUser) {
        return authService.getUserInfo(authUser.getUser());
    }

    @Operation(summary = "Đăng kí 1 tài khoản với ảnh avatar cung cấp")
    @PostMapping(path = "signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public AuthDto signUp(
        @RequestPart("json") String json,
        @RequestPart(required = false) MultipartFile avatar) {
        return authService.signUp(mapper.toPojo(json,  SignupDto.class), avatar);
    }

    @Operation(summary = "Đăng nhập tài khoản")
    @PostMapping("signin")
    public AuthDto signIn(@RequestBody SigninDto signinDto) {
        return authService.signIn(signinDto);
    }

    @Operation(summary = "Đăng xuất")
    @IsAuthenticated
    @PostMapping("sign-out")
    public void signOut(@AuthenticationPrincipal AuthenticatedUser authUser,
        HttpServletRequest request) {
        authService.signOut(authUser.getUser(), refreshTokenService.getRefreshToken(request));
    }

    @Operation(summary = "Làm mới refresh token")
    @PutMapping("refresh-token")
    public AuthDto refreshToken(HttpServletRequest request) {
        String refreshToken = refreshTokenService.getRefreshToken(request);
        return authService.renewRefreshToken(refreshToken);
    }

    @Operation(summary = "Đổi mật khẩu")
    @IsAuthenticated
    @PutMapping("change-pass")
    public void changePass(@AuthenticationPrincipal AuthenticatedUser authUser,
        @RequestBody ChangePasswordDto changePasswordDto) {
        authService.changePassword(authUser.getUser(), changePasswordDto);
    }

    @Operation(summary = "Cập nhật thông tin")
    @IsUser
    @PutMapping(path = "profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void updateProfile(@AuthenticationPrincipal AuthenticatedUser authUser,
        @RequestPart String json, 
        @RequestPart(required = false) MultipartFile avatar) {
        authService.updateProfile(authUser.getUser(), 
            mapper.toPojo(json, UpdateProfileDto.class), 
            avatar);
    }
}
