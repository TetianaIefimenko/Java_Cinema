package by.htp.epam.cinema.web.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidateParamException extends RuntimeException {

	private static final long serialVersionUID = 969443519136553553L;
	private static Logger logger = LoggerFactory.getLogger(ValidateParamException.class);

	public ValidateParamException() {
	}

	public ValidateParamException(String message, Throwable cause) {
		super(message, cause);
		logger.error(message + " " + cause);
	}

	public ValidateParamException(String message) {
		super(message);
		logger.error(message);
	}

	public ValidateParamException(Throwable cause) {
		super(cause);
		logger.error("{}", cause);
	}
}
