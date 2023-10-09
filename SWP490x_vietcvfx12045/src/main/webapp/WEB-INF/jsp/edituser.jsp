<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:import url="header.jsp">
	<c:param name="title" value="${oneAccount.email}"></c:param> <%--Set tiêu đề cho header.jsp khi ở trang này --%>
</c:import>
	
	<div class="adminright">
		<div style="text-align: center; padding-top:30px; padding-bottom:15px; border: 0px solid black; width: 95%; margin: auto;">
				<h2>${oneAccount.account_level} - ${oneAccount.email}</h2>
				${message}
		</div>
		
		<form action="edituser.html" method="post">
			<input type="hidden" name="id_account" value="${oneAccount.id_account}"/> <!-- Ẩn ID nhưng vẫn truyền về Controller -->
			<!-- BẢNG THÔNG TIN CHI TIẾT CỦA MỘT USER -->
			
			<table class="editaccount table table-bordered" style="background-color: white; border-radius: 10px;">
				
				<tr style="padding:10px; text-align: left;"><th>Email</th></tr>
				<tr style="padding:10px; text-align: left;"><td><input type="hidden" value="${oneAccount.email}" name="email" style="width:100%">${oneAccount.email}</td></tr>
				
				<tr><th>Password</th></tr>
				<tr><td><input type="password" value="${oneAccount.password}" name="password" style="width:100%"></td></tr>
				
				<tr><th>Họ tên</th></tr>
				<tr><td><input value="${oneAccount.account_name}" name="account_name" style="width:100%"></td></tr>
				
				<tr><th>Giới tính</th></tr>
				<tr>
					<td>
						<select id="gender" name="gender" style="width:100%">
							<option value="${oneAccount.gender}">${oneAccount.gender_name}</option> <!-- Giới tính ban đầu đã chọn của account (Khác với tên cột của bảng Gender trong CSDL) -->
							<c:forEach var="listGenders" items="${listGenders}"> <!-- Danh sách lựa chọn từ CSDL - Đã loại trừ mã mà Account đang sử dụng -->
								<option value="${listGenders.id_gender}"> ${listGenders.gender_vn} </option>
							</c:forEach>
						</select>
					</td>
				</tr>
				
				<tr><th>Cấp Account</th></tr>
				<tr><td><input type="hidden" value="${oneAccount.id_account_code}" name="id_account_code" style="width:100%">${oneAccount.account_level}</td></tr>
				
				<tr><th>Phone</th></tr>
				<tr><td><input value="${oneAccount.phone}" name="phone" style="width:100%"></input></td></tr>
				
				<tr><th>Tỉnh thành</th></tr>
				<tr>
					<td>
						<select id="province_code" name="province_code" style="width:100%">
							<option value="${oneAccount.province_code}">${oneAccount.province_name}</option>
							<c:forEach var="listProvinces" items="${listProvinces}"> <!-- Danh sách lựa chọn từ CSDL -->
								<option value="${listProvinces.code}"> ${listProvinces.name} </option>
							</c:forEach>
						</select>
					</td>
				</tr>
				
				<tr><th>Trạng thái</th></tr>
				<tr><td><input type="hidden" value="${oneAccount.status}" name="status" style="width:100%">${oneAccount.status_name}</td></tr>
				
			</table>
			
			<br>
			<div style='text-align:center; padding: 10px; width: 20%; margin: auto;'>
				<input type="submit" value="Save" style="padding:10px"/>			
			</div>
			<br>
			
		</form>
	</div>

<%@ include file="footer.jsp"%>