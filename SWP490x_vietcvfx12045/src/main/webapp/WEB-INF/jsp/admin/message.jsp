<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>     
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:import url="headeradmin.jsp">
	<c:param name="title" value="Đã hoàn thành thao tác"></c:param> <%--Set tiêu đề cho header.jsp khi ở trang này --%>
</c:import>

<div class="containeradmin">

	<c:import url="adminleft.jsp"/>

	<div class="adminright">
		<img src="${pageContext.request.contextPath}/Media/BANNER1.png" style="width:100%">
		
		<br><br>
		
		<div style="text-align: center;">
		<h4><c:out value="${message}"></c:out></h4>
		</div>
	</div>

</div>

<c:import url="footer.jsp"/>