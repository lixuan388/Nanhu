<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
div{
  margin: 10px;
}

</style>
</head>
<body>
<div>clientID:<span style="color:red"><%=System.getProperty("clientID")%></span></div>
<div>getRemoteAddr:<%=request.getRemoteAddr()%></div>
<div>request.getHeader("x-forwarded-for"):<%=request.getHeader("x-forwarded-for")%></div>
<div>request.getHeader("HTTP_X_FORWARDED_FOR"):<%=request.getHeader("HTTP_X_FORWARDED_FOR")%></div>

</body>
</html>