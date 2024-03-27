package com.gdu.prj10.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class MyFileUtils {

  // 날짜 기준으로 디렉터리 만들기(업로드 할때마다 디렉터리 생성)
  
  // 현재 날짜 - 필드로 선언해놓기 
  public static final LocalDate TODAY = LocalDate.now(); 
  
  // 업로드 경로 반환 - 원래는 \이나 윈도우도 이제 /를 지원한다!
  public String getUploadPath() {
    return "/upload" + DateTimeFormatter.ofPattern("/yyyy/MM/dd").format(TODAY);
    // C드라이브가 root이므로, 그 아래로 upload 폴더, 밑에 yyyy 밑에 MM 밑에 dd
  }

  // 저장될 파일명 반환
  public String getFilesystemName(String originalFilename) { // 원래 이름을 받아와서 변경
    // 파일명.확장자 - 파일명 부분은 저장할때 사용 X 확장자만 살려서 사용.
    // 일부 확장자의 경우, 확장자에 .이 포함됨. .tar.gz - 이 부분도 처리를 해줘야 함.
    String extName = null;
    if(originalFilename.endsWith(".tar.gz")) {  // 확장자에 마침표가 포함된 경우
      extName = ".tar.gz";
    } else {
      extName = originalFilename.substring(originalFilename.lastIndexOf(".")); // 마지막 .부터 끝까지 추출.
      // 원래 이름, 저장 이름 둘다 DB에 저장하면 됨.
    }
    return UUID.randomUUID().toString().replace("-", "") + extName;
    // charSequence - String이라고 생각하면 됨.
  }
}
