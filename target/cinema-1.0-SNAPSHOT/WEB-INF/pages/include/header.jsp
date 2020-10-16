<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tld/custom.tld" prefix="cctg"%>

<fmt:setLocale value="${currentLocale}" />
<fmt:bundle basename="localization.msg" prefix="msg.jsp.header.">
	<fmt:message key="home" var="homeLoc" />
	<fmt:message key="adminCommands" var="adminCommandsLoc" />
	<%--<fmt:message key="sortBy" var="sortByLoc" />
	<fmt:message key="sortByName" var="sortByNameLoc" />
	<fmt:message key="sortByDate" var="sortByDateLoc" />
	<fmt:message key="sortByFreeSeats" var="sortByFreeSeatsLoc" />--%>
	<fmt:message key="changeUserRole" var="changeUserRoleLoc" />
	<fmt:message key="crudFilm" var="crudFilmLoc" />
	<fmt:message key="crudFilmSession" var="crudFilmSessionLoc" />
	<fmt:message key="logOut" var="logOutLoc" />
	<fmt:message key="logIn" var="logInLoc" />
	<fmt:message key="signUp" var="signUpLoc" />
	<fmt:message key="chooseGenre" var="chooseGenreLoc" />
	<fmt:message key="viewFilmSessionsTickets"
		var="viewFilmSessionsTicketsLoc" />
</fmt:bundle>
<div class="container">
	<nav class="navbar navbar-light bg-light">
		<div class="row mx-auto">
			<a class="nav-link" href="cinema?action=change_locale&locale=en_US">eng</a>
			<a class="nav-link" href="cinema?action=change_locale&locale=ru_RU">rus</a>
		</div>
	</nav>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<a class="navbar-brand" href="/cinema_war_exploded">${homeLoc}</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown"
			aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>

		<div class="collapse navbar-collapse" id="navbarNavDropdown">
			<ul class="nav navbar-nav">
				<c:if test="${currentUser.roleId==1}">
					<div class="dropdown">
						<button class="btn btn-secondary dropdown-toggle" type="button"
							id="dropdownMenuButton" data-toggle="dropdown"
							aria-haspopup="true" aria-expanded="false">${adminCommandsLoc}</button>
						<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
							<a class="dropdown-item" href="cinema?action=change_user_role">${changeUserRoleLoc}</a>
							<div class="dropdown-divider"></div>
							<a class="dropdown-item" href="cinema?action=crud_film">${crudFilmLoc}</a>
							<div class="dropdown-divider"></div>
							<a class="dropdown-item" href="cinema?action=crud_filmSession">${crudFilmSessionLoc}</a>
							<div class="dropdown-divider"></div>
							<a class="dropdown-item"
								href="cinema?action=viewFilmSessionsTickets">${viewFilmSessionsTicketsLoc}</a>
						</div>
					</div>
				</c:if>
			</ul>
			<ul class="nav navbar-nav mx-auto">
				<li id="timer" style="color: #FF0000; font-size: 30px;" />
			</ul>
			<ul class="nav navbar-nav ml-auto">
				<c:choose>
					<c:when test="${currentUser!=null}">
						<li class="nav-item active"><a class="nav-link"
							href="cinema?action=view_profile" style="color: #FF0000"><b>${currentUser.login}</b></a></li>
						<li class="nav-item active"><a class="nav-link"
							href="cinema?action=log_out">${logOutLoc}</a></li>
					</c:when>
					<c:otherwise>
						<li class="nav-item active"><a class="nav-link"
							href="cinema?action=log_in">${logInLoc}</a></li>
						<li class="nav-item active"><a class="nav-link"
							href="cinema?action=sign_up">${signUpLoc}</a></li>
					</c:otherwise>
				</c:choose>
			</ul>
		</div>
	</nav>
	<%--<div class="row">
		&lt;%&ndash;<div class="col-md-2">
			<br>
			<h5>${chooseGenreLoc}</h5>
			<cctg:DisplayGenresBlock />
		</div>&ndash;%&gt;
		<div class="dropdown">
			<button class="btn btn-secondary dropdown-toggle" type="button"
					id="dropdownSort" data-toggle="dropdown"
					aria-haspopup="true" aria-expanded="false">${sortByLoc}</button>
			<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
				<a class="dropdown-item" href="cinema?action=sort_by_name">${sortByNameLoc}</a>
				<div class="dropdown-divider"></div>
				<a class="dropdown-item" href="cinema?action=sort_by_free_seats">${sortByFreeSeatsLoc}</a>
				<div class="dropdown-divider"></div>
				<a class="dropdown-item" href="cinema?action=sort_by_date">${sortByDateLoc}</a>
				<div class="dropdown-divider"></div>

				<a class="dropdown-item"
				   href="cinema?action=viewFilmSessionsTickets">${viewFilmSessionsTicketsLoc}</a>
			</div>
		</div>
		<div class="col-md-10">--%>
