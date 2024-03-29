<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>品目アップデート</title>
    <link rel="stylesheet" type="text/css" href="css/test.css">
</head>
<body>
 <%@ include file="/WEB-INF/view/in/base.jsp" %>
    <div class="content">
    <div align="center">
		<!-- 수정할 품목을 품목코드로 검색해 정보를 불러온다
		修正する品目を品目コードで検索して情報を読み込む -->
		<div>
			<form action="productsearch1.do" method="post">
				<p>
					品目コード:<input type="text" name="p_no" value="${param.p_no}">
					<button type="submit">検索</button>
					<br />
					<c:if test="${errors.NumberFormatException}">品目コードを入力してください。</c:if>
				</p>
			</form>
		</div>
	</div>
	<div align="center">
		<form action="productupdate.do" method="post">
			<table border="1">
				<tr>
				</tr>
				<tr>
					<th>品目コード</th>
					<th>品目名</th>
					<th>ソウル店</th>
					<th>ソウル店<br/>入庫量</th>
					<th>水原店</th>
					<th>水原店<br/>入庫量</th>
					<th>仁川店</th>
					<th>仁川店<br/>入庫量</th>
					<th>単価</th>
					<th>修正する単価</th>
					<th>入庫日</th>
				</tr>
				<c:if test="${productPage.hasNoArticles()}">
					<tr>
						<td colspan="4">登録されていない商品です。</td>
					</tr>
				</c:if>
				<!-- 불러온 품목정보를 표시하는 행
			検索した品目情報を表示する行 -->
				<c:if test="${errors.notnull}">
								<c:if test="${not empty product1.p_no}">
					<tr>
						<td><input type="hidden" name="p_no" value="${param.p_no}">${product1.p_no}</td>
						<td><input type="hidden" name="p_name"
							value="${product1.p_name}">${product1.p_name}</td>
						<td>${product1.p_seoul}</td>
						<td><input type="text" name="p_seoul" size = "5"/></td>
						<td>${product1.p_suwon}</td>
						<td><input type="text" name="p_suwon" size = "5" /></td>
						<td>${product1.p_incheon}</td>
						<td><input type="text" name="p_incheon" size = "5" /></td>
						<td>${product1.price}</td>
						<td><input type="text" name="price" size = "5"  /></td>
						<td><input type="date" name="date" id="today"> <input
							type="hidden" name="writer" value="${authUser.name }"></td>
						
					</tr>
				</c:if>
				</c:if>
			</table>
			<button type="submit" class="submit-button">入庫</button>
		</form>

	</div>
	<!-- 에러발생 및 수정성공시 문자표시
	エラー発生及び修正成功時の文字表示 -->
	<c:if test="${errors.numberInsert}">変更する値を入力してください。</c:if>
	<c:if test="${errors.successUpdate}">
		<script type="text/javascript">
			alert("入庫されました");
		</script>
	</c:if>
	<c:if test="${errors.NoMinus}">
正数を入力してください
</c:if>
</div>
    <div class="footer">
        <p>&copy; 2024 HEXAGON LOGISTICS. All rights reserved.</p>
    </div>
</body>
</html>