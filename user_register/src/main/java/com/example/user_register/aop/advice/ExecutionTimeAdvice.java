package com.example.user_register.aop.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionTimeAdvice {
    Logger logger = LoggerFactory.getLogger(ExecutionTimeAdvice.class);

    /**
     * Log the processing time time of the annotated function.
     *
     * @param proceedingJoinPoint The proceedingJoinPoint.
     * @return Object, the object returned by the proceedingJoinPoint.
     * @throws Throwable when an error occurred.
     */
    @Around("@annotation(com.example.user_register.aop.annotation.ExecutionTime)")
    public Object trackTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object object = proceedingJoinPoint.proceed();
        long endTime = System.currentTimeMillis();
        logger.info("Method "
                .concat(proceedingJoinPoint.getSignature().toShortString())
                .concat(" took: ")
                .concat(String.valueOf(endTime - startTime))
                .concat("ms"));
        return object;
    }
}
