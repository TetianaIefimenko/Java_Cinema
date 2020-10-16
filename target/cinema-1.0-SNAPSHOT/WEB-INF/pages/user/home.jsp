<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tld/custom.tld" prefix="cctg"%>

<fmt:setLocale value="${currentLocale}" />
<fmt:bundle basename="localization.msg" prefix="msg.jsp.home.">
	<fmt:message key="filmDetails" var="filmDetailsLoc" />
	<fmt:message key="genres" var="genresLoc" />
	<fmt:message key="description" var="descriptionLoc" />
	<fmt:message key="sortBy" var="sortByLoc" />
	<fmt:message key="sortByName" var="sortByNameLoc" />
	<fmt:message key="sortByDate" var="sortByDateLoc" />
	<fmt:message key="sortByFreeSeats" var="sortByFreeSeatsLoc" />
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
<div class="row">
	<form action="">
		<select name="sortBy">
			<option value="date">${sortByDateLoc}</option>
			<option value="name" >${sortByNameLoc}</option>
			<option value="free_tickets">${sortByFreeSeatsLoc}</option>
		</select>
		<input type="submit" value="Submit">
	</form>

	<div class="col-md-10">
	<c:import url="../include/header.jsp" />

	<div class="container">
		<c:forEach items="${filmWithGenres}" var="film">

			<div class="row">
				<h4>${film.filmName}</h4>
			</div>
			<div class="row">
				<div class=col-md-3>
					<a href="cinema?action=view_film_page&chosenFilmId=${film.id}"><img
						src="${film.posterUrl}" width="250" height="400" /></a>
				</div>
				<div class="col-md-8 container">
					<div>
						<b>${genresLoc}</b><br>
						<c:forEach items="${film.genres}" var="genre">
							<a
								href="cinema?action=view_genre_films&chosenGenreId=${genre.id}">
								${genre.genreName} </a>
						</c:forEach>
					</div>
					<br>
					<div>
						<b>${descriptionLoc}</b> <br> ${film.description}
					</div>
				</div>
			</div>
			<div align="right">
				<a href="cinema?action=view_film_page&chosenFilmId=${film.id}"
					class="btn btn-success btn-lg active" role="button"
					aria-pressed="true">${filmDetailsLoc}</a>
			</div>
			<hr>
		</c:forEach>
	</div>
	<div align="center">
		<cctg:Paginator urlprefix="cinema?step=5&start=" step="5"
			count="${allFilmsCount}" />
	</div>
	<br>
	<%@ include file="../include/footer.jsp"%>
	<%-- <c:import url="../include/footer.jsp" /> --%>
</body>
</html>