package com.gdu.prj01.anno03;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MyService {
  
  // service가 사용하는 것은 Dao임.
  private MyDao myDao;
  
  public void add() {
     myDao.add();
     System.out.println("MyService add() 호출");
  }
  

}
