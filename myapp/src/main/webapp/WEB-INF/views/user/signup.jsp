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
  
  
  
<script>

var emailCheck = false; // 초기값은 false.
var passwordCheck = false;
var passwordConfirm = false;
var nameCheck = false;
var mobileCheck = false;

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
	    .then(resData=>{  // {"enableEmail": true}
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
	 
	 // 정규식 조건 충족 X시
	 if(!regEmail.test(inpEmail.value)) {
		 alert('이메일 형식이 올바르지 않습니다.');
		 emailCheck = false;
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
	 .then(resData => { 
		 if(resData.enableEmail) {
	 	  document.getElementById('msg-email').innerHTML = ''; // 해당 메시지 지워주는 작업.
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
				 inpCode.disabled = false;
				 btnVerifyCode.addEventListener('click', (evt) => {
					 if(resData.code === inpCode.value) {
						 alert('인증되었습니다.');
						 emailCheck = true;
					 } else {
						 alert('인증되지 않았습니다.');
						 emailCheck = false;
					 }
				 })  
			 })
		 } else {
			 document.getElementById('msg-email').innerHTML = '이미 사용 중인 이메일입니다.';
			 emailCheck = false;
			 return;
		 }
	 })
}

// 이름 글자수 검사
const fnCheckName = () => {
	let inpName = document.getElementById('inp-name');
	let name = inpName.value
	let totalByte = 0;
	for(let i = 0; i < name.length; i++) {
		if(name.charCodeAt(i) > 127) {  // 코드값이 127 초과이면 한 글자당 2byte 처리.
			totalByte += 2;
		} else {
			totalByte++;
		}
	}
	nameCheck = (totalByte <= 100);
	let msgName = document.getElementById('msg-name');
	if(!nameCheck) {
		msgName.innerHTML = '이름은 100 바이트를 초과할 수 없습니다.';
	} else {
		msgName.innerHTML = '';
	}
}


// 전화번호 정규식 검사
const fnCheckMobile = () => {
	let inpMobile = document.getElementById('inp-mobile');
	let mobile = inpMobile.value;
	// 숫자가 아닌 것을 찾아서 빈문자열로 대체한다.
	mobile = mobile.replaceAll(/[^0-9]/g, '');
	mobileCheck = /^010[0-9]{8}$/.test(mobile);
	let msgMobile = document.getElementById('msg-mobile');
	if(mobileCheck) {
	  msgMobile.innerHTML = '';	
	} else {
		msgMobile.innerHTML = '휴대전화를 확인하세요.';
	}
}

// 서비스 동의 여부
const fnCheckAgree = () => {
	let chkService = document.getElementById('chk-service');
	agreeCheck = chkService.checked;
}


// 회원가입
const fnSignup = () => {
	document.getElementById('frm-signup').addEventListener('submit', (evt)=>{
		fnCheckAgree();
		if(!emailCheck) {
			alert('이메일을 확인하세요.');
			evt.preventDefault(); // submit 방지.
			return; // function의 종료.
		} else if(!passwordCheck || !passwordConfirm) {
      alert('비밀번호를 확인하세요.');
      evt.preventDefault(); 
      return;
		} else if(!nameCheck) {
      alert('이름을 확인하세요.');
      evt.preventDefault();
      return; 
    } else if(!mobileCheck) {
      alert('전화번호를 확인하세요.');
      evt.preventDefault();
      return; 
    } else if(!agreeCheck) {
	    alert('서비스 약관에 동의해야 서비스를 이용할 수 있습니다.');
	    evt.preventDefault();
	    return;
  	  }
	})
}

// 비밀번호 체크 
const fnCheckPassword = () => {
	// 비밀번호 4 ~ 12자, 영문/숫자/특수문자 중 2개 이상 포함
	let inpPw = document.getElementById('inp-pw');
	
	// validCount가 2개 이상인지 체크한다.
	let validCount = /[A-Za-z]{}/.test(inpPw.value)    // 영문 포함되어 있으면 true (JavaScript에서 true 는 숫자 1 같다.)
	               + /[0-9]/.test(inpPw.value)         // 숫자 포함되어 있으면 true
	               + /[^A-Za-z0-9]/.test(inpPw.value)  // 영문/숫자가 아니면 true
	               
  let passwordLength = inpPw.value.length;
  passwordCheck = passwordLength >= 4
               && passwordLength <= 12
               && validCount >= 2;
  let msgPw = document.getElementById('msg-pw');
  if(passwordCheck) {
	  msgPw.innerHTML = '사용 가능한 비밀번호입니다.';
  } else {
	  msgPw.innerHTML = '비밀번호 4 ~ 12자, 영문/숫자/특수문자 중 2개 이상 포함';
  }
}

// 비밀번호 확인
const fnConfirmPassword = () => {
	let inpPw = document.getElementById('inp-pw');
	let inpPw2 = document.getElementById('inp-pw2');
	passwordConfirm = (inpPw.value !== '' /* 입력은 되어 있어야 한다.*/ )
	               && (inpPw.value === inpPw2.value)
  let msgPw2 = document.getElementById('msg-pw2');
	if(passwordConfirm) {
		msgPw2.innerHTML = '';
	} else {
		msgPw2.innerHTML = '비밀번호가 일치하지 않습니다.';
	}
}








document.getElementById('btn-code').addEventListener('click', fnCheckEmail);
document.getElementById('inp-pw').addEventListener('keyup', fnCheckPassword);
document.getElementById('inp-pw2').addEventListener('blur', fnConfirmPassword);
document.getElementById('inp-name').addEventListener('blur', fnCheckName);
document.getElementById('inp-mobile').addEventListener('blur', fnCheckMobile);
fnSignup();




</script>

  

</body>
</html>