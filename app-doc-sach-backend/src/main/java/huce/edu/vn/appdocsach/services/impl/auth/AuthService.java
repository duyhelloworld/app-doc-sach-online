package huce.edu.vn.appdocsach.services.impl.auth;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import huce.edu.vn.appdocsach.dto.auth.AuthDto;
import huce.edu.vn.appdocsach.dto.auth.ChangePasswordDto;
import huce.edu.vn.appdocsach.dto.auth.SigninDto;
import huce.edu.vn.appdocsach.dto.auth.SignupDto;
import huce.edu.vn.appdocsach.dto.auth.UpdateProfileDto;
import huce.edu.vn.appdocsach.dto.auth.UserInfoDto;
import huce.edu.vn.appdocsach.entities.RefreshToken;
import huce.edu.vn.appdocsach.entities.Role;
import huce.edu.vn.appdocsach.entities.TokenProvider;
import huce.edu.vn.appdocsach.entities.User;
import huce.edu.vn.appdocsach.enums.ResponseCode;
import huce.edu.vn.appdocsach.exception.AppException;
import huce.edu.vn.appdocsach.mapper.ModelMapper;
import huce.edu.vn.appdocsach.repositories.database.RefreshTokenRepo;
import huce.edu.vn.appdocsach.repositories.database.UserRepo;
import huce.edu.vn.appdocsach.services.abstracts.auth.IAuthService;
import huce.edu.vn.appdocsach.services.abstracts.auth.IRefreshTokenService;
import huce.edu.vn.appdocsach.services.abstracts.auth.IAccessTokenService;
import huce.edu.vn.appdocsach.services.abstracts.file.ICloudinaryService;
import huce.edu.vn.appdocsach.services.impl.auth.users.SupportedOAuth2User;
import huce.edu.vn.appdocsach.services.impl.auth.users.OAuthFactory;
import huce.edu.vn.appdocsach.utils.JsonUtils;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService extends DefaultOAuth2UserService implements IAuthService {

    private final UserRepo userRepo;

    private final RefreshTokenRepo refreshTokenRepo;

    private final IRefreshTokenService refreshTokenService;

    private final PasswordEncoder passwordEncoder;

    private final IAccessTokenService accessTokenService;
    
    private final ICloudinaryService cloudinaryService;

    @Value("${application.defaultAvatar}")
    private String defaultAvatarUrl;

    @Value("${application.auth.disable-duration}")
    private Duration disableDuration;

    @Override
    @Transactional
    public AuthDto signIn(@Valid SigninDto signinDto) {
        log.info("Start signIn with input : {}", JsonUtils.json(signinDto));
        User user = userRepo.findByUsername(signinDto.getUsername()).orElseThrow(() -> {
            throw new AppException(ResponseCode.USERNAME_OR_PASSWORD_INCORRECT);
        });

        if (!passwordEncoder.matches(signinDto.getPassword(), user.getPassword())) {
            throw new AppException(ResponseCode.USERNAME_OR_PASSWORD_INCORRECT);
        }
        
        String refreshTokenValue;
        Optional<RefreshToken> rOptional = refreshTokenRepo.findByUser(user);
        if (rOptional.isPresent() && refreshTokenService.verifyExpiration(rOptional.get())) {
            refreshTokenValue = rOptional.get().getToken();
        } else {
            RefreshToken refreshToken = new RefreshToken();
            refreshTokenValue = refreshTokenService.generateRefreshToken();
            refreshToken.setToken(refreshTokenValue);
            refreshToken.setExpireAt(refreshTokenService.getNewExpiredFromNow());
            refreshToken.setUser(user);
            refreshTokenRepo.save(refreshToken);
        }
        return ModelMapper.convert(
            accessTokenService.buildAccessToken(user),
            refreshTokenValue, user);
    }
    
    @Override
    @Transactional
    public AuthDto signUp(@Valid SignupDto signupDto, MultipartFile avatar) {
        
        String username = signupDto.getUsername();
        String avatarUrl = extractAvatar(avatar);
        
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

        RefreshToken refreshToken = new RefreshToken();
        String refreshTokenValue = refreshTokenService.generateRefreshToken();
        refreshToken.setToken(refreshTokenValue);
        refreshToken.setExpireAt(refreshTokenService.getNewExpiredFromNow());
        refreshToken.setUser(user);
        refreshTokenRepo.save(refreshToken);
        
        return ModelMapper.convert(
            accessTokenService.buildAccessToken(user),
            refreshTokenValue, user);
    }
    
    @Override
    public void signOut(User user, String token) {
        log.info("Start signOut by " + user.getUsername());
        RefreshToken refreshToken = refreshTokenRepo.findByUser(user)
            .orElseThrow(() -> new AppException(ResponseCode.NEVER_SIGNIN));
        refreshToken.setExpireAt(LocalDateTime.now());
        refreshTokenRepo.save(refreshToken);
        SecurityContextHolder.getContext().setAuthentication(null);
    }
    
    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User loadedOAuthUser = super.loadUser(request);
        TokenProvider provider = TokenProvider
                .valueOf(request.getClientRegistration().getRegistrationId().toLowerCase());
        SupportedOAuth2User oAuth2User = OAuthFactory.create(provider, loadedOAuthUser.getAttributes());
        if (!userRepo.existsByUsername(oAuth2User.getUsername())) {
            // Register new account and return to home
            User user = new User();
            user.setUsername(oAuth2User.getUsername());
            user.setEmail(oAuth2User.getEmail());
            user.setRole(Role.USER);
            user.setFullname(oAuth2User.getFullname());
            user.setProvider(oAuth2User.getTokenProvider());
            userRepo.save(user);
        }
        return oAuth2User;
    }

    private String extractAvatar(MultipartFile multipartFile) {
        if (multipartFile == null) {
            return defaultAvatarUrl;
        } else {
            if (!cloudinaryService.isValidFileName(multipartFile)) {
                throw new AppException(ResponseCode.FILE_TYPE_INVALID);
            }
            return cloudinaryService.save(multipartFile);
        }
    }

    @Override
    public void changePassword(User user, ChangePasswordDto changePasswordDto) {
        log.info("Start changePassword by ", user.getUsername());
        if (!passwordEncoder.matches(changePasswordDto.getOldPassword(),
            user.getPassword())) {
            throw new AppException(ResponseCode.USERNAME_OR_PASSWORD_INCORRECT);
        }
        user.setPassword(
            passwordEncoder.encode(changePasswordDto.getNewPassword()));
        userRepo.save(user);
    }

    @Override
    public UserInfoDto getUserInfo(User user) {
        return ModelMapper.convert(user);
    }

    @Override
    public void updateProfile(User user, UpdateProfileDto updateProfileDto, MultipartFile avatar) {
        log.info("Start updateProfile by {} with input : ", 
            user.getUsername(), JsonUtils.json(updateProfileDto));
        if (avatar != null && !avatar.isEmpty()) {
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
    public AuthDto renewRefreshToken(String currentToken) {
        
        RefreshToken refreshToken = refreshTokenRepo.findByToken(currentToken)
            .orElseThrow(() -> new AppException(ResponseCode.UNAUTHORIZED));
        
        if (!refreshToken.getToken().equals(currentToken)) {
            throw new AppException(ResponseCode.AUTH_SESSION_INVALID);
        }

        if (!refreshTokenService.verifyExpiration(refreshToken)) {
            throw new AppException(ResponseCode.AUTH_SESSION_EXPIRED);
        }

        return ModelMapper.convert(
            accessTokenService.buildAccessToken(refreshToken.getUser()),
            currentToken, refreshToken.getUser());
    }

    @Override
    public void disableAccount(User user) {
        log.info("Start disableAccount by {}", user.getUsername());
        user.setIsEnabled(false);
        user.setWillEnableAt(LocalDateTime.now()
            .plus(disableDuration));
        refreshTokenRepo.delete(user.getRefreshToken());
        userRepo.save(user);
    }

    @Override
    public void remove(User user) {
        log.info("Start remove by {}", user.getUsername());
        userRepo.delete(user);
    }
}
