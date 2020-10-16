<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${currentLocale}" />
<fmt:bundle basename="localization.msg" prefix="msg.jsp.signUp.">
	<fmt:message key="login" var="loginLoc" />
	<fmt:message key="email" var="emailLoc" />
	<fmt:message key="password" var="passwordLoc" />
	<fmt:message key="signUpButton" var="signUpButtonLoc" />
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


	<form method="post" action="cinema?action=sign_up">
		<div class="form-group row">
			<label class="col-md-2">${loginLoc}</label> <input class="col-md-3"
				placeholder="${loginLoc}" onkeyup="checkLogin()" required="required"
				name="userLogin" id="userLogin" />
			<div class="col-md-7" id="loginResultValue"></div>
		</div>

		<div class="form-group row">
			<label class="col-md-2">${emailLoc}</label> <input class="col-md-3"
				placeholder="${emailLoc}" onkeyup="checkEmail()" required="required"
				name="userEmail" id="userEmail" />
			<div class="col-md-7" id="emailResultValue"></div>
		</div>
		<div class="form-group row">
			<label class="col-md-2">${passwordLoc}</label> <input
				class="col-md-3" placeholder="${passwordLoc}"
				onkeyup="checkPassword()" required="required" name="userPassword"
				id="userPassword" />
			<div class="col-md-7" id="passwordResultValue"></div>
		</div>
		<div class="col-md-4">
			<button class="btn btn-primary">${signUpButtonLoc}</button>
		</div>
		<!-- <input type="submit" value="sign up" /> -->
	</form>

	<script type="text/javascript">
		function checkLogin() {
			$.ajax({
				url : 'cinema?action=checkUserRegistrationData',
				data : ({
					userLogin : $('#userLogin').val()
				}),
				success : function(data) {
					$('#loginResultValue').html(data);
				}
			});
		}

		function checkEmail() {
			$.ajax({
				url : 'cinema?action=checkUserRegistrationData',
				data : ({
					userEmail : $('#userEmail').val()
				}),
				success : function(data) {
					$('#emailResultValue').html(data);
				}
			})
		}
		function checkPassword() {
			$.ajax({
				url : 'cinema?action=checkUserRegistrationData',
				data : ({
					userPassword : $('#userPassword').val()
				}),
				success : function(data) {
					$('#passwordResultValue').html(data);
				}
			})
		}
	</script>


	<%@ include file="../include/footer.jsp"%>

</body>
</html>