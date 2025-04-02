package org.aioceaneye.userservicewithmailverification.controller;

import lombok.RequiredArgsConstructor;
import org.aioceaneye.userservicewithmailverification.dto.input.LoginForm;
import org.aioceaneye.userservicewithmailverification.dto.input.RegisterUserForm;
import org.aioceaneye.userservicewithmailverification.dto.input.UserGradeDto;
import org.aioceaneye.userservicewithmailverification.dto.input.VerifyUserDto;
import org.aioceaneye.userservicewithmailverification.dto.output.LoginResponse;
import org.aioceaneye.userservicewithmailverification.service.AuthenticationService;
import org.aioceaneye.userservicewithmailverification.service.UserGradeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserGradeService userGradeService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterUserForm form) {
        return ResponseEntity.ok(authenticationService.register(form));
    }

    @GetMapping("/verify/{email}")
    public ResponseEntity<String> getVerificationCode(@PathVariable String email) {
        authenticationService.sendVerificationEmail(email);
        return ResponseEntity.ok("Verification code has been sent to " + email);
    }

    @PutMapping("/approve/{email}")
    public ResponseEntity<String> approve(@PathVariable String email) {
        return ResponseEntity.ok(authenticationService.approveUser(email));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginForm form) {
        return ResponseEntity.ok(authenticationService.login(form));
    }



//    @GetMapping("/verification-code")
//    public ResponseEntity<Void> getVerificationCode(@RequestParam String email) {
//        authenticationService.sendVerificationEmail(email);
//        return ResponseEntity.ok().build();
//    }

//  =============  User Grades API ==================

    @GetMapping("/user-grade")
    public ResponseEntity<List<UserGradeDto>> findAllUserGrades() {
        return ResponseEntity.ok(userGradeService.findAll());
    }

    @GetMapping("/user-grade/{gradeCode}")
    public ResponseEntity<UserGradeDto> findUserGrade(@PathVariable int gradeCode) {
        return ResponseEntity.ok(userGradeService.findById(gradeCode));
    }

    @PostMapping("/user-grade")
    public ResponseEntity<UserGradeDto> saveUserGrade(@RequestBody UserGradeDto grade) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userGradeService.save(grade));
    }
}
