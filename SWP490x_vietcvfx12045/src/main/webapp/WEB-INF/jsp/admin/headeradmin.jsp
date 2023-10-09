<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>     
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
 	<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
  
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <link href="<c:url value='/CSS/mystyle.css' />" rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath}/CSS/adminstyle.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
     
    <%-- <script src="${pageContext.request.contextPath}/libraries/ckeditor5-build-classic/ckeditor.js"></script> --%>
    <script src="${pageContext.request.contextPath}/libraries/ckeditor4-image/ckeditor.js"></script>
    <script src="{!! asset('ckeditor-dev/ckeditor.js') !!}"></script>
   
    <title>${param.title}</title>
<style>

.editaccount {
	width: 50%;
	padding: 10px;
	margin: auto;
	text-align: left;
}

@media screen and (max-width: 900px) {
	
	.editaccount {
		width: 95%;
	}
}

</style>
</head>

<body>