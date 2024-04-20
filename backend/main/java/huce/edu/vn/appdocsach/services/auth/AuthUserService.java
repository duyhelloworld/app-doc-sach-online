package huce.edu.vn.appdocsach.services.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.multipart.MultipartFile;

import huce.edu.vn.appdocsach.constant.AppConst;
import huce.edu.vn.appdocsach.dto.auth.AuthDto;
import huce.edu.vn.appdocsach.dto.auth.SigninDto;
import huce.edu.vn.appdocsach.dto.auth.SignupDto;
import huce.edu.vn.appdocsach.entities.Role;
import huce.edu.vn.appdocsach.entities.TokenProvider;
import huce.edu.vn.appdocsach.entities.User;
import huce.edu.vn.appdocsach.exception.AppError;
import huce.edu.vn.appdocsach.exception.AppException;
import huce.edu.vn.appdocsach.repositories.UserRepo;
import huce.edu.vn.appdocsach.services.auth.users.AuthUser;
import huce.edu.vn.appdocsach.services.auth.users.LocalUser;
import huce.edu.vn.appdocsach.services.auth.users.OAuthFactory;
import huce.edu.vn.appdocsach.services.file.CloudinaryService;
import huce.edu.vn.appdocsach.utils.AppLogger;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class AuthUserService extends DefaultOAuth2UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private CloudinaryService cloudinaryService;

    private AppLogger<AuthUserService> logger = new AppLogger<>(AuthUserService.class);

    public void signOut(AuthUser authUser, HttpServletRequest request) {
        logger.onStart(Thread.currentThread(), authUser);
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    @Transactional
    public AuthDto signIn(@Valid SigninDto signinDto) {
        logger.onStart(Thread.currentThread(), signinDto);
        User user = loadUser(signinDto.getUsername());
        if (user == null) {
            throw new AppException(AppError.Unauthorized);
        }
        if (passwordEncoder.matches(signinDto.getPassword(), user.getPassword())) {
            return getResponse(user);
        }
        throw new AppException(AppError.UsernameOrPasswordIncorrect);
    }

    @Transactional
    public AuthDto signUp(@Valid SignupDto signupDto, MultipartFile avatar) {
        String avatarUrl;
        if (avatar == null) {
            avatarUrl = AppConst.DEFAULT_AVATAR_URL;
            logger.onStart(Thread.currentThread(), signupDto, "Avatar = ", AppConst.DEFAULT_AVATAR_FILENAME_WITH_EXTENSION);
        } else {
            if (!cloudinaryService.isValidFileName(avatar)) {
                throw new AppException(AppError.FileNameInvalid);
            }
            logger.onStart(Thread.currentThread(), signupDto, "Avatar = ", avatar.getOriginalFilename());
            avatarUrl = cloudinaryService.save(avatar);
        }
        if (userRepo.existsByUsername(signupDto.getUsername())) {
            throw new AppException(AppError.UsernameExisted);
        }
        User user = new User();
        user.setUsername(signupDto.getUsername());
        user.setAvatar(avatarUrl);
        user.setEmail(signupDto.getEmail());
        user.setFullname(signupDto.getFullName());
        user.setPassword(passwordEncoder.encode(signupDto.getPassword()));
        user.setProvider(TokenProvider.LOCAL);
        user.setRole(Role.USER);
        userRepo.save(user);
        return getResponse(user);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.onStart(Thread.currentThread(), username);
        User user = loadUser(username);
        if (user == null) {
            throw new AppException(AppError.Unauthorized);
        }
        return LocalUser.getInstance(user);
    }

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        logger.onStart(Thread.currentThread(), request);
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
        Optional<User> users = userRepo.findByUsername(username);
        if (users.isEmpty()) {
            return null;
        }
        return users.get();
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
            throw new AppException(AppError.Unauthorized);
        }
    }

    private AuthDto getResponse(User user) {
        AuthDto authDto = new AuthDto();
        authDto.setEmail(user.getEmail());
        authDto.setFullname(user.getFullname());
        authDto.setUsername(user.getUsername());
        authDto.setJwt(jwtService.buildToken(LocalUser.getInstance(user)));
        return authDto;
    }
}
