package com.itwj.reggie.AOP;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itwj.reggie.common_class.R;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoginASpect {
    private Logger logger=LoggerFactory.getLogger(LoginASpect.class);

    //定义了一个切点
    @Pointcut(value="execution(* com.itwj.reggie.controller.EmployeeController.*(..))")
    public void LoginPointcut(){

    }
    //定义一个通知
    @Around("LoginPointcut()")
    public Object Logger(ProceedingJoinPoint pjp) throws Throwable {

        String className=pjp.getTarget().getClass().toString();
        String methodName=pjp.getSignature().getName();
        Object[] array=pjp.getArgs();

        logger.info("调用了类,"+className+",方法:"+methodName+"传递的参数为:");

        Object obj=pjp.proceed();

        logger.info("完成调用类,"+className+",方法:"+methodName+"返回值:"+obj.toString());
        return obj;
    }

}