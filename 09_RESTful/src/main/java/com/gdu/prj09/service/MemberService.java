package com.gdu.prj09.service;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;

import com.gdu.prj09.dto.MemberDto;

public interface MemberService {
  
  ResponseEntity<Map<String, Object>> getMembers(int page, int display);
  ResponseEntity<MemberDto> getMemberByNo(int memberNo);
  ResponseEntity<Map<String, Object>> registerMember(Map<String, Object> map, HttpServletResponse response); // response는 catch 블록에 예외 발생했을 때 응답보내는 용도!
  ResponseEntity<Map<String, Object>> modifyMember(MemberDto member);
  ResponseEntity<Map<String, Object>> removeMember(int memberNo);
  ResponseEntity<Map<String, Object>> removeMembers(String memberNoList);
  
  /*
   * @DeleteMapping은 주소창에 정보가 전달되는 GET 방식과 유사하다.
   *   /members/1과 다르게 /members/1,2 이렇게 전달받으면... in가 아니라 string이다. 
   */
  
  
  
}
