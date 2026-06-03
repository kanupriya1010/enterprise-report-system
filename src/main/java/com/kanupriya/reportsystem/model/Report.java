package com.kanupriya.reportsystem.model;

import com.kanupriya.reportsystem.enums.ReportStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String type;   // FINANCE, SALES, HR

    @Enumerated(EnumType.STRING)
    private ReportStatus status; // DRAFT, REVIEW, APPROVED

    private Long createdBy;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.status = ReportStatus.DRAFT;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}