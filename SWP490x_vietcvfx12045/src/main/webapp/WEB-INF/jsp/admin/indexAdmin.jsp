<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>     
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:import url="headeradmin.jsp">
	<c:param name="title" value="Trang chủ Admin"></c:param> <%--Set tiêu đề cho header.jsp khi ở trang này --%>
</c:import>

<script>

//Xử lý khi giá trị nhập  là null (hiện tại nếu để trống thì chương trình không xử lý được nên đang dùng cách này. Có tìm cách get null date nếu cần)
function validateForm() {
	  var x = document.forms["formsearch"]["id_campaign_code"].value;
	  var y = document.forms["formsearch"]["province_code"].value;
	  var z = document.forms["formsearch"]["status"].value;

	  //Kiểm tra giá trị giả (!x) gồm: undefined, null, false, "", 0 and -0, 0n, NaN. Kiểm tra cả x == 0 vì trong danh sách CSDL đã đặt phần -- Chọn -- mã là 0
	  if (!x || x == 0 ) {
	    alert("Vui lòng chọn: Hoàn cảnh");
	    return false;
	  } 
	  
	  if (!y || y == 00) {
			 alert("Vui lòng chọn: Tỉnh thành");
			 return false;
	  }
	  
	  if (!z || z == -2) {
			 alert("Vui lòng chọn: Tình trạng");
			 return false;
	  }
}

</script>

