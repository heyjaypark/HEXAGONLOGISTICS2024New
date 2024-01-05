<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>
           
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Hexagon Logistics ver 2.0</title>
    <link rel="stylesheet" type="text/css" href="css/test.css">
</head>
<body>
 <%@ include file="/WEB-INF/view/in/base.jsp" %>
    <div class="content">
<div class="home-intro">
    <h2>ようこそ!ヘキサゴン物流在庫管理プログラムのメイン画面です。<br/>
    次の画像を通じて、当プログラムの様子を事前にご確認ください！</h2>

    <div id="imageDisplay" class="image-area">
        <!-- 버튼을 이미지 영역 내에 배치 -->
        <button id="prevBtn" class="image-button left" onclick="changeImage(-1)">&#10094;</button>
        <img src="imageServlet?imageName=intro.png" alt="이미지">
        <button id="nextBtn" class="image-button right" onclick="changeImage(1)">&#10095;</button>
    </div>
</div>
</div>

    <div class="footer">
        <p>&copy; 2024 HEXAGON LOGISTICS. All rights reserved.</p>
    </div>
    
<script>
    var images = [
        'imageServlet?imageName=intro.png',
        'imageServlet?imageName=stockmanage.png',
        'imageServlet?imageName=sales.png',
        'imageServlet?imageName=stocklist.png'
    ];
    var currentIndex = 0;

    function changeImage(direction) {
        currentIndex += direction;
        if (currentIndex < 0) {
            currentIndex = images.length - 1;
        } else if (currentIndex >= images.length) {
            currentIndex = 0;
        }
        displayImage(images[currentIndex]);
    }

    function displayImage(imageUrl) {
        var imageElement = document.querySelector('#imageDisplay img');
        if (imageElement) {
            imageElement.src = imageUrl;
        } else {
            document.getElementById('imageDisplay').innerHTML = '<img src="' + imageUrl + '" alt="이미지">';
        }
    }
</script>
</body>
</html>