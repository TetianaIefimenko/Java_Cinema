package by.htp.epam.cinema.web.action.impl;

import static by.htp.epam.cinema.web.util.HttpRequestParamFormatter.getInt;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.REQUEST_PARAM_ERROR_MESSAGE;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.REQUEST_PARAM_FILMLIST;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.REQUEST_PARAM_CHOSEN_FILM_ID;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.REQUEST_PARAM_FOUND_FILMSESSIONS;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.REQUEST_PARAM_CHOSEN_FILMSESSION_ID;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.REQUEST_PARAM_FOUND_FILMSESSION_TICKETS;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.REQUEST_PARAM_PAGINATION_START;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.REQUEST_PARAM_PAGINATION_STEP;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.REQUEST_PARAM_CHOSEN_FILM;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.REQUEST_PARAM_CHOSEN_FILMSESSION;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.REQUEST_PARAM_SOLD_TICKETS_COUNT;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.REQUEST_PARAM_SOLD_TCIKETS_SUM;
import static by.htp.epam.cinema.web.util.constant.PageNameConstantDeclaration.PAGE_ADMIN_VIEW_FILMSESSIONS_TICKETS;
import static by.htp.epam.cinema.web.util.constant.PageNameConstantDeclaration.PAGE_ERROR;
import static by.htp.epam.cinema.web.util.constant.ResourceBundleKeysConstantDeclaration.ERROR_MSG_VIEW_FILMSESSIONS_TICKETS_ACTION_USER_IS_NOT_ADMIN;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.epam.cinema.domain.Film;
import by.htp.epam.cinema.domain.FilmSession;
import by.htp.epam.cinema.domain.CompositeEntities.CompositeTicket;
import by.htp.epam.cinema.service.FilmService;
import by.htp.epam.cinema.service.FilmSessionService;
import by.htp.epam.cinema.service.ServiceFactory;
import by.htp.epam.cinema.service.TicketService;
import by.htp.epam.cinema.service.UserService;
import by.htp.epam.cinema.web.action.BaseAction;

public class FilmSessionsTicketsViewAction implements BaseAction {

	private UserService userService = ServiceFactory.getUserService();

	private FilmService filmService = ServiceFactory.getFilmService();

	private FilmSessionService filmSessionService = ServiceFactory.getFilmSessionService();

	private TicketService ticketService = ServiceFactory.getTicketService();

	@Override
	public void executeAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!userService.isUserAdmin(request)) {
			request.setAttribute(REQUEST_PARAM_ERROR_MESSAGE,
					resourceManager.getValue(ERROR_MSG_VIEW_FILMSESSIONS_TICKETS_ACTION_USER_IS_NOT_ADMIN));
			request.getRequestDispatcher(PAGE_ERROR).forward(request, response);
			return;
		}
		List<Film> allfilms = filmService.getAllFilms();
		request.setAttribute(REQUEST_PARAM_FILMLIST, allfilms);
		String filmId = request.getParameter(REQUEST_PARAM_CHOSEN_FILM_ID);
		String filmSessionId = request.getParameter(REQUEST_PARAM_CHOSEN_FILMSESSION_ID);
		if (filmId != null) {
			List<FilmSession> filmFilmSessions = filmSessionService.getFilmSessions(getInt(filmId));
			request.setAttribute(REQUEST_PARAM_CHOSEN_FILM, filmService.getFilm(getInt(filmId)));
			request.setAttribute(REQUEST_PARAM_FOUND_FILMSESSIONS, filmFilmSessions);
			if (filmSessionId != null) {
				int fsId = getInt(filmSessionId);
				String reqPaginatorStart = request.getParameter(REQUEST_PARAM_PAGINATION_START);
				String reqPaginatorStep = request.getParameter(REQUEST_PARAM_PAGINATION_STEP);
				int paginatorStart = reqPaginatorStart != null ? getInt(reqPaginatorStart) : 0;
				int paginatorStep = reqPaginatorStep != null ? getInt(reqPaginatorStep) : 10;
				List<CompositeTicket> filmSessionSoldTickets = ticketService.getAllFilmSessionSoldTickets(fsId,
						paginatorStart, paginatorStep);

				request.setAttribute(REQUEST_PARAM_SOLD_TICKETS_COUNT, ticketService.getSoldTicketCount(fsId));
				request.setAttribute(REQUEST_PARAM_SOLD_TCIKETS_SUM, ticketService.getSoldTicketSum(fsId));
				request.setAttribute(REQUEST_PARAM_CHOSEN_FILMSESSION, filmSessionService.getFilmSession(fsId));
				request.setAttribute(REQUEST_PARAM_FOUND_FILMSESSION_TICKETS, filmSessionSoldTickets);
			}
		}
		request.getRequestDispatcher(PAGE_ADMIN_VIEW_FILMSESSIONS_TICKETS).forward(request, response);
	}
}
