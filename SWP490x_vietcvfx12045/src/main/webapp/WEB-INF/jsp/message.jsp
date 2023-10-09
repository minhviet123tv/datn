<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:import url="header.jsp">
	<c:param name="title" value="Thông báo"></c:param> <%--Set tiêu đề cho header.jsp khi ở trang này --%>
</c:import>
<div style="text-align: center; margin-top: 10px; margin: auto;">
	<h2 style="text-align: center; margin-top: 20px;">Thông báo: </h2>
	<p style="text-align: center; padding: 10px;">${message}</p>
</div>

<c:if test="${not empty viewmyhistory}">
	<div style="text-align: center;">${viewmyhistory}</div>
</c:if>

<br><br><br>

<%@ include file="footer.jsp"%>