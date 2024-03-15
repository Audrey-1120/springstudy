package com.gdu.prj04.serivce;

import java.util.List;

import com.gdu.prj04.dto.BoardDto;

public interface BoardService {
  List<BoardDto> getBoardList();
  BoardDto getBoardByNo(int boardNo);

}
