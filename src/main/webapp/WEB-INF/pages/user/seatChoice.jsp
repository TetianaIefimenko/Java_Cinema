<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/custom.tld" prefix="cctg"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${currentLocale}" />
<fmt:bundle basename="localization.msg" prefix="msg.jsp.seatChoice.">
	<fmt:message key="chooseSeat" var="chooseSeatLoc" />
	<fmt:message key="row" var="rowLoc" />
	<fmt:message key="number" var="numberLoc" />
	<fmt:message key="price" var="priceLoc" />
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

	<p>${chooseSeatLoc}</p>
	<div class="container" align="center">
		<c:forEach begin="1" end="20" step="1" varStatus="row">
			<c:forEach begin="1" end="25" step="1" varStatus="column">
				<cctg:CheckSeatExistence seats="${seatsWithStates}"
					row="${row.index}" column="${column.index}" />
				<c:choose>
					<c:when test="${seat!=null}">
						<form method="post"
							action="cinema?action=to_basket&chosenSeatId=${seat.id}&chosenFilmSessionId=${chosenFilmSession.id}"
							style="display: inline-block;">
							<button class="btn"
								title="${rowLoc}:${seat.row}
${numberLoc}:${seat.number}
${priceLoc}:${chosenFilmSession.ticketPrice}"
								style="background-color:${seat.state.buttonColor};"></button>
						</form>
					</c:when>
					<c:otherwise>
						<button class="btn btn-light" disabled="disabled"></button>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<br>
		</c:forEach>
	</div>

	<%@ include file="../include/footer.jsp"%>
</body>
</html>