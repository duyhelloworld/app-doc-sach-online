package huce.edu.vn.appdocsach;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cloudinary.Cloudinary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppDocSachApplication {

	@Value("${cloudinary.name}")
	private String cloudName;

	@Value("${cloudinary.apikey}")
	private String apiKey;

	@Value("${cloudinary.secret}")
	private String secretKey;

	public static void main(String[] args) {
		SpringApplication.run(AppDocSachApplication.class, args);
	}

	@Bean
	public Cloudinary init() {
		Cloudinary cloudinary = new Cloudinary();
		cloudinary.config.cloudName = cloudName;
		cloudinary.config.apiKey = apiKey;
		cloudinary.config.apiSecret = secretKey;
		cloudinary.config.secure = true;
		cloudinary.config.analytics = false;
		return cloudinary;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}