package com.report.repository;

import com.report.domain.TemplateConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemplateConfigRepository extends JpaRepository<TemplateConfig, String> {
}
