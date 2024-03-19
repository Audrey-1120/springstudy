package com.gdu.prj.dao;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.gdu.prj.dto.StudentDto;

public class StudentDaoImpl implements StudentDao {
  
  private SqlSessionFactory factory = null;
  
  private static StudentDao studentDao = new StudentDaoImpl();

  private StudentDaoImpl() {
    try {
      String resource = "com/gdu/prj/config/mybatis-config.xml";
      InputStream in = Resources.getResourceAsStream(resource);
      factory = new SqlSessionFactoryBuilder().build(in);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  public static StudentDao getInstance() {
    return studentDao;
  }
  
  @Override
  public int insertStudent(StudentDto student) {
    SqlSession sqlSession = factory.openSession(false);
    int insertCount = sqlSession.insert("com.gdu.prj.dao.student_t.insertBoard", student);
    if(insertCount == 1) {
      sqlSession.commit();
    }
    sqlSession.close();
    return insertCount;
  }

}
