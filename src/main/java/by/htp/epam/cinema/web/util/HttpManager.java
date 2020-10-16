package by.htp.epam.cinema.web.util;

public class HttpManager {

	public static String getLocationForRedirect(String actionName) {
		return "cinema?action=" + actionName;
	}
}
