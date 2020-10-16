package by.htp.epam.cinema.web.action.impl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.epam.cinema.service.ServiceFactory;
import by.htp.epam.cinema.service.UserService;
import by.htp.epam.cinema.web.action.BaseAction;
import by.htp.epam.cinema.web.util.HttpManager;

import static by.htp.epam.cinema.web.util.constant.ActionNameConstantDeclaration.ACTION_NAME_LOGIN;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.*;
import static by.htp.epam.cinema.web.util.constant.PageNameConstantDeclaration.PAGE_ERROR;
import static by.htp.epam.cinema.web.util.constant.PageNameConstantDeclaration.PAGE_USER_SIGNUP;

import java.io.IOException;

import static by.htp.epam.cinema.web.util.HttpRequestParamValidator.*;

public class SignUpAction implements BaseAction {

	private UserService userService = ServiceFactory.getUserService();

	@Override
	public void executeAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!isPost(request)) {
			request.getRequestDispatcher(PAGE_USER_SIGNUP).forward(request, response);
			return;
		}
		String login = request.getParameter(REQUEST_PARAM_USER_LOGIN);
		String email = request.getParameter(REQUEST_PARAM_USER_EMAIL);
		String password = request.getParameter(REQUEST_PARAM_USER_PASSWORD);

		String resultOfCheck = userService.checkUserData(login, email, password);
		if (resultOfCheck.length() == 0) {
			userService.createUser(login, email, password);
			response.sendRedirect(HttpManager.getLocationForRedirect(ACTION_NAME_LOGIN));
		} else {
			request.setAttribute(REQUEST_PARAM_ERROR_MESSAGE, resultOfCheck);
			request.getRequestDispatcher(PAGE_ERROR).forward(request, response);
		}
	}
}
