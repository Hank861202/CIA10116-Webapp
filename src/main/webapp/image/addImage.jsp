<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@
taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="product.image.model.*"%>
<%
ImageVO imageVO = (ImageVO) request.getAttribute("imageVO");
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>新增資料 - addImage.jsp</title>

<style>
table#table-1 {
	background-color: #ccccff;
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
	border: 0px solid #ccccff;
}

th, td {
	padding: 1px;
}
</style>
</head>
<body bgcolor="white">
	<table id="table-1">
		<tr>
			<td>
				<h3>新增 - addImage.jsp</h3>
			</td>
			<td>
				<h4>
					<a href="select_page.jsp">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>

	<h3>資料新增:</h3>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<form method="post" action="ImageServlet" name="form1"
		enctype="multipart/form-data">
		<table id="tb">
		       <tbody>
          <tr>
            <td>商品編號:</td>
            <td><input type="TEXT" name="productId" size="45" /></td>
          </tr>
        </tbody>
        <tbody>
          <tr>
            <td>圖片:</td>
            <td><input type="file" name="imageFile1" /></td>
            <td><button type="button" class="btn_delete">移除</button></td>
          </tr>
        </tbody>
		</table>
		<br /> <input type="hidden" name="action" value="insert" /> <input
			type="button" value="新增圖片欄位" id="add" /> <input type="submit"
			value="送出" />
	</form>
	<script>
      let add_btn = document.querySelector("#add");
      let tb_ele = document.querySelector("#tb");
      add_btn.addEventListener("click", () => {
        let list_html = ` 
       	<tbody>
        <tr> 
            <td>圖片:</td>
            <td><input type="file" name="imageFile1" /></td>
            <td><button type="button" class="btn_delete">移除</button></td>
        </tr></tbody>`;
        tb_ele.insertAdjacentHTML("beforeend", list_html);
      });
      document.addEventListener("click", function (e) {
    	  console.log(e.target);
          if (e.target.classList.contains("btn_delete")) {
            let r = confirm("確認移除？");
            if (r) {
              e.target.closest("tbody").remove();
            }
          }
        });
    </script>
</body>
</html>
