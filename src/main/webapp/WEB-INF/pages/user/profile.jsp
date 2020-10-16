<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${currentLocale}" />
<fmt:bundle basename="localization.msg" prefix="msg.jsp.profile.">
	<fmt:message key="login" var="loginLoc" />
	<fmt:message key="email" var="emailLoc" />
	<fmt:message key="orderNumber" var="orderNumberLoc" />
	<fmt:message key="price" var="priceLoc" />
	<fmt:message key="total" var="totalLoc" />
	<fmt:message key="filmName" var="filmNameLoc" />
	<fmt:message key="date" var="dateLoc" />
	<fmt:message key="time" var="timeLoc" />
	<fmt:message key="seat" var="seatLoc" />
	<fmt:message key="payButton" var="payButtonLoc" />
	<fmt:message key="changePassword" var="changePasswordLoc" />
	<fmt:message key="enterPassword" var="enterPasswordLoc" />
	<fmt:message key="oldPassword" var="oldPasswordLoc" />
	<fmt:message key="newPassword" var="newPasswordLoc" />
	<fmt:message key="savePasswordButton" var="savePasswordButtonLoc" />
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

	<div class="row">
		<div class=col-md-2>${loginLoc}:</div>
		<div class=col-md-2>${currentUser.login}</div>
	</div>
	<div class="row">
		<div class=col-md-2>${emailLoc}:</div>
		<div class=col-md-2>${currentUser.email}</div>
	</div>
	<form class="form-horizontal"
		action="cinema?action=change_user_password" method="post">
		<fieldset>
			<!-- Button trigger modal -->
			<button type="button" class="col-md-3 btn btn-primary"
				data-toggle="modal" data-target="#exampleModal">${changePasswordLoc}</button>
			<!-- Modal -->
			<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
				aria-labelledby="exampleModalLabel" aria-hidden="true">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="exampleModalLabel">${enterPasswordLoc}</h5>
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<div class="modal-body">
							<!--Old password input-->
							<div class="form-group">
								<label class="col-md-12 control-label" for="newPassword">${oldPasswordLoc}</label>
								<div class="col-md-12">
									<input id="oldPassword" name="oldPassword" type="password"
										placeholder="${oldPasswordLoc}" class="form-control input-md"
										required="required" onkeyup="checkOldPassword()">
								</div>
							</div>
							<div class="col-md-12" id="oldPasswordResultValue"></div>
							<!--New password input-->
							<div class="form-group">
								<label class="col-md-12 control-label" for="newPassword">${newPasswordLoc}</label>
								<div class="col-md-12">
									<input id="newPassword" name="newPassword" type="password"
										placeholder="${newPasswordLoc}" class="form-control input-md"
										required="required" onkeyup="checkNewPassword()">
								</div>
							</div>
							<div class="col-md-12" id="newPasswordResultValue"></div>
							<!-- Button -->
							<div class="form-group">
								<label class="col-md-4 control-label" for="singlebutton"></label>
								<div class="col-md-4">
									<button id="singlebutton" name="singlebutton"
										class="btn btn-primary">${savePasswordButtonLoc}</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</fieldset>
	</form>

	<c:if test="${currentUserCurrentOrder!=null}">
		<hr style="border: 1px solid black;">
		<div align="center">
			<b>${orderNumberLoc} ${currentUserCurrentOrder.orderNumber}</b>
		</div>

		<c:set var="totalPrice" value="0" />
		<c:forEach items="${currentUserCurrentOrderTickets}" var="ticket"
			varStatus="loop">

			<c:set var="filmSession" value="${ticket.filmSession}" />
			<c:set var="film" value="${ticket.film}" />
			<c:set var="seat" value="${ticket.seat}" />
			<c:set var="ticketPrice" value="${filmSession.ticketPrice}" />
			<c:set var="totalPrice" value="${totalPrice+ticketPrice}" />

			<div class="row">
				<div class="col-md-0.5">${loop.count}.</div>
				<div class="row col-md-11">
					<div class="col-md-2">${filmNameLoc}:</div>
					<div class="col-md-9">${film.filmName}</div>
					<div class="col-md-2">${dateLoc}:</div>
					<div class="col-md-9">${filmSession.date}</div>
					<div class="col-md-2">${timeLoc}:</div>
					<div class="col-md-9">${filmSession.time}</div>
					<div class="col-md-2">${seatLoc}:</div>
					<div class="col-md-9">${seat.row}/${seat.number}</div>
				</div>
			</div>
			<div align="right">${priceLoc}:${ticketPrice}</div>
			<hr>
		</c:forEach>

		<div align="right" style="color: red;">
			<div class="container">
				<b>${totalLoc}: ${totalPrice}</b><br> <a
					href="cinema?action=payOrder&currentUserCurrentOrderId=${currentUserCurrentOrder.id}"><button
						class="btn btn-success btn-lg">${payButtonLoc}</button></a>
			</div>
		</div>
	</c:if>

	<%@ include file="../include/footer.jsp"%>

	<script type="text/javascript">
		function checkOldPassword() {
			$.ajax({
				url : 'cinema?action=checkChangingPassword',
				data : ({
					oldPassword : $('#oldPassword').val()
				}),
				success : function(data) {
					$('#oldPasswordResultValue').html(data);
				}
			})
		}
		function checkNewPassword() {
			$.ajax({
				url : 'cinema?action=checkChangingPassword',
				data : ({
					newPassword : $('#newPassword').val()
				}),
				success : function(data) {
					$('#newPasswordResultValue').html(data);
				}
			})
		}
	</script>

</body>
</html>