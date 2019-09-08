package com.hwq.wudi.config.datasource;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Auther: haowenqiang
 * @Date: 2019/9/2
 * @Description:
 */
@Component
@Aspect
@Order(1) //这是关键，要让该切面调用先于AbstractRoutingDataSource的determineCurrentLookupKey()
public class DynamicDataSourceAspect {

    @Before("@annotation(com.hwq.wudi.config.datasource.DataSource)")
    public void before(JoinPoint point) {
        try {
            DataSource annotationOfClass = point.getTarget().getClass().getAnnotation(DataSource.class);
            String methodName = point.getSignature().getName();
            Class[] parameterTypes = ((MethodSignature) point.getSignature()).getParameterTypes();
            Method method = point.getTarget().getClass().getMethod(methodName, parameterTypes);
            DataSource methodAnnotation = method.getAnnotation(DataSource.class);
            methodAnnotation = methodAnnotation == null ? annotationOfClass : methodAnnotation;
            String dataSourceType = methodAnnotation != null
                    && StringUtils.isNotBlank(methodAnnotation.value()) ? methodAnnotation.value() : "master";
            DataSourceContextHolder.setDataSource(dataSourceType);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @After("@annotation(com.hwq.wudi.config.datasource.DataSource)")
    public void after(JoinPoint point) {
        DataSourceContextHolder.clearDataSource();
    }
}
