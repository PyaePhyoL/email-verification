package org.aioceaneye.userservicewithmailverification;

import org.aioceaneye.userservicewithmailverification.dto.input.UserGradeDto;
import org.aioceaneye.userservicewithmailverification.model.Status;
import org.aioceaneye.userservicewithmailverification.model.User;
import org.aioceaneye.userservicewithmailverification.model.UserGrade;
import org.aioceaneye.userservicewithmailverification.repository.UserGradeRepository;
import org.aioceaneye.userservicewithmailverification.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class UserServiceWithMailVerificationApplication {

	@Bean
	public CommandLineRunner runner(UserGradeRepository userGradeRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {

			var grades = List.of(
					UserGrade.builder().code(9999).grade("ROOT").build(),
					UserGrade.builder().code(9900).grade("ADMIN").build(),
					UserGrade.builder().code(9931).grade("PILOT").build()
			);

			userGradeRepository.saveAll(grades);

			var admin = User.builder()
					.userEmail("admin@mail.com")
					.username("admin")
					.password(passwordEncoder.encode("admin"))
					.userGrade(grades.get(0))
					.enabled(true)
					.status(Status.ACTIVE)
					.build();

			userRepository.save(admin);
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(UserServiceWithMailVerificationApplication.class, args);
	}

}
