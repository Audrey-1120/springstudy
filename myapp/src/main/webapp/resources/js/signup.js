/**
 * 
 */
 
 
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