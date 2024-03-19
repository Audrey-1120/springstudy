<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

  <style>
    #container-avg {
      display: flex;
    }

  </style>

  <h1>학생관리</h1>
  <button type="button">신규학생등록</button>
  
  <hr>
  
  <div id="container-avg">
    <span>평균</span>
    <input type="text" id="begin" name="begin" placeholder="begin"> ~ 
    <input type="text" id="end" name="end" placeholder="end">
    <button type="button" id="btn-getStudent">조회</button>
    <button type="button" id="btn-getStudentList">전체조회</button>
  </div>

  <hr>
  
    <div>
    <table border="1">
      <thead>
        <tr>
          <td>학번</td>
          <td>성명</td>
          <td>국어</td>
          <td>수학</td>
          <td>평균</td>
          <td>학점</td>
          <td>버튼</td>
        </tr>      
      </thead>
      <tbody>
        <tr>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
        </tr>
        <tr>
          <td colspan="4">전체평균</td>
          <td></td>
          <td colspan="2"></td>
        </tr>
        
      </tbody>
    </table>
  </div>

</body>
</html>