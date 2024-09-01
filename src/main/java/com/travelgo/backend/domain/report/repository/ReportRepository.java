package com.travelgo.backend.domain.report.repository;

import com.travelgo.backend.domain.report.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
