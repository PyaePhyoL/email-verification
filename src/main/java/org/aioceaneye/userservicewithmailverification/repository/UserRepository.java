package org.aioceaneye.userservicewithmailverification.repository;

import org.aioceaneye.userservicewithmailverification.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    @Query("""
    select u from User u where u.userEmail=:userEmail
""")
    Optional<User> findByEmail(String userEmail);
}
