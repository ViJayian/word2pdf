<%@ page language="java" contentType="text/html; charset=UTF-8"  
    pageEncoding="UTF-8"%>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>  
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
<title>Insert title here</title> 

</head>  
<body> 
<div> 
    <form action="${pageContext.request.contextPath }/servlet/UploadServlet" method="post" enctype="multipart/form-data">  
    最简单的文件上传：<input type="file" name="fileupload"/>  
    描述：<input type="text" name="desc"/>  
    <input type="submit" value="submit"/>  
    </form>  
    <hr>
 </div>
</body>  

</html>  