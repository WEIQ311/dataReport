package com.report.controller;


import com.alibaba.fastjson.JSON;
import com.report.domain.ReportTemplate;
import com.report.enums.GlobalEnum;
import com.report.service.ReportTemplateService;
import com.report.util.ResultUtil;
import com.report.vo.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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
        request.setAttribute("reportTemplate", reportTemplate);
        return TEMPLATE_INDEX;
    }

    /**
     * 添加或编辑页面
     *
     * @param request
     * @param response
     * @param reportTemplate
     * @return
     */
    @RequestMapping(value = "addOrDetail")
    public String addOrDetail(HttpServletRequest request, HttpServletResponse response, ReportTemplate reportTemplate) {
        ResultEntity resultEntity = new ResultEntity();
        request.setAttribute("status", 200);
        request.setAttribute("title", "添加模板");
        request.setAttribute("insert", true);
        if (!StringUtils.isEmpty(reportTemplate.getTemplateId())) {
            resultEntity = reportTemplateService.findByTemplateId(reportTemplate.getTemplateId());
            if (!resultEntity.isSuccess()) {
                request.setAttribute("status", 404);
                request.setAttribute("message", "未查询到相应详情!");
                request.setAttribute("title", "模板详情");
            } else {
                reportTemplate = (ReportTemplate) resultEntity.getData();
                request.setAttribute("title", reportTemplate.getTemplateName() + "模板详情");
            }
            request.setAttribute("insert", false);
        }
        request.setAttribute("template", reportTemplate);
        return TEMPLATE_ADD_OR_DETAIL;
    }

    /**
     * 增加
     *
     * @param reportTemplate
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "insert", method = RequestMethod.POST)
    public String insert(@Valid ReportTemplate reportTemplate, BindingResult bindingResult,HttpServletRequest request) {
        request.setAttribute("insert", true);
        if (bindingResult.hasErrors()) {
            request.setAttribute("message", bindingResult.getFieldError().getDefaultMessage());
            request.setAttribute("status", 200);
            request.setAttribute("template", reportTemplate);
            request.setAttribute("showDetail", false);
            request.setAttribute("title", "添加模板");
            return TEMPLATE_ADD_OR_DETAIL;
        }
        ResultEntity resultEntity =  reportTemplateService.insert(reportTemplate);
        if (resultEntity.isSuccess()){
            return "redirect:../".concat("reportTemplate");
        }else{
            request.setAttribute("message", resultEntity.getMessage());
            request.setAttribute("status", 200);
            request.setAttribute("template", reportTemplate);
            request.setAttribute("showDetail", false);
            request.setAttribute("title", "添加模板");
            return TEMPLATE_ADD_OR_DETAIL;
        }
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
    public String update(ReportTemplate reportTemplate,HttpServletRequest request) {
        if (StringUtils.isEmpty(reportTemplate.getTemplateId())) {
            request.setAttribute("message",GlobalEnum.ID_ERROR.toString());
            request.setAttribute("template", reportTemplate);
            return "redirect:".concat(TEMPLATE_ADD_OR_DETAIL).concat("?templateId=").concat(reportTemplate.getTemplateId());
        }
        ResultEntity resultEntity = reportTemplateService.update(reportTemplate);
        request.setAttribute("template", resultEntity.getData());
        request.setAttribute("message",resultEntity.getMessage());
        return "redirect:../".concat("reportTemplate");
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
