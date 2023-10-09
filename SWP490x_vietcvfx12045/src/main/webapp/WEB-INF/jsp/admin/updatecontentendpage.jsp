<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>     
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="oneCampaign" value="${oneCampaign}"></c:set>
<c:set var="oneCampaignIndex" value="${oneCampaignIndex}"></c:set>
	
<c:import url="headeradmin.jsp">
	<c:param name="title" value="Cập nhật Campaign ${oneCampaign.id_campaign}"></c:param> <%--Set tiêu đề cho headeradmin.jsp khi ở trang này --%>
</c:import>

<div class="containeradmin">

	<c:import url="adminleft.jsp">
		<c:param name="title" value="Trang Menu trái Admin"></c:param>
	</c:import>

	<div class="adminright" style="background-color: #f0ffcf;">

		<img src="${pageContext.request.contextPath}/Media/BANNER1.png" width="100%">
		
		<div style="text-align: center; padding-top:25px; padding-bottom:15px; border: 0px solid black; width: 95%; margin: auto;">
				<h2>Cập nhật nội dung trao quà Campaign ${oneCampaign.id_campaign}</h2>
		</div>
			
		
		<br/>
		
		<!-- Trình bày nội dung chi tiết -->
	
		<div class="card contentadmin"> <!-- border: 1px solid black -->
		
			<div class="cardview1campaignindex" style="padding-bottom: 30px;">
			
				<div class="aligncenter" style="padding-top:15px; padding-bottom:15px; border: 0px solid black; width: 95%; margin: auto;">
						<h4>${oneCampaignIndex.title}</h4>
				</div>
				
				<div class="aligncenter">
					<img src="${oneCampaignIndex.image_cover_link}" style="max-width: 100%;"/>
				</div>
			</div>
			
			<br/>
			
			<!-- Hiện card thông tin (Phía trên ngay sau ảnh bìa) của campaign đang được xem đối với trường hợp đã đạt mục tiêu và đã trao quà -->
			
				<div class="card cardview1campaignindex">
					<div class="margin1 linkline1">
						<table class="remaining_date_table">
							<tr>
								<th class="tdnonborder fontsize1" ><c:out value="Tổng quyên góp:"/></th>
								<th class="tdnonborder" style="text-align: right;" rowspan="2"> <div class="fakebutton"> Còn ${oneCampaignIndex.remaining_date} ngày</div></th>
							</tr>
							<tr>
								<td class="tdnonborder"><fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="0" value="${oneCampaignIndex.sum_donate_money}"/><c:out value="/"/><fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="0" value="${oneCampaignIndex.target_money}"/></td>
							</tr>
						</table>
					</div>
					
					<c:set var="percent" value="${(oneCampaignIndex.sum_donate_money / oneCampaignIndex.target_money)*100}"></c:set>
					<div class="progress margin13" >
					  <div class="progress-bar" role="progressbar" style="width: ${percent}%" aria-valuenow="10" aria-valuemin="1" aria-valuemax="100"><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${percent}"/>%</div>
					</div>
					
						<table class="remaining_date_table">
							<tr>
								<th class="tdnonborder fontsize1 "><c:out value="ID"/></th>
								<th class="tdnonborder fontsize1 "><c:out value="Hoàn Cảnh"/></th>
								<th class="tdnonborder fontsize1 "><c:out value="Tỉnh Thành"/></th>
								<th class="tdnonborder fontsize1 "><c:out value="Lượt quyên góp"/></th>
							</tr>
							<tr>
								<td class="tdnonborder" ><c:out value="${oneCampaignIndex.id_campaign}"/></td>
								<td class="tdnonborder"><c:out value="${oneCampaignIndex.campaign_code_name}"/></td>
								<td class="tdnonborder" ><c:out value="${oneCampaignIndex.province_name}"/></td>
								<td class="tdnonborder" ><c:out value="${oneCampaignIndex.donate_number_total}"/></td>
							</tr>
							<tr>
								<th class="tdnonborder fontsize1" style="padding-top: 15px;"><c:out value="Ngày bắt đầu"/></th>
								<th class="tdnonborder fontsize1" style="padding-top: 15px;"><c:out value="Ngày kết thúc"/></th>
								<th class="tdnonborder fontsize1" style="padding-top: 15px;">Người sửa cuối cùng</th>
								<th class="tdnonborder fontsize1" style="padding-top: 15px;">Ngày sửa cuối cùng</th>		
							</tr>
							<tr>
								<td class="tdnonborder"><c:out value="${oneCampaignIndex.content_begin_date}"/></td>
								<td class="tdnonborder"><c:out value="${oneCampaignIndex.campaign_end_date}"/></td>
								
								<td class="tdnonborder">
									<c:if test="${empty oneCampaign.email_last_edit}">
										<c:out value="(Chưa có)"></c:out>
									</c:if>
									${oneCampaign.email_last_edit}
								</td>
								<td class="tdnonborder">
									<c:if test="${empty oneCampaign.account_last_edit_date}">
										<c:out value="(Chưa có)"></c:out>
									</c:if>
									${oneCampaign.account_last_edit_date}
								</td>
							</tr>
							
						</table>
						
						<c:if test="${oneCampaignIndex.status == '1'}">
							<div class="margin1" style="text-align: center; width: 100%">
								<button type="button" class="btn btn btn-danger" style="text-align: center; width: 100%;" id="myBtn">${oneCampaignIndex.status_name}</button>
							</div>
						</c:if>
						
						<c:if test="${oneCampaignIndex.status == '2'}">
							<div class="margin1" style="text-align: center; width: 100%">
								<button type="button" class="btn btn btn-success" style="text-align: center; width: 100%;" id="myBtn">${oneCampaignIndex.status_name}</button>
							</div> <!-- background-color: #28a745; -->
						</c:if>
						<c:if test="${oneCampaignIndex.status == '3'}">
							<div class="margin1" style="text-align: center; width: 100%">
								<button type="button" class="btn btn btn-primary" style="text-align: center; width: 100%;" id="myBtn">${oneCampaignIndex.status_name}</button>
							</div> <!-- background-color: #007bff; -->
						</c:if>
				</div>
				<br/>
						
				<form action="edit1campaignpage.html" method="get" style="margin-top: 15px; text-align: center;">
					<input type="hidden" name="id_campaign" value="${oneCampaign.id_campaign}"/>
					<input type="submit" value="Edit Campaign" class="btn btn-warning" style="min-width: 100px;"/>
				</form>
				
			<!--Hiện nội dung đã trao quà (nếu đã trao) -->
			<c:if test="${not empty oneCampaignIndex.content_end}">
				<div class="barcontentadmin" style="width: 100%;">
					<h4 style="margin-top: 10px;">Nội dung trao quà</h4>
					<p class="descriptionleft"><i>Posted date: ${oneCampaignIndex.content_end_date}</i></p>
					<div class ="contentcontainer textalignleft" style="width: 100%; padding: 18px;">${oneCampaignIndex.content_end}</div>
					<p class="descriptionright"><i>Posted by: ${oneCampaignIndex.email_post_content_end}</i></p>
				</div>
				<br/>
			</c:if>
			
				<!-- Nội dung trao quà -->
			
			<form action="updatecontentend.html" method="post">
			
				<input type="hidden" name="id_campaign" value="${oneCampaign.id_campaign}"/>
				<div class="barcontentadmin" style="width: 100%;">
					<h4 style="margin-top: 10px;">Nội dung trao quà</h4>
					<textarea class="ckeditor" id="contentend" name="contentend"> ${oneCampaign.content_end} </textarea>
				</div>
				
				<br>
				<p style='text-align:center; padding: 10px; '><input type="submit" value="Public" style="padding:10px"/>
				
			</form>
			
			<!--Hiện nội dung quyên góp -->
			<div class="barcontentadmin" style="width: 100%;">
					<h4 style="margin-top: 10px;">Nội dung quyên góp</h4>
					<p class="descriptionleft"><i>Posted date: ${oneCampaignIndex.content_begin_date}</i></p>
					<div class ="contentcontainer textalignleft" style="width: 100%; padding: 18px;">${oneCampaignIndex.content_begin}</div>
					<p class="descriptionright"><i>Posted by: ${oneCampaignIndex.email_post_content_begin}</i></p>
			</div>
			<br/>
			
		</div>
		
		<br/>
		
		<!-- Danh sách nhà hảo tâm -->
		
		<div class="card contentadmin"> <!-- border: 1px solid black -->
			<div class="barcontentadmin" style="padding-bottom: 30px;">
				<h3 style="padding: 10px;">Danh sách nhà hảo tâm</h3>
				
				<table class="donatelist">
					<tr>
						<th class="fontsize1 padding10px colorth">Email</th>
						<th class="fontsize1 colorth">Số tiền ủng hộ</th>
						<th class="fontsize1 colorth">Ngày ủng hộ</th>
						<th class="fontsize1 colorth">Cấp tài khoản</th>
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
				  <div class="pagination" style="justify-content: center;">
					  <a href="<c:url value="/view1campaign.html?indexpage=1&id_campaign=${oneCampaign.id_campaign}"/>">&laquo;</a>
						 <c:forEach begin="1" end="${endPageDonate}" var="i">
						 	<a class="${tagcurrent == i?'active': '' }" href="<c:url value="/view1campaign.html?indexpage=${i}&id_campaign=${oneCampaign.id_campaign}"/>"> ${i} </a>
						 </c:forEach>
					  <a href="<c:url value="/view1campaign.html?indexpage=${endPageDonate}&id_campaign=${oneCampaign.id_campaign}"/>" class="">&raquo;</a>
				  </div>
			</div>
		</div>
	
		<br><br>
		
			<form action="edit1campaignpage.html" method="get" style="text-align: center;">
				<input type="hidden" name="id_campaign" value="${oneCampaign.id_campaign}"/>
				<input type="submit" value="Edit Campaign" class="btn btn-warning" style="min-width: 100px;"/>
			</form>
			
		<br><br>
		
	</div>
	
</div>

<c:import url="footer.jsp"/>