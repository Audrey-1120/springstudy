package com.gdu.prj05.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.gdu.prj05.dto.ContactDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class contactDaoImpl implements ContactDao {
  
  private final SqlSessionTemplate sqlSessionTemplate; // 모든 메소드에서 쿼리를 실행할때 사용한다.
  
  public final static String NS = "com.gdu.prj05.mybatis.mapper.contact_t.";
  
  // Spring Container에 만들어진 bean을 가져오면 된다.
  // 우리가 선택한 방법 - final + 생성자 주입.

  @Override
  public int registerContact(ContactDto contact) {
    int insertCount = sqlSessionTemplate.insert(NS + "registerContact", contact); 
    // (실행할 쿼리문의 id, 파라미터)
    // 이때 id만 적는게 아니라 무슨 mapper의 무슨 id인지 적어준다. namespace + id.
    // 근데 앞의 부분은 모든 메소드가 다 써야하는데 너무 길어... -> 따로 변수 처리!
    return insertCount;
  }

  @Override
  public int modifyContact(ContactDto contact) {
    int updateCount = sqlSessionTemplate.update(NS + "modifyContact", contact);
    return updateCount;
  }

  @Override
  public int removeContact(int contactNo) {
    int deleteCount = sqlSessionTemplate.delete(NS + "removeContact", contactNo);
    return deleteCount;
  }

  @Override
  public List<ContactDto> getContactList() {
    List<ContactDto> contactList = sqlSessionTemplate.selectList(NS + "getContactList");
    return contactList;
  }

  @Override
  public ContactDto getContactByNo(int contactNo) {
    ContactDto contact = sqlSessionTemplate.selectOne(NS + "getContactByNo", contactNo);
    return contact;
  }

}
