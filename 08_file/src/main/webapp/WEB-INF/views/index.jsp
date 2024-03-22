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
    <form action="${contextPath}/upload1.do"
          method="post"
          enctype="multipart/form-data">
      <div>
        <input type="file" name="files" class="files" accept="image/*" multiple>
      </div>
      <div>
        <input type="text" name="writer" placeholder="작성자">
      </div>
      <div>
        <button type="submit">전송</button>
      </div>
    </form>
  </div>
  
  <h3>첨부 파일 목록</h3>
  <div id="file-list"></div>
  
  <hr>
  
  <!-- form 이 없을때 -->
  
  
  <div>
  
    <div>
      <input type="file" id="input-files" class="files" multiple>    
    </div>
    <div>
      <input type="text" id="writer" placeholder="작성자">
    </div>
    <div>
      <!-- form이 없으니 type이 submit일 수? 없다. -->
      <button type="button" id="btn-upload">전송</button>
    </div>
  
  
  </div>
  
  <script type="text/javascript">
  
    const fnFileCheck = ()=>{
      $('.files').on('change', (evt)=>{
        const limitPerSize = 1024 * 1024 * 10;
        const limitTotalSize = 1024 * 1024 * 100;
        let totalSize = 0;
        const files = evt.target.files;
        const fileList = document.getElementById('file-list');
        fileList.innerHTML = '';
        for(let i = 0; i < files.length; i++){
          if(files[i].size > limitPerSize){
            alert('각 첨부 파일의 최대 크기는 10MB입니다.');
            evt.target.value = '';
            fileList.innerHTML = '';
            return;
          }
          totalSize += files[i].size;
          if(totalSize > limitTotalSize){
            alert('전체 첨부 파일의 최대 크기는 100MB입니다.');
            evt.target.value = '';
            fileList.innerHTML = '';
            return;
          }
          fileList.innerHTML += '<div>' + files[i].name + '</div>';
        }
      })
    }
    
    const fnAfterInsertCheck = ()=>{
      const insertCount = '${insertCount}';
      if(insertCount !== ''){
        if(insertCount === '1'){
          alert('저장되었습니다.');
        } else {
          alert('저장실패했습니다.');
        }
      }
    }
    
    const fnAsyncUpload = () => {
    	const inputFiles = document.getElementById('input-files');
    	const inputWriter = document.getElementById('input-writer');
    	/* 이제 빈 form 객체를 생성한다. */
    	let formData = new FormData();
    	// 파일 자체는 여러개이기 때문에 반복문 필요!
    	for(let i = 0; i < inputFiles.files.length; i++){
    		// inputFiles.files[i] - 개별파일
    		formData.append('files', inputFiles.files[i]);
    	}
    	
    	fetch('${contaxtPath}/upload2.do', {
    		method: 'POST',
    		body: formData
    	}).then(response=>response.json()) // 시간이 걸리더라도 json 형태로 데이터를 받을 것임.
    	  .then(resData=>{  /* resdata = {"success": 1} 또는 {"succeess": 0}  이값들을 만드는 건 service에서.. */
    		  if(resData.success === 1) {
    			  alert('저장되었습니다.');
    		  } else {
    			  alert('저장실패했습니다.');
    		  }
    		  
    	  })
    }
    
    fnFileCheck();
    fnAfterInsertCheck();
    /* 이벤트 트리거를 바깥으로 뺌. */
    document.getElementById('btn-upload').addEventListener('click', fnAsyncUpload);
    
  </script>
  
</body>
</html>