package by.htp.epam.cinema.web.action.impl;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.epam.cinema.service.ServiceFactory;
import by.htp.epam.cinema.service.UserService;
import by.htp.epam.cinema.web.action.BaseAction;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.REQUEST_PARAM_USER_LOGIN;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.REQUEST_PARAM_ERROR_MESSAGE;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.REQUEST_PARAM_USER_EMAIL;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.REQUEST_PARAM_USER_PASSWORD;
import static by.htp.epam.cinema.web.util.constant.PageNameConstantDeclaration.PAGE_ERROR;
import static by.htp.epam.cinema.web.util.constant.ResourceBundleKeysConstantDeclaration.ERROR_MSG_CHECK_USER_REGISTRATION_DATA_ACTION_INDEFINITE_ERROR;

public class CheckUserRegistrationDataAction implements BaseAction {

	private UserService userService = ServiceFactory.getUserService();

	@Override
	public void executeAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String checkingParamValue = null;
		PrintWriter out = response.getWriter();
		if ((checkingParamValue = request.getParameter(REQUEST_PARAM_USER_LOGIN)) != null) {
			out.write(userService.checkUserLogin(checkingParamValue));
		} else if ((checkingParamValue = request.getParameter(REQUEST_PARAM_USER_EMAIL)) != null) {
			out.write(userService.checkUserEmail(checkingParamValue));
		} else if ((checkingParamValue = request.getParameter(REQUEST_PARAM_USER_PASSWORD)) != null) {
			out.write(userService.checkUserPassword(checkingParamValue));
		} else {
			request.setAttribute(REQUEST_PARAM_ERROR_MESSAGE,
					resourceManager.getValue(ERROR_MSG_CHECK_USER_REGISTRATION_DATA_ACTION_INDEFINITE_ERROR));
			request.getRequestDispatcher(PAGE_ERROR).forward(request, response);
		}
	}
}
