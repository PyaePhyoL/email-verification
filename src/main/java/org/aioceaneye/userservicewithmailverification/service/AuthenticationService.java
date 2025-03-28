package org.aioceaneye.userservicewithmailverification.service;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aioceaneye.userservicewithmailverification.dto.input.LoginForm;
import org.aioceaneye.userservicewithmailverification.dto.input.RegisterUserForm;
import org.aioceaneye.userservicewithmailverification.dto.input.VerifyUserDto;
import org.aioceaneye.userservicewithmailverification.dto.output.LoginResponse;
import org.aioceaneye.userservicewithmailverification.exception.AccountErrorException;
import org.aioceaneye.userservicewithmailverification.exception.MailErrorException;
import org.aioceaneye.userservicewithmailverification.exception.RegistrationErrorException;
import org.aioceaneye.userservicewithmailverification.model.User;
import org.aioceaneye.userservicewithmailverification.repository.UserGradeRepository;
import org.aioceaneye.userservicewithmailverification.repository.UserRepository;
import org.aioceaneye.userservicewithmailverification.util.MailMessagesUtil;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

import static org.aioceaneye.userservicewithmailverification.model.Status.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final EmailService emailService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserGradeRepository userGradeRepository;


    public String register(RegisterUserForm form) {
        var grade = userGradeRepository.findById(form.userGrade()).orElse(null);

        var verificationCode = generateVerificationCode();
        var expirationTime = LocalDateTime.now().plusMinutes(15);

        var registerUser = User.builder()
                .username(form.username())
                .userEmail(form.userEmail())
                .password(passwordEncoder.encode(form.password()))
                .verificationCode(verificationCode)
                .verificationCodeExpiration(expirationTime)
                .status(PENDING_VERIFICATION)
                .enabled(false)
                .userGrade(grade)
                .build();

        sendVerificationEmail(registerUser);
        userRepository.save(registerUser);
        return "Verification Email has been sent to " + form.userEmail();
    }

    public void verifyEmail(VerifyUserDto dto) {
        var registerUser = userRepository.findByEmail(dto.email())
                .orElseThrow(() -> new UsernameNotFoundException("Email not found"));

        if(registerUser.getVerificationCodeExpiration().isBefore(LocalDateTime.now())) {
            throw new RegistrationErrorException("Verification Code has expired");
        }

        if(registerUser.getVerificationCode().equals(dto.code())) {
            registerUser.setStatus(VERIFIED_BUT_INACTIVE);
            registerUser.setVerificationCode(null);
            registerUser.setVerificationCodeExpiration(null);
            userRepository.save(registerUser);
        }
    }

    public String approveUser(String email) {
        var verifiedUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        verifiedUser.setStatus(ACTIVE);
        verifiedUser.setEnabled(true);
        userRepository.save(verifiedUser);
        sendAccountApprovalEmail(verifiedUser.getUserEmail());
        return email + " has been approved.";
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

    public void sendAccountApprovalEmail(String email) {
        var subject = "Account Approval";

        var htmlMessage = MailMessagesUtil.getApproveMail();

        try {
            emailService.sendVerificationEmail(email, subject, htmlMessage);
        } catch (MessagingException e) {
            throw new MailErrorException(e.getMessage());
        }
    }

    public void sendVerificationEmail(User user) {
        var subject = "Account Verification";
        var verificationCode = user.getVerificationCode();

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
            emailService.sendVerificationEmail(user.getUserEmail(), subject, htmlMessage);
        } catch (MessagingException e) {
            throw new MailErrorException(e.getMessage());
        }

    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code =  random.nextInt(900000) + 10000;
        return String.valueOf(code);
    }


}
