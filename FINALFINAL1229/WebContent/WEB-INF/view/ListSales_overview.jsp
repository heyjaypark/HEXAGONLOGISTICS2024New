<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
  <%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
<title>販売履歴</title>
    <link rel="stylesheet" type="text/css" href="css/test.css">
</head>
<body>
 <%@ include file="/WEB-INF/view/in/base.jsp" %>
    <div class="content">
	<div align= "center">
		<form action="salesSearch.do" name="p_no" method="post">
			<div>
				<select name="select_num">
    				<option value="1">取引番号</option>
    				<option value="2">品目コード</option>    
				</select>
				<input type="text" name="code" value = "${param.code}" required><input type="submit" value="検索">
			</div>
			
		</form>
	</div>
	<div align="center">
		<table border="1">
			<tr>
				<th>取引番号</th>
				<th>品目コード</th>
				<th>品目名</th>
				<th>ソウル店<br/>販売量</th>
				<th>水原店<br/>販売量</th>
				<th>仁川店<br/>販売量</th>
				<th>日付</th>
				<th>単価</th>
				<th>Total</th>
				<th>登録者</th>
				
			</tr>
			
			<c:if test="${salesPage.hasNoArticles()}">
				<tr>
					<td colspan="7">販売履歴がありません。</td>
				</tr>
			</c:if>
			
			<!-- 판매이력 리스트
			販売履歴リスト -->
			<c:forEach var="sales" items="${salesPage.content}">
				<tr>
					<td>${sales.s_Num}</td>
					<td>${sales.p_No}</td>
					<td>${sales.p_Name}</td>
					<td>${sales.s_Seoul}</td>
					<td>${sales.s_Suwon}</td>
					<td>${sales.s_Incheon}</td>
					<td>${sales.s_Date}</td>
					<td>${sales.price}</td>
					<td>${sales.price*(sales.s_Seoul+sales.s_Suwon+sales.s_Incheon)}</td>
					<td>${sales.s_Registrant }</td>
				</tr>
			</c:forEach>
			<c:if test="${salesPage.hasArticles()}">		
			<tr>
			<td colspan="10">
						<!-- 페이징 코드. 단위는 5개
						ページングコード。 単位は五つ -->
				<c:if test="${salesPage.startPage > 5}">
					<a href="salesList.do?pageNo=${salesPage.startPage - 5}">[以前]</a>
				</c:if>
				<c:forEach var="pNo"
					begin="${salesPage.startPage}"
					end="${salesPage.endPage}">
					<a href="salesList.do?pageNo=${pNo}">[${pNo}]</a>
				</c:forEach>
				<c:if test="${salesPage.endPage < salesPage.totalPages}">
				<a href="salesList.do?pageNo=${salesPage.startPage + 5}">[次のページへ]</a>
				</c:if>
			</td>
		</tr>
		</c:if>
		</table>
	</div>
	</div>
    <div class="footer">
        <p>&copy; 2024 HEXAGON LOGISTICS. All rights reserved.</p>
    </div>
</body>
</html>