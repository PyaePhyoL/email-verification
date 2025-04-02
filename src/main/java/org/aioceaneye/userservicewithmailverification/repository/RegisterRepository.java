package org.aioceaneye.userservicewithmailverification.repository;

import org.aioceaneye.userservicewithmailverification.model.Register;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegisterRepository extends JpaRepository<Register,Integer> {

    Optional<Register> findByEmail(String email);
}
