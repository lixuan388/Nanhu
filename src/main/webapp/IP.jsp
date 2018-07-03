<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
System.setProperty("clientID",request.getHeader("X-Real-IP"));
%>
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
<div><%=new Date() %></div>
<div id="time">1111</div>
<div>getRemoteAddr:<%=request.getRemoteAddr()%></div>
<div>request.getHeader("X-Real-IP"):<%=request.getHeader("X-Real-IP")%></div>
<div>request.getHeader("x-forwarded-for"):<%=request.getHeader("x-forwarded-for")%></div>
<div>request.getHeader("HTTP_X_FORWARDED_FOR"):<%=request.getHeader("HTTP_X_FORWARDED_FOR")%></div>
<script type="text/javascript">
var t=0
function ip(){
  t++;
  document.getElementById('time').innerHTML=t;
  if (t>300){
    location.href="http://s2.17ecity.cc/Nanhu/IP.jsp?"+new Date().getTime();  
  }
    
  
} 
  //使用方法名字执行方法 
window.setInterval(ip,1000); 

</script>
</body>
</html>