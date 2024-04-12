package com.gdu.myapp.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gdu.myapp.service.UploadService;
import com.gdu.myapp.service.UploadServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class RemoveTempFilesScheduler {
  
  private final UploadService uploadService;

  // 12시 20분에 removeTempFiles 서비스가 동작하는 스케줄러 
  @Scheduled(cron = "0 28 12 * * ?")
  public void execute() {
    uploadService.removeTempFiles();
  }
  
}
