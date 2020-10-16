package by.htp.epam.cinema.web.action.impl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.epam.cinema.web.action.BaseAction;
import by.htp.epam.cinema.web.util.ValidateParamException;

import static by.htp.epam.cinema.web.util.HttpRequestParamValidator.validateRequestParamLocale;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.REQUEST_PARAM_ERROR_MESSAGE;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.REQUEST_PARAM_LOCALE;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.SESSION_PARAM_CURRENT_LOCALES;
import static by.htp.epam.cinema.web.util.constant.PageNameConstantDeclaration.PAGE_ERROR;
import static by.htp.epam.cinema.web.util.constant.ResourceBundleKeysConstantDeclaration.ERROR_MSG_CHANGE_LOCALE_ACTION_INDEFINITE_ERROR;

import java.io.IOException;
import java.util.Locale;

public class ChangeLocaleAction implements BaseAction {

	@Override
	public void executeAction(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String locale = request.getParameter(REQUEST_PARAM_LOCALE);
		try {
			validateRequestParamLocale(locale);
			String[] localArr = locale.split("_");
			resourceManager.changeResource(new Locale(localArr[0], localArr[1]));
			request.getSession().setAttribute(SESSION_PARAM_CURRENT_LOCALES, locale);
			response.sendRedirect(request.getHeader("Referer"));
		} catch (ValidateParamException e) {
			request.setAttribute(REQUEST_PARAM_ERROR_MESSAGE,
					resourceManager.getValue(ERROR_MSG_CHANGE_LOCALE_ACTION_INDEFINITE_ERROR));
			request.getRequestDispatcher(PAGE_ERROR).forward(request, response);
		}
	}
}
