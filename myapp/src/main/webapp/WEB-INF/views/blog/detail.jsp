<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="contextPath" value="<%=request.getContextPath()%>"/>
<c:set var="dt" value="<%=System.currentTimeMillis()%>"/>

<jsp:include page="../layout/header.jsp">
  <jsp:param value="${blog.blogNo}번 블로그" name="title"/>
</jsp:include>

<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<h1 class="title">블로그 상세화면</h1>

  <div>
    <span>작성자</span>
    <span>${blog.user.email}</span>
  </div>
  
  <div>
    <span>조회수</span>
    <span>${blog.hit}</span>
  </div>
  
  <div>
    <span>제목</span>
    <span>${blog.title}</span>
  </div>
  
  <div>
    <span>내용</span>
    <span>${blog.contents}</span>
  </div>
  
  <div>
    <button type="button" class="btn-modify" data-blog-no="${blog.blogNo}">수정하기</a></button>
  </div>

<hr>

<!-- 댓글 작성창 -->
<form id="frm-comment">
  <textarea id="contents" name="contents"></textarea>
  <input type="hidden" name="blogNo" value="${blog.blogNo}">
  <c:if test="${not empty sessionScope.user}">
    <input type="hidden" name="userNo" value="${sessionScope.user.userNo}">
  </c:if>
  <button id="btn-comment-register">댓글등록</button>  
  <!-- 작성자는 현재 로그인한 사람이기 때문에 sessionScope에서 가져와야 한다. blog가 아니라!  -->
  
</form>

<hr>

<!-- 댓글 목록 -->
<div id="comment-list"></div>
<div id="paging"></div>

<script>

// 로그인 검사
const fnCheckSignin = () => {
  if('${sessionScope.user}' === '') {
    if(confirm('Sign In 이 필요한 기능입니다. Sign In 할까요?')) {
      location.href = '${contextPath}/user/signin.page';
    }
  }
}

// 댓글 등록
const fnRegisterComment = () => {
	$('#btn-comment-register').on('click', (evt) => {
		fnCheckSignin();
	  $.ajax({
		  // 요청
		  type: 'POST',
		  url: '${contextPath}/blog/registerComment.do',
		  data: $('#frm-comment').serialize(),
		  // 응답
		  dataType: 'json',
		  success: (resData) => { // resData = {"insertCount" : 1}
			  if(resData.insertCount === 1) {
				  alert('댓글이 등록되었습니다.');
				  $('contents').val('');
				  fnCommentList();
			  } else {
				  alert('댓글 등록이 실패했습니다.');
			  }
			  
		  },
		  error: (jqXHR) => {
			  alert(jqXHR.statusText + '(' + jzXHR.status + ')');
		  }
	  })
  })
}

// 전역 변수
var page = 1;

