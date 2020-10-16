package by.htp.epam.cinema.db.dao;

import java.sql.SQLException;
import java.util.List;

import by.htp.epam.cinema.domain.Film;


public interface FilmDao extends BaseDao<Film> {

	List<Film> readAllFilmsWhereGenreIdPresent(int genreId);

	List<Film> readAll(int start, int step);

	Film readById(int id);

	void createFilmWithGenres(Film film, List<Integer> genresId) throws SQLException;

	void updateFilmWithGenres(Film film, List<Integer> genresId) throws SQLException;

	int readCountOfAllFilms();

    int countFreeSeats(Film film);
}
