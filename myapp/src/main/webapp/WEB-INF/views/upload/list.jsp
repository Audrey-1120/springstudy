<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="contextPath" value="<%=request.getContextPath()%>"/>
<c:set var="dt" value="<%=System.currentTimeMillis()%>"/>

<jsp:include page="../layout/header.jsp"/>


<h1 class="title">업로드 목록</h1>

<a href="${contextPath}/upload/write.page">업로드작성</a>

<div>
  <div>
    <input type="radio" name="sort" value="DESC" id="descending" checked>
    <label for="descending">내림차순</label>
    <input type="radio" name="sort" value="ASC" id="ascending">
    <label for="ascending">오림차순</label>
  </div>
</div>

<div>
  <select id="display" name="display">
    <option>20</option>
    <option>30</option>
    <option>40</option>
  </select>

</div>
  <table class="table align-middle">
    <thead>
      <tr>
        <td>순번</td>
        <td>제목</td>
        <td>작성자</td>
        <td>첨부개수</td>
      </tr>
    </thead>
    <tbody>
      <!-- upload라는 이름 붙이고 vs라는 인덱스 사용 -->
      <c:forEach items="${uploadList}" var="upload" varStatus="vs">
        <tr>
          <!-- beginNo가 100부터 99, 98.. 이렇게 가면 beginNo - index이다. -->
          <!-- ${uploadNo}를 사용하는 것은 좋지 않다. 중간에 삭제되면 번호가 끊길 수도 있기 때문. -->
          <td>${beginNo - vs.index}</td>
          <td>
            <a href="${contextPath}/upload/detail.do?uploadNo=${upload.uploadNo}">${upload.title}</a>
          </td>
          <td>${upload.user.email}</td>
          <td>${upload.attachCount}</td>
        </tr>
      </c:forEach>
    </tbody>
    <tfoot>
      <tr>
        <td colspan="4">${paging}</td>
      </tr>
    </tfoot>
  </table>

<script>

	
const fnDisplay= () => {
  document.getElementById('display').value = '${display}';
  document.getElementById('display').addEventListener('change', (evt) => {
	  location.href='${contextPath}/upload/list.do?page=1&sort=${sort}&display=' + evt.target.value;
  })// 위에서 evt.target은 select임.
	
}

const fnSort = () => {
	$(':radio[value=${sort}]').prop('checked', true);
	$(':radio').on('click', (evt)=> {
	  location.href='${contextPath}/upload/list.do?page=${page}&sort=' + evt.target.value + '&display=${display}';
	})
}

const fnUploadInserted = () => {
	const inserted = '${inserted}';
	if(inserted !== '') {
		if(inserted === 'true') {
			alert('업로드 되었습니다.');
		} else {
			alert('업로드가 실패했습니다.');
		}
	}
}

const getUploadByNo = () => {
	document.getElementById('uploadTitle').addEventListener('click', (evt) => {
		console.log(evt.target.dataset.uploadNo);
		// location.href='${contextPath}/upload/detail.do?uploadNo=' + evt.target.dataset.uploadNo;
	})
}

fnDisplay();
fnSort();
fnUploadInserted();
getUploadByNo();

</script>





<%@ include file="../layout/footer.jsp" %>