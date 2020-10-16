package by.htp.epam.cinema.web.action.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.epam.cinema.domain.User;
import by.htp.epam.cinema.service.ServiceFactory;
import by.htp.epam.cinema.service.UserService;
import by.htp.epam.cinema.web.action.BaseAction;
import by.htp.epam.cinema.web.util.ValidateParamException;

import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.REQUEST_PARAM_OLD_PASSWORD;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.SESSION_PARAM_CURRENT_USER;
import static by.htp.epam.cinema.web.util.constant.PageNameConstantDeclaration.PAGE_ERROR;
import static by.htp.epam.cinema.web.util.constant.PageNameConstantDeclaration.PAGE_SUCCESS;
import static by.htp.epam.cinema.web.util.constant.ResourceBundleKeysConstantDeclaration.SUCCESS_MSG_CHANGE_USER_PASSWORD_ACTION_SUCCESSFULL_CHANGE;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.REQUEST_PARAM_ERROR_MESSAGE;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.REQUEST_PARAM_NEW_PASSWORD;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.REQUEST_PARAM_SUCCESS_MESSAGE;

public class ChangeUserPasswordAction implements BaseAction {

	private UserService userService = ServiceFactory.getUserService();

	@Override
	public void executeAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String oldPassword = request.getParameter(REQUEST_PARAM_OLD_PASSWORD);
		String newPassword = request.getParameter(REQUEST_PARAM_NEW_PASSWORD);
		User sessionUser = (User) session.getAttribute(SESSION_PARAM_CURRENT_USER);
		try {
			User user = userService.changeUserPassword(sessionUser.getId(), oldPassword, newPassword);
			if (user != null) {
				session.setAttribute(SESSION_PARAM_CURRENT_USER, user);
				request.setAttribute(REQUEST_PARAM_SUCCESS_MESSAGE,
						resourceManager.getValue(SUCCESS_MSG_CHANGE_USER_PASSWORD_ACTION_SUCCESSFULL_CHANGE));
				request.getRequestDispatcher(PAGE_SUCCESS).forward(request, response);
			}
		} catch (ValidateParamException e) {
			request.setAttribute(REQUEST_PARAM_ERROR_MESSAGE, e.getMessage());
			request.getRequestDispatcher(PAGE_ERROR).forward(request, response);
		}
	}
}
