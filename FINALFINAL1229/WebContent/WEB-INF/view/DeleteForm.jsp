<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>投稿削除</title>
    <link rel="stylesheet" type="text/css" href="css/test.css">
</head>
<body>
 <%@ include file="/WEB-INF/view/in/base.jsp" %>
    <div class="content">

<div align="center">
<form action="delete.do" method="post">
<p>
	<input type="hidden" name="no" value="${modReq.articleNumber}">

</p>
<p>
	削除しますか？
</p>

<button class=submit-button>削除</button>
	</form>
</div>
</div>
	    <div class="footer">
        <p>&copy; 2024 HEXAGON LOGISTICS. All rights reserved.</p>
    </div>
</body>
</html>