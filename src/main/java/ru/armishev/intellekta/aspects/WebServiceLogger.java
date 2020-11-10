package ru.armishev.intellekta.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class WebServiceLogger {
    private static Logger LOG = LoggerFactory.getLogger(WebServiceLogger.class);

    @Pointcut(value = "execution(public * ru.armishev.intellekta.service.ProductService.*(..))")
    public void serviceMethod() {}

    @Pointcut("@annotation(ru.armishev.intellekta.annotation.Loggable)")
    public void loggableMethod() {}

    @Before("serviceMethod() && loggableMethod()")
    public void logWebServiceCallBefore(JoinPoint thisJointPoint) throws Throwable {
        String methodName = thisJointPoint.getSignature().getName();
        Object[] methodArgs = thisJointPoint.getArgs();

        LOG.info("Call method "+ methodName + " with args "+ Arrays.toString(methodArgs));
    }

    @After("serviceMethod() && loggableMethod()")
    public void logWebServiceCallAfter(JoinPoint thisJointPoint) throws Throwable {
        String methodName = thisJointPoint.getSignature().getName();

        LOG.info("Method "+ methodName + " has been executed");
    }
}
