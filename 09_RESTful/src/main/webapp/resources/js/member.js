/*
*/

// 전역변수 (vXXX)
var vPage = 1;
var vDisplay = 20;

// jQuery 객체 선언 (jqXXX)
var jqMembers = $('#members');
var jqTotal = $('#total');
var jqPaging = $('#paging');
var jqDisplay = $('#display');
var jqMemberNo = $('#member-no');
var jqEmail = $('#email');
var jqName = $('#name');
var jqZonecode = $('#zonecode');
var jqAddress = $('#address');
var jqDetailAddress = $('#detailAddress');
var jqExtraAddress = $('#extraAddress');
var jqBtnInit = $('#btn-init');
var jqBtnRegister = $('#btn-register');
var jqBtnModify = $('#btn-modify');
var jqBtnRemove = $('#btn-remove');
var jqBtnSelectRemove = $('#btn-select-remove');

const fnInit = ()=>{
  jqEmail.val('').prop('disabled', false); // 초기화 되면 아이디 입력 가능해야 하므로 disabled false 설정 
  jqName.val('');
  $('#none').prop('checked', true);
  jqZonecode.val('');
  jqAddress.val('');
  jqDetailAddress.val('');
  jqExtraAddress.val('');
  
  jqBtnRegister.prop('disabled', false);
  jqBtnModify.prop('disabled', true); // 초기화 시 수정, 삭제 버튼은 비활성화.
  jqBtnRemove.prop('disabled', true);
}

const fnGetContextPath = ()=>{
  const host = location.host;  /* localhost:8080 */
  const url = location.href;   /* http://localhost:8080/mvc/getDate.do */
  const begin = url.indexOf(host) + host.length;
  const end = url.indexOf('/', begin + 1);
  return url.substring(begin, end);
}

const fnRegisterMember = ()=>{
  $.ajax({
    // 요청
    type: 'POST',
    url: fnGetContextPath() + '/members',
    contentType: 'application/json',  // 보내는 데이터의 타입
    data: JSON.stringify({            // 보내는 데이터 (문자열 형식의 JSON 데이터)
      'email': jqEmail.val(),
      'name': jqName.val(),
      'gender': $(':radio:checked').val(),
      'zonecode': jqZonecode.val(),
      'address': jqAddress.val(),
      'detailAddress': jqDetailAddress.val(),
      'extraAddress': jqExtraAddress.val()
    }),
    // 응답
    dataType: 'json'  // 받는 데이터 타입
  }).done(resData=>{  // resData = {"insertCount": 2}
    if(resData.insertCount === 2){
      alert('정상적으로 등록되었습니다.');
      fnInit();
      fnGetMemberList();
    }
  }).fail(jqXHR=>{
    alert(jqXHR.responseText);
  })
}

const fnGetMemberList = ()=>{
  $.ajax({
    type: 'GET',
    url: fnGetContextPath() + '/members/page/' + vPage + '/display/' + vDisplay,
    dataType: 'json',
    success: (resData)=>{  /*
                              resData = {
                                "members": [
                                  {
                                    "addressNo": 1,
                                    "zonecode": '12345',
                                    "address": '서울시 구로구'
                                    "detailAddress": '디지털로',
                                    "extraAddress": '(가산동)',
                                    "member": {
                                      "memberNo": 1,
                                      "email": 'aaa@bbb',
                                      "name": 'gildong',
                                      "gender": 'none'
                                    }
                                  }, ...
                                ],
                                "total": 30,
                                "paging": '< 1 2 3 4 5 6 7 8 9 10 >'
                              }
                           */
      jqTotal.html('총 회원 ' + resData.total + '명');
      jqMembers.empty();
      $.each(resData.members, (i, member)=>{
        let str = '<tr>';
        str += '<td><input type="checkbox" class="chk-member" value="' + member.member.memberNo + '"></td>';
        str += '<td>' + member.member.email + '</td>';
        str += '<td>' + member.member.name + '</td>';
        str += '<td>' + member.member.gender + '</td>';
        str += '<td><button type="button" class="btn-detail" data-member-no="' + member.member.memberNo + '">조회</button></td>';
        str += '</tr>';
        jqMembers.append(str);
      })
      jqPaging.html(resData.paging);
    },
    error: (jqXHR)=>{
      alert(jqXHR.statusText + '(' + jqXHR.status + ')');
    }
  })
}

