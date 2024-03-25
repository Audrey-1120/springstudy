package com.gdu.prj09.dao;

import java.util.List;
import java.util.Map;

import com.gdu.prj09.dto.MemberDto;

public interface MemberDao {
  
  int insertMember(MemberDto member);
  int updateMember(MemberDto member);
  
  // 두 삭제는 하나의 서비스로 구성해야하는가? 
  // - 한 서비스에서 둘다 부를 일은 X
  // 사용자가 삭제 버튼을 눌렀는데 둘다 돌아갈 일이 없다는 뜻임.
  // - 이런 경우에는 서비스를 분리하는 것이 좋음.
  int deleteMember(int memberNo);
  int deleteMembers(List<String> memberNoList);
  
  // member_no=1이나 member_no='1'은 모두 똑같이 동작함. String 이든 int든..
  int getTotalMemberCount();
  List<MemberDto> getMemberList(Map<String, Object> map);
  MemberDto getMemberByNo(int memberNo);

}
