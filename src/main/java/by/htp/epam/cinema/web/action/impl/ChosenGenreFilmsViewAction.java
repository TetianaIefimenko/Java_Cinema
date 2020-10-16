package by.htp.epam.cinema.web.action.impl;

import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.REQUEST_PARAM_CHOSEN_GENRE_ID;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.REQUEST_PARAM_ERROR_MESSAGE;
import static by.htp.epam.cinema.web.util.constant.PageNameConstantDeclaration.PAGE_ERROR;
import static by.htp.epam.cinema.web.util.constant.PageNameConstantDeclaration.PAGE_USER_CHOSEN_GENRE_FILMS;
import static by.htp.epam.cinema.web.util.constant.ResourceBundleKeysConstantDeclaration.ERROR_MSG_GENRE_FILMS_VIEW_ACTION_INDEFINITE_ERROR;

import java.io.IOException;

import static by.htp.epam.cinema.web.util.HttpRequestParamValidator.*;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.REQUEST_PARAM_CHOSEN_GENRE;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.REQUEST_PARAM_CHOSEN_GENRE_FILMS;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.epam.cinema.domain.Genre;
import by.htp.epam.cinema.service.FilmService;
import by.htp.epam.cinema.service.GenreService;
import by.htp.epam.cinema.service.ServiceFactory;
import by.htp.epam.cinema.web.action.BaseAction;
import by.htp.epam.cinema.web.util.ValidateParamException;

public class ChosenGenreFilmsViewAction implements BaseAction {

	private FilmService filmService = ServiceFactory.getFilmService();

	private GenreService genreService = ServiceFactory.getGenreService();

	@Override
	public void executeAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String chosenGenreId = request.getParameter(REQUEST_PARAM_CHOSEN_GENRE_ID);
		try {
			validateRequestParamNotNull(chosenGenreId);
			Genre genre = genreService.getGenre(Integer.parseInt(chosenGenreId));
			request.setAttribute(REQUEST_PARAM_CHOSEN_GENRE_FILMS, filmService.getAllFilmsWithTheirGenres(genre));
			request.setAttribute(REQUEST_PARAM_CHOSEN_GENRE, genre);
			request.getRequestDispatcher(PAGE_USER_CHOSEN_GENRE_FILMS).forward(request, response);
		} catch (ValidateParamException e) {
			request.setAttribute(REQUEST_PARAM_ERROR_MESSAGE,
					resourceManager.getValue(ERROR_MSG_GENRE_FILMS_VIEW_ACTION_INDEFINITE_ERROR));
			request.getRequestDispatcher(PAGE_ERROR).forward(request, response);
		}
	}
}
