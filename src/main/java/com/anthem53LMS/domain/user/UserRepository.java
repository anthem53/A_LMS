package com.anthem53LMS.domain.user;

import com.anthem53LMS.domain.lecture.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository  extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("SELECT p FROM User p ORDER BY p.id ASC")
    List<User> findAllDesc();
}
