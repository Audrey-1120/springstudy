package com.gdu.prj05;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gdu.prj05.dao.ContactDao;
import com.gdu.prj05.dto.ContactDto;

import lombok.RequiredArgsConstructor;

/* 1. JUnit4를 이용한다. */
@RunWith(SpringJUnit4ClassRunner.class)


/*
 * 2. ContactDaoImpl 클래스의 bean 생성 방법을 작성한다.
 *  1) <bean>      locations="file:src/main/webapp/WEB-INF/spring/root-context.xml"
 *  - root-context.xml bean의 경로를 작성한다.
 *  
 *  2) @Bean       classes=AppConig.class
 *  - @Bean을 잡는 위치는 AppConfig임.(@Configuration붙여주고.. @Bean)
 *  
 *  3) @Component  locations="file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"
 *  - Component scan이 등록되어있는 servlet-context 등록해주면 됨.  
 */

@ContextConfiguration(locations="file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml")

public class ContactUnitTest {
  
  private ContactDao contactDao;

  @Autowired
  public void setContactDao(ContactDao contactDao) {
    this.contactDao = contactDao;
  }

  @Test
  public void 등록테스트() {
    ContactDto contact = ContactDto.builder()
                              .name("테스트이름")
                              .mobile("테스트모바일")
                              .email("테스트이메일")
                              .address("주소")
                            .build();
    int insertCount = contactDao.registerContact(contact);
    assertEquals(1, insertCount);
  }
  
}