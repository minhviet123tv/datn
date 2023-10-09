<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>     
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:import url="header.jsp">
	<c:param name="title" value=" Trang chủ Đồ án tốt nghiệp"></c:param> <%--Set tiêu đề cho header.jsp khi ở trang này (home) --%>
</c:import>

<h2 style="text-align: center; margin-top: 20px;">Danh sách các chiến dịch quyên góp tìm được</h2>
<br>

<div>
			
	<div class="campaigngrid">
	
		<!-- Danh sách Card -->
		
		<c:forEach var="campaign" items="${listCampaignIndex}">
			<div class="card" style="width: 400px; margin: 10px; box-shadow: 5px 5px 15px #888888;">
				<a href="<c:url value="/view1campaignindex.html?id_campaign=${campaign.id_campaign}&id_campaign_code=${campaign.id_campaign_code}"/>"><img class="card-img-top" src="${campaign.image_cover_link}" alt="Card image cap"></a>
				<div class="margin1 linkline1"> <!-- card-body -->
					<a href="<c:url value="/view1campaignindex.html?id_campaign=${campaign.id_campaign}&id_campaign_code=${campaign.id_campaign_code}"/>"><h6 class="card-title"> ${campaign.title} </h6></a>
					
					<table class="remaining_date_table">
						<tr>
							<td class="tdnonborder"><fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="0" value="${campaign.sum_donate_money}"/><c:out value="/"/><fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="0" value="${campaign.target_money}"/></td>
							<td class="tdnonborder" style="float: right;"><div class="fakebutton">Còn ${campaign.remaining_date} ngày</div>
						</tr>
					</table>
				</div>
				
				<c:set var="percent" value="${(campaign.sum_donate_money / campaign.target_money)*100}"></c:set>
				<div class="progress margin13" >
				  <div class="progress-bar" role="progressbar" style="width: ${percent}%" aria-valuenow="10" aria-valuemin="1" aria-valuemax="100"><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${percent}"/>%</div>
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
						<a href="<c:url value="/view1campaignindex.html?id_campaign=${campaign.id_campaign}&id_campaign_code=${campaign.id_campaign_code}"/>">
						
							<c:if test="${campaign.status == '1' }">
								<button type="button" class="btn btn btn-outline-danger" style="text-align: center; width: 100%;">${campaign.status_name}</button>
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
	
	<div class="center">
	  <div class="pagination">
	  
		  <a href="<c:url value="/searchListCampaignIndex.html?status=${status}&province_code=${province_code}&id_campaign_code=${id_campaign_code}"/>">&laquo;</a>
			 <c:forEach begin="1" end="${endPageCampaign}" var="i">
			 	<a class="${tagcurrent == i?'active': '' }" href="<c:url value="/searchListCampaignIndex.html?status=${status}&indexpage=${i}&province_code=${province_code}&id_campaign_code=${id_campaign_code}"/>"> ${i} </a>
			 </c:forEach>
		  <a href="<c:url value="/searchListCampaignIndex.html?status=${status}&indexpage=${endPageCampaign}&province_code=${province_code}&id_campaign_code=${id_campaign_code}"/>" class="">&raquo;</a>
	  
	  </div>
	</div>
		
	<br>	
	
</div>

<%@ include file="footer.jsp"%>
