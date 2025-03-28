package org.aioceaneye.userservicewithmailverification.dto.input;

import org.aioceaneye.userservicewithmailverification.model.UserGrade;

public record UserGradeDto(
        Integer code,
        String grade
) {

    public static UserGradeDto toDto(UserGrade userGrade) {
        return new UserGradeDto(userGrade.getCode(),userGrade.getGrade());
    }

    public static UserGrade toEntity(UserGradeDto dto) {
        return new UserGrade(dto.code, dto.grade);
    }
}
