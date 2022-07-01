package com.xiaojie.aop;

import com.xiaojie.annotation.MyTransactional;
import com.xiaojie.util.TransactionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;

import java.lang.reflect.Method;

/**
 * 自定义事务切面，原理
 * 通过环绕通知，当执行目标方法没有异常时提交事务
 * 当执行目标方法发生异常时，在异常通知中回滚事务，实现事务提交与回滚
 */
@Aspect
@Component
public class TransactionalAop {

    @Autowired
    private TransactionUtils transactionUtils;
    
    private TransactionStatus status;

    /**
     * 定义切入点
     */
    @Pointcut("execution(* com.xiaojie.service.*.*.*(..))")
    public void transactionalPoint(){}

    @Around("transactionalPoint()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        //获取方法名称
        String methodName = point.getSignature().getName();
        // 获取目标对象
        Class<? extends Object> targetClass = point.getTarget().getClass();
        // 获取目标对象类型
        Class[] par = ((MethodSignature) point.getSignature()).getParameterTypes();
        // 获取目标对象方法
        Method objMethod = targetClass.getDeclaredMethod(methodName, par);
        //获取目标方法上的注解
        MyTransactional declaredAnnotation = objMethod.getDeclaredAnnotation(MyTransactional.class);
        //如果方法上有注解则开始事务
        if (null==declaredAnnotation){
            //如果没有注解，则执行目标方法
            return point.proceed();
        }
        //有注解，开启事务
        System.out.println("开启事务。。。。。。。");
         status = transactionUtils.begin();
        //调用目标方法
        Object proceed = point.proceed();
        if (status!=null){
            //提交事务
            transactionUtils.commit(status);
        }
        return proceed;
    }
    /**
     * 目标方法发生异常，则执行回滚事务
     */
    @AfterThrowing("transactionalPoint()")
    public void afterthrow() {
        if (status!=null){
            //如果发生异常则回滚事务
            transactionUtils.rollback(status);
        }
    }

}
