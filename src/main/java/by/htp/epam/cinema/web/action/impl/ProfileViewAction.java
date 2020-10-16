package by.htp.epam.cinema.web.action.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.epam.cinema.domain.TicketsOrder;
import by.htp.epam.cinema.domain.User;
import by.htp.epam.cinema.domain.CompositeEntities.CompositeTicket;
import by.htp.epam.cinema.service.ServiceFactory;
import by.htp.epam.cinema.service.TicketService;
import by.htp.epam.cinema.service.TicketsOrderService;
import by.htp.epam.cinema.web.action.BaseAction;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.SESSION_PARAM_CURRENT_USER;
import static by.htp.epam.cinema.web.util.constant.PageNameConstantDeclaration.PAGE_USER_PROFILE;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.REQUEST_PARAM_CURRENT_USER_CURRENT_ORDER;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.REQUEST_PARAM_CURRENT_USER_CURRENT_ORDER_TICKETS;


public class ProfileViewAction implements BaseAction {

	private TicketsOrderService ticketsOrderService = ServiceFactory.getTicketsOrderService();

	private TicketService ticketsService = ServiceFactory.getTicketService();

	@Override
	public void executeAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User currentUser = (User) request.getSession().getAttribute(SESSION_PARAM_CURRENT_USER);
		TicketsOrder ticketsOrder = ticketsOrderService.readUserNonPaidOrder(currentUser);
		if (ticketsOrder != null) {
			request.setAttribute(REQUEST_PARAM_CURRENT_USER_CURRENT_ORDER, ticketsOrder);
			List<CompositeTicket> compositeTickets = ticketsService.getOrderTickets(ticketsOrder);
			request.setAttribute(REQUEST_PARAM_CURRENT_USER_CURRENT_ORDER_TICKETS, compositeTickets);
		}
		request.getRequestDispatcher(PAGE_USER_PROFILE).forward(request, response);
	}
}
