<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="contextPath" value="<%=request.getContextPath()%>"/>
<c:set var="dt" value="<%=System.currentTimeMillis()%>"/>

<jsp:include page="../layout/header.jsp">
  <jsp:param value="Sign Up" name="title"/>
</jsp:include>

<h1>Sign Up</h1>

<form method="POST"
      action="${contextPath}/user/signup.do"
      id="frm-signup"> <!-- form의 dom요소를 알아내기 위해 id를 지정한다. -->
      
  <div class="mb-3"> <!-- bootstrap : mb-3는 margin을 아래로(bottom) 3 주겠다는 뜻. -->
    <label for="inp-email">아이디</label>
    <input type="text" id="inp-email" name="email" placeholder="example@example.com">
    <button type="button" id="btn-code" class="btn btn-primary">인증코드받기</button>
    <div id="msg-email"></div>
  </div>
  <div class="mb-3">
    <!-- 받은 인증 코드를 입력하는 공간- 컨트롤러로 보낼 필요는 없다.(DB 처리 X) js로 처리할 예정.-->
    <input type="text" id="inp-code" placeholder="인증 코드 입력" disabled> <!-- disabled : 인증하면 그때 입력할 수 있게끔 풀어준다. -->
    <button type="button" id="btn-verify-code" class="btn btn-primary">인증하기</button> 
  </div>
  
  <hr class="my-3"> 

  <div class="row mb-3">
    <label for="inp-pw" class="col-sm-3 col-form-label">비밀번호</label>
    <div class="col-sm-6"><input type="password" id="inp-pw" name="pw" class="form-control"></div>
    <div class="col-sm-3"></div>
    <div class="col-sm-9" id="msg-pw"></div>
  </div>
  <div class="row mb-3">
    <label for="inp-pw2" class="col-sm-3 col-form-label">비밀번호 확인</label>
    <div class="col-sm-6"><input type="password" id="inp-pw2" class="form-control"></div>
    <div class="col-sm-3"></div>
    <div class="col-sm-9" id="msg-pw2"></div>
  </div>
  
  <hr class="my-3">
  
  <div class="row mb-3">
    <label for="inp-name" class="col-sm-3 col-form-label">이름</label>
    <div class="col-sm-9"><input type="text" name="name" id="inp-name" class="form-control"></div>
    <div class="col-sm-3"></div>
    <div class="col-sm-9" id="msg-name"></div>
  </div>

  <div class="row mb-3">
    <label for="inp-mobile" class="col-sm-3 col-form-label">휴대전화번호</label>
    <div class="col-sm-9"><input type="text" name="mobile" id="inp-mobile" class="form-control"></div>
    <div class="col-sm-3"></div>
    <div class="col-sm-9" id="msg-mobile"></div>
  </div>

  <div class="row mb-3">
    <label class="col-sm-3 form-label">성별</label>
    <div class="col-sm-3">
      <input type="radio" name="gender" value="none" id="rdo-none" class="form-check-input" checked>
      <label class="form-check-label" for="rdo-none">선택안함</label>
    </div>
    <div class="col-sm-3">
      <input type="radio" name="gender" value="man" id="rdo-man" class="form-check-input">
      <label class="form-check-label" for="rdo-man">남자</label>
    </div>
    <div class="col-sm-3">
      <input type="radio" name="gender" value="woman" id="rdo-woman" class="form-check-input">
      <label class="form-check-label" for="rdo-woman">여자</label>
    </div>
  </div>
  
  <hr class="my-3">
  
  <div class="form-check mb-3">
    <input type="checkbox" class="form-check-input" id="chk-service">
    <label class="form-check-label" for="chk-service">서비스 이용약관 동의(필수)</label>
  </div>
  <div>
    <textarea rows="5" class="form-control">본 약관은 ...</textarea>
  </div>
  
  <div class="form-check mb-3">
    <input type="checkbox" name="event" class="form-check-input" id="chk-event">
    <label class="form-check-label" for="chk-event">
      이벤트 알림 동의(선택)
    </label>
  </div>
  <div>
    <textarea rows="5" class="form-control">본 약관은 ...</textarea>
  </div>
  
  <hr class="my-3">
  
  <div class="m-3">
    <button type="submit" id="btn-signup" class="btn btn-primary">가입하기</button>
  </div>
  
</form>

<!-- resources에 있는 건 contextPath로 들어감. -->
<script src="${contextPath}/resources/js/signup.js?dt=${dt}"></script>

<%@ include file="../layout/footer.jsp" %>