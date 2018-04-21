package com.report.service;

import com.report.domain.ReportTemplate;
import com.report.vo.ResultEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author weiQiang
 */
public interface ReportTemplateService {

    /**
     * 增加模板
     *
     * @param reportTemplate
     * @return
     */
    ResultEntity insert(ReportTemplate reportTemplate);

    /**
     * 批量增加模板
     *
     * @param reportTemplates
     * @return
     */
    ResultEntity insert(List<ReportTemplate> reportTemplates);

    /**
     * 删除模板
     *
     * @param templateIds
     * @return
     */
    ResultEntity delete(String... templateIds);

    /**
     * 修改模板
     *
     * @param reportTemplate
     * @return
     */
    ResultEntity update(ReportTemplate reportTemplate);

    /**
     * 批量修改模板
     *
     * @param reportTemplates
     * @return
     */
    ResultEntity update(List<ReportTemplate> reportTemplates);

    /**
     * 通过ReportTemplate信息查询
     *
     * @param reportTemplate
     * @param pageable
     * @return
     */
    ResultEntity findByReportTemplate(ReportTemplate reportTemplate, Pageable pageable);

    /**
     * 查询模板详情
     *
     * @param templateId
     * @return
     */
    ResultEntity findByTemplateId(String templateId);
}
