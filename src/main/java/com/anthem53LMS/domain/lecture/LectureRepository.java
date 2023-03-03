package com.anthem53LMS.domain.lecture;

import com.anthem53LMS.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LectureRepository  extends JpaRepository<Lecture, Long> {
    @Query("SELECT p FROM Lecture p ORDER BY p.id DESC")
    List<Lecture> findAllDesc();
}
