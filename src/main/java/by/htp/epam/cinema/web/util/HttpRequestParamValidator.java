package by.htp.epam.cinema.web.util;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import static by.htp.epam.cinema.web.util.constant.ResourceBundleKeysConstantDeclaration.ERROR_MSG_SIGN_UP_ACTION_LOGIN_IS_NOT_VALID;
import static by.htp.epam.cinema.web.util.constant.ResourceBundleKeysConstantDeclaration.ERROR_MSG_SIGN_UP_ACTION_EMAIL_IS_NOT_VALID;
import static by.htp.epam.cinema.web.util.constant.ResourceBundleKeysConstantDeclaration.ERROR_MSG_SIGN_UP_ACTION_PASSWORD_IS_NOT_VALID;
import static by.htp.epam.cinema.web.util.constant.ResourceBundleKeysConstantDeclaration.ERROR_MSG_LOG_IN_ACTION_AUTHENTICATION_ERROR;

public class HttpRequestParamValidator {

	private static final ResourceManager RM = ResourceManager.LOCALIZATION;
	private static final String LOGIN_INPUT_VALIDATION_REGEX = "[A-Za-z0-9_]{5,15}";
	private static final String EMAIL_INPUT_VALIDATION_REGEX = "(\\w{5,})@(\\w+\\.)([a-z]{2,4})";
	private static final String PASSWORD_INPUT_VALIDATION_REGEX = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{5,10}";
	private static final String LOCALE_PARAM_VALIDATION_REGEX = "[a-zA-Z]{2}_[a-zA-Z]{2}";

	private static final String METHOD_NAME_POST = "POST";
	private static final String ERROR_MSG_EMPTY_PARAM = "Empty param recieved";
	private static final String ERROR_MSG_UNDEFINED_LOCALE = "Undefined locale";

	public static void validateRequestParamNotNull(String... str) {
		for (String s : str) {
			if (s == null) {
				throw new ValidateParamException(ERROR_MSG_EMPTY_PARAM);
			}
		}
	}

	public static void validateRequestParamLocale(String locale) {
		if (locale == null || !Pattern.matches(LOCALE_PARAM_VALIDATION_REGEX, locale))
			throw new ValidateParamException(ERROR_MSG_UNDEFINED_LOCALE);
	}

	public static boolean isPost(HttpServletRequest reg) {
		return reg.getMethod().toUpperCase().equals(METHOD_NAME_POST);
	}

	public static void validateUserCredentialsInput(String login, String password) {
		validateRequestParamNotNull(login, password);
		if (!validateLoginInput(login) || !validatePasswordInput(password))
			throw new ValidateParamException(RM.getValue(ERROR_MSG_LOG_IN_ACTION_AUTHENTICATION_ERROR));
	}

	public static void validateUserCredentialsInput(String login, String email, String password) {
		validateRequestParamNotNull(login, email, password);
		if (!validateLoginInput(login))
			throw new ValidateParamException(RM.getValue(ERROR_MSG_SIGN_UP_ACTION_LOGIN_IS_NOT_VALID));
		if (!validateEmailInput(email))
			throw new ValidateParamException(RM.getValue(ERROR_MSG_SIGN_UP_ACTION_EMAIL_IS_NOT_VALID));
		if (!validatePasswordInput(password))
			throw new ValidateParamException(RM.getValue(ERROR_MSG_SIGN_UP_ACTION_PASSWORD_IS_NOT_VALID));
	}

	public static boolean validateLoginInput(String login) {
		return Pattern.matches(LOGIN_INPUT_VALIDATION_REGEX, login);
	}

	public static boolean validateEmailInput(String email) {
		return Pattern.matches(EMAIL_INPUT_VALIDATION_REGEX, email);
	}

	public static boolean validatePasswordInput(String password) {
		return Pattern.matches(PASSWORD_INPUT_VALIDATION_REGEX, password);
	}
}
