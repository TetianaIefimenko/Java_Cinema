<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${currentLocale}" />
<fmt:bundle basename="localization.msg" prefix="msg.jsp.logIn.">
	<fmt:message key="login" var="loginLoc" />
	<fmt:message key="password" var="passwordLoc" />
	<fmt:message key="logInButton" var="logInButtonLoc" />
</fmt:bundle>

<!DOCTYPE html>
<html>
<head>
<!-- Required meta tags -->
<meta http-equiv="Content-Type"
	content="width=device-width, initial-scale=1, shrink-to-fit=no, text/html"
	charset="UTF-8">
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="<c:url value="/resources/css/bootstrap.min.css"/>">
<title>Cinema</title>
</head>
<body>
	<c:import url="../include/header.jsp" />

	<form method="post" action="cinema?action=log_in">
		<div class="form-group">
			<label class="col-md-2">${loginLoc}</label> <input placeholder="${loginLoc}"
				id="userLogin" name="userLogin" required="required" value="admin" />
		</div>
		<div class="form-group">
			<label class="col-md-2">${passwordLoc}</label> <input
				type="password" placeholder="${passwordLoc}" id="userPassword"
				name="userPassword" required="required" value="Pass%1" />
		</div>
		<div class="col-md-4">
			<button id="singlebutton" name="singlebutton" class="btn btn-primary">${logInButtonLoc}</button>
		</div>
	</form>

	<%@ include file="../include/footer.jsp"%>
</body>
</html>