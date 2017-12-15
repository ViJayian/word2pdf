<%@ page language="java" contentType="text/html; charset=UTF-8"  
    pageEncoding="UTF-8"%>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Document</title>
</head>
<body>
<input type="file"><br>
<div id="fi">
	<img src="" height="200" alt="Image preview area..." title="preview-img">
</div>
<script>

  var fileInput = document.querySelector('input[type=file]');
  console.log(fileInput)
  var previewImg = document.getElementById('fi').getElementsByTagName('img')[0];
         // previewImg = document.querySelector('img');
  fileInput.addEventListener('change', function () {
  console.log(fileInput)
      var file = this.files[0];
      var reader = new FileReader();
      // 监听reader对象的的onload事件，当图片加载完成时，把base64编码賦值给预览图片
      reader.addEventListener("load", function () {
          previewImg.src = reader.result;
      }, false);
      // 调用reader.readAsDataURL()方法，把图片转成base64
      reader.readAsDataURL(file);
  }, false);
</script>
</body>
</html>