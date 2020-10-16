package by.htp.epam.cinema.service.impl;

import static by.htp.epam.cinema.web.util.HttpRequestParamFormatter.fixGoogleDriveUrl;
import static by.htp.epam.cinema.web.util.HttpRequestParamFormatter.getInt;
import static by.htp.epam.cinema.web.util.HttpRequestParamValidator.validateRequestParamNotNull;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.REQUEST_PARAM_FILM_DESCRIPTION;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.REQUEST_PARAM_FILM_ID;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.REQUEST_PARAM_FILM_NAME;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.REQUEST_PARAM_FILM_POSTER_URL;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.REQUEST_PARAM_FILM_YOUTUBE_VIDEO_ID;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import by.htp.epam.cinema.comparators.FilmComparators;
import by.htp.epam.cinema.db.dao.*;
import by.htp.epam.cinema.domain.*;
import by.htp.epam.cinema.domain.CompositeEntities.CompositeTicket;
import by.htp.epam.cinema.domain.CompositeEntities.CompositeFilm;
import by.htp.epam.cinema.service.FilmService;

public class DefaultFilmService implements FilmService {

    private FilmDao filmDao;
    private GenreDao genreDao;
    private TicketDao ticketDao;
    private FilmSessionDao filmSessionDao;
    private SeatDao seatDao;

    public DefaultFilmService(FilmDao filmDao, GenreDao genreDao, TicketDao ticketDao, FilmSessionDao filmSessionDao, SeatDao seatDao) {
        this.filmDao = filmDao;
        this.genreDao = genreDao;
        this.ticketDao = ticketDao;
        this.filmSessionDao = filmSessionDao;
        this.seatDao = seatDao;
    }

    @Override
    public List<Film> getAllFilms() {
        return filmDao.readAll();
    }

    @Override
    public List<CompositeFilm> getAllFilmsWithTheirGenres(Genre genre) {
        List<Film> films = filmDao.readAllFilmsWhereGenreIdPresent(genre.getId());
        return defineFilmsGenres(films);
    }

    @Override
    public Film getFilm(int filmId) {
        return filmDao.read(filmId);
    }

    @Override
    public void createFilm(Film film, List<Integer> genresId) throws SQLException {
        filmDao.createFilmWithGenres(film, genresId);
    }

    @Override
    public void updateFilmAndGenres(Film film, List<Integer> genresId) throws SQLException {
        filmDao.updateFilmWithGenres(film, genresId);
    }

    @Override
    public void deleteFilm(Film film) {
        filmDao.delete(film.getId());
    }

    @Override
    public Film buildFilm(HttpServletRequest request) {
        String id = request.getParameter(REQUEST_PARAM_FILM_ID);
        String filmName = request.getParameter(REQUEST_PARAM_FILM_NAME);
        String description = request.getParameter(REQUEST_PARAM_FILM_DESCRIPTION);
        String posterUrl = request.getParameter(REQUEST_PARAM_FILM_POSTER_URL);
        String youTubeVideoId = request.getParameter(REQUEST_PARAM_FILM_YOUTUBE_VIDEO_ID);
        validateRequestParamNotNull(id, filmName, description, posterUrl);
        return Film.builder().id(getInt(id)).filmName(filmName).description(description)
                .posterUrl(fixGoogleDriveUrl(posterUrl)).youTubeVideoId(youTubeVideoId).build();
    }

    @Override
    public List<CompositeFilm> getFilms(int start, int step, String sortBy) {
        List<Film> films = filmDao.readAll(start, step);
        List<FilmSession> filmSessions = filmSessionDao.readAll();

        if (sortBy.equals("name"))
            films.sort(FilmComparators.NAME);
        if (sortBy.equals("date")) {
            filmSessions.sort(FilmComparators.DATE);
            Set<Film> filmSet = new HashSet<>();
            for (FilmSession fs : filmSessions) {
                Film fl = filmDao.readById(fs.getFilmId());
                filmSet.add(fl);
            }
            films.clear();
            films.addAll(filmSet);
        }

        if (sortBy.equals("free_tickets")) {
            films.sort((f1, f2) -> {
                int f1Count = countFreeSeats(f1);
                int f2Count = countFreeSeats(f2);
                return Integer.compare(f1Count, f2Count);
            });
        }
        return defineFilmsGenres(films);
    }

    private int countFreeSeats(Film film) {
        return seatDao.getAllSeatsCount() - filmDao.countFreeSeats(film);
    }

    @Override
    public int getAllFilmsCount() {
        return filmDao.readCountOfAllFilms();
    }

    @Override
    public CompositeFilm getFilmWithGenres(int filmId) {
        Film film = filmDao.read(filmId);
        return new CompositeFilm(film.getId(), film.getFilmName(), film.getDescription(), film.getPosterUrl(),
                film.getYouTubeVideoId(), genreDao.readAll(film.getId()));
    }

    private List<CompositeFilm> defineFilmsGenres(List<Film> films) {
        List<CompositeFilm> filmsWithGenres = new ArrayList<>();
        for (Film f : films) {
            CompositeFilm cf = new CompositeFilm(f.getId(), f.getFilmName(), f.getDescription(), f.getPosterUrl(),
                    f.getYouTubeVideoId(), genreDao.readAll(f.getId()));
            filmsWithGenres.add(cf);
        }
        return filmsWithGenres;
    }

}
