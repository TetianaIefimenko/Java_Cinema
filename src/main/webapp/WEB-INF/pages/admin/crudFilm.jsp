<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${currentLocale}" />
<fmt:bundle basename="localization.msg" prefix="msg.jsp.crudFilm.">
	<fmt:message key="createButton" var="createButtonLoc" />
	<fmt:message key="updateButton" var="updateButtonLoc" />
	<fmt:message key="deleteButton" var="deleteButtonLoc" />
	<fmt:message key="searchButton" var="searchButtonLoc" />
	<fmt:message key="filmName" var="filmNameLoc" />
	<fmt:message key="description" var="descriptionLoc" />
	<fmt:message key="posterURL" var="posterURLLoc" />
	<fmt:message key="videoId" var="videoIdLoc" />
	<fmt:message key="genres" var="genresLoc" />
	<fmt:message key="chooseGenresMsg" var="chooseGenresMsgLoc" />
	<fmt:message key="searchMsg" var="searchMsgLoc" />
	<fmt:message key="searchResultMsg" var="searchResultMsgLoc" />
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

	<!-- CRUD films -->

	<div class="container">
		<button class="btn btn-success btn-lg" type="button"
			data-toggle="collapse" data-target="#collapseExample"
			aria-expanded="false" aria-controls="collapseExample">${createButtonLoc}</button>

		<div class="collapse" id="collapseExample">
			<div class="card card-body">
				<div class="container">
					<div class="row">
						<div class=col-md-2>${filmNameLoc}</div>
						<div class=col-md-4>${descriptionLoc}</div>
						<div class=col-md-2>${posterURLLoc}</div>
						<div class=col-md-2>${videoIdLoc}</div>
						<div class=col-md-2>${genresLoc}</div>
					</div>
				</div>

				<form class="create-film" action="cinema?action=crud_film&filmId=0"
					method=POST>
					<div class="row">
						<div class=col-md-2>
							<input id="filmName" class="form-control input-md"
								name="filmName" />
						</div>
						<div class=col-md-4>
							<input id="filmDescription" class="form-control input-md"
								name="filmDescription" />
						</div>
						<div class=col-md-2>
							<input id="filmPosterUrl" class="form-control input-md"
								name="filmPosterUrl" />
						</div>
						<div class=col-md-2>
							<input id="filmYouTubeVideoId" class="form-control input-md"
								name="filmYouTubeVideoId" />
						</div>
						<div class=col-md-2>
							<select id="genre" class="form-control" name="filmGenresId"
								multiple="multiple" size="5" required>
								<option disabled>${chooseGenresMsgLoc}</option>
								<c:forEach items="${genrelist}" var="genre">
									<option value="${genre.id}">${genre.genreName}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<button id="create" value="create" name="crudCommand"
						class="btn btn-success">ok</button>
				</form>
			</div>
		</div>
	</div>
	<hr>
	<div class="container">
		<p>${searchMsgLoc}</p>
		<form class="read-film" action="cinema?action=crud_film" method=POST>
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
	<c:if test="${foundFilm!=null}">
		<br>
		<div class="container">
			<form class="update-user" action="cinema?action=crud_film"
				method=POST>

				<div class="row">
					<div class=col-md-3>ID :</div>
					<div class=col-md-9>
						<input id="filmId" class="form-control input-md" name="filmId"
							value="${foundFilm.id}" readonly="readonly" />
					</div>
				</div>
				<div class="row">
					<div class=col-md-3>${filmNameLoc}:</div>
					<div class=col-md-9>
						<input id="filmName" class="form-control input-md" name="filmName"
							value="${foundFilm.filmName}" />
					</div>
				</div>
				<div class="row">
					<div class=col-md-3>${posterURLLoc}:</div>
					<div class=col-md-9>
						<input id="filmPosterUrl" class="form-control input-md"
							name="filmPosterUrl" value="${foundFilm.posterUrl}" />
					</div>
				</div>
				<div class="row">
					<div class=col-md-3>${videoIdLoc}:</div>
					<div class=col-md-9>
						<input id="filmYouTubeVideoId" class="form-control input-md"
							name="filmYouTubeVideoId" value="${foundFilm.youTubeVideoId}" />
					</div>
				</div>

				<div class="row">
					<div class=col-md-3>${descriptionLoc}:</div>
					<div class=col-md-9>
						<textarea id="filmDescription" name="filmDescription" cols="100"
							rows="7">${foundFilm.description} </textarea>
					</div>
				</div>
				<div class="row">
					<div class=col-md-3>${genresLoc}:</div>
					<div class=col-md-5>
						<select id="filmGenresId" class="form-control" name="filmGenresId"
							multiple="multiple" size="5" required>
							<option disabled>${chooseGenresMsgLoc}</option>
							<c:forEach items="${genrelist}" var="genre">
								<option value="${genre.id}"
									${foundFilm.genres.contains(genre)?"selected":""}>
									${genre.genreName}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<button id="update" value="update" name="crudCommand"
					class="btn btn-success">${updateButtonLoc}</button>

				<button id="delete" value="delete" name="crudCommand"
					class="btn btn-danger">${deleteButtonLoc}</button>
			</form>

		</div>
	</c:if>
	<%@ include file="../include/footer.jsp"%>
</body>
</html>