<div class="containeradmin">

	<c:import url="adminleft.jsp">
		<c:param name="title" value="Trang Menu trái Admin"></c:param> <%--Set tiêu đề cho header.jsp khi ở trang này --%>
	</c:import>

	<div class="adminright">
		<img src="${pageContext.request.contextPath}/Media/BANNER1.png" style="width:100%">
		
		<form action="searchListCampaignByKey.html" method="get" style="padding-top:30px;"> 
			<table style="margin: auto;">
				<tr>
					<td class="searchtable"><b>Tìm kiếm:</b></td>
					<td class="searchtable"><input type="search" name="searchkey" class="form-control"></td>
					<td class="searchtable"><input type="submit" value="OK" class="btn btn-light"/></td>
				</tr>
			</table>
		</form>
		
		<form action="searchListCampaign.html" method="get" name="formsearch" onsubmit="return validateForm()" style="padding-top:20px;">
			<table style="margin: auto;">
				<tr>
					<td class="searchtable"><b>Chọn lọc theo: </b></td>
					<td class="searchtable">
							<select name="id_campaign_code" class="form-control">
								<option value="-1">Hoàn cảnh</option>
								<c:forEach var="listCampaignCode" items="${listCampaignCodeModel}"> <!-- Chạy riêng danh sách các kiểu của Campaign (với Campaign code) -->
									<option value="${listCampaignCode.id_campaign_code}"> ${listCampaignCode.name_vn} </option> <!-- Giá trị thật sử dụng là ở value, còn giá trị hiển thị đặt ở giữa hai thẻ -->
								</c:forEach>
							</select>
					</td>
					<td class="searchtable">
							<select name="province_code" class="form-control">
								<option value="-1">Tỉnh thành</option>
								<c:forEach var="listProvinces" items="${listProvincesModel}">
									<option value="${listProvinces.code}"> ${listProvinces.name} </option>
								</c:forEach>
							</select>
					</td>
					<td class="searchtable">
							<select name="status" class="form-control">
								<option value="-1">Tình trạng</option>
								<option value="-2">-- Chọn --</option>
								<option value="0">Lưu nháp</option>
								<option value="1">Đang quyên góp</option>
								<option value="2">Đã đạt mục tiêu</option>
								<option value="3">Đã trao quà</option>
								<option value="4">Xoá</option>
							</select>
							
					</td>
					<td class="searchtable"><input type="submit" value="OK" class="btn btn-light"/></td>
				</tr>
			</table>
		</form>
		
		<br/>
			
			<div style="text-align: center; padding-top:15px; padding-bottom:15px; border: 0px solid black; width: 95%; margin: auto;">
				<form action="newCampaign.html" method="get"><b style="float:left; margin-top:8px;"><input type="submit" value="+ New Campaign" class="btn btn-warning"></b></form>
				<h2>Danh sách các Campaign hiện có</h2>
			</div>
					
			<table class="admintable1" >
	
				<tr>
					<th class="colorthadmin">Edit</th>
					<th class="colorthadmin">ID</th>
					<th class="colorthadmin">Hoàn cảnh</th>
					<th class="colorthadmin">Ảnh bìa</th>
					<th class="colorthadmin">Tiêu đề</th>
					<th class="colorthadmin">Ngày còn lại</th>
					<th class="colorthadmin">Ngày kết thúc</th>
					<th class="colorthadmin">Tỉnh thành</th>
					<th class="colorthadmin">Tiền ủng hộ/ Mục tiêu</th>
					<th class="colorthadmin">Tình trạng</th>
					<th class="colorthadmin">Nội dung trao quà</th>
					<th class="colorthadmin">Xoá</th>
				</tr>
	
			<c:forEach var="campaign" items="${listCampaignAdmin}">
				<tr>
					<td>
						<form action="edit1campaignpage.html" method="get">
						<input type="hidden" name="id_campaign" value="${campaign.id_campaign}"/>
						<input type="submit" value="Edit"/>
						</form>
					</td>
					<td><c:out value="${campaign.id_campaign}"/></td>
					<td><c:out value="${campaign.name_vn}"/></td>
					<td><a href="<c:url value="/view1campaign.html?id_campaign=${campaign.id_campaign}"/>"><img src="${campaign.image_cover_link}" width="120px"/></a></td>
					<td><a href="<c:url value="/view1campaign.html?id_campaign=${campaign.id_campaign}"/>"><c:out value="${campaign.title} ..."/></a></td>
					<td><c:out value="${campaign.remaining_date}"/></td>
					<td><c:out value="${campaign.campaign_end_date}"/></td>
					<td><c:out value="${campaign.province_name}"/></td>
	
					<td>
						<fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="0" value="${campaign.sum_donate_money}"/><c:out value="/"/><fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="0" value="${campaign.target_money}"/>
						
						<c:set var="percent" value="${(campaign.sum_donate_money / campaign.target_money)*100}"></c:set>
						<div class="progress" style="width: 95%; margin: auto; margin-top: 5px; margin-bottom: 5px; background-color: #c0c1c2;">
						  <div class="progress-bar" role="progressbar" style="width: ${percent}%" aria-valuenow="10" aria-valuemin="0" aria-valuemax="100">
						  	<fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${percent}"/>%
						  </div>
						</div>
					</td>
					<td>

						<c:if test="${campaign.status == '1'}">
							<div style="text-align: center; width: 95%; color: #dc3545;">Quyên góp</div>
						</c:if>
						
						<c:if test="${campaign.status == '2'}">
							<div style="text-align: center; width: 95%; color: #28a745;">Đạt mục tiêu</div>
						</c:if>
						
						<c:if test="${campaign.status == '3'}">
							<div style="text-align: center; width: 95%; color: #007bff;">${campaign.status_name}</div>
						</c:if>
						
						<c:if test="${campaign.status != '1' && campaign.status != '2' && campaign.status != '3'}">
							<div style="text-align: center; width: 95%;">${campaign.status_name}</div>
						</c:if>
					
					</td> 		
						<!-- Chỉ thiết lập nút Update đối với những Campaign có trạng thái status = 1 hoặc 2 (Đang quyên góp hoặc đã đạt mục tiêu) -->
					<td>
						<c:choose>
							<c:when test="${campaign.status == '1'}">
								<form action="updatecontentendpage.html" method="get">
									<input type="hidden" name="id_campaign" value="${campaign.id_campaign}"/>
									<input type="submit" value="Thêm nội dung" class="btn btn-outline-danger"/>
								</form>
							</c:when>
							
							<c:when test="${campaign.status == '2'}">
								<form action="updatecontentendpage.html" method="get" >
									<input type="hidden" name="id_campaign" value="${campaign.id_campaign}"/>
									<input type="submit" value="Thêm nội dung" class="btn btn-outline-success"/>
								</form>
							</c:when>
						</c:choose>
					</td>
					<td>
						<form action="delete1campaign.html" method="get" style="padding:5px;">
							<input type="hidden" name="id_campaign" value="${campaign.id_campaign}"/>
							<input type="submit" value="Xoá"/>
						</form>
					</td>
				</tr>
	
			</c:forEach>	
			</table>
			
			<c:if test="${empty listCampaignAdmin}">
			    <br>
			    <p  style="margin: auto; text-align: center;"><c:out value='Không có kết quả tìm kiếm!'></c:out></p>
			</c:if>
			    
			    <br/>
			    
			    <!-- Nút chọn trang. Phân theo các trường hợp -->
				
				  	<div class="pagination">
					  	<div style="margin: auto;">
					  	
					  		<!-- Trường hợp vào trang indexAdmin bình thường -->
					 		<c:if test="${(province_code == null) && (id_campaign_code == null) && searchkey == null}">
					 		
							  <a href="<c:url value="/indexAdmin.html?indexpage=1"/>">&laquo;</a>
								 <c:forEach begin="1" end="${endPageCampaign}" var="i">
								 	<a class="${tagcurrent == i?'active': '' }" href="<c:url value="/indexAdmin.html?indexpage=${i}"/>"> ${i} </a>
								 </c:forEach>
							  <a href="<c:url value="/indexAdmin.html?indexpage=${endPageCampaign}"/>">&raquo;</a>
							  
						  	</c:if>
					  		
					  		<!-- Trường hợp vào trang indexAdmin bằng cách chọn lọc theo 3 thông số -->
						  	<c:if test="${(province_code != null) && (id_campaign_code != null) && (status != null)}">
						  	
						  		<a href="<c:url value="/searchListCampaign.html?indexpage=1&status=${status}&id_campaign_code=${id_campaign_code}&province_code=${province_code}"/>">&laquo;</a>
								 <c:forEach begin="1" end="${endPageCampaign}" var="i">
								 	<a class="${tagcurrent == i?'active': '' }" href="<c:url value="/searchListCampaign.html?status=${status}&indexpage=${i}&id_campaign_code=${id_campaign_code}&province_code=${province_code}"/>"> ${i} </a>
								 </c:forEach>
							  	
							  	<a href="<c:url value="/searchListCampaign.html?status=${status}&indexpage=${endPageCampaign}&id_campaign_code=${id_campaign_code}&province_code=${province_code}"/>" class="">&raquo;</a>
						  	</c:if>
					  		
					  		<!-- Trường hợp vào trang indexAdmin bằng tìm kiếm searchkey -->
					  		<c:if test="${searchkey != null}">
							  <a href="<c:url value="/searchListCampaignByKey.html?indexpage=1&searchkey=${searchkey}"/>">&laquo;</a>
							 <c:forEach begin="1" end="${endPageCampaignSearchKey}" var="i">
							 	<a class="${indexcurrent == i?'active': '' }" href="<c:url value="/searchListCampaignByKey.html?indexpage=${i}&searchkey=${searchkey}"/>"> ${i} </a>
							 </c:forEach>
							  <a href="<c:url value="/searchListCampaignByKey.html?indexpage=${endPageCampaignSearchKey}&searchkey=${searchkey}"/>">&raquo;</a>
							  
						  	</c:if>
					  	
					 	</div>
					</div>
				
				<br/><br/>
		
		</div>
	
	<br>
	
</div>

<c:import url="footer.jsp"/>