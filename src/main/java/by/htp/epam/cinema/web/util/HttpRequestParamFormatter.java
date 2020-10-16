package by.htp.epam.cinema.web.util;

public class HttpRequestParamFormatter {

	private static final String SIGN_UP_CHECK_USER_DATA_RESULT_STRING = "<span style=\"color:%s;\">%s</span>";
	private static final String SIGN_UP_CHECK_USER_DATA_ERROR_COLOR = "#FF0000";
	private static final String SIGN_UP_CHECK_USER_DATA_SUCCESS_COLOR = "#008B00";

	public static int getInt(String param) {
		return Integer.parseInt(param);
	}

	public static String fixGoogleDriveUrl(String url) {
		if (url.contains("open?id="))
			url = url.replace("open?id=", "uc?id=");
		if (url.contains("file/d/"))
			url = url.replace("file/d/", "uc?id=");
		if (url.contains("/edit?usp=sharing"))
			url = url.replace("/edit?usp=sharing", "");
		return url;
	}

	public static String styleCheckUserDataResult(String result, boolean isSuccess) {
		return String.format(SIGN_UP_CHECK_USER_DATA_RESULT_STRING,
				isSuccess ? SIGN_UP_CHECK_USER_DATA_SUCCESS_COLOR : SIGN_UP_CHECK_USER_DATA_ERROR_COLOR, result);
	}
}
