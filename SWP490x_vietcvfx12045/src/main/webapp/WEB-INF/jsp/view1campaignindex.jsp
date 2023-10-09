<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>     
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="oneCampaign" value="${oneCampaign}"></c:set>
	
<c:import url="header.jsp">
	<c:param name="title" value="${oneCampaign.title}"></c:param> <%--Set tiêu đề cho headeradmin.jsp khi ở trang này --%>
</c:import>

<div class="containerindex">

	<div class="card contentindex" > <!-- border: 1px solid black -->
	
		<div class="barcontent" style="padding-bottom: 30px;">
		
			<div class="aligncenter" style="padding-top:15px; padding-bottom:15px; border: 0px solid black; width: 95%; margin: auto;">
					<h4>${oneCampaign.title}</h4>
			</div>
			
			<div class="aligncenter">
				<img src="${oneCampaign.image_cover_link}" style="max-width: 100%;"/>
			</div>
		</div>
		
		<br/>
		
		<!-- Hiện card thông tin (Phía trên ngay sau ảnh bìa) của campaign đang được xem đối với trường hợp đã đạt mục tiêu và đã trao quà -->
		<c:if test="${oneCampaign.status == '2' || oneCampaign.status == '3'}">
		
			<div class="card cardview1campaignindex" style="width: 95%;">
				<div class="margin1 linkline1">
					<table class="remaining_date_table">
						<tr>
							<th class="tdnonborder fontsize1" ><c:out value="Tổng quyên góp:"/></th>
						<c:if test="${oneCampaign.status == '2'}">
							<th class="tdnonborder" style="text-align: right;" rowspan="2"> <div class="fakebutton"> Còn ${oneCampaign.remaining_date} ngày</div></th>
						</c:if>
						</tr>
						<tr>
							<td class="tdnonborder"><fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="0" value="${oneCampaign.sum_donate_money}"/><c:out value="/"/><fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="0" value="${oneCampaign.target_money}"/></td>
						</tr>
					</table>
				</div>
				
				<c:set var="percent" value="${(oneCampaign.sum_donate_money / oneCampaign.target_money)*100}"></c:set>
				<div class="progress margin13" >
				  <div class="progress-bar" role="progressbar" style="width: ${percent}%" aria-valuenow="10" aria-valuemin="1" aria-valuemax="100"><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${percent}"/>%</div>
				</div>
				
					<table class="remaining_date_table">
						<tr>
							
							<th class="tdnonborder fontsize1 "><c:out value="Lượt quyên góp"/></th>
							<th class="tdnonborder fontsize1 "><c:out value="Ngày kết thúc"/></th>
							<th class="tdnonborder fontsize1 "><c:out value="Hoàn Cảnh"/></th>
							<th class="tdnonborder fontsize1 "><c:out value="Tỉnh Thành"/></th>
							<th class="tdnonborder fontsize1 "><c:out value="Mã"/></th>
						</tr>
						<tr>
							
							<td class="tdnonborder"><c:out value="${oneCampaign.donate_number_total}"/></td>
							<td class="tdnonborder"><c:out value="${oneCampaign.campaign_end_date}"/></td>
							<td class="tdnonborder"><c:out value="${oneCampaign.campaign_code_name}"/></td>
							<td class="tdnonborder" ><c:out value="${oneCampaign.province_name}"/></td>
							<td class="tdnonborder" ><c:out value="${oneCampaign.id_campaign}"/></td>
						</tr>
					</table>
					
					<c:if test="${oneCampaign.status == '2'}">
						<div class="margin1" style="text-align: center; width: 100%">
							<button type="button" class="btn btn btn-success" style="text-align: center; width: 100%;" id="myBtn">${oneCampaign.status_name}</button>
						</div> <!-- background-color: #28a745; -->
					</c:if>
					<c:if test="${oneCampaign.status == '3'}">
						<div class="margin1" style="text-align: center; width: 100%">
							<button type="button" class="btn btn btn-primary" style="text-align: center; width: 100%;" id="myBtn">${oneCampaign.status_name}</button>
						</div> <!-- background-color: #007bff; -->
					</c:if>
			</div>
			<br/>
		</c:if>
		
		<!--Hiện nội dung đã trao quà (nếu đã trao) -->
		<c:if test="${not empty oneCampaign.content_end}">
			<div class="barcontent">
				<h4 style="margin-top: 10px;">Nội dung trao quà</h4>
				<p class="descriptionleft"><i>Posted date: ${oneCampaign.content_end_date}</i></p>
				<div class ="contentcontainer textalignleft" style="width: 100%; padding: 18px;">${oneCampaign.content_end}</div>
				<p class="descriptionright"><i>Posted by: ${oneCampaign.email_post_content_end}</i></p>
			</div>
			<br/>
		</c:if>
		
		<!--Hiện nội dung quyên góp -->
		
		<div class="barcontent">
				<h4 style="margin-top: 10px;">Nội dung quyên góp</h4>
				<p class="descriptionleft"><i>Posted date: ${oneCampaign.content_begin_date}</i></p>
				<div class ="contentcontainer textalignleft" style="width: 100%; padding: 18px;">${oneCampaign.content_begin}</div>
				<p class="descriptionright"><i>Posted by: ${oneCampaign.email_post_content_begin}</i></p>
		</div>
		<br/>
		
		<!-- Hiện card thông tin của campaign đang được xem (Phía dưới nội dung quyên góp) đối với trường hợp đang quyên góp -->
		
		<c:if test="${oneCampaign.status == '1'}">
			<div class="card barcontent">
					
				<div class="margin1 linkline1">
					
					<table class="remaining_date_table">
						<tr>
							<th class="tdnonborder fontsize1" ><c:out value="Tổng quyên góp:"/></th>
							<c:if test="${oneCampaign.status == '1'}">
								<th class="tdnonborder" style="text-align: right;" rowspan="2"> <div class="fakebutton"> Còn ${oneCampaign.remaining_date} ngày</div></th>
							</c:if>
						</tr>
						<tr>
							<td class="tdnonborder"><fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="0" value="${oneCampaign.sum_donate_money}"/><c:out value="/"/><fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="0" value="${oneCampaign.target_money}"/></td>
						</tr>
					</table>
				</div>
				
				<c:set var="percent" value="${(oneCampaign.sum_donate_money / oneCampaign.target_money)*100}"></c:set>
				<div class="progress margin13" >
				  <div class="progress-bar" role="progressbar" style="width: ${percent}%" aria-valuenow="10" aria-valuemin="1" aria-valuemax="100"><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${percent}"/>%</div>
				</div>
				
					<table class="remaining_date_table">
						<tr>
							<th class="tdnonborder fontsize1 "><c:out value="Lượt quyên góp"/></th>
							<th class="tdnonborder fontsize1 "><c:out value="Ngày kết thúc"/></th>
							<th class="tdnonborder fontsize1 "><c:out value="Hoàn Cảnh"/></th>
							<th class="tdnonborder fontsize1 "><c:out value="Tỉnh Thành"/></th>
							<th class="tdnonborder fontsize1 "><c:out value="Mã"/></th>
						</tr>
						<tr>
							<td class="tdnonborder"><c:out value="${oneCampaign.donate_number_total}"/></td>
							<td class="tdnonborder"><c:out value="${oneCampaign.campaign_end_date}"/></td>
							<td class="tdnonborder"><c:out value="${oneCampaign.campaign_code_name}"/></td>
							<td class="tdnonborder" ><c:out value="${oneCampaign.province_name}"/></td>
							<td class="tdnonborder" ><c:out value="${oneCampaign.id_campaign}"/></td>
						</tr>
					</table>
					
						<div class="margin1" style="text-align: center; width: 100%">
							<button type="button" class="btn btn btn-danger" style="text-align: center; width: 100%;" id="myBtn">Quyên góp</button>
						</div>
						
							<!-- MODEL BOX THỰC HIỆN QUYÊN GÓP -->
							<div id="myModal" class="modal">
							  <!-- Modal content -->
							  <div class="modal-content" style="margin-top: 7%;">
							    <span class="close" style="text-align: right;">&times;</span>
							   
							    <c:if test="${not empty email && id_account != '3' && id_account != '4'}">
							     	<p style="color: blue;"><b>${email}</b></p>
							    	<p><b>Nhập số tiền muốn quyên góp:</b></p>
							    
								    <form action="donatefromuser.html" method="post">
								    	<input type="hidden" name="id_account" value="${oneUser.id_account}">
								    	<input type="hidden" name="id_campaign" value="${oneCampaign.id_campaign}">
								    
								    	<p><input style="width: 50%; height: 40px;" type="number" name="donate_money" min="1000" required> &nbsp;&nbsp; <c:out value=" VNĐ"/></p>
								    	<input type="submit" value="Quyên góp" class="btn btn-outline-danger">
							    	</form>
							    </c:if>
							    
							     <c:if test="${(not empty email && id_account == '3') || not empty email && id_account == '4'}">
							    	<p><b>Chức năng này không dành cho quản trị viên </b></p>
							    </c:if>
							    
							    <c:if test="${empty email || empty keyShowMenu}">
							    	<form action="login.html" method="get">
							    		<b>Vui lòng <input type="submit" value="Đăng nhập" class="btn btn-outline-danger"> để sử dụng tính năng này! </b>
							    	</form>
							    	<br/>
							    </c:if>
							    
						    	
							  </div>
							</div>
			</div>
		</c:if>
		
	</div>
	
	<br/>
	
	<div class="card contentindex"> <!-- border: 1px solid black -->
	
		<!-- Danh sách nhà hảo tâm -->
		
		<div class="card barcontent" style="padding-bottom: 30px;">
			<h3 style="padding: 10px;">Danh sách nhà hảo tâm</h3>
			
			<table class="donatelist">
				<tr>
					<th class="fontsize1 padding10px colorth">Email</th>
					<th class="fontsize1 colorth">Số tiền ủng hộ</th>
					<th class="fontsize1 colorth">Ngày ủng hộ</th>
					<th class="fontsize1 colorth">Danh hiệu</th>
					<th class="fontsize1 colorth">Địa chỉ</th>
				</tr>
				<c:forEach var="accountDonate" items="${accountDonateList}">
				<tr>
					<td class="padding5px"><c:out value="${accountDonate.email}"></c:out></td>
					<td><fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="0" value="${accountDonate.donate_money}"/></td>
					<td><c:out value="${accountDonate.donate_date}"></c:out></td>
					<td><c:out value="${accountDonate.account_code_name}"></c:out></td>
					<td><c:out value="${accountDonate.province_name}"></c:out></td>
				</tr>
				</c:forEach>
			</table>
			
			<!-- Nút chọn trang -->
			<br>
			  <div class="pagination" style="margin: auto;">
				  <a href="<c:url value="/view1campaignindex.html?indexpage=1&id_campaign=${id_campaign_paging}&id_campaign_code=${id_campaign_code_paging}&status=${status_paging}"/>">&laquo;</a>
					 <c:forEach begin="1" end="${endPageDonate}" var="i">
					 	<a class="${tagcurrent == i?'active': '' }" href="<c:url value="/view1campaignindex.html?indexpage=${i}&id_campaign=${id_campaign_paging}&id_campaign_code=${id_campaign_code_paging}&status=${status_paging}"/>"> ${i} </a>
					 </c:forEach>
				  <a href="<c:url value="/view1campaignindex.html?indexpage=${endPageDonate}&id_campaign=${id_campaign_paging}&id_campaign_code=${id_campaign_code_paging}&status=${status_paging}"/>" class="">&raquo;</a>
			  </div>
		</div>
		
		<!-- Danh sách chương trình quyên góp khác có cùng hoàn cảnh -->
		
		<c:if test="${not empty listCampaignIndexCode}">
		
			<div class="barcontent" style="padding-bottom: 30px; padding-top: 10px; margin-top: 20px;">
				<h3 style="padding: 10px;">Chương trình quyên góp cùng hoàn cảnh "${oneCampaign.campaign_code_name}"</h3>
				
				<div class="campaigngrid">
					
					<!-- Danh sách Card -->
					
					<c:forEach var="campaign" items="${listCampaignIndexCode}">
						<div class="card" style="width: 350px; margin: 10px; box-shadow: 5px 5px 15px #888888;">
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
								<div class="progress margin13" >
								  <div class="progress-bar" role="progressbar" style="width: ${percent}%" aria-valuenow="10" aria-valuemin="1" aria-valuemax="100"><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${percent}"/>%</div>
								</div>
								
									<table class="remaining_date_table">
										<tr>
											<th class="tdnonborder fontsize1 floatleft1" ><c:out value="Hoàn Cảnh"/></th>
											<th class="tdnonborder fontsize1 floatright"><c:out value="Tỉnh Thành"/></th>
										</tr>
										<tr>
											<td class="tdnonborder floatleft1"><a href="<c:url value="/searchListCampaignIndex.html?id_campaign_code=${campaign.id_campaign_code}&province_code=-1&status=${campaign.status}"/>"> <c:out value="${campaign.name_vn}"/> </a></td>
											<td class="tdnonborder floatright" ><a href="<c:url value="/searchListCampaignIndex.html?id_campaign_code=-1&province_code=${campaign.province_code}&status=${campaign.status}"/>"><c:out value="${campaign.province_name}"/></a></td>
										</tr>
									</table>
									
									<div class="margin1" style="text-align: center; width: 100%">
										<a href="<c:url value="/view1campaignindex.html?id_campaign=${campaign.id_campaign}&id_campaign_code=${campaign.id_campaign_code}&status=${campaign.status}"/>">
											<c:if test="${oneCampaign.status == '1'}">
												<button type="button" class="btn btn btn-outline-danger" style="text-align: center; width: 100%">Quyên góp</button>
											</c:if>
											<c:if test="${oneCampaign.status == '2'}">
												<button type="button" class="btn btn btn-outline-success" style="text-align: center; width: 100%">${campaign.status_name}</button>
											</c:if>
											<c:if test="${oneCampaign.status == '3'}">
												<button type="button" class="btn btn btn-outline-primary margintop10px" style="text-align: center; width: 100%">${campaign.status_name}</button>
											</c:if>
										</a>
									</div>
						</div>
					</c:forEach>
					
				</div>
			</div>
			
		</c:if>
		
	</div>

</div>

<c:import url="footer.jsp"/>