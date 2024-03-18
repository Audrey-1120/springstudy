package com.gdu.prj.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class StudentDto {
  private int stuNo;
  private String name;
  private int kor;
  private int eng;
  private int mat;
  
  
}
