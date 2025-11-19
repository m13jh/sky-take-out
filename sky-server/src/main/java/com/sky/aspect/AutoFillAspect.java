package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
public class AutoFillAspect {

//    定义切点
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointCut(){}

//    前置通知
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) {
        log.info("autoFill");
//        获取方法签名
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
//        获取注解对象
        AutoFill annotation = signature.getMethod().getAnnotation(AutoFill.class);
//        获取数据库操作类型
        OperationType value = annotation.value();
//        获取实体对象
        Object[] args = joinPoint.getArgs();
        if(args == null || args.length == 0) {
            return;
        }
        Object entry = args[0]; // 默认第一个参数为实体对象
//        准备数据
        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();
//        根据当前不同的操作类型，为对应的属性通过反射来赋值
        if(value == OperationType.INSERT) {
            try {
                Method setCreateTime = entry.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method setUpdateTime = entry.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setCreateUser = entry.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                Method setUpdateUser = entry.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                setCreateTime.invoke(entry,now);
                setCreateUser.invoke(entry,currentId);
                setUpdateTime.invoke(entry,now);
                setUpdateUser.invoke(entry,currentId);
            } catch(Exception e) {
                e.printStackTrace();
            }
        } else if(value == OperationType.UPDATE){
            try {
                Method setUpdateTime = entry.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entry.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                setUpdateTime.invoke(entry, now);
                setUpdateUser.invoke(entry, currentId);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

}
