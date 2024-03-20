package com.gdu.prj06;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gdu.prj06.dao.ContactDao;
import com.gdu.prj06.dto.ContactDto;
import com.gdu.prj06.service.ContactService;

/* 1. JUnit4 를 이용한다. */
@RunWith(SpringJUnit4ClassRunner.class)

// ContactService 타입의 ContactServiceImpl bean이 등록된 파일
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/root-context.xml"})

public class ContactTxTest {

  @Autowired
  private ContactService contactService;
  
  @Test
  public void 트랜잭션_테스트() {
    contactService.txTest();
  }

  
  
}