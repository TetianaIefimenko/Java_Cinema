package by.htp.epam.cinema.db.dao;

import java.util.List;

import by.htp.epam.cinema.domain.FilmSession;

public interface FilmSessionDao extends BaseDao<FilmSession> {

	List<FilmSession> readAll(int filmId);

	FilmSession readByDateAndTime(String date, String time);
}
