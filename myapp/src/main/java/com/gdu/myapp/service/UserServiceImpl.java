package com.gdu.myapp.service;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.gdu.myapp.dto.UserDto;
import com.gdu.myapp.mapper.UserMapper;
import com.gdu.myapp.utils.MySecurityUtils;

import lombok.RequiredArgsConstructor;

@Service
public class UserServiceImpl implements UserService {
  
  private final UserMapper userMapper;

  public UserServiceImpl(UserMapper userMapper) {
    super();
    this.userMapper = userMapper;
  }

  @Override
  public void signin(HttpServletRequest request, HttpServletResponse response) {
    // 현재 3가지 정보(이메일, 패스워드, url)이 넘어왔다.
    
    try {
      
      String email = request.getParameter("email");
      
      // 패스워드의 경우, 값을 가져와서 바로 암호화를 해주자.
      String pw = MySecurityUtils.getSha256(request.getParameter("pw"));
      
      // 가져온 데이터를 map에 담는다.
      Map<String, Object> params = Map.of("email", email, "pw", pw);
      
      UserDto user = userMapper.getUserByMap(params);
      
      if(user != null) {
        // 가져온 객체가 있으므로, 받아온 url로 넘어간다.
        // 로그인의 기본 원리는 session(데이터바인딩 영역)에 회원가입한 사람의 정보를 올려둔다.
        request.getSession().setAttribute("user", user);
        response.sendRedirect(request.getParameter("url"));
      } else {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<script>");
        out.println("alert('일치하는 회원 정보가 없습니다.')");
        out.println("location.href='" + request.getContextPath() + "/main.page'");
        out.println("</script>");
        out.flush();
        out.close();
        
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    
    
  }

  @Override
  public void signout(HttpServletRequest request, HttpServletResponse response) {
    // TODO Auto-generated method stub

  }

  @Override
  public void signup(HttpServletRequest request, HttpServletResponse response) {
    // TODO Auto-generated method stub

  }

  @Override
  public void leave(HttpServletRequest request, HttpServletResponse response) {
    // TODO Auto-generated method stub

  }

}
