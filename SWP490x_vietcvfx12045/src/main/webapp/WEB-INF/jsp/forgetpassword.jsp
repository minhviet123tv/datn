<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:import url="header.jsp">
	<c:param name="title" value="Login page - Đồ án tốt nghiệp"></c:param> <%--Set tiêu đề cho header.jsp khi ở trang này --%>
</c:import>
<div>
<h2 style="text-align: center; margin-top: 20px;">Vui lòng điền thông tin để lấy lại mật khẩu</h2>
      
   <form action="doforgetpassword.html" method="post" accept-charset="utf-8">

		<table id="loginspring" style="margin-top: 20px;">
			<tr>
				<td style="text-align: right; padding-right: 15px;">Email </td>
				<td><input class="form-control" type="text" name="emailforget" value="${emailforget}" maxlength="100" placeholder="Your Email" required="required"/></td> <%--Có thể đặt required để bắt buộc điền của form, hiện tại không đặt để hiểu cách tự Control --%>
			</tr>
			<tr>
				<td style="text-align: right; padding-right: 15px;">Phone </td>
				<td><input class="form-control" type="text" name="phoneforget" value="${phoneforget}" maxlength="100" placeholder="Your Phone Number"/></td>
			</tr>
				<c:if test="${not empty message}">
					<tr>
						<td style="text-align: center; padding-top: 15px; color: red;" colspan="2">${message}</td>
					</tr>
				</c:if>
			<tr>
				<td style="text-align: right; padding-right: 5px;"></td>
				<td style="text-align: left;"><input class="btn btn-outline-primary btn-lg" style="margin-top: 20px;" type="submit" value="OK"/></td>
			</tr>
		</table>
	</form>
	
	<br><br>
</div>

<%@ include file="footer.jsp"%>