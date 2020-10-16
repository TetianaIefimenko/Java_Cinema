package by.htp.epam.cinema.web.action.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.epam.cinema.service.ServiceFactory;
import by.htp.epam.cinema.service.TicketsOrderService;
import by.htp.epam.cinema.web.action.BaseAction;
import by.htp.epam.cinema.web.util.ValidateParamException;

import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.REQUEST_PARAM_CURRENT_USER_CURRENT_ORDER_ID;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.REQUEST_PARAM_ERROR_MESSAGE;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.REQUEST_PARAM_SUCCESS_MESSAGE;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.SESSION_PARAM_TIMER;
import static by.htp.epam.cinema.web.util.constant.PageNameConstantDeclaration.PAGE_SUCCESS;
import static by.htp.epam.cinema.web.util.constant.PageNameConstantDeclaration.PAGE_ERROR;
import static by.htp.epam.cinema.web.util.constant.ResourceBundleKeysConstantDeclaration.ERROR_MSG_PAY_ORDER_ACTION_INDEFINITE_ERROR;
import static by.htp.epam.cinema.web.util.constant.ResourceBundleKeysConstantDeclaration.SUCCESS_MSG_PAY_ORDER_ACTION_SUCCESSFULL_PAYMENT;
import static by.htp.epam.cinema.web.util.HttpRequestParamValidator.validateRequestParamNotNull;

public class PayOrderAction implements BaseAction {

	private TicketsOrderService ticketsOrderService = ServiceFactory.getTicketsOrderService();

	@Override
	public void executeAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String orderForPay = request.getParameter(REQUEST_PARAM_CURRENT_USER_CURRENT_ORDER_ID);
		try {
			validateRequestParamNotNull(orderForPay);
			ticketsOrderService.payOrder(Integer.parseInt(orderForPay));

			HttpSession session = request.getSession();

			request.setAttribute(REQUEST_PARAM_SUCCESS_MESSAGE,
					resourceManager.getValue(SUCCESS_MSG_PAY_ORDER_ACTION_SUCCESSFULL_PAYMENT));
			request.getRequestDispatcher(PAGE_SUCCESS).forward(request, response);
		} catch (ValidateParamException e) {
			request.setAttribute(REQUEST_PARAM_ERROR_MESSAGE,
					resourceManager.getValue(ERROR_MSG_PAY_ORDER_ACTION_INDEFINITE_ERROR));
			request.getRequestDispatcher(PAGE_ERROR).forward(request, response);
		}
	}
}
