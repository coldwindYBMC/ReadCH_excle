<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>上传文件，生成字典</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/upload" enctype="multipart/form-data" method="post">
        多个源文件生成字典: <input type="file" name="fileList" multiple/></br/>  
    <input type="submit" value="生成字典"><br>
    </form>
    
    <form action="${pageContext.request.contextPath}/uploaddic" enctype="multipart/form-data" method="post">
        上传单个字典表文件: <input type="file" name="fileTest"><br/>  
    <input type="submit" value="上传"><br>
     </form>
     
    <form action="${pageContext.request.contextPath}/uploadtran" enctype="multipart/form-data" method="post">
        多个需要翻译的文件: <input type="file" name="fileTest" multiple><br/>  
    <input type="submit" value="翻译"><br>
    </form>
    <br>
    <br>
    <br>
  	 <h1>Failed to find end of row/cell records
  	          打开出错的文件，点击保存，再上传
  	  <br>
      <br>
      <br>
  	 	The request was rejected because its size () exceeds the configured maximum ()
  	 	上传文件过多，前端网面显示无法访问。
  	 </h1> 
  	 
  	 
</body>
</html>