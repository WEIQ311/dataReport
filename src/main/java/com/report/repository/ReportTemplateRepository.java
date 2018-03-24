package com.report.repository;

import com.report.domain.ReportTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author weiQiang
 */
public interface ReportTemplateRepository extends JpaRepository<ReportTemplate, String> {

    /**
     * 通过ReportTemplate信息查询
     *
     * @param templateName
     * @param pageable
     * @return
     */
     Page<ReportTemplate> findByTemplateNameLikeOrderByUpdateTimeDesc(String templateName, Pageable pageable);
}
