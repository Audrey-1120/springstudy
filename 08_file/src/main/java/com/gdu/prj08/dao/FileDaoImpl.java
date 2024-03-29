package com.gdu.prj08.dao;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.prj08.dto.FileDto;
import com.gdu.prj08.dto.HistoryDto;
import com.gdu.prj08.utils.MyFileUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FileDaoImpl implements FileDao {
  
  private final MyFileUtils myFileUtils;

  private final SqlSessionTemplate sqlSessionTemplate;
  
  
  @Override
  public int insertHistory(HistoryDto history) {
    return sqlSessionTemplate.insert("com.gdu.prj08.mybatis.mapper.file_t.insertHistory", history);
  }

  @Override
  public int insertFile(FileDto file) {
    return sqlSessionTemplate.insert("com.gdu.prj08.mybatis.mapper.file_t.insertFile", file);
  }
  
}
