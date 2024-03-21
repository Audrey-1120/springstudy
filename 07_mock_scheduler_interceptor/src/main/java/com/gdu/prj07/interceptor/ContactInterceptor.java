package com.gdu.prj07.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

/*
 * 인터셉터
 * 
 * 1. Controller 의 요청과 응답을 가로챈다.
 * 2. 동작 순서
 * view - filter - DispatcherServlet - interceptor - controller - service - dao - db
 *       (web.xml)      (servlet-context.xml)
 * 3. 생성 방법
 *   1) HandlerInterceptor 인터페이스 구현 (권장)
 *   2) HandlerInterceptorAdaptor 클래스 상속
 * 3. 주요 메소드
 *   1) preHandle()       : 요청 이전에 동작할 코드 (요청을 막을 수 있다.)
 *      - 얘는 반환타입이 boolean임. 요청을 허용할것인지 막을것인지?
 *   2) postHandle()      : 요청 이후에 동작할 코드
 *   3) aftercompletion() : View 처리가 끝난 이후에 동작할 코드
 *   
 */
// 클래스 상속 방법을 쓰게 되면 다중 상속에 걸리게 된다.
// 만약에 위의 클래스의 상속을 받게 되면 다중 상속에 걸리므로 다른 상속은 할 수 없다.

public class ContactInterceptor implements HandlerInterceptor {
  
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    // preHandle은 예외처리 되어있어서 할 필요? 없다.
    
    // preHandle() 메소드 반환값
    // 1. true  : 요청을 처리한다.
    // 2. false : 요청을 처리하지 않는다.
    
    // 굳이 인터셉터를 사이에 넣은 이유? : 요청이 못가게 막으려고.
    // 이 요청은 들어줄 수 없다! -> 다시 앞으로 돌려보내기.
    // 위의 파라미터인 request와 response를 미리 당겨서 쓰기. 
    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<script>");
    out.println("alert('인터셉터가 동작했습니다.')");
    out.println("history.back()");
    out.println("</script>");
    
    
    return false;   // 컨트롤러로 요청이 전달되지 않는다.
  }
  

}
