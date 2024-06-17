package com.chotchip.Project3.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j
public class Logger {


    @Pointcut("within(com.chotchip.Project3.services.*Service)")
    public void isService() {
    }

    @Pointcut("execution(public * com.chotchip.Project3.services.*Service.*(..))")
    public void isInvokeMethodInService() {
    }

//    @AfterReturning(value = "isInvokeMethodInService() && target(service)", returning = "result")
//    public void addLoggingAfterReturning(JoinPoint joinPoint, Object service, Object result) {
//        log.info("fa {}", joinPoint.toShortString());
//    }

    @Around("isService() && isInvokeMethodInService() && target(service) ")
    public Object addLoggingAround(ProceedingJoinPoint joinPoint, Object service) {
        log.info("Around before - invoked method in class {}", service);
        try {
            Object res = joinPoint.proceed();
            String args = Arrays.stream(joinPoint.getArgs())
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));
            log.info("Around after returning - invoked method {} with param {} in class {}, result {}", joinPoint.toShortString(), args, service, res);
            return res;
        } catch (Throwable e) {
            log.warn("Around after throwing - invoked method in class {}, exception {}", service, e.getClass());
            throw new RuntimeException(e);
        } finally {
            log.info("Around after - invoked methods in class {}", service);
        }
    }
}
