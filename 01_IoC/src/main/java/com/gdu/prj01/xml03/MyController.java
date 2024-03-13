package com.gdu.prj01.xml03;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MyController {
  
  private MyService myService; // 이곳에 데이터를 넣으려면 생성자 or setter 방식
  
  public void add() {
    myService.add();
    System.out.println("MyController add() 호출");
  }
  

}
