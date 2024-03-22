package com.gdu.prj08.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdu.prj08.service.UploadService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UploadController {

  private final UploadService uploadService;
  
  @PostMapping("/upload1.do")
  public String upload1(MultipartHttpServletRequest multipartRequest
                      , RedirectAttributes redirectAttributes) {
    
    int insertCount = uploadService.upload1(multipartRequest);
    // insertCount 결과가 넘어오고 이를 FlashAttribute형태로 redirec함. 
    // 다시 말해서 insertCount라는 속성(attribute)를 가지고 main.do로!
    redirectAttributes.addFlashAttribute("insertCount", insertCount);
    
    
    return "redirect:/main.do";
    // 그냥 /main.do라고 작성하면, viewResolver가 앞뒤에 prefiex같은걸 붙ㅇ여서
    // 자기 맘대로 jsp 페이지로 이동한다.
    // 요청으로 이동하려면 redirect!
  }
  
  // 방법1. Json을 위해 map을 사용함.
  @ResponseBody // viewResolver에게. 이 데이터는 JSP 경로를 의미하는게 아니야.
  @PostMapping(value="/upload2.do", produces="application/json") // produces -> 이 Map은 사실 json입니다. 알려주는 작업.
  public Map<String, Object> upload2(MultipartHttpServletRequest multipartRequest) {
    return uploadService.upload2(multipartRequest); // 이대로 가면 안됨 -> viewResolver가 무조건 반환값에 무언가를 붙임..!! 
    // Jackson이 하는 일. Map을 자동으로 JSON 데이터로 바꿔주는 일 수행. 반대도 가능.
  }
  
  /*
  // 방법2. 
  @PostMapping(value="/upload2.do", produces="application/json") 
  public ResponseEntity<Map<String, Object>> upload2(MultipartHttpServletRequest multipartRequest) {
    return new ResponseEntity<T>(Map.of("success", 1), HttpStatus.OK) ;
  }
  */
}
