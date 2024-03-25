package com.gdu.prj09.service;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.gdu.prj09.dao.MemberDao;
import com.gdu.prj09.dto.MemberDto;
import com.gdu.prj09.utils.MyPageUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

  
  private final MemberDao memberDao;
  private final MyPageUtils myPageUtils;
 

  @Override
  public ResponseEntity<Map<String, Object>> getMembers(int page, int display) {
    return null;
  }

  @Override
  public ResponseEntity<MemberDto> getMemberByNo(int memberNo) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ResponseEntity<Map<String, Object>> registerMember(MemberDto member, HttpServletResponse response) {

    int insertCount = memberDao.insertMember(member);
      
    return new ResponseEntity<Map<String,Object>>(
                  Map.of("insertCount", insertCount),
                  HttpStatus.OK);
    
  }

  @Override
  public ResponseEntity<Map<String, Object>> modifyMember(MemberDto member) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ResponseEntity<Map<String, Object>> removeMember(int memberNo) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ResponseEntity<Map<String, Object>> removeMembers(String memberNoList) {
    // TODO Auto-generated method stub
    return null;
  }

}
