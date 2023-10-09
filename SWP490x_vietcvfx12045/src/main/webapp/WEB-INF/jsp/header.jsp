<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- Hàm function fn --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${pageContext.request.contextPath}/CSS/mystyle.css" rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath}/CSS/springstyle.css" rel="stylesheet" type="text/css"/>
    
    <link href="${pageContext.request.contextPath}/CSS/adminstyle.css" rel="stylesheet" type="text/css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
	
	<title>${param.title}</title> <%-- Sẽ tự get tiêu đề của từng trang khi chuyển, sử dụng trang đó. Vì header.jsp là dùng chung --%>
<style>

.dropbtn {
  background-color: #9A3558;
  padding: 14px;
  font-size: 16px;
  border: none;
  cursor: pointer;
}

.dropbtn:hover, .dropbtn:focus {
  background-color: #4287f5;
}

.dropdown {
  position: relative;
  display: inline-block;
  z-index: 1;
}

.dropdown-content {
  display: none;
  position: absolute;
  background-color: #f1f1f1;
  min-width: 160px;
  overflow: auto;
  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
}

.dropdown-content a {
  color: black;
  padding: 12px 16px;
  text-decoration: none;
  display: block;
}

.dropdown a:hover {background-color: #ddd;}

.show {display: block;}


/* MODEL BOX THANH TOÁN */

/* The Modal (background) */
.modal {
  display: none; /* Hidden by default */
  position: fixed; /* Stay in place */
  z-index: 1; /* Sit on top */
  padding-top: 100px; /* Location of the box */
  left: 0;
  top: 0;
  width: 100%; /* Full width */
  height: 100%; /* Full height */
  overflow: auto; /* Enable scroll if needed */
  background-color: rgb(0,0,0); /* Fallback color */
  background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
  text-align: center;
}

/* Modal Content */
.modal-content {
  background-color: #fefefe;
  margin: auto;
  padding: 20px;
  border: 1px solid #888;
  width: 75%;
}

/* The Close Button */
.close {
  color: #aaaaaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: #000;
  text-decoration: none;
  cursor: pointer;
}


.editaccount {
	width: 45%;
	padding: 10px;
	margin: auto;
	text-align: left;
}

@media screen and (max-width: 900px) {
	
	.editaccount {
		width: 95%;
	}
}

/* PHÂN TRANG */

.pagination {
  display: inline-block;
}

.pagination a {
  color: black;
  float: left;
  padding: 8px 16px;
  text-decoration: none;
  transition: background-color .3s;
  border: 1px solid #ddd;
  margin: 0 4px;
}

.pagination a.active {
  background-color: #4CAF50;
  color: white;
  border: 1px solid #4CAF50;
}

.pagination a:hover:not(.active) {background-color: #ddd;}

</style>
</head>
<body>

<div class="header">
	<img src="Media/banner_heart4.jpg" style="width:100%; overflow: auto;">
</div>

<div class="topnav">
	<div class="navleft">
		<a href="index.html?status=1">Trang chủ</a>
		<a href="index.html?status=2">Đạt mục tiêu</a>
		<a href="index.html?status=3">Đã trao quà</a>
		<c:if test="${empty keyShowMenu}">
			<a href="login.html" >Đăng nhập</a>
			<a href="registerpage.html">Đăng ký</a>
		</c:if>
		<!-- Nếu có tài khoản đang login thì nhập thêm menu theo cấp tài khoản -->
		<c:if test="${not empty id_account_code}">
			<div class="dropdown">
			  <button onclick="myFunction()" class="dropbtn" style="color: #0500ca;">${email}</button>
			  <div id="myDropdown" class="dropdown-content">
			  
				  <c:choose>
				  	<c:when test="${id_account_code == '1' || id_account_code == '2' || id_account_code == '5'}">
				  		<a href="<c:url value="/myhistory.html"/>" style="color:black">Lịch sử quyên góp</a>
					    <a href="<c:url value='/edituserpage.html'/>" style="color:black">Tài khoản của tôi</a>
				  	</c:when>
				  </c:choose>
				    
			      
			       <c:choose>
				  	<c:when test="${id_account_code == '3' || id_account_code == '4'}">
					    <a href="<c:url value="/indexAdmin.html"/>" style="color:black">Về trang Admin</a>
			      	</c:when>
				  </c:choose>
				  
			      <form action='logout.html' method='get' style="text-align: center;">
					<a><input type='submit' value='Logout' style="margin: auto; text-align: center; padding: 6px;"></a>
				  </form>
				  
			  </div>
			</div>
		</c:if>

	</div>
	
  	<div class="navright">
  		<form name="searchform" action="searchListCampaignIndexByKey.html" method="get">  		
		  	<input type="text" name="searchkey" placeholder="Tìm kiếm tất cả" size="25" value="${searchkey}">
		  	<input type="submit" value="Search"/>
	  	</form>
	</div>	
		  	 	
</div>
