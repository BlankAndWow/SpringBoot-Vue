package com.example.demo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
@Order(-5)
public class LoggerAop {

    private static final Logger logger = LoggerFactory.getLogger(LoggerAop.class);

    ThreadLocal<Long> startTime = new ThreadLocal<Long>();


    @Pointcut("execution(public * com.example.demo.controller..*.*(..))")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {

        // 接受请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 记录请求内容
        logger.info("===========开始记录网站日志============");
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("client host: : " + request.getRemoteAddr());
        logger.info("server host: : " + request.getLocalAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));

        startTime.set(System.currentTimeMillis());
    }

    @AfterReturning("webLog()")
    public void doAfterReturning(JoinPoint joinPoint) {

        // 处理完请求，返回内容
        logger.info("耗时（毫秒） : " + (System.currentTimeMillis() - startTime.get()));
        logger.info("===========日志记录结束==============");
        startTime.remove();
    }

}
