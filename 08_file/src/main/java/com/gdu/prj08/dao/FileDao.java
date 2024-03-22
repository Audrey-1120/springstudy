package com.gdu.prj08.dao;

import java.util.Map;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.prj08.dto.FileDto;

public interface FileDao {
  
  int upload1(FileDto fileDto);
  int upload2(FileDto fileDto);
  
  
  
  

}
