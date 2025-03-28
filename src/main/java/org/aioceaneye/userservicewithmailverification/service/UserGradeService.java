package org.aioceaneye.userservicewithmailverification.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.aioceaneye.userservicewithmailverification.dto.input.UserGradeDto;
import org.aioceaneye.userservicewithmailverification.repository.UserGradeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserGradeService {

    private final UserGradeRepository userGradeRepository;

    public UserGradeDto findById(int id) {
        return userGradeRepository.findById(id)
                .map(UserGradeDto::toDto)
                .orElseThrow(() -> new EntityNotFoundException("UserGrade not found"));
    }

    public UserGradeDto save(UserGradeDto userGradeDto) {
        return UserGradeDto.toDto(userGradeRepository.save(UserGradeDto.toEntity(userGradeDto)));
    }

    public List<UserGradeDto> findAll() {
        return userGradeRepository.findAll().stream()
                .map(UserGradeDto::toDto).collect(Collectors.toList());
    }
}
