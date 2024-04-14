<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="product.image.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*,java.io.*"%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>

<%
ImageVO imageVO = (ImageVO) request.getAttribute("imageVO"); //EmpServlet.java(Concroller), 存入req的empVO物件
%>

<html>
<head>
<title>資料 - listOneImage.jsp</title>

<style>
table#table-1 {
	background-color: #CCCCFF;
	border: 2px solid black;
	text-align: center;
}

table#table-1 h4 {
	color: red;
	display: block;
	margin-bottom: 1px;
}

h4 {
	color: blue;
	display: inline;
}
</style>

<style>
table {
	width: 600px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
}

table, th, td {
	border: 1px solid #CCCCFF;
}

th, td {
	padding: 5px;
	text-align: center;
}
</style>

</head>
<body bgcolor='white'>

	<table id="table-1">
		<tr>
			<td>
				<h3>資料 - listOneImage.jsp</h3>
				<h4>
					<a href="select_page.jsp">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>

	<table>
		<tr>
			<th>圖片編號</th>
			<th>商品編號</th>
			<th>圖片</th>
		</tr>
		<tr>
			<td>${imageVO.imageId}</td>
			<td>${imageVO.productId}</td>
			<c:set var="base64Image"
				value="${Base64.getEncoder().encodeToString(imageVO.image)}" />
			<td><img src="data:image/jpeg;base64,${base64Image}" alt="Image"></td>
		</tr>
	</table>

</body>
</html>