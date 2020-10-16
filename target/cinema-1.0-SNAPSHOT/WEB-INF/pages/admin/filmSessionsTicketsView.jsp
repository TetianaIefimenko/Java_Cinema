<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tld/custom.tld" prefix="cctg"%>

<fmt:setLocale value="${currentLocale}" />
<fmt:bundle basename="localization.msg"
	prefix="msg.jsp.viewFilmSessionsTickets.">
	<fmt:message key="searchFilmMsg" var="searchFilmMsgLoc" />
	<fmt:message key="searchFilmSessionMsg" var="searchFilmSessionMsgLoc" />
	<fmt:message key="searchButton" var="searchButtonLoc" />
	<fmt:message key="user" var="userLoc" />
	<fmt:message key="filmName" var="filmNameLoc" />
	<fmt:message key="orderNumber" var="orderNumberLoc" />
	<fmt:message key="price" var="priceLoc" />
	<fmt:message key="countOfTicket" var="countOfTicketLoc" />
	<fmt:message key="sumOfTicket" var="sumOfTicketLoc" />
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

	<form class="read-film" action="cinema?action=viewFilmSessionsTickets"
		method=POST>

		<div class="container">
			<p>${searchFilmMsgLoc}</p>
			<div class="row">
				<div class=col-md-6>
					<select id="chosenFilmId" class="form-control" name="chosenFilmId"
						required="required">
						<c:forEach items="${filmlist}" var="film">
							<option value="${film.id}"
								${(chosenFilm!=null) && (chosenFilm.id==film.id)?"selected":""}>${film.filmName}</option>
						</c:forEach>
					</select>
				</div>
				<button class="col-md-1 btn btn-success">${searchButtonLoc}</button>
			</div>
		</div>
		<hr>
		<c:if test="${foundFilmSessions!=null}">
			<p>${searchFilmSessionMsgLoc}</p>
			<div class="row">
				<div class=col-md-6>
					<select id="chosenFilmSessionId" class="form-control"
						name="chosenFilmSessionId">
						<c:forEach items="${foundFilmSessions}" var="filmSession">
							<option value="${filmSession.id}">${filmSession.date}
								${filmSession.time}</option>
						</c:forEach>
					</select>
				</div>
				<button class="col-md-1 btn btn-success">${searchButtonLoc}</button>
			</div>
			<hr>
			<c:if
				test="${foundFilmSessionTcikets!=null&&foundFilmSessionTcikets.size()>0}">
				<div class="row">
					<div class=col-md-10>${countOfTicketLoc}</div>
					<div class=col-md-2>${soldTicketsCount}</div>
				</div>
				<div class="row">
					<div class=col-md-10>${sumOfTicketLoc}</div>
					<div class=col-md-2>${soldTicketsSum}</div>
				</div>
				<hr>
				<div class="container">
					<div class="row">
						<div class=col-md-1>ID</div>
						<div class=col-md-2>${userLoc}</div>
						<div class=col-md-3>${filmNameLoc}</div>
						<div class=col-md-2>${orderNumberLoc}</div>
						<div class=col-md-2>${priceLoc}</div>
					</div>
					<hr>
					<c:forEach items="${foundFilmSessionTcikets}" var="ticket">
						<div class="row">
							<div class=col-md-1>${ticket.id}</div>
							<div class=col-md-2>${ticket.user.login}</div>
							<div class=col-md-3>${ticket.film.filmName}</div>
							<div class=col-md-2>${ticket.ticketsOrder.orderNumber}</div>
							<div class=col-md-2>${ticket.filmSession.ticketPrice}</div>
						</div>
					</c:forEach>
				</div>
				<div align="center">
					<cctg:Paginator
						urlprefix="cinema?action=viewFilmSessionsTickets&chosenFilmId=${chosenFilm.id}&chosenFilmSessionId=${chosenFilmSession.id}&step=10&start="
						step="10" count="${soldTicketsCount}" />
				</div>
			</c:if>
		</c:if>
	</form>

	<%@ include file="../include/footer.jsp"%>
</body>
</html>