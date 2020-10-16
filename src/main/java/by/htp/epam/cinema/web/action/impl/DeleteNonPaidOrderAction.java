package by.htp.epam.cinema.web.action.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.epam.cinema.domain.User;
import by.htp.epam.cinema.service.ServiceFactory;
import by.htp.epam.cinema.service.TicketsOrderService;
import by.htp.epam.cinema.web.action.BaseAction;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.SESSION_PARAM_CURRENT_USER;

public class DeleteNonPaidOrderAction implements BaseAction {

	private TicketsOrderService ticketsOrderService = ServiceFactory.getTicketsOrderService();

	@Override
	public void executeAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User currentUser = (User) request.getSession().getAttribute(SESSION_PARAM_CURRENT_USER);
		ticketsOrderService.deleteNonPaidOrder(currentUser);
	}
}
