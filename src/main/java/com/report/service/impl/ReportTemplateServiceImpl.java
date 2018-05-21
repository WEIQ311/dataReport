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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author weiQiang
 */
@Service(value = "ReportTemplateService")
public class ReportTemplateServiceImpl implements ReportTemplateService {

    @Autowired
    private ReportTemplateRepository reportTemplateRepository;

    /**
     * 增加模板
     *
     * @param reportTemplate
     * @return
     */
    @Override
    public ResultEntity insert(ReportTemplate reportTemplate) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        reportTemplate.setCreateTime(timestamp);
        reportTemplate = reportTemplateRepository.save(reportTemplate);
        return ResultUtil.success(GlobalEnum.INSERT_SUCCESS, reportTemplate);
    }

    /**
     * 批量增加模板
     *
     * @param reportTemplates
     * @return
     */
    @Override
    public ResultEntity insert(List<ReportTemplate> reportTemplates) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        reportTemplates.forEach(reportTemplate -> reportTemplate.setCreateTime(timestamp));
        reportTemplates = reportTemplateRepository.saveAll(reportTemplates);
        return ResultUtil.success(GlobalEnum.INSERT_SUCCESS, reportTemplates);
    }

    /**
     * 删除模板
     *
     * @param templateIds
     * @return
     */
    @Override
    public ResultEntity delete(String... templateIds) {
        List<ReportTemplate> reportTemplates = new ArrayList<ReportTemplate>() {{
            for (String templateId : templateIds) {
                if (StringUtils.isEmpty(templateId)) {
                    new RuntimeException(GlobalEnum.ID_ERROR.toString());
                }
                add(ReportTemplate.builder().templateId(templateId).build());
            }
        }};
        reportTemplateRepository.deleteAll(reportTemplates);
        return ResultUtil.success(GlobalEnum.DELETE_SUCCESS, templateIds.length);
    }

    /**
     * 修改模板
     *
     * @param reportTemplate
     * @return
     */
    @Override
    public ResultEntity update(ReportTemplate reportTemplate) {
        if (StringUtils.isEmpty(reportTemplate.getTemplateId())) {
            new RuntimeException(GlobalEnum.ID_ERROR.toString());
        }
        ReportTemplate oldTemplate = null;
        Optional<ReportTemplate> template = reportTemplateRepository.findById(reportTemplate.getTemplateId());
        if (template.isPresent()) {
            oldTemplate = template.get();
        } else {
            return ResultUtil.error(GlobalEnum.QUERY_ERROR);
        }
        reportTemplate.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        reportTemplate.setCreateTime(null == oldTemplate.getCreateTime() ? new Timestamp(System.currentTimeMillis()) : oldTemplate.getCreateTime());
        reportTemplate.setUpdateCount(Integer.sum(null == oldTemplate.getUpdateCount() ? 0 : oldTemplate.getUpdateCount(), 1));
        reportTemplate = reportTemplateRepository.save(reportTemplate);
        return ResultUtil.success(GlobalEnum.UPDATE_SUCCESS, reportTemplate);
    }

    /**
     * 批量修改模板
     *
     * @param reportTemplates
     * @return
     */
    @Override
    public ResultEntity update(List<ReportTemplate> reportTemplates) {
        reportTemplates.forEach(reportTemplate -> {
            if (StringUtils.isEmpty(reportTemplate.getTemplateId())) {
                new RuntimeException(GlobalEnum.ID_ERROR.toString());
            }
            reportTemplate.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            reportTemplate.setUpdateCount(Integer.sum(reportTemplate.getUpdateCount(), 1));
        });
        reportTemplates = reportTemplateRepository.saveAll(reportTemplates);
        return ResultUtil.success(GlobalEnum.UPDATE_SUCCESS, reportTemplates);
    }

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

    /**
     * 查询模板详情
     *
     * @param templateId
     * @return
     */
    @Override
    public ResultEntity findByTemplateId(String templateId) {
        ResultEntity resultEntity = new ResultEntity();
        Optional<ReportTemplate> optionalReportTemplate = reportTemplateRepository.findById(templateId);
        if (optionalReportTemplate.isPresent()) {
            resultEntity.setSuccess(true);
            resultEntity.setData(optionalReportTemplate.get());
        }
        return resultEntity;
    }
}
