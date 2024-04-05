package com.gdu.myapp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.gdu.myapp.dto.BbsDto;
import com.gdu.myapp.dto.UserDto;
import com.gdu.myapp.mapper.BbsMapper;
import com.gdu.myapp.utils.MyPageUtils;
import com.gdu.myapp.utils.MySecurityUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BbsServiceImpl implements BbsService {

  private final BbsMapper bbsMapper;
  private final MyPageUtils myPageUtils;
  
  @Override
  public int registerBbs(HttpServletRequest request) {
    
    String contents = MySecurityUtils.getPreventXss(request.getParameter("contents"));
    
    int userNo = Integer.parseInt(request.getParameter("userNo"));
    
    UserDto user = new UserDto();
    user.setUserNo(userNo);
    
    BbsDto bbs = BbsDto.builder()
                    .contents(contents)
                    .user(user)
                  .build();
    return bbsMapper.insertBbs(bbs);
  }

  @Override
  public void loadBbsList(HttpServletRequest request, Model model) {

    int total = bbsMapper.getBbsCount();
    
    int display = 20;
    
    Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
    int page = Integer.parseInt(opt.orElse("1"));
    
    myPageUtils.setPaging(total, display, page);
    
    Map<String, Object> map = Map.of("begin", myPageUtils.getBegin(), "end", myPageUtils.getEnd());
    
    List<BbsDto> bbsList = bbsMapper.getBbsList(map);
    
    model.addAttribute("bbsList", bbsList);
    model.addAttribute("paging", myPageUtils.getPaging(request.getContextPath() + "/bbs/list.do"
                                                     , null
                                                     , display));
    
  }

  @Override
  public int registerReply(HttpServletRequest request) {
    
    // 요청 파라미터
    // 답글 정보 : userNo, contents
    // 원글 정보 : depth, groupNo, groupOrder
    int userNo = Integer.parseInt(request.getParameter("userNo"));
    String contents = MySecurityUtils.getPreventXss(request.getParameter("contents"));
    int depth = Integer.parseInt(request.getParameter("depth"));
    int groupNo = Integer.parseInt(request.getParameter("groupNo"));
    int groupOrder = Integer.parseInt(request.getParameter("groupOrder"));
    
    // 원글 BbsDto 객체 생성
    BbsDto bbs = BbsDto.builder()
                    .depth(depth)
                    .groupNo(groupNo)
                    .groupOrder(groupOrder)
                  .build();
    
    // 기존 답글들의 groupOrder 업데이트
    bbsMapper.updateGroupOrder(bbs);
    
    // 답글 BbsDto 객체 생성
    UserDto user = new UserDto();
    user.setUserNo(userNo);
    BbsDto reply = BbsDto.builder()
                        .user(user)
                        .contents(contents)
                        .depth(depth + 1)
                        .groupNo(groupNo)
                        .groupOrder(groupOrder + 1)
                      .build();
    
    // 새 답글의 추가
    return bbsMapper.insertReply(reply);
    
  }

  @Override
  public int removeBbs(int bbsNo) {
    return bbsMapper.removeBbs(bbsNo);
  }

  @Override
  public void loadBbsSearchList(HttpServletRequest request, Model model) {
    
    // 요청 파라미터
    String column = request.getParameter("column");
    String query = request.getParameter("query");
    
    // 검색 데이터 개수를 구할 때 사용할 Map 생성
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("column", column);
    map.put("query", query);
    
    // 검색 데이터 개수 구하기
    int total = bbsMapper.getSearchCount(map);
    
    // 한 페이지에 표시할 검색 데이터 개수
    int display = 20;
    
    // 현재 페이지 번호
    Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
    int page = Integer.parseInt(opt.orElse("1")); // 검색 결과 1페이지 보여주세요.
    
    // 페이징 처리에 필요한 처리
    myPageUtils.setPaging(total, display, page);
    
    // 검색 목록 가져오기 위해서 기존 Map에 begin와 end를 추가
    map.put("begin", myPageUtils.getBegin());
    map.put("end", myPageUtils.getEnd());
    
    // 검색 목록 가져오기
    List<BbsDto> bbsList = bbsMapper.getSearchList(map);
    
    // view로 전달할 데이터
    model.addAttribute("beginNo", total - (page - 1) * display);
    model.addAttribute("bbsList", bbsList);
    model.addAttribute("paging", myPageUtils.getPaging(request.getContextPath() + "/bbs/search.do"
                                                     , ""
                                                     , display
                                                     , "column=" + column + "&query=" + query));
    // sort는 null 처리, 검색 결과를 가져오는 것이기 때문에 list.do가 아니라 search.do로 보내야 함.
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /*
    Map<String, Object> map = Map.of("column", request.getParameter("column")
                                   , "query", request.getParameter("query"));
    
    int total = bbsMapper.getSearchCount(map);
    
    int display = 20;
    
    Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
    int page = Integer.parseInt(opt.orElse("1"));
    
    myPageUtils.setPaging(total, display, page);
    
    Map<String, Object> map2 = Map.of("column", request.getParameter("column")
                                   , "query", request.getParameter("query")
                                   , "begin", myPageUtils.getBegin()
                                   , "end", myPageUtils.getEnd());
    
    List<BbsDto> bbsList = bbsMapper.getBbsList(map2);
    
    model.addAttribute("beginNo", total - (page - 1) * display);
    model.addAttribute("bbsSearchList", bbsList);
    model.addAttribute("paging", myPageUtils.getPaging(request.getContextPath() + "/bbs/list.do"
                                                     , null
                                                     , display));
    */
    
  }

}