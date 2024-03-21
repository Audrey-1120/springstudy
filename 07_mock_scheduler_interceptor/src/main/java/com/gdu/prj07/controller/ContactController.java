package com.gdu.prj07.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.support.HttpRequestHandlerServlet;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdu.prj07.service.ContactService;

import ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping(value="/contact")
@RequiredArgsConstructor
@Controller
public class ContactController {

  private final ContactService contactService;
  
  // private static final Logger log = LoggerFactory.getLogger(ContactController.class); // ContactController 가 동작할 때 로그를 찍는 log

  @GetMapping(value="/list.do")
  public String list(Model model) {
    model.addAttribute("contactList", contactService.getContactList());
    return "contact/list"; // viewResolver가 알아서 완성시켜줌.
  }
  
  @GetMapping(value="/detail.do")
  public String detail(@RequestParam(value="contact-no", required=false, defaultValue="0") int contactNo
                     , Model model) {
    model.addAttribute("contact", contactService.getContactByNo(contactNo));
    return "contact/detail";
    // 번호를 바로 받아오는 requestParam사용하면 좋을듯.
  }
  
  // 메인화면에서 write.jsp로 이동
  @GetMapping(value="/write.do")
  public String write() {
    return "contact/write";
  }
  
  //서비스에게 전달해주는 것 두가지가 파라미터.
  @PostMapping(value="/register.do")
  public void register(HttpServletRequest request
                     , HttpServletResponse response) {
    contactService.registerContact(request, response);
  }
  
  // 삭제
  @GetMapping(value="/remove.do")
  public void remove1(HttpServletRequest request, HttpServletResponse response) {
    contactService.removeContact(request, response);
  }
  
  // 삭제2
  @PostMapping(value="/remove.do")
  public void remove2(HttpServletRequest request, HttpServletResponse response) {
    contactService.removeContact(request, response);
  }
  
  // 수정
  @PostMapping(value="/modify.do")
  public void modify(HttpServletRequest request, HttpServletResponse response) {
    contactService.modifyContact(request, response);
  }
  
  @GetMapping(value="/tx/test.do")
  public String txTest() {
    contactService.txTest();
    return "redirect:/contact/list.do";
  }
  
  
}