// MyPageUtils 클래스의 getAsyncPaging() 메소드에서 만든 <a href="javascript:fnPaging()"> 에 의해서 실행되는 함수
const fnPaging = (p)=>{
  vPage = p;
  fnGetMemberList();
}

const fnChangeDisplay = ()=>{
  vDisplay = jqDisplay.val();
  fnGetMemberList();
}

const fnGetMemberByNo = (evt)=>{
  $.ajax({
    type: 'GET',
    url: fnGetContextPath() + '/members/' + evt.target.dataset.memberNo,
    dataType: 'json'
  }).done(resData=>{  /* resData = {
                           "addressList": [
                             {
                               "addressNo": 1,
                               "zonecode": "12345",
                               "address": "서울시 구로구 디지털로",
                               "detailAddress": "카카오",
                               "extraAddress": "(가산동)"
                             },
                             ...
                           ],
                           "member": {
                             "memberNo": 1,
                             "email": "email@email.com",
                             "name": "gildong",
                             "gender": "man"
                           }
                         }
                      */
    fnInit();
    if(resData.member !== null){
      // 여기서 memberNo를 채워주자!!
      jqMemberNo.val(resData.member.memberNo);
      
      
      // 이메일은 수정하지 못하게 한다. - readonly or disabled
      jqEmail.val(resData.member.email).prop('disabled', true); // .attr('disabled', 'disabled') (attribute로 변경)
      jqName.val(resData.member.name);
      $(':radio[value=' + resData.member.gender + ']').prop('checked', true);
      // 회원 상세 조회에서는 등록 버튼 비활성화, 수정 및 삭제 버튼은 활성화
      jqBtnRegister.prop('disabled', true);
      jqBtnModify.prop('disabled', false);
      jqBtnRemove.prop('disabled', false);
    }
    if(resData.addressList.length !== 0){
      jqZonecode.val(resData.addressList[0].zonecode);
      jqAddress.val(resData.addressList[0].address);
      jqDetailAddress.val(resData.addressList[0].detailAddress);
      jqExtraAddress.val(resData.addressList[0].extraAddress);
    }
  }).fail(jqXHR=>{
    alert(jqXHR.statusText + '(' + jqXHR.status + ')');
  })
}

const fnModifyMember = () => {
  $.ajax({
    type: 'PUT',
    url: fnGetContextPath() + '/members',
    contentType: 'application/json',
    data: JSON.stringify({
      // mapper의 property이름이 사용되어야 함.
      'memberNo': jqMemberNo.val(),
      'name': jqName.val(),
      'gender': $(':radio:checked').val(),
      'zonecode': jqZonecode.val(),
      'address': jqAddress.val(),
      'detailAddress': jqDetailAddress.val(),
      'extraAddress': jqExtraAddress.val()
    }),
    dataType: 'json',
    suceess: (resData)=>{ //resData = {"updateCount": 2}
      if(resData.updateCount === 2) {
        alert('회원 정보가 수정되었습니다.');
        fnGetMemberList();
      } else {
        alert('회원 정보가 수정되지 않았습니다.');
      }
  },
  error: (jqXHR)=>{
    alert(jqXHR.statusText + '(' + jqXHR.status + ')');
  }
 })
}

// 함수 호출 및 이벤트
fnInit();
jqBtnInit.on('click', fnInit);
jqBtnRegister.on('click', fnRegisterMember);
fnGetMemberList();
jqDisplay.on('change', fnChangeDisplay);
$(document).on('click', '.btn-detail', (evt)=>{ fnGetMemberByNo(evt); });
jqBtnModify.on('click', fnModifyMember);