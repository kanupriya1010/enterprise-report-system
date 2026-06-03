package com.kanupriya.reportsystem.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ReportAsyncService {

    @Async
    public void processReport(Long reportId) {
        try {
            System.out.println("Processing report: " + reportId);

            // simulate long task
            Thread.sleep(5000);

            System.out.println("Report processed: " + reportId);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}