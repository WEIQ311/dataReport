package com.report.controller;

import com.report.domain.TemplateConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 模板配置中心
 *
 * @author weiQiang
 */
@Controller
@RequestMapping(value = "templateConfig")
public class TemplateConfigController extends BaseController {
    /**
     * 首页
     *
     * @param request
     * @param response
     * @param templateConfig
     * @return
     */
    @RequestMapping(value = {"", "/", "/index", "/list"}, method = RequestMethod.GET)
    public String index(HttpServletRequest request, HttpServletResponse response, TemplateConfig templateConfig) {
        return TEMPLATE_INDEX;
    }
}
