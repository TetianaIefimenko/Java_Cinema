package by.htp.epam.cinema.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import by.htp.epam.cinema.domain.FilmSession;

public interface FilmSessionService extends Service {

	List<FilmSession> getFilmSessions(int filmId);

	FilmSession getFilmSession(int filmSessionId);

	FilmSession buildFilmSession(HttpServletRequest request);

	void createFilmSession(FilmSession filmSession);

	void updateFilmSession(FilmSession filmSession);

	void deleteFilmSession(int filmSessionId);

	boolean isRemovalPossible(int filmSessionId);

	boolean isFilmSessionTimeFree(FilmSession filmSession);

}
