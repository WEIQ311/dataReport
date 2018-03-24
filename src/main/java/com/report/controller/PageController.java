package com.report.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.context.WebEngineContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author weiQiang
 */
@Controller
public class PageController {

    @RequestMapping(value = {"/", "", "index"})
    public String index(HttpServletRequest request, HttpServletResponse response) {
        return "index";
    }
}
