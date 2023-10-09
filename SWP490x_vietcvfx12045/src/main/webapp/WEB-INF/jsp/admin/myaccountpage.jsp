<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>     
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="oneAccount" value="${oneAccount}"/>
	
<c:import url="headeradmin.jsp">
	<c:param name="title" value="Đang sửa Account ${oneAccount.id_account} - ${oneAccount.email}"></c:param> <%--Set tiêu đề cho header.jsp khi ở trang này --%>
</c:import>

<script>
	//Xử lý khi giá trị nhập date là null (hiện tại nếu để trống thì chương trình không xử lý được nên đang dùng cách này. Có tìm cách get null date nếu cần)
	function validateForm() {
	  var b = document.forms["myForm"]["id_account_code"].value;
	  var c = document.forms["myForm"]["province_code"].value;
	  
	  //Kiểm tra giá trị giả (!x) gồm: undefined, null, false, "", 0 and -0, 0n, NaN	  
	  
	  if (!b || b==-1) {
		 alert("Vui lòng chọn: Cấp của Account");
		 return false;
	  }
	  
	  if (!c || c==00) {
		 alert("Vui lòng chọn: Tỉnh thành");
		 return false;
	  }
	}
</script>

<div class="containeradmin">
	<c:import url="adminleft.jsp"/>

	<div class="adminright">
		<img src="${pageContext.request.contextPath}/Media/BANNER1.png" style="width:100%">
		
		<div style="text-align: center; padding-top:15px; padding-bottom:15px; border: 0px solid black; width: 95%; margin: auto;">
				<h2>Đang sửa Account ID ${oneAccount.id_account} - ${oneAccount.email}</h2>
		</div>
		
		<form action="edit1account.html" method="post" name="myForm" onsubmit="return validateForm()">
			<input type="hidden" name="id_account" value="${oneAccount.id_account}"/> <!-- Ẩn ID nhưng vẫn truyền về Controller -->
			
			<table class="editaccount table table-bordered" style="background-color: white;">
				<tr style="padding:10px; text-align: left;"><th>ID</th></tr>
				<tr style="padding:10px; text-align: left;"><td>${oneAccount.id_account}</td></tr>
				<tr style="padding:10px; text-align: left;"><th>Email</th></tr>
				<tr style="padding:10px; text-align: left;"><td><input type="hidden" value="${oneAccount.email}" name="email" style="width:100%">${oneAccount.email}</td></tr>
				<tr><th>Password</th></tr>
				<tr><td><input type="password" value="${oneAccount.password}" name="password" style="width:100%" class="form-control"></td></tr>
				<tr><th>Họ tên</th></tr>
				<tr><td><input value="${oneAccount.account_name}" name="account_name" style="width:100%" class="form-control"></td></tr>
				<tr><th>Giới tính</th></tr>
				<tr><td>
						<select id="gender" name="gender" style="width:100%" class="form-control">
							<option value="${oneAccount.gender}">${oneAccount.gender_name}</option> <!-- Giới tính ban đầu đã chọn của account (Khác với tên cột của bảng Gender trong CSDL) -->
							<c:forEach var="listGenders" items="${listGenders}"> <!-- Danh sách lựa chọn từ CSDL - Đã loại trừ mã mà Account đang sử dụng -->
								<option value="${listGenders.id_gender}"> ${listGenders.gender_vn} </option>
							</c:forEach>
						</select>
				</td></tr>
				
				<tr><th>Cấp Account</th></tr>
				<tr><td>
						<select id="id_account_code" name="id_account_code" style="width:100%" class="form-control">
							<option value="${oneAccount.id_account_code}">${oneAccount.account_level}</option>
							<c:forEach var="listAccountCodes" items="${listAccountCodes}"> <!-- Danh sách lựa chọn từ CSDL -->
								<option value="${listAccountCodes.id_account_code}"> ${listAccountCodes.name_en} </option>
							</c:forEach>
						</select>
					</td></tr>
				<tr><th>Phone</th></tr>
				<tr><td><input value="${oneAccount.phone}" name="phone" style="width:100%" class="form-control"></input></td></tr>
				<tr><th>Tỉnh thành</th></tr>
				<tr><td>
						<select id="province_code" name="province_code" style="width:100%" class="form-control">
							<option value="${oneAccount.province_code}">${oneAccount.province_name}</option>
							<c:forEach var="listProvinces" items="${listProvinces}"> <!-- Danh sách lựa chọn từ CSDL -->
								<option value="${listProvinces.code}"> ${listProvinces.name} </option>
							</c:forEach>
						</select>
					</td></tr>
				<tr><th>Trạng thái</th></tr>
				<tr><td>
						<select id="status" name="status" style="width:100%" class="form-control">
							<option value="${oneAccount.status}">${oneAccount.status_name}</option>
							<c:forEach var="listAccountStatus" items="${listAccountStatus}"> <!-- Danh sách lựa chọn từ CSDL -->
								<option value="${listAccountStatus.status_code}"> ${listAccountStatus.status_name} </option>
							</c:forEach>
						</select>
					</td></tr>
			</table>
		
			<br/>
							
			<div style='text-align:center; padding: 10px; width: 20%; margin: auto;'>
				<input type="submit" value="Save" style="padding:10px"/>			
			</div>
			
			<br/>
			
		</form>
	</div>
</div>

<script>
	CKEDITOR.config.filebrowserImageUploadUrl = '{!! route('uploadPhoto').'?_token='.csrf_token() !!}';
</script>

<c:import url="footer.jsp"/>