// 댓글 목록 가져오기
const fnCommentList = () => {
	$.ajax({
		type: 'GET',
		url: '${contextPath}/blog/comment/list.do',
		data: 'blogNo=${blog.blogNo}&page=' + page,
		dataType: 'json',
		success: (resData) => {  // resData = {"commentList": [], "paging": "< 1 2 3 4 5>"}
			// 기존 목록, 페이징 정보 초기화
		  let commentList = $('#comment-list');
		  let paging = $('#paging');
		  commentList.empty();
		  paging.empty();
		  if(resData.commentList.length === 0) {
			  commentList.append('<div>첫 번째 댓글의 주인공이 되어 보세요</div>');
			  paging.empty();
			  return;
		  }
		  $.each(resData.commentList, (i, comment) => {
			  let str = '';
			  // 댓글은 들여쓰기 (댓글 여는 div)
			  if(comment.depth === 0) {
				  str += '<div>';
			  } else {
				  str += '<div style="padding-left: 32px;">'
			  }
			  
			  if(comment.state === 1) {
  			  // 댓글 내용 표시
  			  str += '<span>'
  			  str += comment.user.email;
  			  str += '(' + moment(comment.createDt).format('YYYY.MM.DD.') + ')';
  			  str += '</span>';
  			  str += '<div>' + comment.contents + '</div>';
  			  // 답글 버튼 (원글에만 답글 버튼이 생성됨)
  			  if(comment.depth === 0) {
  				  str += '<button type="button" class="btn btn-success btn-reply">답글</button>';
  			  }
  			  // 삭제 버튼 (내가 작성한 댓글에만 삭제 버튼이 생성됨)
  			  if(Number('${sessionScope.user.userNo}') === comment.user.userNo) {
  				  str += '<button type="button" class="btn btn-danger btn-remove" data-comment-no="' + comment.commentNo + '">삭제</button>';
  			  }
  			  /********************* 답글 입력 화면 *********************/
  			  if(comment.depth === 0) {
    			  str += '<div>';
    			  str += '  <form class="frm-reply">';
    			  str += '    <input type="hidden" name="groupNo" value="' + comment.groupNo + '">';
    			  str += '    <input type="hidden" name="blogNo" value="${blog.blogNo}">';
    			  // comment 혹은 상세보기 blogNo 둘다 가능
    			  str += '    <input type="hidden" name="userNo" value="${sessionScope.user.userNo}">';
    			  str += '    <textarea name="contents" placeholder="답글 입력"></textarea>';
    			  str += '    <button type="button" class="btn btn-warning btn-register-reply">작성완료</button>';
    			  str += '  </form>';
    			  str += '</div>'
  			  }
  			  /**********************************************************/
  			  // 댓글 닫는 <div>
  			  str += '</div>';
			  } else {
				  str += '<span>'
				  str += '삭제된 댓글입니다.';
				  str += '</span>';
			  }
			  // 목록에 댓글 추가
			  commentList.append(str); 
		  })
		  // 페이징 표시
		  paging.append(resData.paging);
		},
		error: (jqXHR) => {
			alert(jqXHR.statusText + '(' + jqXHR.status + ')');
		}
	})
}

// 페이징
const fnPaging = (p) => {
	page = p;
	fnCommentList();
}

// 대댓글 등록
const fnRegisterReply = () => {
	$(document).on('click', '.btn-register-reply', (evt) => {
		fnCheckSignin();
		$.ajax({
			type: 'POST',
			url: '${contextPath}/blog/comment/registerReply.do',
			data: $(evt.target).closest('.frm-reply').serialize(), 
			dataType: 'json',
			success: (resData) => {
				if(resData.insertReplyCount === 1) {
					alert('답글이 등록되었습니다.');
					$(evt.target).prev().val(''); // button의 형제 요소 -> prev() 이용
					fnCommentList();
				} else {
					alert('답글 등록이 실패했습니다.');
				}
			},
			error: (jqXHR) => {
				alert(jqXHR.statusText + '(' + jqXHR.status + ')');
			}
		})
	})
}

// 댓글 삭제
const fnRemoveComment = () => {
	$(document).on('click', '.btn-remove', (evt) => {
		// dataset에 들어있는 comment_no을 서버로 보낸다.
		$.ajax({
			type: 'POST',
			url: '${contextPath}/blog/removeComment.do',
			data: 'commentNo=' + evt.target.dataset.commentNo,
			dataType: 'json',
			success: (resData) => {
				if(resData.deleteCount === 1) {
					alert('답글이 삭제되었습니다.');
					fnCommentList();
				} else {
					alert('답글 삭제가 실패하였습니다.');
				}
			},
			error: (jqXHR) => {
				alert(jqXHR.statusText + '(' + jqXHR.status + ')');
			}
		})
	})
}

// 게시글 수정
const fnModify = () => {
	$('btn-modify').on('click', (evt) => {
		location.href = '${contextPath}/blog/modifyBlog.do?blogNo=' + evt.target.dataset.blogNo;
		// 해당 게시글의 blogNo값을 보내서 blogDto객체 데이터를 가져와서 write.jsp로 넘긴다.
		
	})
}


$('#contents').on('click', fnCheckSignin);
fnRegisterComment();
fnCommentList();
fnRemoveComment();
fnRegisterReply();




</script>


<%@ include file="../layout/footer.jsp" %>