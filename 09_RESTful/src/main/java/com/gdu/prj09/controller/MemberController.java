package com.gdu.prj09.controller;

import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.gdu.prj09.dto.AddressDto;
import com.gdu.prj09.service.MemberService;

import lombok.RequiredArgsConstructor;

/*
 * RESTful
 * 1. RepresentationState Transfer
 * 2. 요청 주소를 작성하는 한 방식이다.
 * 3. 요청 파라미터를 ? 뒤에 추가하는 Query String 방식을 사용하지 않는다.
 * 4. 요청 파라미터를 주소에 포함하는 Path Variable 방식을 사용하거나, 요청 본문에 포함하는 방식을 사용한다.
 * 5. 요청의 구분을 "주소 + 메소드" 조합으로 구성한다.
 * 6. CRUD 요청 예시
 *             | URL                         | Method
 *     --------|---------------------------- |------------
 *   1) 목록   | /members                    | GET
 *             | /members/page/1             |
 *             | /members/page/1/display/20  |
 *   2) 상세   | /members/1                  | GET 
 *   3) 삽입   | /members                    | POST
 *   4) 수정   | /members                    | PUT
 *   5) 삭제   | /member/1                   | DELETE
 *             | /members/1,2,3              |
 *   
 *   삽입의 경우, 주소가 아닌 본문에 포함시켜서 보낸다. - POST
 *   수정의 경우, 수정할 정보를 담아서 삽입과 유사한 방식으로 내부동작이 이루어진다. - PUT
 *   삽입과 수정은 주소가 동일하지만 method가 다르기 때문에 구분 가능.
 *   
 *   
 */             


@RequiredArgsConstructor
@Controller
public class MemberController {

  private final MemberService memberService;
  
  @GetMapping("/admin/member.do")
  public void adminMember() {}
  
  // 회원 등록
  @PostMapping(value="/members", produces="application/json")
  public ResponseEntity<Map<String, Object>> registerMember(@RequestBody Map<String, Object> map
                                                          , HttpServletResponse response) {
    return memberService.registerMember(map, response);
  }
  
  @GetMapping(value="/members/page/{p}/display/{dp}", produces="application/json")
  public ResponseEntity<Map<String, Object>> getMembers(@PathVariable(value="p", required=false) Optional<String> optPage
                                                      , @PathVariable(value="dp", required=false) Optional<String> optDisplay) {
    int page = Integer.parseInt(optPage.orElse("1"));
    int display = Integer.parseInt(optDisplay.orElse("20"));
    return memberService.getMembers(page, display);
  }
  
  @GetMapping(value="/members/{memberNo}", produces="application/json")
  public ResponseEntity<Map<String, Object>> getMemberByNo(@PathVariable(value="memberNo", required=false) Optional<String> opt) {
    int memberNo = Integer.parseInt(opt.orElse("0"));
    return memberService.getMemberByNo(memberNo);
  }
  
  // jsp가 전달하는 json 데이터는 body에 포함되어 있음.
  // 
  @PutMapping(value="/members", produces="application/json")
  public ResponseEntity<Map<String, Object>> modifyMember(@RequestBody Map<String, Object> map) {
    return memberService.modifyMember(map);
  }
  
  @DeleteMapping(value="/member/{memberNo}", produces="application/json")
  public ResponseEntity<Map<String, Object>> removeMember(@PathVariable(value="memberNo", required=false)Optional<String> opt) {
    int memberNo = Integer.parseInt(opt.orElse("0"));
    return memberService.removeMember(memberNo);
  }
  
  @DeleteMapping(value="/members/{memberNoList}", produces="application/json")
  public ResponseEntity<Map<String, Object>> removeMembers(@PathVariable(value="memberNoList", required=false) Optional<String> opt) {
    return memberService.removeMembers(opt.orElse("0"));
  }
  
  
}