package huce.edu.vn.appdocsach.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cloudinary.Cloudinary;

@Configuration
public class CloudinaryConfig {

    @Value("${cloudinary.name}")
	private String cloudName;

	@Value("${cloudinary.apikey}")
	private String apiKey;

	@Value("${cloudinary.secret}")
	private String secretKey;

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
    @Bean
	Cloudinary init() {
		Cloudinary cloudinary = new Cloudinary();
		cloudinary.config.cloudName = cloudName;
		cloudinary.config.apiKey = apiKey;
		cloudinary.config.apiSecret = secretKey;
		cloudinary.config.secure = true;
		cloudinary.config.analytics = false;
		return cloudinary;
	}
}
