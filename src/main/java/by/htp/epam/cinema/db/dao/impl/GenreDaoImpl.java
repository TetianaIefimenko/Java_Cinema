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
import by.htp.epam.cinema.db.dao.GenreDao;
import by.htp.epam.cinema.domain.Genre;

public class GenreDaoImpl extends AbstractDao implements GenreDao {
	private static Logger logger = LoggerFactory.getLogger(GenreDaoImpl.class);

	private static final String SQL_QUERY_GENRE_CREATE = "INSERT INTO `cinema_v2.0`.`genres` (`genreName`) VALUES (?);";
	private static final String SQL_QUERY_GENRE_READ = "SELECT `id`, `genreName` FROM `cinema_v2.0`.`genres` WHERE  `id`=?;";
	private static final String SQL_QUERY_GENRE_READ_BY_FILM_ID = "SELECT `id`, `genreName`"
			+ "FROM `cinema_v2.0`.`genres` g INNER JOIN `cinema_v2.0`.`films_genres` fg ON g.`id`=fg.`genre_id` WHERE fg.`film_id`=?;";
	private static final String SQL_QUERY_GENRE_READ_ALL = "SELECT `id`, `genreName` FROM `cinema_v2.0`.`genres`;";
	private static final String SQL_QUERY_GENRE_UPDATE = "UPDATE `cinema_v2.0`.`genres` SET `genreName`=? WHERE  `id`=?;";
	private static final String SQL_QUERY_GENRE_DELETE = "DELETE FROM `cinema_v2.0`.`genres` WHERE  `id`=?;";

	@Override
	public void create(Genre entity) {
		Connection con = connectionPool.getConnection();
		try (PreparedStatement ps = con.prepareStatement(SQL_QUERY_GENRE_CREATE)) {
			ps.setString(1, entity.getGenreName());
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("SQLException in create method of GenreDaoImpl class", e);
		} finally {
			connectionPool.putConnection(con);
		}
	}

	@Override
	public Genre read(int id) {
		ResultSet rs = null;
		Connection con = connectionPool.getConnection();
		try (PreparedStatement ps = con.prepareStatement(SQL_QUERY_GENRE_READ)) {
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next())
				return buildEntity(rs);
		} catch (SQLException e) {
			logger.error("SQLException in read method of GenreDaoImpl class", e);
		} finally {
			connectionPool.putConnection(con);
			close(rs);
		}
		return null;
	}

	@Override
	public List<Genre> readAll() {
		List<Genre> genres = null;
		ResultSet rs = null;
		Connection con = connectionPool.getConnection();
		try (Statement ps = con.createStatement()) {
			rs = ps.executeQuery(SQL_QUERY_GENRE_READ_ALL);
			genres = new ArrayList<>();
			while (rs.next()) {
				genres.add(buildEntity(rs));
			}
		} catch (SQLException e) {
			logger.error("SQLException in readAll method of GenreDaoImpl class", e);
		} finally {
			connectionPool.putConnection(con);
			close(rs);
		}
		return genres;
	}

	@Override
	public List<Genre> readAll(int filmId) {
		List<Genre> genres = null;
		ResultSet rs = null;
		Connection con = connectionPool.getConnection();
		try (PreparedStatement ps = con.prepareStatement(SQL_QUERY_GENRE_READ_BY_FILM_ID)) {
			ps.setInt(1, filmId);
			rs = ps.executeQuery();
			genres = new ArrayList<>();
			while (rs.next()) {
				genres.add(buildEntity(rs));
			}
		} catch (SQLException e) {
			logger.error("SQLException in readAll(Film film) method of GenreDaoImpl class", e);
		} finally {
			connectionPool.putConnection(con);
			close(rs);
		}
		return genres;
	}

	@Override
	public void update(Genre entity) {
		Connection con = connectionPool.getConnection();
		try (PreparedStatement ps = con.prepareStatement(SQL_QUERY_GENRE_UPDATE)) {
			ps.setString(1, entity.getGenreName());
			ps.setInt(2, entity.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("SQLException in update method of GenreDaoImpl class", e);
		} finally {
			connectionPool.putConnection(con);
		}
	}

	@Override
	public void delete(int entityId) {
		Connection con = connectionPool.getConnection();
		try (PreparedStatement ps = con.prepareStatement(SQL_QUERY_GENRE_DELETE)) {
			ps.setInt(1, entityId);
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("SQLException in delete method of GenreDaoImpl class", e);
		} finally {
			connectionPool.putConnection(con);
		}
	}

	@Override
	public Genre buildEntity(ResultSet rs) throws SQLException {
		return Genre.builder()
				.id(rs.getInt("id"))
				.genreName(rs.getString("genreName")).build();
	}

}
