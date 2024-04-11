package com.gdu.myapp.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.myapp.dto.UploadDto;

public interface UploadService {
  boolean registerUpload(MultipartHttpServletRequest multipartRequest); // 파일 업로드
  void loadUploadList(Model model); // 목록 - request를 model에 실어서 넘김
  void loadUploadList(int uploadNom, Model model);
}
