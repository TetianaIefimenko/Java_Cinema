package by.htp.epam.cinema.web.action.impl;

import static by.htp.epam.cinema.web.util.HttpRequestParamValidator.isPost;
import static by.htp.epam.cinema.web.util.HttpRequestParamValidator.validateRequestParamNotNull;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.*;
import static by.htp.epam.cinema.web.util.constant.PageNameConstantDeclaration.PAGE_ADMIN_CHANGE_USER_ROLE;
import static by.htp.epam.cinema.web.util.constant.PageNameConstantDeclaration.PAGE_ERROR;
import static by.htp.epam.cinema.web.util.constant.ResourceBundleKeysConstantDeclaration.ERROR_MSG_CHANGE_USER_ROLE_ACTION_INDEFINITE_ERROR;
import static by.htp.epam.cinema.web.util.constant.ResourceBundleKeysConstantDeclaration.ERROR_MSG_CHANGE_USER_ROLE_ACTION_USER_IS_NOT_ADMIN;
import static by.htp.epam.cinema.web.util.constant.ResourceBundleKeysConstantDeclaration.ERROR_MSG_CHANGE_USER_ROLE_ACTION_USER_NOT_FOUND;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.epam.cinema.domain.Role;
import by.htp.epam.cinema.domain.User;
import by.htp.epam.cinema.service.RoleService;
import by.htp.epam.cinema.service.ServiceFactory;
import by.htp.epam.cinema.service.UserService;
import by.htp.epam.cinema.web.action.BaseAction;
import by.htp.epam.cinema.web.util.ValidateParamException;

public class ChangeUserRoleAction implements BaseAction {

	private UserService userService = ServiceFactory.getUserService();

	private RoleService roleService = ServiceFactory.getRoleService();

	@Override
	public void executeAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!userService.isUserAdmin(request)) {
			request.setAttribute(REQUEST_PARAM_ERROR_MESSAGE,
					resourceManager.getValue(ERROR_MSG_CHANGE_USER_ROLE_ACTION_USER_IS_NOT_ADMIN));
			request.getRequestDispatcher(PAGE_ERROR).forward(request, response);
			return;
		}
		User user = null;
		try {
			if (isPost(request)) {
				String crudCommand = request.getParameter(REQUEST_PARAM_CRUD_COMMAND);
				validateRequestParamNotNull(crudCommand);
				switch (crudCommand) {
				case CRUD_COMMAND_READ:
					String userLogin = request.getParameter(REQUEST_PARAM_USER_LOGIN);
					validateRequestParamNotNull(userLogin);
					user = userService.getUser(userLogin);
					if (user == null) {
						request.setAttribute(REQUEST_PARAM_ERROR_MESSAGE,
								resourceManager.getValue(ERROR_MSG_CHANGE_USER_ROLE_ACTION_USER_NOT_FOUND));
						request.getRequestDispatcher(PAGE_ERROR).forward(request, response);
						return;
					}
					break;
				case CRUD_COMMAND_UPDATE:
					user = userService.buildUser(request);
					userService.updateUser(user);
					break;
				}
				List<Role> roles = roleService.getAll();
				request.setAttribute(REQUEST_PARAM_FOUND_USER, user);
				request.setAttribute(REQUEST_PARAM_ROLELIST, roles);
			}
			request.getRequestDispatcher(PAGE_ADMIN_CHANGE_USER_ROLE).forward(request, response);
		} catch (ValidateParamException e) {
			request.setAttribute(REQUEST_PARAM_ERROR_MESSAGE,
					resourceManager.getValue(ERROR_MSG_CHANGE_USER_ROLE_ACTION_INDEFINITE_ERROR));
			request.getRequestDispatcher(PAGE_ERROR).forward(request, response);
		}
	}
}
