<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${currentLocale}" />
<fmt:bundle basename="localization.msg" prefix="msg.jsp.changeUserRole.">
	<fmt:message key="searchMsg" var="searchMsgLoc" />
	<fmt:message key="searchButton" var="searchButtonLoc" />
	<fmt:message key="updateButton" var="updateButtonLoc" />
	<fmt:message key="login" var="loginLoc" />
	<fmt:message key="email" var="emailLoc" />
	<fmt:message key="password" var="passwordLoc" />
	<fmt:message key="salt" var="saltLoc" />
	<fmt:message key="role" var="roleLoc" />
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



	<div class="container">
		<p>${searchMsgLoc}</p>
		<form class="read-user" action="cinema?action=change_user_role"
			method=POST>
			<div class="row">
				<div class=col-md-6>
					<input id="userLogin" class="form-control input-md"
						name="userLogin" />
				</div>
				<button id="read" value="read" name="crudCommand"
					class="btn btn-success">${searchButtonLoc}</button>
			</div>
		</form>
	</div>
	<hr>

	<c:if test="${foundUser!=null}">
		<div class="container">
			<div class="row">
				<div class=col-md-1>ID</div>
				<div class=col-md-2>${loginLoc}</div>
				<div class=col-md-2>${emailLoc}</div>
				<div class=col-md-2>${passwordLoc}</div>
				<div class=col-md-2>${saltLoc}</div>
				<div class=col-md-2>${roleLoc}</div>
			</div>
		</div>

		<div class="container">
			<form class="update-user" action="cinema?action=change_user_role"
				method=POST>
				<div class="row">
					<div class=col-md-1>
						<input id="userId" class="form-control input-md" name="userId"
							value="${foundUser.id}" readonly="readonly" />
					</div>
					<div class=col-md-2>
						<input id="userLogin" class="form-control input-md"
							name="userLogin" value="${foundUser.login}" readonly="readonly" />
					</div>
					<div class=col-md-2>
						<input id="userEmail" class="form-control input-md"
							name="userEmail" value="${foundUser.email}" readonly="readonly" />
					</div>
					<div class=col-md-2>
						<input id="userPassword" class="form-control input-md"
							name="userPassword" value="${foundUser.password}"
							readonly="readonly" />
					</div>
					<div class=col-md-2>
						<input id="userSalt" class="form-control input-md" name="userSalt"
							value="${foundUser.salt}" readonly="readonly" />
					</div>
					<div class=col-md-2>
						<select id="userRoleId" class="form-control" name="userRoleId">
							<c:forEach items="${rolelist}" var="role">
								<option value="${role.id}"
									${role.id==foundUser.roleId?"selected":""}>
									${role.roleName}</option>
							</c:forEach>
						</select>
					</div>

					<button id="update" value="update" name="crudCommand"
						class="btn btn-success">${updateButtonLoc}</button>
				</div>
			</form>
		</div>
	</c:if>


	<%@ include file="../include/footer.jsp"%>

</body>
</html>