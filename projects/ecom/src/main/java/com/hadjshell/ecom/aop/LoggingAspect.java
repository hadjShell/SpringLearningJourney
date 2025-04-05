package com.hadjshell.ecom.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    // return-type, class-name.method-name(args)
    @Before("execution(* com.hadjshell.ecom.controller.ProductController.*(..))")
    public void logMethodCall(JoinPoint jp) {
        LOGGER.info("Method called: " + jp.getSignature().getName());
    }

    @AfterReturning("execution(* com.hadjshell.ecom.controller.ProductController.*(..))")
    public void logMethodExecution(JoinPoint jp) {
        LOGGER.info("Method executed successfully: " + jp.getSignature().getName());
    }

    @AfterThrowing("execution(* com.hadjshell.ecom.controller.ProductController.*(..))")
    public void logMethodCrash(JoinPoint jp) {
        LOGGER.info("Method has some issues: " + jp.getSignature().getName());
    }
}
