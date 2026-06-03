package com.kanupriya.reportsystem.controller;

import com.kanupriya.reportsystem.enums.ReportStatus;
import com.kanupriya.reportsystem.model.Report;
import com.kanupriya.reportsystem.service.ReportService;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<Report> createReport(@RequestBody Report report) {
        return ResponseEntity.ok(reportService.createReport(report));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ResponseEntity<Page<Report>> getReports(
            @RequestParam(required = false) ReportStatus status,
            @RequestParam(required = false) String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(
                reportService.getFilteredReports(status, type, page, size)
        );
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<Report> getReportById(@PathVariable Long id) {
        return ResponseEntity.ok(reportService.getReportById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReport(@PathVariable Long id) {
        reportService.deleteReport(id);
        return ResponseEntity.ok("Report deleted successfully");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/status")
    public ResponseEntity<Report> updateStatus(@PathVariable Long id,
                                               @RequestParam ReportStatus status) {
        return ResponseEntity.ok(reportService.updateStatus(id, status));
    }
}