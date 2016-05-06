package com.aspectj;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;


@Aspect
public class TestAspect {

	
 /*   @Around("execution (* *(..))")
    public Object advice(final ProceedingJoinPoint joinPoint) throws Throwable {
        //System.out.printf("TestAspect.adaroundvice() called on '%s'%n", joinPoint);
       // retrieve the runtime method arguments (dynamic)
        for(final Object argument : joinPoint.getArgs()){
            System.out.println("Parameter value:" + argument);
        }
	
        return joinPoint.proceed();
    }
 */   
    
    @Around("execution (* android.widget.TextView.*(..))")
    public Object myAspect(final ProceedingJoinPoint pjp) throws Throwable{

    	 AspectjLog.log(pjp,"Enter",true);
    	    Object retVal = pjp.proceed();
    	 AspectjLog.log(pjp,"Exit",true);
    	    return retVal;
    }
    
    
    
    
}
