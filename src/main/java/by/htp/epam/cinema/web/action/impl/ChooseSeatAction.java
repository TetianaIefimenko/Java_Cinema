package by.htp.epam.cinema.web.action.impl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.epam.cinema.domain.FilmSession;
import by.htp.epam.cinema.domain.Seat;
import by.htp.epam.cinema.domain.User;
import by.htp.epam.cinema.service.FilmSessionService;
import by.htp.epam.cinema.service.SeatService;
import by.htp.epam.cinema.service.ServiceFactory;
import by.htp.epam.cinema.web.action.BaseAction;
import by.htp.epam.cinema.web.util.ValidateParamException;

import static by.htp.epam.cinema.web.util.HttpRequestParamValidator.validateRequestParamNotNull;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.*;
import static by.htp.epam.cinema.web.util.constant.PageNameConstantDeclaration.*;
import static by.htp.epam.cinema.web.util.constant.ResourceBundleKeysConstantDeclaration.*;

import java.io.IOException;
import java.util.List;

public class ChooseSeatAction implements BaseAction {

	SeatService seatService = ServiceFactory.getSeatService();

	FilmSessionService filmSessionService = ServiceFactory.getFilmSessionService();

	@Override
	public void executeAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(SESSION_PARAM_CURRENT_USER);
		if (user != null) {
			String chosenFilmSessionId = request.getParameter(REQUEST_PARAM_CHOSEN_FILMSESSION_ID);
			try {
				validateRequestParamNotNull(chosenFilmSessionId);
				FilmSession chosenFilmSession = filmSessionService
						.getFilmSession(Integer.parseInt(chosenFilmSessionId));
				List<Seat> seatsWithStates = seatService.getSeatsWithState(chosenFilmSession.getId());
				request.setAttribute(REQUEST_PARAM_CHOSEN_FILMSESSION, chosenFilmSession);
				request.setAttribute(REQUEST_PARAM_SEATS_WITH_STATES, seatsWithStates);
				request.getRequestDispatcher(PAGE_USER_SEAT_CHOICE).forward(request, response);
			} catch (ValidateParamException e) {
				request.setAttribute(REQUEST_PARAM_ERROR_MESSAGE,
						resourceManager.getValue(ERROR_MSG_CHOOSE_SEAT_ACTION_INDEFINITE_ERROR));
				request.getRequestDispatcher(PAGE_ERROR).forward(request, response);
			}
		} else {
			request.setAttribute(REQUEST_PARAM_ERROR_MESSAGE,
					resourceManager.getValue(ERROR_MSG_CHOOSE_SEAT_ACTION_USER_IS_NOT_LOGGED_IN));
			request.getRequestDispatcher(PAGE_ERROR).forward(request, response);
		}
	}
}
