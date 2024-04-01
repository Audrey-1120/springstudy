<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<!-- include libraries(jquery, bootstrap) -->
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<style>
  @import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css')
  @import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&display=swap')
  *{
    font-family: "Noto Sans KR", sans-serif;
  }
</style>

</head>
<body>

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
    
  </form>
  
<script>
const fnGetContextPath = ()=>{
  const host = location.host;  /* localhost:8080 */
  const url = location.href;   /* http://localhost:8080/mvc/getDate.do */
  const begin = url.indexOf(host) + host.length;
  const end = url.indexOf('/', begin + 1);
  return url.substring(begin, end);
}

const fnCheckEmail =()=> {
	
	  
	  /*
	    new Promise((resolve, reject) => {
	      $.ajax({
	        url: '이메일중복체크요청'
	      })
	      .done(resData => {
	        if(resData.enableEmail){
	          resolve();
	        } else {
	          reject();
	        }
	      })
	    })
	    .then(() => {
	      $.ajax({
	        url: '인증코드전송요청'
	      })
	      .done(resData => {
	        if(resData.code === 인증코드입력값)
	      })
	    })
	    .catch(() => {
	      
	    })
	  */
	  
	  /*
	    fetch('이메일중복체크요청', {})
	    .then(response=>response.json())
	    .then(resData=>{
	      if(resData.enableEmail){
	        fetch('인증코드전송요청', {})
	        .then(response=>response.json())
	        .then(resData=>{  // {"code": "123asb"}
	          if(resData.code === 인증코드입력값)
	        })
	      }
	    })
	  */	
	// 이메일 입력 + 인증코드 보내기
	let inpEmail = document.getElementById('inp-email');
	let regEmail = /^[A-Za-z0-9-_]{2,}@[A-Za-z0-9]+(\.[A-Za-z]{2,6}){1,2}$/;
	// [] : 이 안에 들어간건 한글자. A-Z 또는, a-z 또는 0-9 또는 - 또는 _ 중 한글자.
	// {2,} : 최소 두 글자 이상.
	// 이메일 골뱅이
	// [A-Za-z0-9]{2,} : naver, gmail이 들어가는 부분.
	// (\.[A-Za-z]{2,6}){1,2} : ()안이 1 또는 2개가 나옴.
	 // \.은 마침표. 영문은 2글자 혹은 6글자.
	 // [A-Za-z]{2,6} : co 혹은 com 같은거. 2번 나올수도 있으면 .co.kr 같은거. 1번 나오면 .com
	 
	 // 정규식 조건 충족 X시
	 if(!regEmail.test(inpEmail.value)) {
		 alert('이메일 형식이 올바르지 않습니다.');
		 return;
	 }
	 
	 // 이메일 중복 체크
	 fetch(fnGetContextPath() + '/user/checkEmail.do', {
		 // 이곳은 옵션..
		 method: 'POST',
		 // header(요청헤더) 이것도 객체로 전달 가능
		 headers: {
			 'Content-Type': 'application/json'
		 },
		 body: JSON.stringify({
			 'email': inpEmail.value
		 })
	 })
	 .then(response=> response.json())   // .then( (response) => { return response.json(); } 
	 .then(resData => {  // 늦게 오더라도 기다렸다가 동작한다. 그래야 순서대로 동작하기 때문.
		 if(resData.enableEmail) {
			fetch(fnGetContextPath() + '/user/sendCode.do', {
			  method: 'POST',
			   headers: {
			    'Content-Type': 'application/json'
			   },
			   body: JSON.stringify({
			    'email': inpEmail.value
			   })
			 })
			 .then(response=>response.json())
			 .then(resData=> {  // resData = {"code": "123qaz"}
				 let inpCode = document.getElementById('inp-code');
				 let btnVerifyCode = document.getElementById('btn-verify-code');
				 alert(inpEmail.value + '로 인증코드를 전송했습니다.');
				 inpCode.disabled = false; // 인증코드 전송 버튼을 누르고 난뒤는 입력할 수 있게 함.
				 btnVerifyCode.addEventListener('click', (evt) => {
					 if(resData.code === inpCode.value) {
						 alert('인증되었습니다.');
					 } else {
						 alert('인증되지 않았습니다.');
					 }
				 })  
			 })
		 } else {
			 document.getElementById('msg-email').innerHTML = '이미 사용 중인 이메일입니다.';
			 return;
		 }
	 })
}

document.getElementById('btn-code').addEventListener('click', fnCheckEmail);

</script>

  

</body>
</html>