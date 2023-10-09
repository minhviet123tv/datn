<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>     

<c:import url="header.jsp">
	<c:param name="title" value="Tạo tài khoản mới"></c:param> <%--Set tiêu đề cho header.jsp khi ở trang này --%>
</c:import>

<script>
function validateForm() {
	  var x = document.forms["formregister"]["gender"].value;
	  var y = document.forms["formregister"]["provincecode"].value;
	 
	  //Kiểm tra giá trị giả (!x) gồm: undefined, null, false, "", 0 and -0, 0n, NaN. Kiểm tra cả x == 00 vì trong danh sách CSDL đã đặt phần -- Chọn -- mã là 00
	  if (!x || x == 00 ) {
		    alert("Vui lòng chọn: Giới tính");
		    return false;
	  } 
	  
	  if (!y || y == 00 ) {
	    alert("Vui lòng chọn: Tỉnh thành");
	    return false;
	  } 
	  
}
</script>

        <div class="card col-md-6 col-md-offset-3" style="margin: auto; margin-top:30px; padding:30px;">
            <div class="panel panel-primary">
                <div class="panel-heading" style="text-align: center; padding-bottom:20px;">
	                <h2>Tạo tài khoản mới</h2>
	                <p style="color:red;">${message}</p>
                </div>
                
                <div class="panel-body">
                
                    <form action="register.html" method="post" name="formregister" onsubmit="return validateForm()">
						<div class="form-group">
							<label for="emailregister">Email của bạn *</label>
							<input type="email" id="emailregister" class="form-control" name="emailregister" value="${emailregister}" placeholder="text@text.text">
						</div>
						<div class="form-group">
							<label for="password">Mật khẩu *</label>
							<input type="password" id="password" class="form-control" name="password" value="${password}" placeholder="8-255 ký tự">
                        </div>
						<div class="form-group">
							<label for="verifypass">Nhập lại mật khẩu *</label>
							<input type="password" id="confirmpass" class="form-control" name="verifypass" value="${verifypass}" placeholder="8-255 ký tự">
                        </div>
                        <div class="form-group">
							<label for="name">Họ và tên</label>
							<input type="text" id="name" class="form-control" name="name" value="${name}" placeholder="Nguyễn Văn A">
                        </div>
						
                        <div class="form-group">
							<label for="phonenumber">Số điện thoại</label>
							<input type="text" id="phonenumber" class="form-control" name="phonenumber" value="${phonenumber}" placeholder="">
								
						</div>
						<div class="form-group">
							<label for="gender">Giới tính</label>
							<select id="gender" class="form-control" name="id_gender">
								<option value="${oneGender.id_gender}"> ${oneGender.gender_vn} </option>
								<c:forEach var="listGenders" items="${listGendersModel}">
									<option value="${listGenders.id_gender}"> ${listGenders.gender_vn} </option>
								</c:forEach>
							</select>
                        </div>
                        
                        <div class="form-group">
							<label for="provincecode">Tỉnh thành phố *</label>
							<select id="provincecode" class="form-control" name="provincecode">
								<option value="${oneProvince.code}"> ${oneProvince.name} </option>
								<c:forEach var="listProvinces" items="${listProvincesModel}">
									<option value="${listProvinces.code}"> ${listProvinces.name} </option>
								</c:forEach>
							</select>
                        </div>

						<div class="form-group text-center">
							<button type="submit" class="btn btn-primary btn-lg" id="submitbtn" name="submit"> Đăng ký </button>
                        </div>
                        
                    </form>
                    
                </div>
            </div>
        </div>

   
<%@ include file="footer.jsp"%>