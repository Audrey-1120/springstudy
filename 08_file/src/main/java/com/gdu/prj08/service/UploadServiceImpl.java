package com.gdu.prj08.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.prj08.dao.FileDao;
import com.gdu.prj08.dto.FileDto;
import com.gdu.prj08.utils.MyFileUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UploadServiceImpl implements UploadService {
  
  // 필드, 업로드Dao 넣기
  // 첨부된 것들이 insert 되도록 하면 됨.
  
  private final FileDao fileDao;
  
  // component를 이용한 bean 생성을 했고, 이를 가져오기 위해서 생성자 주입을 사용.
  private final MyFileUtils myFileUtils;

  @Override
  public int upload1(MultipartHttpServletRequest multipartRequest) {
    
    int insertCount = 0;

    // 첨부 파일 목록
    // - 첨부가 여러개이므로 getFiles. -> 반환타입은 List<MultipartFile>. 파일 하나하나가 MultipartFile임.
    // - 괄호 안의 이름은 파라미터 이름.
    List<MultipartFile> files = multipartRequest.getFiles("files");
    
    // 첨부 파일 목록 순회
    for(MultipartFile multipartFile : files) {
      
      // 첨부 파일의 존재 여부 확인
      if(multipartFile == null || !multipartFile.isEmpty()) { // null이거나 비어있으면 안됨.
        
        // 첨부 파일 경로
        String uploadPath = myFileUtils.getUploadPath();
        
        // 첨부 파일 경로 디렉토리 만들기
        File dir = new File(uploadPath);
        if(!dir.exists()) {
          dir.mkdirs();
        }
        
        // 첨부 파일 원래 이름
        String originalFileName = multipartFile.getOriginalFilename();
        
        // 첨부 파일 저장 이름
        String filesystemName = myFileUtils.getFilesystemName(originalFileName);
        
        // 첨부 파일 File 객체
        File file = new File(dir, filesystemName);

        // 저장      
        try {
          multipartFile.transferTo(file);
          
          FileDto fileDto = FileDto.builder()
                               .uploadPath(uploadPath)
                               .originalFileName(originalFileName)
                               .fileSystemName(filesystemName)
                             .build();
          
          insertCount = fileDao.upload2(fileDto);
          
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
    return insertCount; // insertCount가 1이라고 가정!
  }

  @Override
  public Map<String, Object> upload2(MultipartHttpServletRequest multipartRequest) {

    int insertCount = 0;
    
    // 첨부 파일 목록
    List<MultipartFile> files = multipartRequest.getFiles("files");
    
    // 첨부 파일 목록 순회
    for(MultipartFile multipartFile : files) {
      
      // 첨부 파일 존재 여부 확인
      if(multipartFile != null && !multipartFile.isEmpty()) {
        
        // 첨부 파일 경로
        String uploadPath = myFileUtils.getUploadPath();
        
        // 첨부 파일 경로 디렉터리 만들기
        File dir = new File(uploadPath);
        if(!dir.exists()) {
          dir.mkdirs();
        }
        
        // 첨부 파일 원래 이름
        String originalFileName = multipartFile.getOriginalFilename();
        
        // 첨부 파일 저장 이름
        String filesystemName = myFileUtils.getFilesystemName(originalFileName);
        
        // 첨부 파일 File 객체
        File file = new File(dir, filesystemName);
        
        // 저장
        try {          
          multipartFile.transferTo(file);
          
          // 객체 만들기
          FileDto fileDto = FileDto.builder()
                              .uploadPath(uploadPath)
                              .originalFileName(originalFileName)
                              .fileSystemName(filesystemName)
                            .build();
          
          insertCount = fileDao.upload1(fileDto);
          
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
    return Map.of("success", insertCount);
     
  }

  
  
}
