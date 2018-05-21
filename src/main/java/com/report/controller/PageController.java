package com.report.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
