package com.gdu.myapp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.gdu.myapp.dto.UserDto;
import com.gdu.myapp.mapper.UserMapper;

@ExtendWith(SpringExtension.class)

@ContextConfiguration(locations="file:src/main/webapp/WEB-INF/spring/root-context.xml")

class InsertUserTest {
  
  @Autowired
  private UserMapper userMapper;
  
  @Test
  public void test01_insert() {
    assertEquals(1, userMapper.insertUser(new UserDto()));
  }








}
