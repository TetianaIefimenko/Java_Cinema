package by.htp.epam.cinema.web.action.impl;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.epam.cinema.web.action.BaseAction;
import by.htp.epam.cinema.web.util.Timer;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.SESSION_PARAM_TIMER;

public class ViewTimerAction implements BaseAction {

	@Override
	public void executeAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Timer timer = (Timer) session.getAttribute(SESSION_PARAM_TIMER);

		PrintWriter out = response.getWriter();
		if (!timer.isAlive()) {
			session.setAttribute(SESSION_PARAM_TIMER, null);
			out.write("Time is over");
		} else {
			out.write(String.format("%02d : %02d", timer.getMinutesDisplay(), timer.getSecondsDisplay()));
		}
	}
}
