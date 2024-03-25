package com.gdu.prj09.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

public interface MemberService {
  
  // 하나의 service는 여러개의 Dao를 호출할 수 있음.
  // singlePage이므로 service의 반환값은 모두 하나로 처리
  // @ResponseBody를 품고있는 클래스.
  
  
  // 1. 목록 : 목록 + 개수도 저장한다.
  // display = 20라고 가정하면 개수는? int값.
  // 이 둘을 한번에 저장할 수 있는 타입 -> Map, 여기에 list와 int 둘다 저장!
  public ResponseEntity<Map<String, Object>> getMembers(int page, int display);
  // 파라미터로 전달될 수 값은 display, page값임.
  // Spring에서 파라미터 받는법 - HttpServletRequest, @RequestParam. 
  
  //    /prj09/members?page=1&display=20 (원래 주소)
  //    /prj09/members/page/1/display20 (오늘 해보려는것^^)
  // 보통 ?뒤에 있는 값들을 파라미터라고 부르니까 @RequestParam을 사용.
  // 그러나 두번째 주소는 파라미터가 아니다. 경로에 포함된 데이터이므로 @PathVariable을 사용.
   
}
