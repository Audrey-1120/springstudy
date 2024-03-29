package com.gdu.prj06.aspect;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
public class MyAroundAspect {
  
  // PointCut : 언제 동작하는가?
  @Pointcut("execution (* com.gdu.prj06.controller.*Controller.*(..))")
  public void setPointCut() {}
  // 위치상 메소드 레벨임.
  // 할일이 없네..(중괄호 안에 처리 구문이 없음)
  
  // Advice : 무슨 동작을 하는가?
  @Around("setPointCut()")
  
  /*
   * Around Advice 메소드 작성 방법
   * 1. 반환타입 : Object
   * 2. 메소드명 : 마음대로
   * 3. 매개변수 : ProceedingJoinPoint 타입 객체 
   */
  
  public Object myAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable { // Throwable은 Exception보다 상위
    
    log.info("{}", "-".repeat(80));                                                      // 동작 이전 (@Before 이전)
    
    Object obj = proceedingJoinPoint.proceed();                                          // Advice가 동작하는 시점
    
    log.info("{}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));  // 동작 이후 (@After 이전)
    
    return obj;
    
  }

}
