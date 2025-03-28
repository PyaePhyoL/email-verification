package org.aioceaneye.userservicewithmailverification.dto.input;

public record LoginForm(
        String userEmail,
        String password
) {
}
