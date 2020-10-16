package by.htp.epam.cinema.web.action.impl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.epam.cinema.domain.User;
import by.htp.epam.cinema.service.ServiceFactory;
import by.htp.epam.cinema.service.UserService;
import by.htp.epam.cinema.web.action.BaseAction;
import by.htp.epam.cinema.web.util.HttpManager;
import by.htp.epam.cinema.web.util.ValidateParamException;

import static by.htp.epam.cinema.web.util.constant.ActionNameConstantDeclaration.ACTION_NAME_VIEW_HOME_PAGE;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.*;
import static by.htp.epam.cinema.web.util.constant.PageNameConstantDeclaration.PAGE_ERROR;
import static by.htp.epam.cinema.web.util.constant.PageNameConstantDeclaration.PAGE_USER_LOGIN;
import static by.htp.epam.cinema.web.util.constant.ResourceBundleKeysConstantDeclaration.*;

import java.io.IOException;

import static by.htp.epam.cinema.web.util.HttpRequestParamValidator.*;

public class LogInAction implements BaseAction {

	private UserService userService = ServiceFactory.getUserService();

	@Override
	public void executeAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute(SESSION_PARAM_CURRENT_USER) != null) {
			request.setAttribute(REQUEST_PARAM_ERROR_MESSAGE,
					resourceManager.getValue(ERROR_MSG_LOG_IN_ACTION_REPEATED_LOGGING));
			request.getRequestDispatcher(PAGE_ERROR).forward(request, response);
			return;
		}
		if (!isPost(request)) {
			request.getRequestDispatcher(PAGE_USER_LOGIN).forward(request, response);
		} else {
			String login = request.getParameter(REQUEST_PARAM_USER_LOGIN);
			String password = request.getParameter(REQUEST_PARAM_USER_PASSWORD);
			try {
				User user = userService.getUser(login, password);
				session.setAttribute(SESSION_PARAM_CURRENT_USER, user);
				session.setMaxInactiveInterval(500);
				response.sendRedirect(HttpManager.getLocationForRedirect(ACTION_NAME_VIEW_HOME_PAGE));
			} catch (ValidateParamException e) {
				request.setAttribute(REQUEST_PARAM_ERROR_MESSAGE, e.getMessage());
				request.getRequestDispatcher(PAGE_ERROR).forward(request, response);
			}
		}
	}
}
