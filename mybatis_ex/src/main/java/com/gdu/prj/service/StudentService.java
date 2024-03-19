package com.gdu.prj.service;

import javax.servlet.http.HttpServletRequest;

import com.gdu.prj.common.ActionForward;

public interface StudentService {
  ActionForward addStudent(HttpServletRequest request);
  ActionForward getStudentList(HttpServletRequest request);
  ActionForward getStudentByNo(HttpServletRequest request);
  ActionForward editStudent(HttpServletRequest request);
  ActionForward modifyStudent(HttpServletRequest request);
  ActionForward removeStudent(HttpServletRequest request);
  
  

}
