package com.gdu.prj04.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdu.prj04.dto.BoardDto;
import com.gdu.prj04.serivce.BoardService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/ajax1") // /ajax1으로 시작하는 모든 요청 담당하는 컨트롤러.
@Controller
@RequiredArgsConstructor
public class BoardController1 {

  private final BoardService boardService;
  
  @ResponseBody // 반환 값은 jsp 의 이름이 아니고 어떤 데이터이다. (비동기 작업에서 꼭 필요한 annotation)
  @GetMapping(value="/list.do", produces="application/json") // produces : 응답 데이터 타입 (content-Type)
  public List<BoardDto> list(){  // jackson 라이브러리가 List<BoardDto> 를 JSON 데이터로 변환한다.
    return boardService.getBoardList();
    // return 이 있을 때 view resolver가 이 값 뒤에 .jsp 붙인다. -> ??? 개입하지말라고 얘기해야함.
    // 내가 반환하는 건 jsp가 아니고 다른 데이터니까 개입하지마. -> @ResponseBody
  }
  
  @ResponseBody
  @GetMapping(value="/detail.do", produces="application/json")
  public BoardDto detail(int boardNo) { // @RequestParam은 생략 가능
    return boardService.getBoardByNo(boardNo);
  }
  
}
