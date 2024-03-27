package com.gdu.prj10.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.prj10.utils.MyFileUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {
  
  private final MyFileUtils myFileUtils;

  @Override
  public ResponseEntity<Map<String, Object>> summernoteImageUpload(MultipartHttpServletRequest multipartRequest) {
    
    // 저장 경로
    String uploadPath = myFileUtils.getUploadPath();
    File dir = new File(uploadPath);
    // 없으면 만드세요
    if(!dir.exists()) {
      dir.mkdirs();
    }
    
    // 저장 파일
    MultipartFile multipartFile = multipartRequest.getFile("image");
    // - 저장할 이름 결정
    String filesystemName = myFileUtils.getFilesystemName(multipartFile.getOriginalFilename());
    File file = new File(dir, filesystemName);
    
    // 실제 저장
    try {
      multipartFile.transferTo(file);
    } catch (Exception e) {
      e.printStackTrace();
    }
    // 이 그림이 보고싶으면 이 주소를 입력하세요. view로 이미지의 경로를 넘긴다.
    // - 주소를 보내는게 목적이기 때문에 contextPath 붙여서.
    
    // 반환할 Map
    // view 단으로 보낼 src = "/prj10/upload/2024/03/27/1234567890.jpg" 이건 경로가 아님!!
    // servlet-context.xml 에서 <resources> 태그를 추가한다. 
    // <resources mapping="/upload/**" location="file:///upload/"> - /로 시작하는 root 밑에(C:/upload/...)
    // - 프로젝트 외부의 일반 경로의 경우, file://를 붙인다.
    Map<String, Object> map = Map.of("src", multipartRequest.getContextPath() + uploadPath + "/" + filesystemName); // 이름도 처리해주기
    
    
    
    return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
  }

}
