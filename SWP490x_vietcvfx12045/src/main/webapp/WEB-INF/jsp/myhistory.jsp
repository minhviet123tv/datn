<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:import url="header.jsp">
	<c:param name="title" value="Lịch sử quyên góp ${oneAccount.email}"></c:param> <%--Set tiêu đề cho header.jsp khi ở trang này --%>
</c:import>

<div class="adminright">
	<div style="text-align: center; padding-top:30px; padding-bottom:15px; border: 0px solid black; width: 95%; margin: auto;">
			<h2>Lịch sử quyên góp của: ${oneAccount.account_level} - ${oneAccount.email}</h2>
	</div>

	<div class="listcampaignindex" style="margin: auto; text-align: center; margin-top: 20px; padding-bottom:30px">
		<table class="admintable table table-bordered" style="margin: auto; background-color: white; width: 95%;">
			<tr>
				<th>Hoàn cảnh</th>
				<th>Ảnh bìa</th> <%-- 	<a href="<c:url value="/view1campaignindex.html?id_campaign=${campaign.id_campaign}"/>">Chi tiết </a> --%>
				<th>Tiêu đề</th>
				
				<th>Đã quyên góp (VNĐ)</th>
				<th>Ngày quyên góp</th>
				
				<th>Mục tiêu (VNĐ)</th>
				<th>Ngày kết thúc</th>
				<th>Tỉnh thành</th>
				<th>Tình trạng</th>
			</tr>
			
			<c:if test="${not empty historyDonateUserList}">
				<c:forEach var="campaign" items="${historyDonateUserList}">
					<tr>
						<td><c:out value="${campaign.campaign_code_name}"/></td>
						<td><a href="<c:url value="/view1campaignindex.html?id_campaign=${campaign.id_campaign}&id_campaign_code=${campaign.id_campaign_code}&status=${campaign.campaign_status}"/>"><img src="${campaign.image_cover_link}" width="150px"/></a></td>
						<td style="max-width:300px;"><a href="<c:url value="/view1campaignindex.html?id_campaign=${campaign.id_campaign}&id_campaign_code=${campaign.id_campaign_code}&status=${campaign.campaign_status}"/>"><c:out value="${campaign.title}"/></a></td>
						
						<td><fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="0" value="${campaign.donate_money}"/></td>
						<td><c:out value="${campaign.donate_date}"/></td>
						
						<td><fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="0" value="${campaign.target_money}"/></td>
						<td><c:out value="${campaign.campaign_end_date}"/></td>
						<td><c:out value="${campaign.province_name}"/></td>
						<td><a href="<c:url value="/view1campaignindex.html?id_campaign=${campaign.id_campaign}&id_campaign_code=${campaign.id_campaign_code}&status=${campaign.campaign_status}"/>">
								<c:if test="${campaign.campaign_status == '1'}">
									<button type="button" class="btn btn btn-outline-danger" style="text-align: center; width: 100%;">${campaign.campaign_status_name}</button>
								</c:if>
								<c:if test="${campaign.campaign_status == '2' }">
								<button type="button" class="btn btn btn-success" style="text-align: center; width: 100%">${campaign.campaign_status_name}</button>
								</c:if>
								<c:if test="${campaign.campaign_status == '3' }">
									<button type="button" class="btn btn btn-primary" style="text-align: center; width: 100%">${campaign.campaign_status_name}</button>
								</c:if>
							</a>
						</td>
					</tr>
				</c:forEach>
			</c:if>
		
		</table>
		
		<c:if test="${empty historyDonateUserList}">
			<br>
			Bạn chưa tham gia ủng hộ chiến dịch nào!
		</c:if>
		
		<br/><br/>
		
		<!-- Nút chọn trang -->
			
			<div class="center">
			  	<div class="pagination">
				  	<div style="margin: auto;">
						  <a href="<c:url value="/myhistory.html?indexpage=1"/>">&laquo;</a>
							 <c:forEach begin="1" end="${endPage}" var="i">
							 	<a class="${tagcurrent == i?'active': '' }" href="<c:url value="/myhistory.html?indexpage=${i}"/>"> ${i} </a>
							 </c:forEach>
						  <a href="<c:url value="/myhistory.html?indexpage=${endPage}"/>">&raquo;</a>
				 	</div>
				</div>
			</div>
	</div>
</div>

<%@ include file="footer.jsp"%>