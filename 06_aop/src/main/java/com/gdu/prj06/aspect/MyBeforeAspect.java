package com.gdu.prj06.aspect;

import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
public class MyBeforeAspect {

  // PointCut
  @Pointcut("execution (* com.gdu.prj06.controller.*Controller.*(..))")
  public void setPointCut() {}
  
  // Advice
  /*
   * Before Advice 메소드 작성 방법
   * 1. 반환타입 : void
   * 2. 메소드명 : 마음대로
   * 3. 매개변수 : JoinPoint 타입 객체
   */
  @Before("setPointCut()")
  public void myBeforeAdvice(JoinPoint joinPoint) {
    
    // 요청 메소드/주소/파라미터 로그 남기기
    
    ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
    HttpServletRequest request = servletRequestAttributes.getRequest();
    
    // 파라미터마다 이름이 다르고, 요청마다 파라미터가 다 다르다.
    // 그러므로 파라미터를 전부 가져와서 Map으로 반환시켜야 한다.
    Map<String, String[]> params = request.getParameterMap();
    // 파라미터 map의 반환타입은 string 배열로 정해져 있다.
    String str = "";
    // params가 올때 null로 체크? ㄴㄴ 오긴하는데 비어있는 배열이 오는거임. -> isEmpty()
    if(params.isEmpty()) {
      str += "No Parameter";
    } else {
      for(Entry<String, String[]> entry : params.entrySet()) {
        str += entry.getKey() + ":" + Arrays.toString(entry.getValue()) + " ";
      }
    }
    
    log.info("{} | {}", request.getMethod(), request.getRequestURI());
    log.info("{}", str);
    
  }
  
  
  
}
