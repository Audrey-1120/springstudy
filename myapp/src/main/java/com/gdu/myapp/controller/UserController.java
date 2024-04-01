package com.gdu.myapp.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gdu.myapp.service.UserService;

@RequestMapping("/user")
@Controller
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    super();
    this.userService = userService;
  }
  
  @GetMapping("/signin.page")
  public String signinPage(HttpServletRequest request
                         , Model model) {
    // - request에 필요한 데이터가 담겨있다. 이를 model에 담아서 signin 페이지로 전달한다.
    // - 로그인 후에 이동할 url을 request로부터 꺼낸다.
    
    // Sign In 페이지 이전의 주소가 저장되어 있는 Request Header 의 referer
    String referer = request.getHeader("referer");
    
    // referer 로 돌아가면 안 되는 예외 상황 (아이디/비밀번호 찾기 화면, 가입 화면 등)
    // 사용하면 안되는 url들을 적는다.
    String[] excludeUrls = {"/findId.page", "/findPw.page", "/signup.page"};
    
    // Sign In 이후 이동할 url
    // 어떤 사이트는 들어가자마자 로그인 함. -> referer값 없다!
    String url = referer;
    if(referer != null) {
      // referer값이 존재한다면 ..
      for(String excludeUrl : excludeUrls) { // excludeUrl에 담겨있는 주소와 같은 게 나오면 진행할 필요 없으므로 break;
        if(referer.contains(excludeUrl)) {
          url = request.getContextPath() + "/main.page";
          break;
        }
      }
    } else {
      url = request.getContextPath() + "/main.page";
    }
    
    // Sign In 페이지로 url 또는 referer 넘겨 주기
    // referer의 존재 여부와 포함 된 게 있는지에 따라 url을 넘김.
    model.addAttribute("url", url);
    
    return "user/signin";
    
  }
  
  @PostMapping("/signin.do")
  public void signin(HttpServletRequest request, HttpServletResponse response) {
    userService.signin(request, response); // 서비스에서 모두 처리하세요~
  }
  
  @GetMapping("/signup.page")
  public String signupPage() {
    return "user/signup";
  }
  
  @PostMapping(value="/checkEmail.do", produces="application/json")
  public ResponseEntity<Map<String, Object>> checkEmail(@RequestBody Map<String, Object> params) {
    return userService.checkEmail(params);
  }
  
  // produces : fetch의 결과 응답의 타입은??
  @PostMapping(value="/sendCode.do", produces="application/json")
  public ResponseEntity<Map<String, Object>> sendCode(@RequestBody Map<String, Object> params) {
    return userService.sendCode(params);
  }
  
  
}
