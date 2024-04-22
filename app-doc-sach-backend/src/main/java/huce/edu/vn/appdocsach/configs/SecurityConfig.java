package huce.edu.vn.appdocsach.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import huce.edu.vn.appdocsach.exception.SecurityExceptionHanlder;
import huce.edu.vn.appdocsach.filter.AuthFilter;
import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig {

    private AuthFilter authFilter;

    private SecurityExceptionHanlder securityExceptionHanlder;

    private OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(c-> c.disable())
                .cors(c -> c.disable())
                .sessionManagement(ss -> 
                    ss.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(r -> 
                    r.anyRequest().permitAll())
                .exceptionHandling(t -> t.accessDeniedHandler(securityExceptionHanlder)
                    .authenticationEntryPoint(securityExceptionHanlder))
                .oauth2Login(oauth -> 
                    oauth
                        .userInfoEndpoint(oauth2 -> oauth2.userService(oAuth2UserService)))
                .formLogin(Customizer.withDefaults())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
