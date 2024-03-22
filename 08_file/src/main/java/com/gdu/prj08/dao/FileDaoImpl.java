package com.gdu.prj08.dao;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.prj08.dto.FileDto;
import com.gdu.prj08.utils.MyFileUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FileDaoImpl implements FileDao {
  
  private final MyFileUtils myFileUtils;

  private final SqlSessionTemplate sqlSessionTemplate;
  
  public final static String NS = "com.gdu.prj08.mybatis.mapper.file_t.";
  
  @Override
  public int upload1(FileDto fileDto) {
    int insertCount = sqlSessionTemplate.insert(NS + "registerFile", fileDto);
    return insertCount;
  }

  @Override
  public int upload2(FileDto fileDto) {
    int insertCount = sqlSessionTemplate.insert(NS + "registerFile", fileDto);
    return insertCount;
  }
  
}
