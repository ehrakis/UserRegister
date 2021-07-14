package com.example.user_register.aop.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogInputOutputAdvice {
    Logger logger = LoggerFactory.getLogger(LogInputOutputAdvice.class);

    /**
     * Log the input parameters and output of the annotated function.
     *
     * @param proceedingJoinPoint The proceedingJoinPoint.
     * @return Object, the object returned by the proceedingJoinPoint.
     * @throws Throwable when an error occurred.
     */
    @Around("@annotation(com.example.user_register.aop.annotation.LogInputOutput)")
    public Object trackTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        logger.info("Inputs for: "
                .concat(proceedingJoinPoint.getSignature().toShortString()));
        for (Object object: proceedingJoinPoint.getArgs()) {
            if(object != null) {
                logger.info(object.toString());
            } else {
                logger.info("null");
            }
        }

        Object object = proceedingJoinPoint.proceed();

        logger.info("Outputs for: "
                .concat(proceedingJoinPoint.getSignature().toShortString()));
        if(object != null) {
            logger.info(object.toString());
        } else {
            logger.info("null");
        }

        return object;
    }
}
