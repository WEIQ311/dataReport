package com.report.repository;

import com.report.domain.TemplateConfig;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 模板配置
 *
 * @author weiQiang
 */
public interface TemplateConfigRepository extends JpaRepository<TemplateConfig, String> {
}
