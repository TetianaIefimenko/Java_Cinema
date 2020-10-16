package by.htp.epam.cinema.service.impl;

import static by.htp.epam.cinema.web.util.HttpRequestParamFormatter.getInt;
import static by.htp.epam.cinema.web.util.HttpRequestParamValidator.validateRequestParamNotNull;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import by.htp.epam.cinema.db.dao.FilmSessionDao;
import by.htp.epam.cinema.db.dao.TicketDao;
import by.htp.epam.cinema.domain.FilmSession;
import by.htp.epam.cinema.domain.Ticket;
import by.htp.epam.cinema.service.FilmSessionService;


public class FilmSessionServiceImpl implements FilmSessionService {

	private FilmSessionDao filmSessionDao;
	private TicketDao ticketDao;

	public FilmSessionServiceImpl(FilmSessionDao filmSessionDao, TicketDao ticketDao) {
		this.filmSessionDao = filmSessionDao;
		this.ticketDao = ticketDao;
	}

	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	@Override
	public List<FilmSession> getFilmSessions(int filmId) {
		return filmSessionDao.readAll(filmId);
	}

	@Override
	public FilmSession getFilmSession(int filmSessionId) {
		return filmSessionDao.read(filmSessionId);
	}

	@Override
	public void createFilmSession(FilmSession filmSession) {
		filmSessionDao.create(filmSession);
	}

	@Override
	public void updateFilmSession(FilmSession filmSession) {
		filmSessionDao.update(filmSession);
	}

	@Override
	public void deleteFilmSession(int filmSessionId) {
		filmSessionDao.delete(filmSessionId);
	}

	@Override
	public boolean isFilmSessionTimeFree(FilmSession filmSession) {
		FilmSession filmSessionFromDB = filmSessionDao.readByDateAndTime(filmSession.getDate(), filmSession.getTime());
		return filmSessionFromDB == null || filmSession.getId() == filmSessionFromDB.getId();
	}

	@Override
	public boolean isRemovalPossible(int filmSessionId) {
		FilmSession filmSession = filmSessionDao.read(filmSessionId);
		StringBuilder sb = new StringBuilder();
		sb.append(filmSession.getDate()).append(" ").append(filmSession.getTime());
		LocalDateTime filmSessionDateAndTime = LocalDateTime.parse(sb, dateTimeFormatter);
		if (filmSessionDateAndTime.isBefore(LocalDateTime.now())) {
			return true;
		} else {
			List<Ticket> tickets = ticketDao.readAllWhereFilmSessionIdPresent(filmSessionId);
			return tickets == null || tickets.size() == 0;
		}
	}

	@Override
	public FilmSession buildFilmSession(HttpServletRequest request) {
		String filmSessionid = request.getParameter(REQUEST_PARAM_FILMSESSION_ID);
		String fkFilmId = request.getParameter(REQUEST_PARAM_FILM_ID);
		String filmSessionDate = request.getParameter(REQUEST_PARAM_FILMSESSION_DATE);
		String filmSessionTime = request.getParameter(REQUEST_PARAM_FILMSESSION_TIME);
		String filmSessionTicketPrice = request.getParameter(REQUEST_PARAM_FILMSESSION_TICKET_PRICE);
		validateRequestParamNotNull(filmSessionid, fkFilmId, filmSessionDate, filmSessionTime, filmSessionTicketPrice);

		return FilmSession.builder().id(getInt(filmSessionid)).filmId(getInt(fkFilmId))
				.date(filmSessionDate).time(filmSessionTime)
				.ticketPrice(new BigDecimal(filmSessionTicketPrice)).build();
	}
}
