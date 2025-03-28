package org.aioceaneye.userservicewithmailverification.dto.input;

public record RegisterUserForm(
        String username,
        String userEmail,
        String password,
        String passwordCheck,
        Integer userGrade
) {
}
