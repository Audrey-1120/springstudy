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

<h1 class="title">업로드 상세화면</h1>

<div>
  <span>작성자</span>
  <span>${upload.user.email}</span>
</div>

<div>
  <span>제목</span>
  <span>${upload.title}</span>
</div>

<div>
  <span>내용</span>
  <span>${upload.contents}</span>
</div>

<div>
  <span>작성일자</span>
  <span>${upload.createDt}</span>
</div>

<div>
  <span>최종수정일</span>
  <span>${upload.modifyDt}</span>
</div>

<div>
  <c:if test="${not empty sessionScope.user}">  
    <c:if test="${sessionScope.user.userNo == upload.user.userNo}">
      <form id="frm-btn" method="POST">  
        <input type="hidden" name="uploadNo" value="${upload.uploadNo}">
        <button type="button" id="btn-edit" class="btn btn-warning btn-sm">편집</button>
        <button type="button" id="btn-remove" class="btn btn-danger btn-sm">삭제</button>
      </form>
    </c:if>
  </c:if>
</div>

<hr>

<!-- 첨부 목록 -->


<script>


</script>

<%@ include file="../layout/footer.jsp" %>