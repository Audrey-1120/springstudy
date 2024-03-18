package com.gdu.prj05.dao;

import java.util.List;

import com.gdu.prj05.dto.ContactDto;

public interface ContactDao {
  int registerContact(ContactDto contact);  // 연락처 등록
  int modifyContact(ContactDto contact);    // 연락처 수정
  int removeContact(int contactNo);         // 연락처 삭제
  List<ContactDto> getContactList();        // 전체 연락처 조회
  ContactDto getContactByNo(int contactNo); // 연락처 상세보기
  

}
