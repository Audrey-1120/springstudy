package com.gdu.myapp.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CommentDto {
  private int commentNo, depth, groupNo, blogNo, state;
  private String contents;
  private Timestamp createDt;
  private UserDto user;
  // comment가 blog의 전체 정보를 가져올 필요는 없음.
  // 보통 상세보기를 한 다음 댓글을 담.
  
  // blogNo대신 blogDto를 넣는다면? blogDto에도 userNo가 있는데
  // userDto에도 userNo가 있음. 그러면 select할때
  // 오류가 생길 수 있다.
}
