package com.gdu.prj.dao;

import java.util.List;
import java.util.Map;

import com.gdu.prj.dto.StudentDto;

public interface StudentDao {
  int insertStudent(StudentDto student); // 신규 학생 등록
  int updateStudent(StudentDto student);
  int deleteStudent(int stuNo); // 학생 삭제
  List<StudentDto> selectStudentList(Map<String, Object> params); // 학생 리스트
  int getStudentCount();
  StudentDto selectStudentByNo(int stuNo); // 학생 선택 조회
  void close();

}
