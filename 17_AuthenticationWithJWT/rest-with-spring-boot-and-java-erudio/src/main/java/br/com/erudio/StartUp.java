package br.com.erudio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class StartUp {

	public static void main(String[] args) {
		SpringApplication.run(StartUp.class, args);

		//testPasswordEncoder();

	}

	private static void testPasswordEncoder(){
		Map<String, PasswordEncoder> enconders = new HashMap<>();
		Pbkdf2PasswordEncoder pbkdf2Encoder = new Pbkdf2PasswordEncoder("", 8, 18500, Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
		enconders.put("pbkdf2", pbkdf2Encoder);
		DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", enconders);
		passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2Encoder);

		String result1 = passwordEncoder.encode("admin123");
		String result2 = passwordEncoder.encode("admin234");
		System.out.println("My hash result1 " + result1);
		System.out.println("My hash result2 " + result2);
	}

}
