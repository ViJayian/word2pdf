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

	<form action="${pageContext.request.contextPath }/servlet/UploadServlet"  method="post"  enctype="multipart/form-data"> 
	  <input type="file"  name="image"  accept="image/*"  capture="user" > 
	  <input type="submit"  value="Upload" > 
	</form> 
	<hr> 
    <input type = 'text' id="normal_stream" value="中文乱码不?" />  
    <input type = 'file' id="binary_stream" accept ="image/jpg" multiple="multiple" />  
    <input type = 'button' value="SUBMIT" onclick="save()" />  
    <div id="show"></div>
    
    <input type="file" accept="image/*" capture="camera">
	<input type="file" accept="video/*" capture="camcorder">
	<input type="file" accept="audio/*" capture="microphone">  
	
	<videoid videoid="video" autoplay=""></video>   
    <script>  
    //console.log(JSON.parse('{"a":"123"}'));  
    function save(){  
        //var normal_stream = 	document.getElementById("normal_stream").value;  
        var files = document.getElementById("binary_stream").files;  
        var arg={  
            url : '${pageContext.request.contextPath }/servlet/UploadServlet',  
            data:{},  
            progress:function(e){  
                //展示进度条 e.loaded/e.total;  
                console.log(e.loaded/e.total);  
            },  
            fail:function(e){  
                //失败处理  
                console.log("fail:"+e);  
            },  
            success:function(json){  
                //成功处理  
                console.log(json);  
                document.getElementById("show").innerHTML=json.normal+"本地文件名: "+json.fileName;  
            }  
        };  
        for(var i=0;i<files.length;i++)  
            arg.data["file-"+i] = files[i];  
        upload(arg);  
    }  
    function upload(arg){  
        var form = new FormData();  
        for(var key in arg.data)  
            form.append(key+"",arg.data[key]);  
        xhr = new XMLHttpRequest();  
        xhr.open("POST", arg.url, true);  
        xhr.upload.onprogress = arg.progress;  
        xhr.onreadystatechange=function(){  
            if(xhr.readyState==4){  
                if(xhr.status>=200&&xhr.status<300){  
                    //console.log(xhr.responseText);  
                    arg.success&&arg.success(JSON.parse(xhr.responseText));  
                }else arg.fail&&arg.fail(xhr.status);  
            }  
        }  
        xhr.send(form);  
    }
    
    var video_element=document.getElementById("video");   
	if(navigator.getUserMedia){//operashoulduseopera.getUserMedianow   
		navigator.getUserMedia("video",success,error);   
	}   
	function success(stream){   
		video_element.src=stream;   
	}   
    
    function aaa(){
	    var canvas=document.createElement("canvas");   
		var ctx=canvas.getContext("2d");   
		var cw=vw;   
		var ch=vh;   
		ctx.fillStyle="#ffffff";   
		ctx.fillRect(0,0,cw,ch);   
		ctx.drawImage(video_element,0,0,vvw,vvh,0,0,vw,vh);   
		document.body.append(canvas);  
    }  
    </script>  
</body>  
</html> 