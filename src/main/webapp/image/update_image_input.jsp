<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="product.image.model.*"%>

<%
ImageVO imageVO = (ImageVO) request.getAttribute("imageVO");
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>員工資料修改 - update_image_input.jsp</title>

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
	width: 450px;
	background-color: white;
	margin-top: 1px;
	margin-bottom: 1px;
}

table, th, td {
	border: 0px solid #CCCCFF;
}

th, td {
	padding: 1px;
}
</style>

</head>
<body bgcolor='white'>

	<table id="table-1">
		<tr>
			<td>
				<h3>資料修改 - update_image_input.jsp</h3>
				<h4>
					<a href="select_page.jsp">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>

	<h3>資料修改:</h3>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<FORM METHOD="post" ACTION="ImageServlet" name="form1"
		enctype="multipart/form-data">
		<table id="tb">
			<tbody>
				<tr>
					<td>圖片編號:<font color=red><b>*</b></font></td>
					<td><%=imageVO.getImageId()%></td>
				</tr>
			</tbody>
			<tbody>
				<tr>
					<td>商品編號:</td>
					<td><input type="TEXT" name="productId"
						value="<%=imageVO.getProductId()%>" size="45" /></td>
				</tr>
			</tbody>

			<tbody>
				<tr>
					<td>圖片:</td>
					<td><input type="file" name="imageFile" /></td>
				</tr>
			</tbody>
		</table>
		<br> <input type="hidden" name="action" value="update"> <input
			type="hidden" name="imageId" value="<%=imageVO.getImageId()%>">
		<input type="submit" value="送出修改">
	</FORM>

</body>

</html>