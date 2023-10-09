<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>     
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:import url="headeradmin.jsp">
	<c:param name="title" value="Trang chủ Admin"></c:param> <%--Set tiêu đề cho header.jsp khi ở trang này --%>
</c:import>


	<div class="adminleft">
		<p style="margin-top:15px; color: white;"><c:out value="Today: ${ngayHomNay}"></c:out></p>		
		<hr>
		<p style="background-color: #d62026; padding: 5px;"> <a href="mycampaign.html" style="color: #ffd633;">${email} </a> </p>
		<p>	<a href="<c:url value="/indexAdmin.html"/>"><b class="textheaderamdinleft">Trang chủ</b></a></p>
		<p> <a href="mycampaign.html"><b class="textheaderamdinleft"> My Campaign </b></a></p>
		<p> <a href="staffmanager.html"><b class="textheaderamdinleft"> Staff Manager </b></a></p>
		<p> <a href="myaccountpage.html"><b class="textheaderamdinleft"> My Account </b></a></p>
		<p> <a href="index.html"><b class="textheaderamdinleft"> Go to Index </b></a></p>
		
		<form action="logout.html" method="get">
			<input type="hidden" name="emailTruyenModelMap" value="${email}"> <%--Vì ModelMap chỉ truyền xong đến đây là kết thúc nên muốn truyền nó 1 lần nữa thì phải đặt chỗ để truyền tiếp --%>
			<input type="submit" value="Logout">
		</form>
		
	</div>