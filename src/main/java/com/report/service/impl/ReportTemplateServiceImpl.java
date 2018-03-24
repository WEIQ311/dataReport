package com.report.service.impl;

import com.report.domain.ReportTemplate;
import com.report.enums.GlobalEnum;
import com.report.repository.ReportTemplateRepository;
import com.report.service.ReportTemplateService;
import com.report.util.ResultUtil;
import com.report.vo.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author weiQiang
 */
@Service(value = "reportTemplateService")
public class ReportTemplateServiceImpl implements ReportTemplateService {

    @Autowired
    private ReportTemplateRepository reportTemplateRepository;

    /**
     * 通过ReportTemplate信息查询
     *
     * @param reportTemplate
     * @param pageable
     * @return
     */
    @Override
    public ResultEntity findByReportTemplate(ReportTemplate reportTemplate, Pageable pageable) {
        String templateName = reportTemplate.getTemplateName();
        if (!StringUtils.isEmpty(templateName)) {
            templateName = "%".concat(templateName).concat("%");
        } else {
            templateName = "%%";
        }
        Page<ReportTemplate> reportTemplates = reportTemplateRepository.findByTemplateNameLikeOrderByUpdateTimeDesc(templateName, pageable);
        return ResultUtil.success(GlobalEnum.QUERY_SUCCESS, reportTemplates);
    }
}
