package com.report.aspect;

import com.report.util.GloabalUtils;
import com.report.vo.ResultEntity;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.NamedThreadLocal;
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
@Slf4j
@Aspect
@Component
public class ReportAspect {

    /**
     * 开始时间,用于记录请求耗时
     */
    private NamedThreadLocal<Long> namedThreadLocal = new NamedThreadLocal<>("threadLocalTime");
    private NamedThreadLocal<String> urlThreadLocal = new NamedThreadLocal<>("urlThreadLocalTime");

    @Pointcut(value = "execution(* com.report.controller.*.*(..))")
    public void doController() {
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
        long totalTime = System.currentTimeMillis() - namedThreadLocal.get();
        if (null != object) {
            if (object instanceof ResultEntity) {
                ((ResultEntity) object).setTotalTime(totalTime);
                if (null != ((ResultEntity) object).getData() && (((ResultEntity) object).getData() instanceof ArrayList)) {
                    ((ResultEntity) object).setTotal(((ArrayList) ((ResultEntity) object).getData()).size());
                } else if (null != ((ResultEntity) object).getData() && (((ResultEntity) object).getData() instanceof Map)) {
                    ((ResultEntity) object).setTotal(((Map) ((ResultEntity) object).getData()).size());
                } else if (null != ((ResultEntity) object).getData() && (((ResultEntity) object).getData() instanceof Pageable)) {
                    ((ResultEntity) object).setTotal(((Pageable) ((ResultEntity) object).getData()).getPageSize());
                }
            }
        } else {
            log.debug("response:{}", "无返回值");
        }
        log.info("请求:{} 返回,耗时:{}ms", urlThreadLocal.get(), totalTime);
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
        namedThreadLocal.set(System.currentTimeMillis());
        urlThreadLocal.set(request.getRequestURL().toString());
        log.info("url:{},方法:{},请求ip:{},类和方法:{}()", request.getRequestURL(), request.getMethod(), GloabalUtils.getIpAddress(request), joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.debug("参数:{}", joinPoint.getArgs());
    }


}
