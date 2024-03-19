package com.gdu.prj.service;

import javax.servlet.http.HttpServletRequest;

import com.gdu.prj.common.ActionForward;
import com.gdu.prj.dao.StudentDao;
import com.gdu.prj.dao.StudentDaoImpl;

public class StudentServiceImpl implements StudentService {
  
  private StudentDao studentDao = StudentDaoImpl.getInstance();
  
  

  @Override
  public ActionForward addStudent(HttpServletRequest request) {
    String name = request.getParameter("name");
    int kor = Integer.parseInt(request.getParameter("kor"));
    int eng = Integer.parseInt(request.getParameter("eng"));
    int mat = Integer.parseInt(request.getParameter("mat"));
    return null;
  }

}
