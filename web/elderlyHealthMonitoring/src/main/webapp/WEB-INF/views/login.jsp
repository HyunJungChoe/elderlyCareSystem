<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action = "/member/memberLogin" method = "post" id = "loginForm">
<div>
	아이디<input type = "text" name = "m_id" >
	비밀번호<input type = "password" name = "m_pwd">
	<button type = "submit">로그인</button>
</div>
</form>

</body>
</html>