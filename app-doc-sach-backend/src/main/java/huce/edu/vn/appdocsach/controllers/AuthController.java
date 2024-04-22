package huce.edu.vn.appdocsach.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import huce.edu.vn.appdocsach.annotations.auth.IsAuthenticated;
import huce.edu.vn.appdocsach.dto.auth.AuthDto;
import huce.edu.vn.appdocsach.dto.auth.SigninDto;
import huce.edu.vn.appdocsach.dto.auth.SignupDto;
import huce.edu.vn.appdocsach.services.auth.AuthUserService;
import huce.edu.vn.appdocsach.services.auth.users.AuthUser;
import huce.edu.vn.appdocsach.utils.Mapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    
    @Autowired
    private AuthUserService authService;

    @Autowired
    private Mapper mapper; 

    @PostMapping(path = "signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public AuthDto signUp(
            @RequestPart String jsonModel,
            @RequestPart MultipartFile avatar) {
        SignupDto signupDto = mapper.getInstance(jsonModel,  SignupDto.class);
        return authService.signUp(signupDto, avatar);
    }

    @PostMapping("signup/default")
    public AuthDto signUpWithDefaultAvatar(
            @RequestBody @Valid SignupDto signupDto) {
        return authService.signUp(signupDto, null);
    }

    @PostMapping("signin")
    public AuthDto signIn(@RequestBody @Valid SigninDto signinDto) {
        return authService.signIn(signinDto);
    }

    @IsAuthenticated
    @PostMapping("signout")
    public void signOut(@AuthenticationPrincipal AuthUser authUser, HttpServletRequest request) {
        authService.signOut(authUser, request);
    }
}
