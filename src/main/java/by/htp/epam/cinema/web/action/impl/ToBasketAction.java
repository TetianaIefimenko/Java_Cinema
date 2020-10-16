package by.htp.epam.cinema.web.action.impl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.epam.cinema.domain.Seat;
import by.htp.epam.cinema.domain.TicketsOrder;
import by.htp.epam.cinema.domain.User;
import by.htp.epam.cinema.service.FilmSessionService;
import by.htp.epam.cinema.service.SeatService;
import by.htp.epam.cinema.service.ServiceFactory;
import by.htp.epam.cinema.service.TicketService;
import by.htp.epam.cinema.service.TicketsOrderService;
import by.htp.epam.cinema.web.action.BaseAction;
import by.htp.epam.cinema.web.util.ValidateParamException;

import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.REQUEST_PARAM_CHOSEN_SEAT_ID;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.SESSION_PARAM_TIMER;
import static by.htp.epam.cinema.web.util.constant.PageNameConstantDeclaration.PAGE_ERROR;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.REQUEST_PARAM_ERROR_MESSAGE;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.SESSION_PARAM_CURRENT_USER;
import static by.htp.epam.cinema.web.util.constant.ResourceBundleKeysConstantDeclaration.ERROR_MSG_TO_BASKET_ACTION_INDEFINITE_ERROR;
import static by.htp.epam.cinema.web.util.constant.ResourceBundleKeysConstantDeclaration.ERROR_MSG_TO_BASKET_ACTION_SEAT_IS_NOT_FREE;
import static by.htp.epam.cinema.web.util.constant.ResourceBundleKeysConstantDeclaration.ERROR_MSG_TO_BASKET_ACTION_USER_IS_NOT_LOGGED_IN;

import java.io.IOException;

import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.REQUEST_PARAM_CHOSEN_FILMSESSION_ID;
import static by.htp.epam.cinema.web.util.HttpRequestParamValidator.validateRequestParamNotNull;;


public class ToBasketAction implements BaseAction {

	SeatService seatService = ServiceFactory.getSeatService();

	FilmSessionService filmSessionService = ServiceFactory.getFilmSessionService();

	TicketsOrderService ticketsOrderService = ServiceFactory.getTicketsOrderService();

	TicketService ticketService = ServiceFactory.getTicketService();

	@Override
	public void executeAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(SESSION_PARAM_CURRENT_USER);
		if (user != null) {
			String chosenSeatIdString = request.getParameter(REQUEST_PARAM_CHOSEN_SEAT_ID);
			String chosenFilmSessioIdString = request.getParameter(REQUEST_PARAM_CHOSEN_FILMSESSION_ID);
			try {
				validateRequestParamNotNull(chosenSeatIdString, chosenFilmSessioIdString);
			} catch (ValidateParamException e) {
				request.setAttribute(REQUEST_PARAM_ERROR_MESSAGE,
						resourceManager.getValue(ERROR_MSG_TO_BASKET_ACTION_INDEFINITE_ERROR));
				request.getRequestDispatcher(PAGE_ERROR).forward(request, response);
			}
			int chosenSeatIdInt = Integer.parseInt(chosenSeatIdString);
			int chosenFilmSessioIdInt = Integer.parseInt(chosenFilmSessioIdString);
			Seat chosenSeat = seatService.getSeat(chosenSeatIdInt);
			if (!seatService.isSeatFree(chosenSeatIdInt, chosenFilmSessioIdInt)) {
				request.setAttribute(REQUEST_PARAM_ERROR_MESSAGE,
						resourceManager.getValue(ERROR_MSG_TO_BASKET_ACTION_SEAT_IS_NOT_FREE));
				request.getRequestDispatcher(PAGE_ERROR).forward(request, response);
				return;
			}
			TicketsOrder ticketsOrder = null;
			if ((ticketsOrder = ticketsOrderService.readUserNonPaidOrder(user)) == null) {
				ticketsOrder = ticketsOrderService.createTicketsOrder(user);
			}
			ticketService.createTicket(filmSessionService.getFilmSession(chosenFilmSessioIdInt), chosenSeat,
					ticketsOrder);
			response.sendRedirect(request.getHeader("Referer"));
		} else {
			request.setAttribute(REQUEST_PARAM_ERROR_MESSAGE,
					resourceManager.getValue(ERROR_MSG_TO_BASKET_ACTION_USER_IS_NOT_LOGGED_IN));
			request.getRequestDispatcher(PAGE_ERROR).forward(request, response);
		}
	}
}
