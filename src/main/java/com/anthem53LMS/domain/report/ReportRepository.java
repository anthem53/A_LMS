package com.anthem53LMS.domain.report;

import com.anthem53LMS.domain.notice.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report,Long> {

    @Query("SELECT p FROM Report p ORDER BY p.id ASC")
    List<Report> findAllAsc();

}
