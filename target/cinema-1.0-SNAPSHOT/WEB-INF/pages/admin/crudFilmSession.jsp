<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${currentLocale}" />
<fmt:bundle basename="localization.msg"
	prefix="msg.jsp.crudFilmSession.">
	<fmt:message key="createButton" var="createButtonLoc" />
	<fmt:message key="searchButton" var="searchButtonLoc" />
	<fmt:message key="updateButton" var="updateButtonLoc" />
	<fmt:message key="deleteButton" var="deleteButtonLoc" />
	<fmt:message key="film" var="filmLoc" />
	<fmt:message key="date" var="dateLoc" />
	<fmt:message key="time" var="timeLoc" />
	<fmt:message key="ticketPrice" var="ticketPriceLoc" />
	<fmt:message key="searchMsg" var="searchMsgLoc" />
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

	<!-- CRUD FilmSession -->
	<div class="container">
		<button class="btn btn-success btn-lg" type="button"
			data-toggle="collapse" data-target="#collapseExample"
			aria-expanded="false" aria-controls="collapseExample">${createButtonLoc}</button>

		<div class="collapse" id="collapseExample">
			<div class="card card-body">
				<div class="container">
					<div class="row">
						<div class=col-md-3>${filmLoc}</div>
						<div class=col-md-3>${dateLoc}</div>
						<div class=col-md-2>${timeLoc}</div>
						<div class=col-md-2>${ticketPriceLoc}</div>
					</div>

					<form class="create-filmSession"
						action="cinema?action=crud_filmSession&filmSessionId=0"
						method=POST>
						<div class="row">
							<div class=col-md-3>
								<select id="filmId" class="form-control" name="filmId"
									required="required">
									<c:forEach items="${filmlist}" var="film">
										<option value="${film.id}">${film.filmName}</option>
									</c:forEach>
								</select>
							</div>
							<div class=col-md-3>
								<input id="filmSessionDate" type="date"
									class="form-control input-md" name="filmSessionDate"
									required="required" />
							</div>
							<div class=col-md-2>
								<input id="filmSessionTime" type="time"
									class="form-control input-md" name="filmSessionTime"
									required="required" />
							</div>
							<div class=col-md-2>
								<input id="filmSessionTicketPrice" class="form-control input-md"
									name="filmSessionTicketPrice" placeholder="${ticketPriceLoc}"
									required="required" />
							</div>
							<button id="create" value="create" name="crudCommand"
								class="btn btn-success">ok</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<hr>
	<div class="container">
		<p>${searchMsgLoc}</p>
		<form class="read-film" action="cinema?action=crud_filmSession"
			method=POST>
			<div class="row">
				<div class=col-md-6>
					<select id="filmId" class="form-control" name="filmId"
						required="required">
						<c:forEach items="${filmlist}" var="film">
							<option value="${film.id}">${film.filmName}</option>
						</c:forEach>
					</select>
				</div>
				<button id="read" value="read" name="crudCommand"
					class="col-md-1 btn btn-success">${searchButtonLoc}</button>
			</div>
		</form>
	</div>
	<hr>

	<c:if test="${foundFilmSessions.size()>0}">
		<div class="container">
			<div class="row">
				<div class=col-md-1>ID</div>
				<div class=col-md-4>${filmLoc}</div>
				<div class=col-md-3>${dateLoc}</div>
				<div class=col-md-2>${timeLoc}</div>
				<div class=col-md-2>${ticketPriceLoc}</div>
			</div>
		</div>
		<div class="container">
			<c:forEach items="${foundFilmSessions}" var="filmSession">
				<form action="cinema?action=crud_filmSession" method=POST>
					<div class="row">
						<div class="col-md-1">
							<input id="filmSessionId" class="form-control input-md"
								name="filmSessionId" value="${filmSession.id}"
								readonly="readonly" />
						</div>
						<div class=col-md-4>
							<select id="filmId" class="form-control" name="filmId"
								required="required">
								<c:forEach items="${filmlist}" var="film">
									<option value="${film.id}"
										${film.id==filmSession.filmId? 'selected':''}>${film.filmName}</option>
								</c:forEach>
							</select>
						</div>
						<div class=col-md-3>
							<input id="filmSessionDate" type="date"
								class="form-control input-md" name="filmSessionDate"
								required="required" value="${filmSession.date}" />
						</div>
						<div class=col-md-2>
							<input id="filmSessionTime" type="time"
								class="form-control input-md" name="filmSessionTime"
								required="required" value="${filmSession.time}" />
						</div>
						<div class="col-md-2">
							<input id="filmSessionTicketPrice" class="form-control input-md"
								name="filmSessionTicketPrice" placeholder="${ticketPriceLoc}"
								required="required" value="${filmSession.ticketPrice}" />
						</div>
					</div>
					<button id="update" value="update" name="crudCommand"
						class="btn btn-success">${updateButtonLoc}</button>

					<button id="delete" value="delete" name="crudCommand"
						class="btn btn-danger">${deleteButtonLoc}</button>
				</form>
				<br>
			</c:forEach>
		</div>
	</c:if>

	<%@ include file="../include/footer.jsp"%>

</body>
</html>