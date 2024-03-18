package com.gdu.prj05.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.prj05.service.ContactService;

import lombok.RequiredArgsConstructor;

@RequestMapping(value="/contact")
@RequiredArgsConstructor
@Controller
public class ContactController {

  private final ContactService contactService;

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
  
}
