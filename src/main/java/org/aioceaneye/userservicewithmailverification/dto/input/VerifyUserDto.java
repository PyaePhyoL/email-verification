package org.aioceaneye.userservicewithmailverification.dto.input;

public record VerifyUserDto(
        String email,
        String code
) {
}
