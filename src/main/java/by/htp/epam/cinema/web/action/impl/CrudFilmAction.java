package by.htp.epam.cinema.web.action.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.epam.cinema.domain.Film;
import by.htp.epam.cinema.domain.CompositeEntities.CompositeFilm;
import by.htp.epam.cinema.service.FilmService;
import by.htp.epam.cinema.service.GenreService;
import by.htp.epam.cinema.service.ServiceFactory;
import by.htp.epam.cinema.service.UserService;
import by.htp.epam.cinema.web.action.BaseAction;
import by.htp.epam.cinema.web.util.HttpManager;
import by.htp.epam.cinema.web.util.ValidateParamException;

import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.*;
import static by.htp.epam.cinema.web.util.constant.PageNameConstantDeclaration.PAGE_ADMIN_CRUD_FILM;
import static by.htp.epam.cinema.web.util.constant.PageNameConstantDeclaration.PAGE_ERROR;
import static by.htp.epam.cinema.web.util.constant.ActionNameConstantDeclaration.ACTION_NAME_CRUD_FILM;
import static by.htp.epam.cinema.web.util.constant.ResourceBundleKeysConstantDeclaration.ERROR_MSG_CRUD_FILM_ACTION_INDEFINITE_ERROR;
import static by.htp.epam.cinema.web.util.constant.ResourceBundleKeysConstantDeclaration.ERROR_MSG_CRUD_FILM_ACTION_USER_IS_NOT_ADMIN;
import static by.htp.epam.cinema.web.util.HttpRequestParamValidator.*;
import static by.htp.epam.cinema.web.util.HttpRequestParamFormatter.*;

public class CrudFilmAction implements BaseAction {

	private FilmService filmService = ServiceFactory.getFilmService();

	private GenreService genreService = ServiceFactory.getGenreService();

	private UserService userService = ServiceFactory.getUserService();

	@Override
	public void executeAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!userService.isUserAdmin(request)) {
			request.setAttribute(REQUEST_PARAM_ERROR_MESSAGE,
					resourceManager.getValue(ERROR_MSG_CRUD_FILM_ACTION_USER_IS_NOT_ADMIN));
			request.getRequestDispatcher(PAGE_ERROR).forward(request, response);
			return;
		}
		List<Film> allfilms = filmService.getAllFilms();
		request.setAttribute(REQUEST_PARAM_FILMLIST, allfilms);
		if (isPost(request)) {
			String crudCommand = request.getParameter(REQUEST_PARAM_CRUD_COMMAND);
			try {
				validateRequestParamNotNull(crudCommand);
				Film film;
				List<Integer> genresIdList;
				switch (crudCommand) {
				case CRUD_COMMAND_CREATE:
					film = filmService.buildFilm(request);
					genresIdList = genreService.getFilmGenresId(request);
					filmService.createFilm(film, genresIdList);
					break;
				case CRUD_COMMAND_READ:
					String filmId = request.getParameter(REQUEST_PARAM_FILM_ID);
					validateRequestParamNotNull(filmId);
					CompositeFilm filmWithGenres = filmService.getFilmWithGenres(getInt(filmId));
					request.setAttribute(REQUEST_PARAM_FOUND_FILM, filmWithGenres);
					request.getRequestDispatcher(PAGE_ADMIN_CRUD_FILM).forward(request, response);
					return;
				case CRUD_COMMAND_UPDATE:
					film = filmService.buildFilm(request);
					genresIdList = genreService.getFilmGenresId(request);
					filmService.updateFilmAndGenres(film, genresIdList);
					break;
				case CRUD_COMMAND_DELETE:
					film = filmService.buildFilm(request);
					filmService.deleteFilm(film);
					break;
				}
				response.sendRedirect(HttpManager.getLocationForRedirect(ACTION_NAME_CRUD_FILM));
				return;
			} catch (SQLException | ValidateParamException e) {
				request.setAttribute(REQUEST_PARAM_ERROR_MESSAGE,
						resourceManager.getValue(ERROR_MSG_CRUD_FILM_ACTION_INDEFINITE_ERROR));
				request.getRequestDispatcher(PAGE_ERROR).forward(request, response);
				return;
			}
		}
		request.getRequestDispatcher(PAGE_ADMIN_CRUD_FILM).forward(request, response);
	}
}
