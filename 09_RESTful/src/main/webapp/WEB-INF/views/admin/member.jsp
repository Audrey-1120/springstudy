<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
</head>
<body>

  <div>
    <h1>회원관리</h1>
    <input type="hidden" id="member-no">
    <!-- 평소에는 비어있다가 상세보기를 하게 되면 채워짐. -->
    <div>
      <label for="email">이메일</label>
      <input type="text" id="email">
    </div>
    <div>
      <label for="name">이름</label>
      <input type="text" id="name">
    </div>
    <div>
      <input type="radio" name="gender" id="none" value="none" checked>
      <label for="none">선택안함</label>
      <input type="radio" name="gender" id="man" value="man">
      <label for="man">남자</label>
      <input type="radio" name="gender" id="woman" value="woman">
      <label for="woman">여자</label>
    </div>
    <div>
      <input type="text" id="zonecode" onclick="execDaumPostcode()" placeholder="우편번호" readonly>
      <input type="button" onclick="execDaumPostcode()" value="우편번호 찾기"><br>
      <input type="text" id="address" placeholder="주소" readonly><br>
      <input type="text" id="detailAddress" placeholder="상세주소">
      <input type="text" id="extraAddress" placeholder="참고항목">
      <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
      <script>
        function execDaumPostcode() {
          new daum.Postcode({
            oncomplete: function(data) {
              // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
              // 각 주소의 노출 규칙에 따라 주소를 조합한다.
              // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
              var addr = ''; // 주소 변수
              var extraAddr = ''; // 참고항목 변수
              // 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
              if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
              } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
              }
              // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
              if(data.userSelectedType === 'R'){
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                  extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                  extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraAddr !== ''){
                  extraAddr = ' (' + extraAddr + ')';
                }
                // 조합된 참고항목을 해당 필드에 넣는다.
                document.getElementById('extraAddress').value = extraAddr;
              } else {
                document.getElementById('extraAddress').value = '';
              }
              // 우편번호와 주소 정보를 해당 필드에 넣는다.
              document.getElementById('zonecode').value = data.zonecode;
              document.getElementById('address').value = addr;
              // 커서를 상세주소 필드로 이동한다.
              document.getElementById('detailAddress').focus();
            }
          }).open();
        }
      </script>
    </div>
    <div>
      <button id="btn-init">초기화</button>
      <button id="btn-register">등록</button>
      <button id="btn-modify">수정</button>
      <button id="btn-remove">삭제</button>      
    </div>
    
    <hr>
    
    <div>
      <div id="total"></div>
      <button type="button" id="btn-select-remove">선택삭제</button>
      <div><select id="display"><option>20</option><option>50</option><option>100</option></select></div>
      <table border="1">
        <thead>
          <tr>
            <td>선택</td>
            <td>이메일</td>
            <td>이름</td>
            <td>성별</td>
            <td>버튼</td>
          </tr>
        </thead>
        <tbody id="members"></tbody>
        <tfoot>
          <tr>
            <td colspan="5" id="paging"></td>
          </tr>
        </tfoot>
      </table>
    </div>
    
  </div>
  
  <script src="${contextPath}/resources/js/member.js?dt=<%=System.currentTimeMillis()%>"></script>
  <script>
  
// jQuery 객체 선언
  
// 함수 표현식 (함수 만들기)
const fnRemoveMember = () => {
	if(!confirm('삭제할까요?')){
		return;
	}
	$.ajax({ // GET 방식과 동일하게 돌아간다.
		type: 'DELETE',
		url: fnGetContextPath() + '/member/' + jqMemberNo.val(),
		dataType: 'json'
	}).done(resData=>{ // {"deleteCount": 1}
		if(resData.deleteCount === 1) {
			alert('회원 정보가 삭제되었습니다.');
			fnInit(); // 입력란 초기화
			vPage = 1;
			fnGetMemberList(); // 목록 새로고침
		} else {
			alert('회원 정보가 삭제되지 않았습니다.');
		}
	}).fail(jqXHR=>{
		alert(jqXHR.statusText + '(' + jqXHR.status + ')');
	})
}

const fnRemoveMembers = () => {
	// 체크된 요소를 배열에 저장하기
	let arr = [];
	$.each($('.chk-member'), (i, chk) => {  // jqCheckMember은 배열로 인식된다. 여기에서 하나씩 빼서 chk에 넣으면 chk는 더이상 jQuery 객체가 아님. js 객체임.
		if($(chk).is(':checked')) { // js객체가 된 chk를 다시 jQuery객체로 래핑함. -> jQuery에 check 여부 메소드 있기 때문에
			arr.push(chk.value);  // chk는 js 객체이므로 val? 사용안됨! js객체인 value 이용해야한다.
			  // 이제 체크된 요소가 arr에 모인다. 
		} 
	})
	// 체크된 요소가 없으면? 함수 종료!
  if(arr.length === 0) { // 체크된게 없으므로 배열의 길이는 당연히 0!
	  alert('선택된 회원 정보가 없습니다.');
	  return;
  } 
	// 삭제 확인
	if(!confirm('선택된 회원 정보를 모두 삭제할까요?')) {
		return;
	}
	// 삭제
	$.ajax({
		type: 'DELETE',
		url: fnGetContextPath() + '/members/' + arr.join(','), // join은 배열 요소를 자동으로 알아서 꺼내서 붙여준다.
		dataType: 'json', // 받아오기
		success: (resData)=>{  // {"deleteCount": 3}
			if(resData.deleteCount === arr.length) { // 체크된 요소가 3개이면 지워지는 것도 3임.
				alert('선택된 회원 정보가 삭제되었습니다.');
			  vPage = 1;
				fnGetMemberList();
			} else {
				alert('선택된 회원 정보가 삭제되지 않았습니다.');
			}
		},
		error: (jqXHR)=> {
			alert(jqXHR.statusText + '(' + jqXHR.status + ')');
		}
	})
  
}

// 함수 호출 및 이벤트
jqBtnRemove.on('click', fnRemoveMember);
jqBtnSelectRemove.on('click', fnRemoveMembers);

  </script>

</body>
</html>