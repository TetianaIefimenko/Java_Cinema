package by.htp.epam.cinema.web.action.impl;

import static by.htp.epam.cinema.web.util.HttpRequestParamValidator.*;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.REQUEST_PARAM_CHOSEN_FILM_ID;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.REQUEST_PARAM_CHOSEN_FILM;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.REQUEST_PARAM_CHOSEN_FILM_GENRES;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.REQUEST_PARAM_CHOSEN_FILM_FILMSESSIONS;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.REQUEST_PARAM_ERROR_MESSAGE;
import static by.htp.epam.cinema.web.util.constant.PageNameConstantDeclaration.PAGE_ERROR;
import static by.htp.epam.cinema.web.util.constant.PageNameConstantDeclaration.PAGE_USER_FILM;
import static by.htp.epam.cinema.web.util.constant.ResourceBundleKeysConstantDeclaration.ERROR_MSG_FILM_PAGE_VIEW_ACTION_INDEFINITE_ERROR;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.epam.cinema.domain.Film;
import by.htp.epam.cinema.domain.FilmSession;
import by.htp.epam.cinema.domain.Genre;
import by.htp.epam.cinema.service.FilmService;
import by.htp.epam.cinema.service.FilmSessionService;
import by.htp.epam.cinema.service.GenreService;
import by.htp.epam.cinema.service.ServiceFactory;
import by.htp.epam.cinema.web.action.BaseAction;
import by.htp.epam.cinema.web.util.ValidateParamException;

public class FilmPageViewAction implements BaseAction {

	private FilmService filmService = ServiceFactory.getFilmService();

	private GenreService genreService = ServiceFactory.getGenreService();

	private FilmSessionService filmSessionService = ServiceFactory.getFilmSessionService();

	@Override
	public void executeAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String filmId = request.getParameter(REQUEST_PARAM_CHOSEN_FILM_ID);
		try {
			validateRequestParamNotNull(filmId);
			int chosenFilmid = Integer.parseInt(filmId);
			Film chosenFilm = filmService.getFilm(chosenFilmid);
			List<Genre> chosenFilmGenres = genreService.getFilmGenres(chosenFilmid);
			List<FilmSession> chosenFilmSessions = filmSessionService.getFilmSessions(chosenFilmid);

			request.setAttribute(REQUEST_PARAM_CHOSEN_FILM, chosenFilm);
			request.setAttribute(REQUEST_PARAM_CHOSEN_FILM_GENRES, chosenFilmGenres);
			request.setAttribute(REQUEST_PARAM_CHOSEN_FILM_FILMSESSIONS, chosenFilmSessions);
			request.getRequestDispatcher(PAGE_USER_FILM).forward(request, response);
		} catch (ValidateParamException e) {
			request.setAttribute(REQUEST_PARAM_ERROR_MESSAGE,
					resourceManager.getValue(ERROR_MSG_FILM_PAGE_VIEW_ACTION_INDEFINITE_ERROR));
			request.getRequestDispatcher(PAGE_ERROR).forward(request, response);
		}
	}
}
