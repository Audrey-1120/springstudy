package com.gdu.prj01.xml03;

import java.sql.Connection;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MyDao {
  
  private Connection con;
  private MyConnection myConnection;  // 일단 가져오기
  
  
  private void close() {
    try {
      
      if(con != null) {
        con.close();
        System.out.println(myConnection.getUser() + " 접속해제되었습니다.");
      }
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public void add() {
    con = myConnection.getConnection();
    System.out.println("MyDao add() 호출");
    close();
  }
  

}
