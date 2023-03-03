package com.anthem53LMS.domain.notice;

import com.anthem53LMS.domain.lecture.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoticeRepository  extends JpaRepository<Notice, Long> {

    @Query("SELECT p FROM Notice p ORDER BY p.id DESC")
    List<Notice> findAllDesc();
}
