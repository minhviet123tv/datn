<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:import url="header.jsp">
	<c:param name="title" value="Login page - Đồ án tốt nghiệp"></c:param> <%--Set tiêu đề cho header.jsp khi ở trang này --%>
</c:import>
<div>
<h2 style="text-align: center; margin-top: 20px;">Điền thông tin đăng nhập</h2>
      
   <form action="dologin.html" method="post" accept-charset="utf-8"> <%-- Sẽ gửi lên class servlet DispatcherServlet và DispatcherServlet sẽ điều hướng đến Controller tương ứng --%>

		<table id="loginspring" style="margin-top: 20px;">
			<tr>
				<td style="text-align: right; padding-right: 15px;">Email </td>
				<td><input class="form-control" type="text" name="email" value="${email}" maxlength="100" placeholder="your email"/></td> <%--Có thể đặt required để bắt buộc điền của form, hiện tại không đặt để hiểu cách tự Control --%>
			</tr>
			<tr>
				<td style="text-align: right; padding-right: 15px;">Password </td>
				<td><input class="form-control" type="password" name="password" value="${password}" maxlength="64" placeholder="your password"/></td>
			</tr>
				<c:if test="${not empty message}">
					<tr>
						<td style="text-align: center; padding-top: 15px;" colspan="2">${message}</td>
					</tr>
				</c:if>
			<tr>
				<td style="text-align: right; padding-right: 5px;"></td>
				<td style="text-align: left;"><input class="btn btn-primary btn-lg" style="background-color: #9a3558; margin-top: 20px;" type="submit" value="Đăng nhập"/></td> <!-- background-color: #9a3558; -->
			</tr>
			<tr>
				<td style="text-align: right;"></td>
				<td style="text-align: left; padding-top: 10px;">
					<a href="<c:url value="forgetpassword.html"/>">Forget password?</a>
				</td>
			</tr>
			
		</table>
	</form>

</div>

<%@ include file="footer.jsp"%>