<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>     
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:import url="header.jsp">
	<c:param name="title" value=" Trang chủ Đồ án tốt nghiệp"></c:param> <%--Set tiêu đề cho header.jsp khi ở trang này (home) --%>
</c:import>

<script>
//Xử lý khi giá trị nhập date là null (hiện tại nếu để trống thì chương trình không xử lý được nên đang dùng cách này. Có tìm cách get null date nếu cần)
function validateForm() {
	  var x = document.forms["formsearchindex"]["id_campaign_code"].value;
	  var y = document.forms["formsearchindex"]["province_code"].value;
	  var z = document.forms["formsearchindex"]["status"].value;
	  
	  //Kiểm tra giá trị giả (!x) gồm: undefined, null, false, "", 0 and -0, 0n, NaN. Kiểm tra cả x == 0 vì trong danh sách CSDL đã đặt phần -- Chọn -- mã là 0
	  if (!x) {
	    alert("Vui lòng chọn: Hoàn cảnh");
	    return false;
	  } 
	  
	  if (!y || y == 00) {
			 alert("Vui lòng chọn: Tỉnh thành");
			 return false;
	  }
	  
	  if (!z) {
			 alert("Vui lòng chọn: Tình trạng");
			 return false;
	  }
}
</script>


<h2 style="text-align: center; margin-top: 20px;">Danh sách các chiến dịch quyên góp</h2>

