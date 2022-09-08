package com.technokratos.adboard.aspect;

import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * @author d.gilfanova
 */
@Slf4j
@Aspect
@Component
public class LoggingServiceAspect {

    @Before("@within(org.springframework.stereotype.Service))")
    public void logBeforeMethodExecution(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        log.info("Method \""
                 + methodSignature.getMethod().getName()
                 + "\" with args "
                 + Arrays.toString(joinPoint.getArgs())
                 + " started executing");
    }

    @AfterReturning(value = "@within(org.springframework.stereotype.Service))", returning = "result")
    public void logAfterMethodExecution(JoinPoint joinPoint, Object result) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        log.info("Method \""
                 + methodSignature.getMethod().getName()
                 + "\" return \""
                 + result
                 + "\" when return type is "
                 + ((MethodSignature) joinPoint.getSignature()).getReturnType());
    }
}
