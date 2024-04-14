<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>IBM Image: Home</title>

<style>
  table#table-1 {
	width: 450px;
	background-color: #CCCCFF;
	margin-top: 5px;
	margin-bottom: 10px;
    border: 3px ridge Gray;
    height: 80px;
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

</head>
<body bgcolor='white'>

<table id="table-1">
   <tr><td><h3>IBM Image: Home</h3><h4>( MVC )</h4></td></tr>
</table>

<h3>資料查詢:</h3>
	
<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
	    <c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<ul>
  <li><a href='listAllImage.jsp'>List</a> all Images.  <br><br></li>
  
  
  <li>
    <FORM METHOD="post" ACTION="ImageServlet" >
        <b>輸入圖片編號:</b>
        <input type="text" name="imageId">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出">
    </FORM>
  </li>

  <jsp:useBean id="imageSvc" scope="page" class="product.image.model.ImageService" />
   
  <li>
     <FORM METHOD="post" ACTION="ImageServlet" >
       <b>選擇圖片編號:</b>
       <select size="1" name="imageId">
         <c:forEach var="imageVO" items="${imageSvc.all}" > 
          <option value="${imageVO.imageId}">${imageVO.imageId}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="送出">
    </FORM>
  </li>
  
  <li>
     <FORM METHOD="post" ACTION="ImageServlet" >
       <b>商品編號:</b>
       <select size="1" name="imageId">
         <c:forEach var="imageVO" items="${imageSvc.all}" > 
          <option value="${imageVO.imageId}">${imageVO.productId}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="送出">
     </FORM>
  </li>
</ul>


<h3>員工管理</h3>

<ul>
  <li><a href='addImage.jsp'>Add</a> a new Image.</li>
</ul>

</body>
</html>