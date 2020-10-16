package by.htp.epam.cinema.web.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.epam.cinema.web.util.ResourceManager;

public interface BaseAction {

	public static final ResourceManager resourceManager = ResourceManager.LOCALIZATION;

	public void executeAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException;

}
