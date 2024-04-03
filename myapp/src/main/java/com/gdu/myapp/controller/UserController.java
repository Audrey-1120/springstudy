package com.gdu.myapp.controller;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.gdu.myapp.dto.UserDto;
import com.gdu.myapp.service.UserService;

@RequestMapping("/user")
@Controller
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    super();
    this.userService = userService;
  }
  
  // 로그인 페이지
  @GetMapping("/signin.page")
  public String signinPage(HttpServletRequest request
                         , Model model) {
    
    // Sign In 페이지로 url 넘겨 주기 (로그인 후 이동할 경로를 의미함)
    model.addAttribute("url",  userService.getRedirectURLAfterSignin(request));
    
    // Sign In 페이지로 naverLoginURL 넘겨 주기 (네이버 로그인 요청 주소를 의미함)
    model.addAttribute("naverLoginURL", userService.getNaverLoginURL(request));
    
    return "user/signin";
    
  }
  
  // 로그인 기능
  @PostMapping("/signin.do")
  public void signin(HttpServletRequest request, HttpServletResponse response) {
    userService.signin(request, response); // 서비스에서 모두 처리하세요~
  }
  
  // 회원가입 기능
  @PostMapping("/signup.do")
  public void signup(HttpServletRequest request, HttpServletResponse response) {
    userService.signup(request, response);
  }
  
  
  @GetMapping("/naver/getAccessToken.do")
  public String getAccessToken(HttpServletRequest request) {
    String accessToken = userService.getNaverLoginAccessToken(request);
    return "redirect:/user/naver/getProfile.do?accessToken=" + accessToken;
  }
  
  @GetMapping("/naver/getProfile.do")
  public String getProfile(HttpServletRequest request, Model model) {
    // 간편가입 화면으로 보낼때는 사용자 정보를 model에 담아서 보낸다.
    
    // 네이버로부터 받은 프로필 정보
    UserDto naverUser = userService.getNaverLoginProfile(request.getParameter("accessToken"));

    // 반환 경로
    String path = null;
    
    
    // 프로필이 DB에 있는지 확인 (있으면 Sign In, 없으면 Sign Out)
    if(userService.hasUser(naverUser)) {
      // Sign In
      userService.naverSignin(request, naverUser);
      path = "redirect:/main.page";
    } else {
      // Sign Up (네이버 가입 화면으로 이동)
      
      model.addAttribute("naverUser", naverUser);
      path = "user/naver_signup"; // forward할때는 경로만 작성
    }
    
    return null;
  }
  
  
  // 회원가입 페이지
  @GetMapping("/signup.page")
  public String signupPage() {
    return "user/signup";
  }
  
  // 이메일 중복체크
  @PostMapping(value="/checkEmail.do", produces="application/json")
  public ResponseEntity<Map<String, Object>> checkEmail(@RequestBody Map<String, Object> params) {
    return userService.checkEmail(params);
  }
  
  // 이메일 인증코드
  // produces : fetch의 결과 응답의 타입은??
  @PostMapping(value="/sendCode.do", produces="application/json")
  public ResponseEntity<Map<String, Object>> sendCode(@RequestBody Map<String, Object> params) {
    return userService.sendCode(params);
  }
  
  // 회원 탈퇴
  @GetMapping("/leave.do")
  public void leave(HttpServletRequest request, HttpServletResponse response) {
    userService.leave(request, response);
  }
  
  // 로그아웃
  @GetMapping("/signout.do")
  public void signout(HttpServletRequest request, HttpServletResponse response) {
    userService.signout(request, response);
  }
  
  
}
