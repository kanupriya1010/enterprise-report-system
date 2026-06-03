package com.kanupriya.reportsystem.service;

import com.kanupriya.reportsystem.enums.ReportStatus;
import com.kanupriya.reportsystem.model.Report;
import com.kanupriya.reportsystem.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private ReportAsyncService reportAsyncService;

    public Report createReport(Report report) {
        Report saved = reportRepository.save(report);

        reportAsyncService.processReport(saved.getId());

        return saved;
    }

    public Page<Report> getAllReports(int page, int size) {
        return reportRepository.findAll(PageRequest.of(page, size));
    }

    public Report getReportById(Long id) {
        return reportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found"));
    }

    public void deleteReport(Long id) {
        reportRepository.deleteById(id);
    }

    public Report updateStatus(Long id, ReportStatus newStatus) {
        Report report = getReportById(id);

        ReportStatus currentStatus = report.getStatus();

        if (currentStatus == ReportStatus.DRAFT && newStatus == ReportStatus.REVIEW) {
            report.setStatus(newStatus);
        }
        else if (currentStatus == ReportStatus.REVIEW && newStatus == ReportStatus.APPROVED) {
            report.setStatus(newStatus);
        }
        else {
            throw new RuntimeException("Invalid status transition");
        }

        return reportRepository.save(report);
    }

    public Page<Report> getFilteredReports(ReportStatus status, String type, int page, int size) {

        if (status != null && type != null) {
            return reportRepository.findByStatusAndType(status, type, PageRequest.of(page, size));
        }
        else if (status != null) {
            return reportRepository.findByStatus(status, PageRequest.of(page, size));
        }
        else if (type != null) {
            return reportRepository.findByType(type, PageRequest.of(page, size));
        }
        else {
            return reportRepository.findAll(PageRequest.of(page, size));
        }
    }


}