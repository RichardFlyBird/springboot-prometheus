package com.example.springboot2demo.aop;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @description:
 * @author: Richard.JI
 * @date: 2020-11-27 14:42
 **/
@Component
@Aspect
public class AspectAop {

    @Autowired
    MeterRegistry registry;

    private Counter counter_total;

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution(public * com.example.springboot2demo.controller.*.*(..))")
    private void pointCut(){}

    @PostConstruct
    public void init(){
        counter_total = registry.counter("app_requests_count", "v1", "all");
    }

    @Before("pointCut()")
    public void doBefore(JoinPoint joinPoint)throws Throwable {
        startTime.set(System.currentTimeMillis());
        counter_total.increment();
    }

    @AfterReturning(returning = "returnVal", pointcut = "pointCut()")
    public void doAftereReturning(Object returnVal){
        System.out.println("请求执行时间：" + (System.currentTimeMillis() - startTime.get()));
    }
}