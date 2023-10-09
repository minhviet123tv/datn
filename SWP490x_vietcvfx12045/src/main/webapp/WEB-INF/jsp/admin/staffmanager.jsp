<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>     
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:import url="headeradmin.jsp">
	<c:param name="title" value="Staff Manager"></c:param> <%--Set tiêu đề cho header.jsp khi ở trang này --%>
</c:import>

<script>

//Xử lý khi giá trị nhập  là null (hiện tại nếu để trống thì chương trình không xử lý được nên đang dùng cách này. Có tìm cách get null date nếu cần)
	function validateForm() {
		  var x = document.forms["formsearch"]["hoancanh"].value;
		  var y = document.forms["formsearch"]["tinhthanh"].value;
		  var z = document.forms["formsearch"]["trangthai"].value;
	  

	  //Kiểm tra giá trị giả (!y) gồm: undefined, null, false, "", 0 and -0, 0n, NaN.
		if (y == 00) {
			alert("Vui lòng chọn: Tỉnh thành");
			return false;
		}

</script>

<div class="containeradmin">

	<c:import url="adminleft.jsp"/>

	<div class="adminright">
		<img src="${pageContext.request.contextPath}/Media/BANNER1.png" style="width:100%">
		
		<form action="searchKeyListAccount.html" method="get" style="padding-top:20px;"> 
			<table style="margin: auto;">
				<tr>
					<td class="searchtable"><b>Tìm kiếm:</b></td>
					<td class="searchtable"><input type="search" name="searchkey" class="form-control"></td>
					<td class="searchtable"><input type="submit" value="OK" class="btn btn-light"/></td>
				</tr>
			</table>
		</form>
		
		<form action="searchListAccount.html" method="get" name="formsearch" onsubmit="return validateForm()" style="padding-top:20px;"> <!-- onsubmit="return validateForm()"  -->
			<table style="margin: auto;">
				<tr>
					<td class="searchtable"><b>Tìm kiếm nâng cao: </b></td>
					<td class="searchtable"><c:out value="Cấp Account: "></c:out></td>
					<td class="searchtable">
							<select name="capaccount" class="form-control">
								<c:forEach var="listAccountCodes" items="${listAccountCodes}"> 
									<option value="${listAccountCodes.id_account_code}"> ${listAccountCodes.name_en} </option> 
								</c:forEach>
							</select>
					</td>
					<td class="searchtable"><c:out value="Tỉnh Thành: "></c:out></td>
					<td class="searchtable">
							<select name="tinhthanh" class="form-control">
								<c:forEach var="listProvinces" items="${listProvinces}">
									<option value="${listProvinces.code}"> ${listProvinces.name} </option>
								</c:forEach>
							</select>
					</td>
					<td class="searchtable"><c:out value="Trạng Thái: "></c:out></td>
					<td class="searchtable">
							<select name="trangthai" class="form-control">
								<c:forEach var="listAccountStatus" items="${listAccountStatus}">
									<option value="${listAccountStatus.status_code}"> ${listAccountStatus.status_name} </option>
								</c:forEach>
							</select>
					</td>
					<td class="searchtable"><input type="submit" value="OK" class="btn btn-light"/></td>
				</tr>
			</table>
		</form>
		
	<div class="card" style="width: 95%; margin: auto; padding: 20px; margin-bottom: 30px; margin-top: 30px;">
	
			<div style="text-align: center; padding-top:15px; padding-bottom:15px; border: 0px solid black; width: 95%; margin: auto;">
				<h2>Danh sách thành viên xếp theo ngày đăng nhập cuối</h2>
			</div>
				
		<table class="admintable1">

			<tr>
				<th class="colorthadmin">Edit</th>
				<th class="colorthadmin">ID</th>
				<th class="colorthadmin">Cấp Account</th>
				<th class="colorthadmin">Email</th>
				<th class="colorthadmin">Tên</th>
				<th class="colorthadmin">Tỉnh thành</th>
				<th class="colorthadmin">Ngày đăng nhập cuối</th>
				<th class="colorthadmin">Trạng thái</th>
				<th class="colorthadmin">Tổng tiền ủng hộ</th>
				<th class="colorthadmin">Xoá</th>
			</tr>

		<c:forEach var="listAccount" items="${listAccount}">
			<tr>
				<td>
					<form action="edit1accountpage.html" method="post">
					<input type="hidden" name="id_account" value="${listAccount.id_account}"/>
					<input type="submit" value="Edit"/>
					</form>
				</td>
				<td><c:out value="${listAccount.id_account}"/></td>
				<td><c:out value="${listAccount.account_level}"/></td>
				<td><a href="<c:url value="/view1account.html?id_account=${listAccount.id_account}"/>"><c:out value="${listAccount.email}"/></a></td>
				<td><c:out value="${listAccount.account_name}"/></td>
				<td><c:out value="${listAccount.province_name}"/></td>
				<td><c:out value="${listAccount.last_login_date}"/></td>
				<td><c:out value="${listAccount.status_name}"/></td>
				<td style="text-align: right; padding-right: 10px;"><fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="0" value="${listAccount.sum_donate_account}"/></td>
				<td>
					<form action="delete1account.html" method="get" style="padding:5px;">
						<input type="hidden" name="id_account" value="${listAccount.id_account}"/>
						<input type="submit" value="Xoá"/>
					</form>
				</td>
			</tr>

		</c:forEach>	
		</table>
		    
	    <c:if test="${empty listAccount}">
	    	<br>
	    	<p  style="margin: auto; text-align: center;"><c:out value='Không tồn tại kết quả với "${searchkey}"'></c:out></p>
	    </c:if>
		
			<br/>
		
		<!-- Nút chọn trang. Phân theo các trường hợp -->
			
			  	<div class="pagination">
				  	<div style="margin: auto;">
				  		<c:if test="${capaccount == null && tinhthanh == null && trangthai == null && searchkey == null}">
						  <a href="<c:url value="/staffmanager.html?indexpage=1"/>">&laquo;</a>
							 <c:forEach begin="1" end="${endPageAccount}" var="i">
							 	<a class="${tagcurrent == i?'active': '' }" href="<c:url value="/staffmanager.html?indexpage=${i}"/>"> ${i} </a>
							 </c:forEach>
						  <a href="<c:url value="/staffmanager.html?indexpage=${endPageAccount}"/>">&raquo;</a>
					   	</c:if>
					   	
					   	<c:if test="${capaccount != null && tinhthanh != null && trangthai != null}">
						  <a href="<c:url value="/searchListAccount.html??indexpage=1&capaccount=${capaccount}&tinhthanh=${tinhthanh}&trangthai=${trangthai}"/>">&laquo;</a>
							 <c:forEach begin="1" end="${endPageAccount}" var="i">
							 	<a class="${tagcurrent == i?'active': '' }" href="<c:url value="/searchListAccount.html?indexpage=${i}&capaccount=${capaccount}&tinhthanh=${tinhthanh}&trangthai=${trangthai}"/>"> ${i} </a>
							 </c:forEach>
						  <a href="<c:url value="/searchListAccount.html?indexpage=${endPageAccount}&capaccount=${capaccount}&tinhthanh=${tinhthanh}&trangthai=${trangthai}"/>">&raquo;</a>
					   	</c:if>
					   	
				   		<c:if test="${searchkey != null}">
						  <a href="<c:url value="/searchKeyListAccount.html?indexpage=1&searchkey=${searchkey}"/>">&laquo;</a>
							 <c:forEach begin="1" end="${endPageAccount}" var="i">
							 	<a class="${tagcurrent == i?'active': '' }" href="<c:url value="/searchKeyListAccount.html?indexpage=${i}&searchkey=${searchkey}"/>"> ${i} </a>
							 </c:forEach>
						  <a href="<c:url value="/searchKeyListAccount.html?indexpage=${endPageAccount}&searchkey=${searchkey}"/>">&raquo;</a>
					   	</c:if>
						  
				 	</div>
				</div>
			
			<br><br>
	</div>	
</div>

</div>

<c:import url="footer.jsp"/>