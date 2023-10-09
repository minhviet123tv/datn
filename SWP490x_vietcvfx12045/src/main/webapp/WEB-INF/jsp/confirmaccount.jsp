<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:import url="header.jsp">
	<c:param name="title" value="Login page - Đồ án tốt nghiệp"></c:param> <%--Set tiêu đề cho header.jsp khi ở trang này --%>
</c:import>

<div>
<h2 style="text-align: center; margin-top: 20px;">Vui lòng nhập mã kích hoạt tài khoản</h2>
      
   <form action="confirmaccount.html" method="post" accept-charset="utf-8"> <%-- Sẽ gửi lên class servlet DispatcherServlet và DispatcherServlet sẽ điều hướng đến Controller tương ứng --%>
		<table id="loginspring" style="margin-top: 20px;">
			<tr>
				<td><input class="form-control" type="text" name="random_code_from_user" value="${random_code_from_user}" maxlength="100" required/></td> <%--Có thể đặt required để bắt buộc điền của form, không đặt để hiểu cách tự Control --%>
			</tr>
			<tr>
				<td colspan="2" style="text-align: center;"><input class="btn btn-primary btn-lg" style="margin-top: 20px;" type="submit" value="confirm"/></td> <!-- background-color: #9a3558; -->
			</tr>
		</table>
	</form>
	
	<c:if test="${empty message}">
		<br/>
		<br/>
		<br/>
		<br/>
	</c:if>
	
	<c:if test="${not empty message}">
		<p style="text-align: center; padding: 10px;">${message} </p>
	</c:if>

</div>

<%@ include file="footer.jsp"%>