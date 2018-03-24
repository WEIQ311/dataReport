package com.report.service;

import com.report.domain.ReportTemplate;
import com.report.vo.ResultEntity;
import org.springframework.data.domain.Pageable;

/**
 * @author weiQiang
 */
public interface ReportTemplateService {

    /**
     * 通过ReportTemplate信息查询
     *
     * @param reportTemplate
     * @param pageable
     * @return
     */
    ResultEntity findByReportTemplate(ReportTemplate reportTemplate, Pageable pageable);
}
