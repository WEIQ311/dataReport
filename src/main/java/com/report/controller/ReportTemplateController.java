package com.report.controller;


import com.report.domain.ReportTemplate;
import com.report.enums.GlobalEnum;
import com.report.service.ReportTemplateService;
import com.report.util.ResultUtil;
import com.report.vo.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 报告模板Controller
 *
 * @author weiQiang
 */
@Controller
@RequestMapping(value = "reportTemplate")
public class ReportTemplateController extends BaseController {

    @Autowired
    private ReportTemplateService reportTemplateService;

    /**
     * 首页
     *
     * @param request
     * @param response
     * @param reportTemplate
     * @return
     */
    @RequestMapping(value = {"", "/", "/index", "/list"}, method = RequestMethod.GET)
    public String index(HttpServletRequest request, HttpServletResponse response, ReportTemplate reportTemplate) {
        ResultEntity resultEntity = reportTemplateService.findByReportTemplate(reportTemplate, Pageable.unpaged());
        request.setAttribute("result", resultEntity);
        return REPORT_INDEX;
    }

    /**
     * 增加
     *
     * @param reportTemplate
     * @return
     */
    @RequestMapping(value = "insert", method = RequestMethod.POST)
    @ResponseBody
    public ResultEntity insert(ReportTemplate reportTemplate) {
        return reportTemplateService.insert(reportTemplate);
    }

    /**
     * 批量增加
     *
     * @param reportTemplates
     * @return
     */
    @RequestMapping(value = "insertBatch", method = RequestMethod.POST)
    @ResponseBody
    public ResultEntity insert(@RequestBody List<ReportTemplate> reportTemplates) {
        return reportTemplateService.insert(reportTemplates);
    }

    /**
     * 批量删除
     *
     * @param templateIds
     * @return
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultEntity delete(String... templateIds) {
        return reportTemplateService.delete(templateIds);
    }

    /**
     * 修改
     *
     * @param reportTemplate
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public ResultEntity update(ReportTemplate reportTemplate) {
        if (StringUtils.isEmpty(reportTemplate.getTemplateId())) {
            return ResultUtil.error(GlobalEnum.ID_ERROR);
        }
        return reportTemplateService.update(reportTemplate);
    }

    /**
     * 批量修改
     *
     * @param reportTemplates
     * @return
     */
    @RequestMapping(value = "updateBatch", method = RequestMethod.POST)
    @ResponseBody
    public ResultEntity update(@RequestBody List<ReportTemplate> reportTemplates) {
        reportTemplates.forEach(reportTemplate -> {
            if (StringUtils.isEmpty(reportTemplate.getTemplateId())) {
                new RuntimeException(GlobalEnum.ID_ERROR.toString());
            }
        });
        return reportTemplateService.update(reportTemplates);
    }
}
