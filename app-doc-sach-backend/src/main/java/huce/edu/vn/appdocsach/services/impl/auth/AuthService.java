package huce.edu.vn.appdocsach.services.impl.auth;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import huce.edu.vn.appdocsach.constants.AvatarSaver;
import huce.edu.vn.appdocsach.dto.auth.AuthDto;
import huce.edu.vn.appdocsach.dto.auth.ChangePasswordDto;
import huce.edu.vn.appdocsach.dto.auth.SigninDto;
import huce.edu.vn.appdocsach.dto.auth.SignupDto;
import huce.edu.vn.appdocsach.dto.auth.UpdateProfileDto;
import huce.edu.vn.appdocsach.dto.auth.UserInfoDto;
import huce.edu.vn.appdocsach.entities.Role;
import huce.edu.vn.appdocsach.entities.TokenProvider;
import huce.edu.vn.appdocsach.entities.User;
import huce.edu.vn.appdocsach.enums.ResponseCode;
import huce.edu.vn.appdocsach.exception.AppException;
import huce.edu.vn.appdocsach.repositories.UserRepo;
import huce.edu.vn.appdocsach.services.abstracts.auth.IAuthService;
import huce.edu.vn.appdocsach.services.abstracts.auth.IJwtService;
import huce.edu.vn.appdocsach.services.abstracts.file.ICloudinaryService;
import huce.edu.vn.appdocsach.services.impl.auth.users.AuthUser;
import huce.edu.vn.appdocsach.services.impl.auth.users.LocalUser;
import huce.edu.vn.appdocsach.services.impl.auth.users.OAuthFactory;
import huce.edu.vn.appdocsach.utils.AppLogger;
import huce.edu.vn.appdocsach.utils.ConvertUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService extends DefaultOAuth2UserService implements IAuthService, UserDetailsService {

    UserRepo userRepo;
    
    PasswordEncoder passwordEncoder;
    
    IJwtService jwtService;
    
    ICloudinaryService cloudinaryService;

    AppLogger<AuthService> logger;

    public AuthService(UserRepo userRepo, PasswordEncoder passwordEncoder, 
            IJwtService jwtService, 
            ICloudinaryService cloudinaryService) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.cloudinaryService = cloudinaryService;
        this.logger = new AppLogger<>(AuthService.class);
    }

    @Override
    public void signOut(User user, HttpServletRequest request) {
        logger.onStart(Thread.currentThread(), user.getUsername());
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    @Override
    @Transactional
    public AuthDto signIn(@Valid SigninDto signinDto) {
        logger.onStart(Thread.currentThread(), signinDto);
        User user = loadUser(signinDto.getUsername());
        if (user == null) {
            throw new AppException(ResponseCode.USERNAME_OR_PASSWORD_INCORRECT);
        }
        if (passwordEncoder.matches(signinDto.getPassword(), user.getPassword())) {
            return new AuthDto(jwtService.buildToken(user));
        }
        throw new AppException(ResponseCode.USERNAME_OR_PASSWORD_INCORRECT);
    }

    @Override
    @Transactional
    public AuthDto signUp(@Valid SignupDto signupDto, MultipartFile avatar) {
        String username = signupDto.getUsername();
        String avatarUrl;
        if (avatar == null) {
            avatarUrl = AvatarSaver.getAvatarUrl();
            logger.onStart(Thread.currentThread(), username, "Avatar = DEFAULT");
        } else {
            logger.onStart(Thread.currentThread(), username, "Avatar = ", avatar.getOriginalFilename());
            if (!cloudinaryService.isValidFileName(avatar)) {
                throw new AppException(ResponseCode.FILE_TYPE_INVALID);
            }
            avatarUrl = cloudinaryService.save(avatar);
        }
        if (userRepo.existsByUsername(username)) {
            throw new AppException(ResponseCode.USERNAME_EXISTED);
        }
        User user = new User();
        user.setUsername(username);
        user.setAvatar(avatarUrl);
        user.setEmail(signupDto.getEmail());
        user.setFullname(signupDto.getFullname());
        user.setPassword(passwordEncoder.encode(signupDto.getPassword()));
        user.setProvider(TokenProvider.LOCAL);
        user.setRole(Role.USER);
        userRepo.save(user);
        return new AuthDto(jwtService.buildToken(user));
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = loadUser(username);
        if (user == null) {
            throw new AppException(ResponseCode.UNAUTHORIZED);
        }
        return LocalUser.getInstance(user);
    }

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User loadedOAuthUser = super.loadUser(request);
        TokenProvider provider = TokenProvider
                .valueOf(request.getClientRegistration().getRegistrationId().toLowerCase());
        AuthUser authUser = OAuthFactory.create(provider, loadedOAuthUser.getAttributes());
        User user = loadUser(authUser.getUsername());
        if (user == null) {
            user = registerNewOauthUser(authUser);
        }
        authUser.setUser(user);
        return authUser;
    }

    private User loadUser(String username) {
        return userRepo.findByUsername(username).orElse(null);
    }

    private User registerNewOauthUser(AuthUser authUser) {
        try {
            User user = new User();
            if (userRepo.existsByUsername(authUser.getUsername())) {
                user.setUsername(authUser.getEmail());
            } else {
                user.setUsername(authUser.getUsername());
            }
            user.setPassword(authUser.getPassword());
            user.setEmail(authUser.getEmail());
            user.setRole(authUser.getRole());
            user.setFullname(authUser.getFullname());
            user.setProvider(authUser.getProvider());
            return userRepo.save(user);
        } catch (Exception e) {
            throw new AppException(ResponseCode.UNAUTHORIZED);
        }
    }

    @Override
    public void changePassword(User user, ChangePasswordDto changePasswordDto) {
        if (!passwordEncoder.matches(changePasswordDto.getOldPassword(), user.getPassword())) {
            throw new AppException(ResponseCode.USERNAME_OR_PASSWORD_INCORRECT);
        }
        user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
        userRepo.save(user);
    }

    @Override
    public void updateProfile(User user, UpdateProfileDto updateProfileDto, MultipartFile avatar) {
        logger.onStart(Thread.currentThread(), user.getUsername(), updateProfileDto);
        if (avatar != null && !avatar.isEmpty()) {
            logger.onStart(Thread.currentThread(), "avatar", avatar.getOriginalFilename());
            cloudinaryService.deleteOne(user.getAvatar());
            String newAvtName = cloudinaryService.save(avatar);
            user.setAvatar(newAvtName);
        }
        if (StringUtils.hasText(updateProfileDto.getEmail())) {
            user.setEmail(updateProfileDto.getEmail());
        }
        if (StringUtils.hasText(updateProfileDto.getFullname())) {
            user.setFullname(updateProfileDto.getFullname());
        }
        userRepo.save(user);
    }

    @Override
    public UserInfoDto getUserInfo(AuthUser authUser) {
        return ConvertUtils.convert(authUser);
    }
}