<div>
	<form action="searchListCampaignIndex.html" method="get" name="formsearchindex" onsubmit="return validateForm()">
		<input type="hidden" name="status" value="${status}">
		<table style="margin: auto;">
				<tr>
					<td class="searchtable" style="padding:30px;">
						<b>Chọn lọc theo: </b>
					</td>					
					<td class="searchtable">
						<select name="id_campaign_code" class="form-select" aria-label="Default select example">
							<option value="-1">-- Hoàn cảnh --</option>
							<c:forEach var="listCampaignCode" items="${listCampaignCodeModel}"> <!-- Chạy riêng danh sách các kiểu của Campaign (với Campaign code) -->
								<option value="${listCampaignCode.id_campaign_code}"> ${listCampaignCode.name_vn} </option> <!-- Giá trị thật sử dụng là ở value, còn giá trị hiển thị đặt ở giữa hai thẻ -->
							</c:forEach>
						</select>
					</td>
					<td class="searchtable">
						<select name="province_code" class="form-select" aria-label="Default select example">
							<option value="-1">-- Tỉnh thành --</option>
							<c:forEach var="listProvinces" items="${listProvincesModel}">
								<option value="${listProvinces.code}"> ${listProvinces.name} </option>
							</c:forEach>
						</select>
					</td>
					<td class="searchtable">
							<input type="submit" value="OK" class="btn btn-outline-dark"/>
					</td>
				</tr>	
			</table>
		</form>
		
	<div class="campaigngrid">
	
		<!-- Danh sách Card -->
		
		<c:forEach var="campaign" items="${listCampaignIndex}">
			<div class="card" style="width: 400px; margin: 10px; box-shadow: 5px 5px 15px #888888;">
				<a href="<c:url value="/view1campaignindex.html?id_campaign=${campaign.id_campaign}&id_campaign_code=${campaign.id_campaign_code}&status=${campaign.status}"/>"><img class="card-img-top" src="${campaign.image_cover_link}" alt="Card image cap"></a>
				<div class="margin1 linkline1"> <!-- card-body -->
					<a href="<c:url value="/view1campaignindex.html?id_campaign=${campaign.id_campaign}&id_campaign_code=${campaign.id_campaign_code}&status=${campaign.status}"/>"><h6 class="card-title"> ${campaign.title} </h6></a>
					
					<table class="remaining_date_table">
						<tr>
							<td class="tdnonborder"><fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="0" value="${campaign.sum_donate_money}"/><c:out value="/"/><fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="0" value="${campaign.target_money}"/></td>
							<td class="tdnonborder" style="float: right;"><div class="fakebutton">Còn ${campaign.remaining_date} ngày</div>
						</tr>
					</table>
				</div>
				
				<c:set var="percent" value="${(campaign.sum_donate_money / campaign.target_money)*100}"></c:set>
				<div class="progress margin13">
				  <div class="progress-bar" role="progressbar" style="width: ${percent}%" aria-valuenow="10" aria-valuemin="1" aria-valuemax="100"><fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="1" value="${percent}"/>%</div>
				</div>
				
					<table class="remaining_date_table">
						<tr>
							<th class="tdnonborder fontsize1" ><c:out value="Hoàn Cảnh"/></th>
							<th class="tdnonborder fontsize1 floatright"><c:out value="Tỉnh Thành"/></th>
						</tr>
						<tr>
							<td class="tdnonborder"><a href="<c:url value="/searchListCampaignIndex.html?id_campaign_code=${campaign.id_campaign_code}&province_code=-1&status=${campaign.status}"/>"> <c:out value="${campaign.name_vn}"/> </a></td>
							<td class="tdnonborder floatright"><a href="<c:url value="/searchListCampaignIndex.html?id_campaign_code=-1&province_code=${campaign.province_code}&status=${campaign.status}"/>"><c:out value="${campaign.province_name}"/></a></td>
						</tr>
					</table>
					<div class="margin1" style="text-align: center; width: 100%">
						<a href="<c:url value="/view1campaignindex.html?id_campaign=${campaign.id_campaign}&id_campaign_code=${campaign.id_campaign_code}&status=${campaign.status}"/>">
						
							<c:if test="${campaign.status == '1' }">
								<button type="button" class="btn btn btn-outline-danger" style="text-align: center; width: 100%;">Quyên góp</button>
							</c:if>
							<c:if test="${campaign.status == '2' }">
								<button type="button" class="btn btn btn-success" style="text-align: center; width: 100%">${campaign.status_name}</button>
							</c:if>
							<c:if test="${campaign.status == '3' }">
								<button type="button" class="btn btn btn-primary" style="text-align: center; width: 100%">${campaign.status_name}</button>
							</c:if>
							
						</a>
					</div>
			</div>
		</c:forEach>
	</div>
	
	<c:if test="${empty listCampaignIndex}">
		<p style="text-align: center;"> Không có kết quả tìm kiếm!</p>
	</c:if>
	
	<br>
	
	<!-- Nút chọn trang -->
	
	<div class="center">
	  <div class="pagination">
	  
  		<c:if test="${(province_code == null) && (id_campaign_code == null) && (searchkey == null) }">
		  <a href="<c:url value="/index.html?indexpage=1&status=${status}"/>">&laquo;</a>
			 <c:forEach begin="1" end="${endPageCampaign}" var="i">
			 	<a class="${tagcurrent == i?'active': '' }" href="<c:url value="/index.html?status=${status}&indexpage=${i}"/>"> ${i} </a>
			 </c:forEach>
		  <a href="<c:url value="/index.html?status=${status}&indexpage=${endPageCampaign}"/>" class="">&raquo;</a>
	  	</c:if>
	  	
	  	<c:if test="${(province_code != null) && (id_campaign_code != null)}">
	  		<a href="<c:url value="/searchListCampaignIndex.html?indexpage=1&status=${status}&id_campaign_code=${id_campaign_code}&province_code=${province_code}"/>">&laquo;</a>
			 <c:forEach begin="1" end="${endPageCampaign}" var="i">
			 	<a class="${tagcurrent == i?'active': '' }" href="<c:url value="/searchListCampaignIndex.html?status=${status}&indexpage=${i}&id_campaign_code=${id_campaign_code}&province_code=${province_code}"/>"> ${i} </a>
			 </c:forEach>
		  	<a href="<c:url value="/searchListCampaignIndex.html?status=${status}&indexpage=${endPageCampaign}&id_campaign_code=${id_campaign_code}&province_code=${province_code}"/>" class="">&raquo;</a>
	  	</c:if>
	  	
	  	<c:if test="${searchkey != null}">
	  		<a href="<c:url value="/searchListCampaignIndexByKey.html?indexpage=1&searchkey=${searchkey}"/>">&laquo;</a>
			 <c:forEach begin="1" end="${endPageCampaign}" var="i">
			 	<a class="${tagcurrent == i?'active': '' }" href="<c:url value="/searchListCampaignIndexByKey.html?indexpage=${i}&searchkey=${searchkey}"/>"> ${i} </a>
			 </c:forEach>
		  	<a href="<c:url value="/searchListCampaignIndexByKey.html?indexpage=${endPageCampaign}&searchkey=${searchkey}"/>" class="">&raquo;</a>
	  	</c:if>
	  	
	  </div>
	</div>
		
	<br>	
</div>

<%@ include file="footer.jsp"%>
