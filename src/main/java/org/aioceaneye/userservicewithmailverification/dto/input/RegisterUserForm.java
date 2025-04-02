package org.aioceaneye.userservicewithmailverification.dto.input;

public record RegisterUserForm(
        String username,
        String userEmail,
        long code,
        String password,
        String passwordCheck,
        Integer userGrade
) {
}
