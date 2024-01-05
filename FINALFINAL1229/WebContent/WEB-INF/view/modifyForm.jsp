<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>投稿修正</title>
    <link rel="stylesheet" type="text/css" href="css/test.css">
</head>
<body>
 <%@ include file="/WEB-INF/view/in/base.jsp" %>
    <div class="content">
<div align="center">
<form action="modify.do" method="post">
<div class="contentTable" align="center">
<table border="1">
<tr>
<th>
No
	<input type="hidden" name="no" value="${modReq.articleNumber}">
	</th>

	<td>${modReq.articleNumber}</td>


</tr>
<tr>
	<th>タイトル<br/>
	</th>
	<!-- 제목 공백 아닐것
	 タイトルは空白ではないこと -->
	<td><input type="text" name="title" value="${modReq.title}"></td>
	</tr>
	<c:if test="${errors.title}">タイトルを入力してください。</c:if>

<tr>
	<th>内容<br/></th>
	<td><textarea name="content" rows="30" cols="50">${modReq.content}</textarea></td>
</tr>
</table>
</div>
<button class= submit-button>投稿修正</button>
</form>
</div>
</div>
    <div class="footer">
        <p>&copy; 2024 HEXAGON LOGISTICS. All rights reserved.</p>
    </div>
</body>
</html>