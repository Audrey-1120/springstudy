<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>작성화면</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
</head>
<body>

  <h1>신규학생등록 화면</h1>

  <div>
    <form id="frm-register"
          method="POST"
          action="${contextPath}/student/register.brd">
      <div>
        <label for="name">이름</label>
        <input type="text" id="name" name="name">
      </div>
      <div>
        <label for="kor">국어</label>
        <input type="text" id="kor" name="kor">
      </div>
      <div>
        <label for="eng">영어</label>
        <input type="text" id="eng" name="eng">
      </div>
      <div>
        <label for="mat">수학</label>
        <input type="text" id="mat" name="mat">
      </div>
      <hr>
      <div>
        <button type="submit">작성완료</button>
        <button type="reset">다시작성</button>
        <button type="button" id="btn-list">목록보기</button>
      </div>
    </form>
  </div>

</body>
</html>