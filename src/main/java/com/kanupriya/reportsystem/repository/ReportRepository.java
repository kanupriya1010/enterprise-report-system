package com.kanupriya.reportsystem.repository;

import com.kanupriya.reportsystem.enums.ReportStatus;
import com.kanupriya.reportsystem.model.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {

    Page<Report> findByStatus(ReportStatus status, Pageable pageable);

    Page<Report> findByType(String type, Pageable pageable);

    Page<Report> findByStatusAndType(ReportStatus status, String type, Pageable pageable);
}