package com.hwq.wudi.config;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Aspect
@Component
@Slf4j
public class LogAop {

    @Pointcut("execution(public * com.hwq.wudi.*.controller.*.*(..))")
    public void webLog(){}

    @Around("webLog()")
    @SneakyThrows
    public Object arround(ProceedingJoinPoint pjp) {
        long startTime = System.currentTimeMillis();
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        log.info("URL : {}", request.getRequestURL().toString());
        log.info("IP : {}", request.getRemoteAddr());

        //参数
        Enumeration<String> paramter = request.getParameterNames();
        StringBuilder args = new StringBuilder("[");
        while (paramter.hasMoreElements()) {
            String str = (String)paramter.nextElement();
            str += "="+request.getParameter(str);
            args.append(str+",");
        }
        args.append("]");
        log.info("ARGS : {}", args.toString());
        //获取返回值
        Object result = pjp.proceed();
        String elapsedTime = (System.currentTimeMillis() - startTime) + "ms";
        log.info("[{}]use time: {}", pjp.getSignature(), elapsedTime);
        return result;
    }
}
