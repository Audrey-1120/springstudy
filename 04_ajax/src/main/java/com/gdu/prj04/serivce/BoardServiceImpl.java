package com.gdu.prj04.serivce;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gdu.prj04.dao.BoardDao;
import com.gdu.prj04.dto.BoardDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
// final 형태의 주입은 requiredArgsConstructor를 사용한다.    
  
  // 서비스가 사용하는건? Dao! - DB에 접근해야하면 dao에게 요청해서 처리 위임.
  private final BoardDao boardDao;

  @Override
  public List<BoardDto> getBoardList() {
    return boardDao.getBoardList();
  }

  @Override
  public BoardDto getBoardByNo(int boardNo) {
    return boardDao.getBoardByNo(boardNo);
  }

}
