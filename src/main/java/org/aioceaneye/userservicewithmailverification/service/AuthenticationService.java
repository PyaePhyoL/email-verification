package org.aioceaneye.userservicewithmailverification.service;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aioceaneye.userservicewithmailverification.dto.input.LoginForm;
import org.aioceaneye.userservicewithmailverification.dto.input.RegisterUserForm;
import org.aioceaneye.userservicewithmailverification.dto.output.LoginResponse;
import org.aioceaneye.userservicewithmailverification.exception.AccountErrorException;
import org.aioceaneye.userservicewithmailverification.exception.MailErrorException;
import org.aioceaneye.userservicewithmailverification.exception.RegistrationErrorException;
import org.aioceaneye.userservicewithmailverification.model.User;
import org.aioceaneye.userservicewithmailverification.model.UserGrade;
import org.aioceaneye.userservicewithmailverification.repository.UserGradeRepository;
import org.aioceaneye.userservicewithmailverification.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final EmailService emailService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserGradeRepository userGradeRepository;

    private Integer verificationCode;

    public String register(RegisterUserForm form) {
        log.info("Verification code is {}", verificationCode);
        log.info("Form code is {}", form.verificationCode());

        if(verificationCode != form.verificationCode()){
            throw new RegistrationErrorException("Verification code not match");
        }
        if(!form.password().equals(form.passwordCheck())){
            throw new RegistrationErrorException("Password not match");
        }

        UserGrade userGrade = userGradeRepository.findById(form.userGrade())
                .orElseThrow(() -> new EntityNotFoundException("UserGrade not found"));

        User user = User.builder()
                .userEmail(form.userEmail())
                .username(form.username())
                .password(passwordEncoder.encode(form.password()))
                .userGrade(userGrade)
                .enabled(true)
                .build();

        userRepository.save(user);

        return user.getUsername() + " created successfully";
    }

    public LoginResponse login(LoginForm form) {
        var user = userRepository.findByEmail(form.userEmail()).orElseThrow(
                () -> new AccountErrorException(form.userEmail() + "  not found")
        );

        var isPasswordMatch = passwordEncoder.matches(form.password(), user.getPassword());
        if(!isPasswordMatch){
            throw new AccountErrorException("Wrong password");
        }

        return  new LoginResponse(jwtService.generateAccessToken(user));
    }

    public void sendVerificationEmail(String userEmail) {
        var subject = "Account Verification";
        verificationCode = generateVerificationCode();

        String htmlMessage = "<html>"
                + "<body style=\"font-family: Arial, sans-serif;\">"
                + "<div style=\"background-color: #f5f5f5; padding: 20px;\">"
                + "<h2 style=\"color: #333;\">Welcome to our app!</h2>"
                + "<p style=\"font-size: 16px;\">Please enter the verification code below to continue:</p>"
                + "<div style=\"background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);\">"
                + "<h3 style=\"color: #333;\">Verification Code:</h3>"
                + "<p style=\"font-size: 18px; font-weight: bold; color: #007bff;\">" + "Verification Code " + verificationCode + "</p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";

        try {
            emailService.sendVerificationEmail(userEmail, subject, htmlMessage);
        } catch (MessagingException e) {
            throw new MailErrorException(e.getMessage());
        }

    }

    private Integer generateVerificationCode() {
        Random random = new Random();
        return random.nextInt(90000) + 10000;
    }
}
