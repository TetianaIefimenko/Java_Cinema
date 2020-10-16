package by.htp.epam.cinema.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.htp.epam.cinema.db.dao.AbstractDao;
import by.htp.epam.cinema.db.dao.FilmDao;
import by.htp.epam.cinema.domain.Film;

public class FilmDaoImpl extends AbstractDao implements FilmDao {
	private static Logger logger = LoggerFactory.getLogger(FilmDaoImpl.class);

	private static final String SQL_QUERY_FILM_CREATE = "INSERT INTO `cinema_v2.0`.`films` (`filmName`, `description`, `posterUrl`, `youTubeVideoId`) VALUES (?,?,?,?);";
	private static final String SQL_QUERY_ADD_GENRES_TO_FILM = "INSERT INTO `cinema_v2.0`.`films_genres` (`film_id`, `genre_id`) VALUES (?, ?);";
	private static final String SQL_QUERY_FILM_READ = "SELECT `id`, `filmName`, `description`, `posterUrl`, `youTubeVideoId` FROM `cinema_v2.0`.`films` WHERE  `id`=?;";
	private static final String SQL_QUERY_FILM_READ_ALL = "SELECT `id`, `filmName`, `description`, `posterUrl`, `youTubeVideoId` FROM `cinema_v2.0`.`films`;";
	private static final String SQL_QUERY_FILM_READ_ALL_WITH_LIMIT = "SELECT `id`, `filmName`, `description`, `posterUrl`, `youTubeVideoId` FROM `cinema_v2.0`.`films` LIMIT ?,?;";
	private static final String SQL_QUERY_FILM_READ_ALL_BY_GENRE_ID = "SELECT `id`, `filmName`, `description`, `posterUrl`, `youTubeVideoId` FROM `cinema_v2.0`.`films` f "
			+ "inner join `cinema_v2.0`.`films_genres` fg on f.id=fg.film_id where fg.genre_id=?;";
	private static final String SQL_QUERY_FILM_UPDATE = "UPDATE `cinema_v2.0`.`films` SET `filmName`=?, `description`=?, `posterUrl`=?, `youTubeVideoId`=? WHERE `id`=?;";
	private static final String SQL_QUERY_FILM_DELETE = "DELETE FROM `cinema_v2.0`.`films` WHERE  `id`=?;";
	private static final String SQL_QUERY_FILM_GENRES_DELETE = "DELETE FROM `cinema_v2.0`.`films_genres` WHERE  `film_id`=?;";
	private static final String SQL_QUERY_FILMS_COUNT = "SELECT COUNT(`id`) FROM `cinema_v2.0`.`films`;";
	private static final String SQL_QUERY_FILM_READ_BY_ID = "SELECT * from films where id = ?";
	private static final String SQL_QUERY_OCCUPIED_SEATS_BY_FILM_ID = "SELECT COUNT(seats.id) FROM films as f JOIN sessions as s on f.id = s.film_id JOIN tickets as t on t.session_id = s.id JOIN seats on seats.id = t.seat_id WHERE f.id = ?";

	@Override
	public void create(Film entity) {
		Connection con = connectionPool.getConnection();
		try (PreparedStatement ps = con.prepareStatement(SQL_QUERY_FILM_CREATE)) {
			ps.setString(1, entity.getFilmName());
			ps.setString(2, entity.getDescription());
			ps.setString(3, entity.getPosterUrl());
			ps.setString(4, entity.getYouTubeVideoId());
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("SQLException in create method of FilmDaoImpl class", e);
		} finally {
			connectionPool.putConnection(con);
		}
	}

