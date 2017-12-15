<%@ page language="java" contentType="text/html; charset=UTF-8"  
    pageEncoding="UTF-8"%>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>  
<html>  
<head>  
    <meta charset="utf-8">  
    <title>标题</title>
    <script type="text/javascript" src="js/jquery-1.11.1.js"></script>  
</head>  
  
<body>

	<input id="fileUpload" type="file" width="140px" name="img" accept="image/*" onchange="changImg(event)" multiple="multiple">
	<input type="button" value="上传" id="upload"/>
	<div style="background:#848484;width:100px;height:10px;margin-top:5px">  
        <div id="progressNumber" style="background:#428bca;width:0px;height:10px" >  
        </div>      
    </div>  
    <font id="percent">0%</font>  
	<img alt="暂无图片" id="myImg"src="" height="100px" width="100px">
	<a href="${pageContext.request.contextPath }/servlet/DownLoadServlet?name=132.txt">下载</a>
	<a href="E:/files/132.txt" target="_blank">打开本地文件</a>
    <script>
    var file = null;
    $(function(){
    	$("#upload").click(function(){
    		upload();
    	});
    })
    var input = document.getElementById("fileUpload");
    input.addEventListener('change', readFile, false);
    
    function readFile(){
    	file = this.files[0];
    }
    //upload
    function upload(){
    	var xhr = new XMLHttpRequest();
    	var fd = new FormData();
    	fd.append("fileName",file);
    	xhr.upload.addEventListener("progress",uploadProgress,false);
    	xhr.open("POST","${pageContext.request.contextPath }/servlet/UploadServlet",true);
    	xhr.send(fd);
    }
    function uploadProgress(evt){
    	if(evt.lengthComputable){
    		var percentComplete = Math.round((evt.loaded) * 100 / evt.total);      
            //加载进度条，同时显示信息            
            $("#percent").html(percentComplete + '%')  
            $("#progressNumber").css("width",""+percentComplete+"px");
    	}
    }
      
    function changImg(e){
    	console.log(e);
        for (var i = 0; i < e.target.files.length; i++) {  
            var file = e.target.files.item(i);  
            if (!(/^image\/.*$/i.test(file.type))) {  
                continue; //不是图片 就跳出这一次循环  
            }  
            //实例化FileReader API  
            var freader = new FileReader();  
            freader.readAsDataURL(file);  
            freader.onload = function(e) {  
                $("#myImg").attr("src",e.target.result);  
            };
        }
    }
    </script>  
</body>  
</html> 