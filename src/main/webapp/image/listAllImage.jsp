<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="product.image.model.*"%>

<%
ImageService imageSvc = new ImageService();
List<ImageVO> list = imageSvc.getAll();
pageContext.setAttribute("list", list);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>所有資料 - listAllImage.jsp</title>

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
	width: 800px;
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
<body>
	<table id="table-1">
		<tr>
			<td>
				<h3>所有資料 - listAll.jsp</h3>
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
			<th>修改</th>
			<th>刪除</th>
		</tr>
		<%@ include file="page1.file"%>
		<c:forEach var="imageVO" items="${list}" begin="<%=pageIndex%>"
			end="<%=pageIndex+rowsPerPage-1%>">
			<tr>
				<td>${imageVO.imageId}</td>
				<td>${imageVO.productId}</td>
				<c:if test="${not empty imageVO.image}">
					<c:set var="base64Image" value="${Base64.getEncoder().encodeToString(imageVO.image)}" />
					<td><img src="data:image/jpeg;base64,${base64Image}" alt="Image"></td>
				</c:if>
				<c:if test="${empty imageVO.image}">
					<td></td>
				</c:if>
				<td>
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/image/ImageServlet"
						style="margin-bottom: 0px;">
						<input type="submit" value="修改"> 
						<input type="hidden" name="imageId" value="${imageVO.imageId}"> 
						<input type="hidden" name="action" value="getOne_For_Update">
					</FORM>
				</td>
				<td>
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/image/ImageServlet"
						style="margin-bottom: 0px;">
						<input type="submit" value="刪除">
						<input type="hidden" name="imageId" value="${imageVO.imageId}"> 
						<input type="hidden" name="action" value="delete">
					</FORM>
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="page2.file"%>

</body>
</html>