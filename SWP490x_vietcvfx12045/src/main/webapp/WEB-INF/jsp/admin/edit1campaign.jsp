<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>     
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="oneCampaign" value="${oneCampaign}"/>
	
<c:import url="headeradmin.jsp">
	<c:param name="title" value="Đang sửa Campaign ${oneCampaign.id_campaign}"></c:param> <%--Set tiêu đề cho header.jsp khi ở trang này --%>
</c:import>

<script>
	//Xử lý khi giá trị nhập date là null (hiện tại nếu để trống thì chương trình không xử lý được nên đang dùng cách này. Có tìm cách get null date nếu cần)
	function validateForm() {
	  var x = document.forms["myForm"]["campaignbegindate"].value;
	  var y = document.forms["myForm"]["campaignenddate"].value;
	  var z = document.forms["myForm"]["provincecode"].value;
	  
	  //Kiểm tra giá trị giả (!x) gồm: undefined, null, false, "", 0 and -0, 0n, NaN (Đã xử lý thành công)
	  if (!x) {
	    alert("Vui lòng nhập: Ngày bắt đầu quyên góp");
	    return false;
	  }
	  
	  if (y == "" || y == null) {
		 alert("Vui lòng nhập: Ngày kết thúc quyên góp");
		 return false;
	  }
	  
	  if (!z || z == 00) {
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
				<h2>Campaign ${oneCampaign.id_campaign}</h2>
		</div>
		
		<form action="edit1campaign.html" method="post" name="myForm" onsubmit="return validateForm()">
			<input type="hidden" name="idcampaign" value="${oneCampaign.id_campaign}">
			
			<table class="admintable" style="border: 0px solid black;">
				<tr>
					<td style="text-align: center; border: 0px solid black; padding-bottom: 20px" colspan="6"><img src="${oneCampaign.image_cover_link}" style="width:50%;" /></td> <!-- Có thể Crop ảnh theo lệnh: object-fit: cover; object-position: 100% 0; -->
				</tr>
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
						    	<option value="${oneCampaign.id_campaign_code}">${oneCampaign.name_vn}</option> <!-- Đặt sẵn giá trị vốn có của Campaign đang được xử lý -->
						    	
						    	<c:forEach var="listCampaignCode" items="${listCampaignCodeModel}"> <!-- Chạy riêng danh sách các kiểu của Campaign (với Campaign code) -->
									<option value="${listCampaignCode.id_campaign_code}"> ${listCampaignCode.name_vn} </option> <!-- Giá trị thật sử dụng là ở value, còn giá trị hiển thị đặt ở giữa thẻ -->
								</c:forEach>
						 </select>
		
					</td>
					<td><input type="text" name="imagecoverlink" value="${oneCampaign.image_cover_link}" style="width:100%;" maxlength="4000" class="form-control"/></td>
					<td><input type="number" name="targetmoney" value="${oneCampaign.target_money}" style="width:100%;" min="1" max="100000000000000" class="form-control"/></td>
					<td>
						<select id="provincecode" name="provincecode" style="width:100%" class="form-control">
									<option value="${oneCampaign.province_code}"> ${oneCampaign.province_name} </option>
						    	<c:forEach var="listProvinces" items="${listProvincesModel}">
									<option value="${listProvinces.code}"> ${listProvinces.name} </option>
								</c:forEach>
						</select>	
					</td>
					<td><input type="date" name="campaignbegindate" value="${oneCampaign.campaign_begin_date}" style="width:100%;" class="form-control"/></td>
					<td><input type="date" name="campaignenddate" value="${oneCampaign.campaign_end_date}" style="width:100%;" class="form-control"/></td>
				</tr>
				
			</table>

			<br/><br/>
			
			<table class="admintable">
				<tr>
					<th style="text-align: left;">Tiêu đề</th>
				</tr>
				<tr>
					<td><input type="text" value="${oneCampaign.title}" name ="title" style="width:100%; height:35px" maxlength="4000" class="form-control"/></td>
				</tr>

				<tr>
					<th style="text-align: left;">Nội dung quyên góp</th>
				</tr>
				<tr>
					<td><textarea class="ckeditor" id="contentbegin" name="contentbegin" class="form-control">${oneCampaign.content_begin}</textarea></td>
				</tr>

				<tr>
					<th style="text-align: left;">Nội dung trao quà</th>
				</tr>
				<tr>
					<td><textarea class="ckeditor" id="contentend" name="contentend" class="form-control">${oneCampaign.content_end}</textarea></td>
				</tr>
				
				<tr>
					<th style="text-align: left;">Chọn trạng thái chiến dịch</th>					
				</tr>
				
				<tr>
					<td>
						<select id="status" name="status" style="width:100%; padding:5px" class="form-control">
							<option value="${oneCampaign.status}">${oneCampaign.status_name}</option>
							<option value="0">-- Chọn --</option>
							<option value="0">Lưu nháp</option>
							<option value="1">Đang quyên góp</option>
							<option value="2">Đã đạt mục tiêu</option>
							<option value="3">Đã trao quà</option>		<!-- Đúc kết lại nên tạo bảng mới (cho status) trong CSDL. Còn dùng ở jsp, jstl thì nên dùng choose, lưu ý cú pháp điều kiện =='number' -->						
							<option value="4">Xoá</option>
						</select>						
					</td>
				</tr>
			</table>
			<br>		
				<div style='text-align:center; padding: 10px; width: 20%; margin: auto;'>							
					<input type="submit" value="Save" style="padding:10px"/>				
				</div>
			<br>
		</form>
	</div>
</div>

<script>
	CKEDITOR.config.filebrowserImageUploadUrl = '{!! route('uploadPhoto').'?_token='.csrf_token() !!}';
</script>

<c:import url="footer.jsp"/>