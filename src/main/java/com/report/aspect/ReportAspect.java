package com.report.aspect;


import com.report.util.GloabalUtils;
import com.report.vo.ResultEntity;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Map;

/**
 * AOP处理
 *
 * @author weiQiang
 */
@Aspect
@Component
public class ReportAspect {

    private final static Logger logger = LoggerFactory.getLogger(ReportAspect.class);

    /**
     * 开始时间,用于记录请求耗时
     */
    private static long BEGIN_TIME = System.currentTimeMillis();

    @Pointcut(value = "execution(* com.report.controller.*.*(..))")
    public void doController() {
    }

    /**
     * 请求之前拦截
     *
     * @param joinPoint
     */
    @Before("doController()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        BEGIN_TIME = System.currentTimeMillis();
        logger.info("url:{},方法:{},请求ip:{},类和方法:{}(),参数:{}", request.getRequestURL(), request.getMethod(), GloabalUtils.getIpAddress(request), joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName(), joinPoint.getArgs());

    }

    /**
     * 请求之后拦截
     */
    @After("doController()")
    public void doAfter() {

    }

    /**
     * 返回时拦截
     *
     * @param object
     */
    @AfterReturning(returning = "object", pointcut = "doController()")
    public void doAfterReturning(Object object) {
        if (null != object) {
            if (object instanceof ResultEntity) {
                ((ResultEntity) object).setTotalTime((System.currentTimeMillis() - BEGIN_TIME));
                if (null != ((ResultEntity) object).getData() && (((ResultEntity) object).getData() instanceof ArrayList)) {
                    ((ResultEntity) object).setTotal(((ArrayList) ((ResultEntity) object).getData()).size());
                } else if (null != ((ResultEntity) object).getData() && (((ResultEntity) object).getData() instanceof Map)) {
                    ((ResultEntity) object).setTotal(((Map) ((ResultEntity) object).getData()).size());
                } else if (null != ((ResultEntity) object).getData() && (((ResultEntity) object).getData() instanceof Pageable)) {
                    ((ResultEntity) object).setTotal(((Pageable) ((ResultEntity) object).getData()).getPageSize());
                }
            }
            logger.debug("response:{}", object.toString());
        } else {
            logger.debug("response:{}", "无返回值");
        }

    }
}
