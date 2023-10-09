<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>     
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="oneAccount" value="${oneAccount}"></c:set>
	
<c:import url="headeradmin.jsp">
	<c:param name="title" value="Đang xem Account ${oneAccount.id_account} - ${oneAccount.email}"></c:param> <%--Set tiêu đề cho headeradmin.jsp khi ở trang này --%>
</c:import>

<div class="containeradmin">

	<c:import url="adminleft.jsp"/>

	<div class="adminright" style="background-color: #f0ffcf;">

		<img src="${pageContext.request.contextPath}/Media/BANNER1.png" style="width:100%">
		
		<br>
		
		<div class="card" style="width: 95%; margin: auto; margin-top: 30px;">
			<div style="text-align: center; padding-top:15px; padding-bottom:15px; border: 0px solid black; width: 95%; margin: auto;">
					<h4>Thông tin chi tiết Account ID ${oneAccount.id_account} - ${oneAccount.email}</h4>
			</div>
			
			<table class="admintable table table-bordered" style="width: 95%;">
				<tr style="text-align: center; padding:10px; background-color: #a9c8ff;">
					<th>Edit</th>
					<th>ID</th>
					<th>Cấp Account</th>
					<th>Email</th>
					<th>Họ tên</th>
					<th>Giới tính</th>
					<th>Phone</th>
				</tr>
				<tr style="background-color: white;">
					<td>
						<form action="edit1accountpage.html" method="post">
						<input type="hidden" name="id_account" value="${oneAccount.id_account}"/>
						<input type="submit" value="Edit"/>
						</form>
					</td>
	
					<td>${oneAccount.id_account}</td>
					<td>${oneAccount.account_level}</td>
					<td>${oneAccount.email}</td>
					<td>${oneAccount.account_name}</td>
					<td>${oneAccount.gender_name}</td>
					<td>${oneAccount.phone}</td>
				</tr>
			</table>
			
			<br/>
			
			<table class="admintable table table-bordered" style="width: 95%;">
				<tr style="text-align: center; padding:10px; background-color: #a9c8ff;">
					<th>Tỉnh thành</th>
					<th>Ngày đăng ký</th>
					<th>Trạng thái</th>
					<th>Ngày đăng nhập gần đây</th>
					<th>Tổng tiền ủng hộ</th>
				</tr>
				<tr style="background-color: white;">
					<td>${oneAccount.province_name}</td>
					<td>${oneAccount.register_date}</td>
					<td>${oneAccount.status_name}</td>
					<td>${oneAccount.last_login_date}</td>
					<td><fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="0" value="${oneAccount.sum_donate_account}"/></td>
				</tr>
			</table>
		</div>
		
		
		<!-- LỊCH SỬ QUYÊN GÓP -->
		
		<div class="card" style="width: 95%; margin: auto; margin-top: 30px;">
			<div style="text-align: center; padding-top:15px; padding-bottom:15px; border: 0px solid black; width: 95%; margin: auto;">
				<h4>Lịch sử quyên góp của: ${oneAccount.account_level} - ${oneAccount.email}</h4>
			</div>
	
			<div style="margin: auto; text-align: center; margin-top: 20px; padding-bottom:50px; width: 95%;">
				<table class="admintable table table-bordered" style="margin: auto; background-color: white;">
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
								<td><a href="<c:url value="/view1campaign.html?id_campaign=${campaign.id_campaign}"/>"><img src="${campaign.image_cover_link}" width="150px"/></a></td>
								<td style="max-width:300px;"><a href="<c:url value="/view1campaign.html?id_campaign=${campaign.id_campaign}"/>"><c:out value="${campaign.title}"/></a></td>
								
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
					Tài khoản này chưa tham gia ủng hộ chiến dịch nào!
				</c:if>
				
			</div>
		
			
			<!-- Nút chọn trang -->
				
			<div class="center">
			  	<div class="pagination">
				  	<div style="margin: auto;">
						  <a href="<c:url value="/view1account.html?id_account=${oneAccount.id_account}"/>">&laquo;</a>
							 <c:forEach begin="1" end="${endPage}" var="i">
							 	<a class="${tagcurrent == i?'active': '' }" href="<c:url value="/view1account.html?indexpage=${i}&id_account=${oneAccount.id_account}"/>"> ${i} </a>
							 </c:forEach>
						  <a href="<c:url value="/view1account.html?indexpage=${endPage}&id_account=${oneAccount.id_account}"/>">&raquo;</a>
				 	</div>
				</div>
			</div>
			<br><br>
			
		</div>
		
		<br><br>
		
	</div>
	
</div>


<c:import url="footer.jsp"/>