	@Override
	public void createFilmWithGenres(Film film, List<Integer> genresId) throws SQLException {
		Connection con = connectionPool.getConnection();
		PreparedStatement createFilmPs = null;
		PreparedStatement addGenrePs = null;
		try {
			con.setAutoCommit(false);
			createFilmPs = con.prepareStatement(SQL_QUERY_FILM_CREATE, Statement.RETURN_GENERATED_KEYS);
			addGenrePs = con.prepareStatement(SQL_QUERY_ADD_GENRES_TO_FILM);

			createFilmPs.setString(1, film.getFilmName());
			createFilmPs.setString(2, film.getDescription());
			createFilmPs.setString(3, film.getPosterUrl());
			createFilmPs.setString(4, film.getYouTubeVideoId());
			if (createFilmPs.executeUpdate() > 0) {
				ResultSet rs = createFilmPs.getGeneratedKeys();
				if (rs.next())
					film.setId(rs.getInt(1));
			} else
				throw new SQLException();

			for (int genreId : genresId) {
				addGenrePs.setInt(1, film.getId());
				addGenrePs.setInt(2, genreId);
				addGenrePs.executeUpdate();
			}
			con.commit();
		} catch (SQLException e) {
			logger.error("SQLException in createFilmWithGenres method of FilmDaoImpl class", e);
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException ex) {
					logger.error("Transaction can't be rolled back", ex);
				}
			}
		} finally {
			if (createFilmPs != null) {
				createFilmPs.close();
			}
			if (addGenrePs != null) {
				addGenrePs.close();
			}
			con.setAutoCommit(true);
			connectionPool.putConnection(con);
		}
	}

	@Override
	public Film read(int id) {
		ResultSet rs = null;
		Connection con = connectionPool.getConnection();
		try (PreparedStatement ps = con.prepareStatement(SQL_QUERY_FILM_READ)) {
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next())
				return buildEntity(rs);
		} catch (SQLException e) {
			logger.error("SQLException in read method of FilmDaoImpl class", e);
		} finally {
			connectionPool.putConnection(con);
			close(rs);
		}
		return null;
	}

	@Override
	public List<Film> readAll() {
		List<Film> films = null;
		ResultSet rs = null;
		Connection con = connectionPool.getConnection();
		try (Statement ps = con.createStatement()) {
			rs = ps.executeQuery(SQL_QUERY_FILM_READ_ALL);
			films = new ArrayList<>();
			while (rs.next()) {
				films.add(buildEntity(rs));
			}
		} catch (SQLException e) {
			logger.error("SQLException in readAll method of FilmDaoImpl class", e);
		} finally {
			connectionPool.putConnection(con);
			close(rs);
		}
		return films;
	}

	@Override
	public List<Film> readAll(int start, int step) {
		List<Film> films = null;
		ResultSet rs = null;
		Connection con = connectionPool.getConnection();
		try (PreparedStatement ps = con.prepareStatement(SQL_QUERY_FILM_READ_ALL_WITH_LIMIT)) {
			ps.setInt(1, start);
			ps.setInt(2, step);
			rs = ps.executeQuery();
			films = new ArrayList<>();
			while (rs.next()) {
				films.add(buildEntity(rs));
			}
		} catch (SQLException e) {
			logger.error("SQLException in readAllFilmsWhereGenreIdPresent method of FilmDaoImpl class", e);
		} finally {
			connectionPool.putConnection(con);
			close(rs);
		}
		return films;
	}

	@Override
	public Film readById(int id) {
		Film film = null;
		ResultSet rs = null;
		Connection con = connectionPool.getConnection();
		try (PreparedStatement ps = con.prepareStatement(SQL_QUERY_FILM_READ_BY_ID)) {
			ps.setInt(1,id);
			rs = ps.executeQuery();
			if(rs.next()) {
				film  = buildEntity(rs);
			}
		} catch (SQLException e) {
			logger.error("SQLException in readAllFilmsWhereGenreIdPresent method of FilmDaoImpl class", e);
		} finally {
			connectionPool.putConnection(con);
			close(rs);
		}
		return film;
	}

	@Override
	public List<Film> readAllFilmsWhereGenreIdPresent(int genreId) {
		List<Film> films = null;
		ResultSet rs = null;
		Connection con = connectionPool.getConnection();
		try (PreparedStatement ps = con.prepareStatement(SQL_QUERY_FILM_READ_ALL_BY_GENRE_ID)) {
			ps.setInt(1, genreId);
			rs = ps.executeQuery();
			films = new ArrayList<>();
			while (rs.next()) {
				films.add(buildEntity(rs));
			}
		} catch (SQLException e) {
			logger.error("SQLException in readAllFilmsWhereGenreIdPresent method of FilmDaoImpl class", e);
		} finally {
			connectionPool.putConnection(con);
			close(rs);
		}
		return films;
	}

	@Override
	public int readCountOfAllFilms() {
		ResultSet rs = null;
		Connection con = connectionPool.getConnection();
		try (Statement ps = con.createStatement()) {
			rs = ps.executeQuery(SQL_QUERY_FILMS_COUNT);
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			logger.error("SQLException in readAll method of FilmDaoImpl class", e);
		} finally {
			connectionPool.putConnection(con);
			close(rs);
		}
		return -1;
	}

	@Override
	public int countFreeSeats(Film film) {
		ResultSet rs = null;
		Connection con = connectionPool.getConnection();
		try (PreparedStatement ps = con.prepareStatement(SQL_QUERY_OCCUPIED_SEATS_BY_FILM_ID)) {
			ps.setInt(1, film.getId());
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			logger.error("SQLException in countFreeSeats method of FilmDaoImpl class", e);
		} finally {
			connectionPool.putConnection(con);
			close(rs);
		}
		return 0;
	}

	@Override
	public void update(Film entity) {
		Connection con = connectionPool.getConnection();
		try (PreparedStatement ps = con.prepareStatement(SQL_QUERY_FILM_UPDATE)) {
			ps.setString(1, entity.getFilmName());
			ps.setString(2, entity.getDescription());
			ps.setString(3, entity.getPosterUrl());
			ps.setString(4, entity.getYouTubeVideoId());
			ps.setInt(5, entity.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("SQLException in update method of FilmDaoImpl class", e);
		} finally {
			connectionPool.putConnection(con);
		}
	}

	@Override
	public void updateFilmWithGenres(Film film, List<Integer> genresId) throws SQLException {
		Connection con = connectionPool.getConnection();
		PreparedStatement updateFilmPs = null;
		PreparedStatement deleteGenrePs = null;
		PreparedStatement addGenrePs = null;
		try {
			con.setAutoCommit(false);
			updateFilmPs = con.prepareStatement(SQL_QUERY_FILM_UPDATE);
			deleteGenrePs = con.prepareStatement(SQL_QUERY_FILM_GENRES_DELETE);
			addGenrePs = con.prepareStatement(SQL_QUERY_ADD_GENRES_TO_FILM);

			updateFilmPs.setString(1, film.getFilmName());
			updateFilmPs.setString(2, film.getDescription());
			updateFilmPs.setString(3, film.getPosterUrl());
			updateFilmPs.setString(4, film.getYouTubeVideoId());
			updateFilmPs.setInt(5, film.getId());
			updateFilmPs.executeUpdate();

			deleteGenrePs.setInt(1, film.getId());
			deleteGenrePs.executeUpdate();

			for (int genreId : genresId) {
				addGenrePs.setInt(1, film.getId());
				addGenrePs.setInt(2, genreId);
				addGenrePs.executeUpdate();
			}
			con.commit();
		} catch (SQLException e) {
			logger.error("SQLException in updateFilmWithGenres method of FilmDaoImpl class", e);
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException ex) {
					logger.error("Transaction can't be rolled back", ex);
				}
			}
		} finally {
			if (updateFilmPs != null) {
				updateFilmPs.close();
			}
			if (deleteGenrePs != null) {
				deleteGenrePs.close();
			}
			if (addGenrePs != null) {
				addGenrePs.close();
			}
			con.setAutoCommit(true);
			connectionPool.putConnection(con);
		}
	}

	@Override
	public void delete(int entityId) {
		Connection con = connectionPool.getConnection();
		try (PreparedStatement ps = con.prepareStatement(SQL_QUERY_FILM_DELETE)) {
			ps.setInt(1, entityId);
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("SQLException in delete method of FilmDaoImpl class", e);
		} finally {
			connectionPool.putConnection(con);
		}
	}

	@Override
	public Film buildEntity(ResultSet rs) throws SQLException {
		return Film.builder().id(rs.getInt("id")).filmName(rs.getString("filmName"))
				.description(rs.getString("description")).posterUrl(rs.getString("posterUrl"))
				.youTubeVideoId(rs.getString("youTubeVideoId")).build();
	}
}
