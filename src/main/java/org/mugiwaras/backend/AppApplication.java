package org.mugiwaras.backend;

import com.auth0.jwt.algorithms.Algorithm;
import org.mugiwaras.backend.auth.UserBusiness;
import org.mugiwaras.backend.auth.filter.AuthConstants;
import org.mugiwaras.backend.model.persistence.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.util.Base64;


@SpringBootApplication
@Slf4j
public class AppApplication extends SpringBootServletInitializer  implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}
	
	@Value("${spring.profiles.active}")
	private String profile;

	@Autowired
	private ProductRepository productDAO;
	@Override
	public void run(String... args) throws Exception {
		log.info("Perfil Activo: {}",profile);

//		productDAO.findAll();
//		System.out.println("--------------------------------------------");
//		String key = "admin";
////		System.out.println(Algorithm.HMAC512(AuthConstants.SECRET.getBytes()));
//		//PasswordEncoder hola
//
//		System.out.println(hola.encode(key).getBytes());
//		System.out.println("--------------------------------------------");
	}

}
