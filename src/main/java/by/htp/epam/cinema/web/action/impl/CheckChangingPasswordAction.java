package by.htp.epam.cinema.web.action.impl;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.epam.cinema.service.ServiceFactory;
import by.htp.epam.cinema.service.UserService;
import by.htp.epam.cinema.web.action.BaseAction;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.REQUEST_PARAM_OLD_PASSWORD;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.REQUEST_PARAM_NEW_PASSWORD;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.REQUEST_PARAM_ERROR_MESSAGE;
import static by.htp.epam.cinema.web.util.constant.PageNameConstantDeclaration.PAGE_ERROR;
import static by.htp.epam.cinema.web.util.constant.ResourceBundleKeysConstantDeclaration.ERROR_MSG_CHECK_CHANGING_PASSWORD_ACTION_INDEFINITE_ERROR;

public class CheckChangingPasswordAction implements BaseAction {

	private UserService userService = ServiceFactory.getUserService();

	@Override
	public void executeAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String checkingPassword = null;
		PrintWriter out = response.getWriter();
		if ((checkingPassword = request.getParameter(REQUEST_PARAM_OLD_PASSWORD)) != null) {
			out.write(userService.checkUserPassword(checkingPassword));
		} else if ((checkingPassword = request.getParameter(REQUEST_PARAM_NEW_PASSWORD)) != null) {
			out.write(userService.checkUserPassword(checkingPassword));
		} else {
			request.setAttribute(REQUEST_PARAM_ERROR_MESSAGE,
					resourceManager.getValue(ERROR_MSG_CHECK_CHANGING_PASSWORD_ACTION_INDEFINITE_ERROR));
			request.getRequestDispatcher(PAGE_ERROR).forward(request, response);
		}
	}
}
