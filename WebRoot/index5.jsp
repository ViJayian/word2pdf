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
<-- 一个能上传多媒体文件的表单 -->
<input type="file" id="upload-file" multiple />

<-- 显示图片的地方 -->
<div id="destination"></div>
  <script>
document.getElementById('upload-file').addEventListener('change', function() {
	var file;
	var destination = document.getElementById('destination');
	destination.innerHTML = '';

	// 循环用户多选的文件
	for(var x = 0, xlen = this.files.length; x < xlen; x++) {
		file = this.files[x];
		if(file.type.indexOf('image') != -1) { // 非常简单的交验

			var reader = new FileReader();

			reader.onload = function(e) {
				var img = new Image();
				img.src = e.target.result; // 显示图片的地方

				destination.appendChild(img);
			};
			
			reader.readAsDataURL(file);
		}
	}
});
</script>
</body>
</html>