package by.htp.epam.cinema.service.impl;

import by.htp.epam.cinema.db.dao.UserDao;
import by.htp.epam.cinema.domain.User;
import by.htp.epam.cinema.service.UserService;
import by.htp.epam.cinema.web.util.PasswordSecurity;
import by.htp.epam.cinema.web.util.ResourceManager;
import by.htp.epam.cinema.web.util.ValidateParamException;

import static by.htp.epam.cinema.web.util.constant.ResourceBundleKeysConstantDeclaration.*;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.htp.epam.cinema.web.util.HttpRequestParamFormatter.getInt;
import static by.htp.epam.cinema.web.util.HttpRequestParamFormatter.styleCheckUserDataResult;
import static by.htp.epam.cinema.web.util.HttpRequestParamValidator.*;

public class DefaultUserService implements UserService {

	private static final ResourceManager RM = ResourceManager.LOCALIZATION;

	private UserDao userDao;

	public DefaultUserService(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public User getUser(int userId) {
		return userDao.read(userId);
	}

	@Override
	public User getUser(String login) {
		return userDao.readByLogin(login);
	}

	@Override
	public User getUser(String login, String password) {
		validateUserCredentialsInput(login, password);
		User user = userDao.readByLogin(login);
		if (user != null && user.getPassword().equals(PasswordSecurity.getHashPassword(password, user.getSalt()))) {
			return user;
		} else
			throw new ValidateParamException(RM.getValue(ERROR_MSG_LOG_IN_ACTION_AUTHENTICATION_ERROR));
	}

	@Override
	public String checkUserData(String login, String email, String password) {
		try {
			validateUserCredentialsInput(login, email, password);
			if (userDao.readByLogin(login) != null)
				return RM.getValue(ERROR_MSG_SIGN_UP_ACTION_LOGIN_IS_NOT_FREE);
			if (userDao.readByEmail(email) != null)
				return RM.getValue(ERROR_MSG_SIGN_UP_ACTION_EMAIL_IS_NOT_FREE);
			return "";
		} catch (ValidateParamException e) {
			return e.getMessage();
		}
	}

	@Override
	public String checkUserLogin(String login) {
		if (login.length() == 0)
			return "";
		else if (!validateLoginInput(login))
			return styleCheckUserDataResult(RM.getValue(ERROR_MSG_SIGN_UP_ACTION_LOGIN_IS_NOT_VALID), false);
		else if (userDao.readByLogin(login) != null)
			return styleCheckUserDataResult(RM.getValue(ERROR_MSG_SIGN_UP_ACTION_LOGIN_IS_NOT_FREE), false);
		else
			return styleCheckUserDataResult(RM.getValue(SUCCESS_MSG_SIGN_UP_ACTION_LOGIN_IS_FREE), true);
	}

	@Override
	public String checkUserEmail(String email) {
		if (email.length() == 0)
			return "";
		else if (!validateEmailInput(email))
			return styleCheckUserDataResult(RM.getValue(ERROR_MSG_SIGN_UP_ACTION_EMAIL_IS_NOT_VALID), false);
		else if (userDao.readByEmail(email) != null)
			return styleCheckUserDataResult(RM.getValue(ERROR_MSG_SIGN_UP_ACTION_EMAIL_IS_NOT_FREE), false);
		else
			return styleCheckUserDataResult(RM.getValue(SUCCESS_MSG_SIGN_UP_ACTION_EMAIL_IS_FREE), true);
	}

	@Override
	public String checkUserPassword(String password) {
		if (password.length() == 0)
			return "";
		else if (password.length() < 5)
			return styleCheckUserDataResult(RM.getValue(ERROR_MSG_SIGN_UP_ACTION_PASSWORD_IS_TOO_SMALL), false);
		else if (password.length() > 15)
			return styleCheckUserDataResult(RM.getValue(ERROR_MSG_SIGN_UP_ACTION_PASSWORD_IS_TOO_LONG), false);
		else if (!validatePasswordInput(password))
			return styleCheckUserDataResult(RM.getValue(ERROR_MSG_SIGN_UP_ACTION_PASSWORD_IS_NOT_VALID), false);
		else
			return styleCheckUserDataResult(RM.getValue(SUCCESS_MSG_SIGN_UP_ACTION_PASSWORD_IS_VALID), true);
	}

	@Override
	public void createUser(String login, String email, String password) {
		String userSalt = PasswordSecurity.getSalt();
		String userPassword = PasswordSecurity.getHashPassword(password, userSalt);
		User user = User.builder().id(0).login(login).email(email).password(userPassword)
				.salt(userSalt).roleId(2).build();
		userDao.create(user);
	}

	@Override
	public boolean isUserAdmin(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(SESSION_PARAM_CURRENT_USER);
		return user != null && user.getRoleId() == 1;
	}

	@Override
	public void updateUser(User user) {
		userDao.update(user);
	}

	@Override
	public User changeUserPassword(int userId, String oldPassword, String newPassword) {
		validateRequestParamNotNull(oldPassword, newPassword);
		if (validatePasswordInput(newPassword)) {
			User user = userDao.read(userId);
			if (user != null
					&& user.getPassword().equals(PasswordSecurity.getHashPassword(oldPassword, user.getSalt()))) {
				String newPasswordSalt = PasswordSecurity.getSalt();
				String newPasswordHash = PasswordSecurity.getHashPassword(newPassword, newPasswordSalt);
				user.setSalt(newPasswordSalt);
				user.setPassword(newPasswordHash);
				userDao.update(user);
				return user;
			}
		} else
			throw new ValidateParamException(RM.getValue(ERROR_MSG_CHANGE_USER_PASSWORD_ACTION_INCORRECT_PASSWORD));
		return null;
	}

	@Override
	public User buildUser(HttpServletRequest request) {
		String userId = request.getParameter(REQUEST_PARAM_USER_ID);
		String userLogin = request.getParameter(REQUEST_PARAM_USER_LOGIN);
		String userEmail = request.getParameter(REQUEST_PARAM_USER_EMAIL);
		String userPassword = request.getParameter(REQUEST_PARAM_USER_PASSWORD);
		String userSalt = request.getParameter(REQUEST_PARAM_USER_SALT);
		String userRoleId = request.getParameter(REQUEST_PARAM_USER_ROLE_ID);
		validateRequestParamNotNull(userId, userLogin, userEmail, userPassword, userSalt, userRoleId);

		return User.builder().id(getInt(userId)).login(userLogin).email(userEmail).password(userPassword)
				.salt(userSalt).roleId(getInt(userRoleId)).build();
	}
}
