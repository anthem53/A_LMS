package com.anthem53LMS.domain.message;

import com.anthem53LMS.domain.notice.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("SELECT p FROM Message p ORDER BY p.id DESC")
    List<Notice> findAllDesc();

}
