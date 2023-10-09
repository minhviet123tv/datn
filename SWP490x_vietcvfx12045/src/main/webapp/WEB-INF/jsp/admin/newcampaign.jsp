<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>     
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
	
<c:import url="headeradmin.jsp">
	<c:param name="title" value="Đang thêm Campaign mới"></c:param> <%--Set tiêu đề cho header.jsp khi ở trang này --%>
</c:import>

<script>
	//Xử lý khi giá trị nhập date là null (hiện tại nếu để trống thì chương trình không xử lý được nên đang dùng cách này. Có tìm cách get null date nếu cần)
	function validateForm() {
	  var x = document.forms["myForm"]["campaignbegindate"].value;
	  var y = document.forms["myForm"]["campaignenddate"].value;
	  
	  //Kiểm tra giá trị giả (!x) gồm: undefined, null, false, "", 0 and -0, 0n, NaN	  
	  if (!x) {
	    alert("Vui lòng nhập: Ngày bắt đầu quyên góp");
	    return false;
	  }
	  
	  if (y == "" || y == null) {
		 alert("Vui lòng nhập: Ngày kết thúc quyên góp");
		 return false;
	  }
	}
</script>

<div class="containeradmin">
	<c:import url="adminleft.jsp"/>

	<div class="adminright" style="background-color: #fafcb6;">
		<img src="${pageContext.request.contextPath}/Media/BANNER1.png" style="width:100%">
		
		<div style="text-align: center; padding-top:15px; padding-bottom:15px; border: 0px solid black; width: 95%; margin: auto;">
			<h2>Thêm Campaign mới</h2>
		</div>
		
		<form name="myForm" action="addNewCampaign.html" onsubmit="return validateForm()" method="post">
			<table class="admintable">
				<tr>
					<th>Phân loại</th>
					<th>Link ảnh bìa</th>
					<th>Số tiền mục tiêu</th>
					<th>Tỉnh thành</th>
					<th>Ngày bắt đầu quyên góp</th>
					<th>Ngày kết thúc quyên góp</th>
				</tr>
				
				<tr>
					<!-- Tạo option (Vẫn nằm trong form lớn) là danh sách truyền từ CampaignController là: danh sách kiểu CampaignCode (kèm số code) và danh sách Tỉnh Thành (kèm code tỉnh - để truyền vào hàm add new Campaign) -->
					<td>
						 <select id="idcampaigncode" name="idcampaigncode" style="width:100%" class="form-control">
						  		<!-- Có thể đặt sẵn một option (điển hình như là giá trị sẵn lấy lên)
						  		<c:set var="oneNameCampaignCode" value="${oneCampaign}"/>
						    	<option name="name_vn2"> -- ${oneNameCampaignCode.name_vn} -- </option> -->
						    	<!-- <option value="0"> -- Chọn 2 -- </option> -->
						    	<c:forEach var="listCampaignCode" items="${listCampaignCodeModel}"> <!-- Chạy riêng danh sách các kiểu của Campaign (với Campaign code) -->
									<option value="${listCampaignCode.id_campaign_code}"> ${listCampaignCode.name_vn} </option> <!-- Giá trị thật sử dụng là ở value, còn giá trị hiển thị đặt ở giữa hai thẻ -->
								</c:forEach>
						 </select>
		
					</td>
					<td><input type="text" name="imagecoverlink" value="" style="width:100%;" maxlength="4000" class="form-control"/></td>
					<td><input type="number" name="targetmoney" value="0" style="width:100%;" min="1" max="100000000000000" class="form-control"/></td>
					<td>
						<select id="provincecode" name="provincecode" style="width:100%" class="form-control">
						    	<c:forEach var="listProvinces" items="${listProvincesModel}">
									<option value="${listProvinces.code}"> ${listProvinces.name} </option>
								</c:forEach>
						</select>	
					</td>
					<td><input type="date" name="campaignbegindate" id="campaignbegindate" style="width:100%;" class="form-control"/></td> <!-- Có thể cho thêm required để bắt buộc nhập dữ liệu nếu muốn. Hiện tại để xử lý null và hiện cửa sổ nhỏ thông báo  -->
					<td><input type="date" name="campaignenddate" id="campaignenddate" style="width:100%;" class="form-control"/></td>
					
				</tr>
				
			</table>
			
			<br/><br/>
			
			<table class="admintable">
				<tr>
					<th style="text-align: left;">Tiêu đề</th>
				</tr>
				<tr>
					<td><input type="text" value="" name ="title" style="width:100%; height:35px" maxlength="4000" class="form-control"/></td>
				</tr>

				<tr>
					<th style="text-align: left;">Nội dung quyên góp</th>
				</tr>
				<tr>
					<td><textarea class="ckeditor" id="contentbegin" name="contentbegin" placeholder="Nội dung" class="form-control"></textarea></td>
				</tr>
				
				<tr>
					<th style="text-align: left;">Chọn trạng thái chiến dịch</th>					
				</tr>
				<tr>
					<td>
						<select id="status" name="status" style="width:100%; padding:5px" class="form-control">
							<option value="1">Bắt đầu quyên góp</option>
							<option value="0">Lưu nháp</option>
						</select>						
					</td>
				</tr>
			</table>
					
			<div style='text-align:center; padding: 20px; width: 20%; margin: auto;'>							
				<input type="submit" value="OK" style="padding:15px"/>				
			</div>
			
		</form>
	</div>
</div>

<script>
	CKEDITOR.config.filebrowserImageUploadUrl = '{!! route('uploadPhoto').'?_token='.csrf_token() !!}';
</script>

<c:import url="footer.jsp"/>