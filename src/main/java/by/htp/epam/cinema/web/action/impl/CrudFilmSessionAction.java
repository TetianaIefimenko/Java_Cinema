package by.htp.epam.cinema.web.action.impl;

import static by.htp.epam.cinema.web.util.HttpRequestParamFormatter.getInt;
import static by.htp.epam.cinema.web.util.HttpRequestParamValidator.isPost;
import static by.htp.epam.cinema.web.util.HttpRequestParamValidator.validateRequestParamNotNull;
import static by.htp.epam.cinema.web.util.constant.ActionNameConstantDeclaration.ACTION_NAME_CRUD_FILMSESSION;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.*;
import static by.htp.epam.cinema.web.util.constant.PageNameConstantDeclaration.PAGE_ADMIN_CRUD_FILMSESSION;
import static by.htp.epam.cinema.web.util.constant.PageNameConstantDeclaration.PAGE_ERROR;
import static by.htp.epam.cinema.web.util.constant.ResourceBundleKeysConstantDeclaration.ERROR_MSG_CRUD_FILMSESSION_ACTION_INDEFINITE_ERROR;
import static by.htp.epam.cinema.web.util.constant.ResourceBundleKeysConstantDeclaration.ERROR_MSG_CRUD_FILMSESSION_ACTION_TIME_IS_BUSY;
import static by.htp.epam.cinema.web.util.constant.ResourceBundleKeysConstantDeclaration.ERROR_MSG_CRUD_FILMSESSION_ACTION_USER_IS_NOT_ADMIN;
import static by.htp.epam.cinema.web.util.constant.ResourceBundleKeysConstantDeclaration.ERROR_MSG_CRUD_FILMSESSION_DELETE_IMPOSIBLE;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.epam.cinema.domain.Film;
import by.htp.epam.cinema.domain.FilmSession;
import by.htp.epam.cinema.service.FilmService;
import by.htp.epam.cinema.service.FilmSessionService;
import by.htp.epam.cinema.service.ServiceFactory;
import by.htp.epam.cinema.service.UserService;
import by.htp.epam.cinema.web.action.BaseAction;
import by.htp.epam.cinema.web.util.HttpManager;
import by.htp.epam.cinema.web.util.ValidateParamException;

public class CrudFilmSessionAction implements BaseAction {

	private UserService userService = ServiceFactory.getUserService();

	private FilmService filmService = ServiceFactory.getFilmService();

	private FilmSessionService filmSessionService = ServiceFactory.getFilmSessionService();

	@Override
	public void executeAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!userService.isUserAdmin(request)) {
			request.setAttribute(REQUEST_PARAM_ERROR_MESSAGE,
					resourceManager.getValue(ERROR_MSG_CRUD_FILMSESSION_ACTION_USER_IS_NOT_ADMIN));
			request.getRequestDispatcher(PAGE_ERROR).forward(request, response);
			return;
		}
		List<Film> films = filmService.getAllFilms();
		request.setAttribute(REQUEST_PARAM_FILMLIST, films);
		if (isPost(request)) {
			String crudCommand = request.getParameter(REQUEST_PARAM_CRUD_COMMAND);
			try {
				validateRequestParamNotNull(crudCommand);
				FilmSession filmSession = null;
				switch (crudCommand) {
				case CRUD_COMMAND_CREATE:
					filmSession = filmSessionService.buildFilmSession(request);
					if (!filmSessionService.isFilmSessionTimeFree(filmSession)) {
						request.setAttribute(REQUEST_PARAM_ERROR_MESSAGE,
								resourceManager.getValue(ERROR_MSG_CRUD_FILMSESSION_ACTION_TIME_IS_BUSY));
						request.getRequestDispatcher(PAGE_ERROR).forward(request, response);
						return;
					}
					filmSessionService.createFilmSession(filmSession);
					break;
				case CRUD_COMMAND_READ:
					String filmId = request.getParameter(REQUEST_PARAM_FILM_ID);
					validateRequestParamNotNull(filmId);
					List<FilmSession> foundFilmSessions = filmSessionService.getFilmSessions(getInt(filmId));
					request.setAttribute(REQUEST_PARAM_FOUND_FILMSESSIONS, foundFilmSessions);
					request.getRequestDispatcher(PAGE_ADMIN_CRUD_FILMSESSION).forward(request, response);
					return;
				case CRUD_COMMAND_UPDATE:
					filmSession = filmSessionService.buildFilmSession(request);
					if (!filmSessionService.isFilmSessionTimeFree(filmSession)) {
						request.setAttribute(REQUEST_PARAM_ERROR_MESSAGE,
								resourceManager.getValue(ERROR_MSG_CRUD_FILMSESSION_ACTION_TIME_IS_BUSY));
						request.getRequestDispatcher(PAGE_ERROR).forward(request, response);
						return;
					}
					filmSessionService.updateFilmSession(filmSession);
					break;
				case CRUD_COMMAND_DELETE:
					String filmSessionId = request.getParameter(REQUEST_PARAM_FILMSESSION_ID);
					validateRequestParamNotNull(filmSessionId);
					if (!filmSessionService.isRemovalPossible(getInt(filmSessionId))) {
						request.setAttribute(REQUEST_PARAM_ERROR_MESSAGE,
								resourceManager.getValue(ERROR_MSG_CRUD_FILMSESSION_DELETE_IMPOSIBLE));
						request.getRequestDispatcher(PAGE_ERROR).forward(request, response);
						return;
					}
					filmSessionService.deleteFilmSession(getInt(filmSessionId));
					break;
				}
				response.sendRedirect(HttpManager.getLocationForRedirect(ACTION_NAME_CRUD_FILMSESSION));
				return;
			} catch (ValidateParamException e) {
				request.setAttribute(REQUEST_PARAM_ERROR_MESSAGE,
						resourceManager.getValue(ERROR_MSG_CRUD_FILMSESSION_ACTION_INDEFINITE_ERROR));
				request.getRequestDispatcher(PAGE_ERROR).forward(request, response);
				return;
			}
		}
		request.getRequestDispatcher(PAGE_ADMIN_CRUD_FILMSESSION).forward(request, response);
	}
}
