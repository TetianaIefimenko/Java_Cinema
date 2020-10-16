package by.htp.epam.cinema.service;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import by.htp.epam.cinema.domain.Film;
import by.htp.epam.cinema.domain.Genre;
import by.htp.epam.cinema.domain.CompositeEntities.CompositeFilm;

public interface FilmService extends Service {

	List<Film> getAllFilms();

	List<CompositeFilm> getFilmsWithTheirGenres(int start, int step, String sortBy);

	List<CompositeFilm> getAllFilmsWithTheirGenres(Genre genre);

	Film getFilm(int filmId);

	void createFilm(Film film, List<Integer> genresId) throws SQLException;

	void updateFilmAndGenres(Film film, List<Integer> genresId) throws SQLException;

	void deleteFilm(Film film);

	Film buildFilm(HttpServletRequest request);

	int getAllFilmsCount();

	CompositeFilm getFilmWithGenres(int filmId);
}
