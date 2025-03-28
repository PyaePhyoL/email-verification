package org.aioceaneye.userservicewithmailverification;

import org.aioceaneye.userservicewithmailverification.dto.input.UserGradeDto;
import org.aioceaneye.userservicewithmailverification.model.User;
import org.aioceaneye.userservicewithmailverification.model.UserGrade;
import org.aioceaneye.userservicewithmailverification.repository.UserGradeRepository;
import org.aioceaneye.userservicewithmailverification.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UserServiceWithMailVerificationApplication {

	@Bean
	public CommandLineRunner runner(UserGradeRepository userGradeRepository, UserRepository userRepository) {
		return args -> {

			UserGrade grade = userGradeRepository.findById(9999).get();

			User rootAcc = User.builder()
					.username("Root")
					.password("admin")
					.userEmail("aioceaneye@gmail.com")
					.enabled(true)
					.userGrade(grade)
					.build();

			userRepository.save(rootAcc);
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(UserServiceWithMailVerificationApplication.class, args);
	}

}
