package com.gdu.prj09.utils;

import lombok.Data;

@Data
public class MyPageUtils {
  

  private int total; // 전체 게시글 개수
  // 전체 member의 개수 - DB에서 구해오기
  private int display; // 한 화면에 보여줄 게시글 개수
  // 파라미터로 전달 - 전달안되면 기본값 세팅
  private int page; // 현재 페이지
  // 파라미터로 전달 - 전달안되면 기본값 세팅
  
  // 위의 total, display, page로 begin, end 구함.
  private int begin;
  private int end; // begin과 end는 map으로 만들어서 전달(전체보기)
  
  private int pagePerBlock = 10;
  private int totalPage;
  private int beginPage;
  private int endPage;
  
  public void setPaging(int total, int display, int page) {
    
    // total, display, page값 받아와서 채워주기
    this.total = total;
    this.display = display;
    this.page = page;
 
    // 1 - 1 20
    // 2 - 21 40
    // 3 - 41 60
    begin = (page - 1) * display + 1;
    end = begin + display - 1;
    
    /*
     * total display  totalPage
     * 1000  20       1000 / 20 = 50.0 = 50
     * 1002  20       1001 / 20 = 50.x = 51 (total/display값을 올림처리)
     */
    
    totalPage = (int)Math.ceil((double)total / display);
    
    /*
     * 1 2 3 4 5 6 7 8 9 10 - 1 block
     * 11 12 13 14 ... 20 - 2 block
     */
    beginPage = ((page - 1) / pagePerBlock) * pagePerBlock + 1;
    endPage = Math.min(totalPage, beginPage + pagePerBlock - 1);
    
  }
  
  public String getAsyncPaging() {
    
    StringBuilder builder = new StringBuilder();
    
    // <
    if(beginPage == 1) {
      builder.append("<a>&lt;</a>");
    } else {
      builder.append("<a href=\"javascript:fnPaging(" + (beginPage - 1) + ")\">&lt;</a>");
    }
    
    // 1 2 3 4 5 6 7 8 9 10
    for(int p = beginPage; p <= endPage; p++) {
      if(p == page) {
        builder.append("<a>" + p + "</a>");
      } else {        
        builder.append("<a href=\"javascript:fnPaging(" + p + ")\">" + p + "</a>");
      }
    }
    
    // >
    if(endPage == totalPage) {
      builder.append("<a>&gt;</a>");
    } else {   
      builder.append("<a href=\"javascript:fnPaging(" + (endPage + 1) + ")\">&gt;</a>");
    }
    
    return builder.toString();
    
  }
  

}
