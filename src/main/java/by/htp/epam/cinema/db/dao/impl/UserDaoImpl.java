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
import by.htp.epam.cinema.db.dao.UserDao;
import by.htp.epam.cinema.domain.User;

public class UserDaoImpl extends AbstractDao implements UserDao {
	private static Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

	private static final String SQL_QUERY_USER_CREATE = "INSERT INTO `cinema_v2.0`.`users` (`login`, `email`, `password`, `salt`, `role_id`) VALUES (?,?,?,?,?);";
	private static final String SQL_QUERY_USER_READ = "SELECT `id`, `login`, `email`, `password`, `salt`, `role_id` FROM `cinema_v2.0`.`users` WHERE `id`=?;";
	private static final String SQL_QUERY_USER_READ_BY_LOGIN = "SELECT `id`, `login`, `email`, `password`, `salt`, `role_id` FROM `cinema_v2.0`.`users` WHERE `login`=?;";
	private static final String SQL_QUERY_USER_READ_BY_EMAIL = "SELECT `id`, `login`, `email`, `password`, `salt`, `role_id` FROM `cinema_v2.0`.`users` WHERE `email`=?;";
	private static final String SQL_QUERY_USER_READ_ALL = "SELECT `id`, `login`, `email`, `password`, `salt`, `role_id` FROM `cinema_v2.0`.`users`;";
	private static final String SQL_QUERY_USER_UPDATE = "UPDATE `cinema_v2.0`.`users` SET `login`=?, `email`=?, `password`=?, `salt`=?, `role_id`=? WHERE  `id`=?;";
	private static final String SQL_QUERY_USER_DELETE = "DELETE FROM `cinema_v2.0`.`users` WHERE  `id`=?;";

	@Override
	public void create(User entity) {
		Connection con = connectionPool.getConnection();
		try (PreparedStatement ps = con.prepareStatement(SQL_QUERY_USER_CREATE)) {
			ps.setString(1, entity.getLogin());
			ps.setString(2, entity.getEmail());
			ps.setString(3, entity.getPassword());
			ps.setString(4, entity.getSalt());
			ps.setInt(5, entity.getRoleId());
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("SQLException in create method of UserDaoImpl class", e);
		} finally {
			connectionPool.putConnection(con);
		}
	}

	@Override
	public User read(int id) {
		ResultSet rs = null;
		Connection con = connectionPool.getConnection();
		try (PreparedStatement ps = con.prepareStatement(SQL_QUERY_USER_READ)) {
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next())
				return buildEntity(rs);
		} catch (SQLException e) {
			logger.error("SQLException in read(int id) method of UserDaoImpl class", e);
		} finally {
			connectionPool.putConnection(con);
			close(rs);
		}
		return null;
	}

	@Override
	public User readByLogin(String login) {
		ResultSet rs = null;
		Connection con = connectionPool.getConnection();
		try (PreparedStatement ps = con.prepareStatement(SQL_QUERY_USER_READ_BY_LOGIN)) {
			ps.setString(1, login);
			rs = ps.executeQuery();
			if (rs.next())
				return buildEntity(rs);
		} catch (SQLException e) {
			logger.error("SQLException in read(String login, String password) method of UserDaoImpl class", e);
		} finally {
			connectionPool.putConnection(con);
			close(rs);
		}
		return null;
	}

	@Override
	public User readByEmail(String email) {
		ResultSet rs = null;
		Connection con = connectionPool.getConnection();
		try (PreparedStatement ps = con.prepareStatement(SQL_QUERY_USER_READ_BY_EMAIL)) {
			ps.setString(1, email);
			rs = ps.executeQuery();
			if (rs.next())
				return buildEntity(rs);
		} catch (SQLException e) {
			logger.error("SQLException in read(String login, String password) method of UserDaoImpl class", e);
		} finally {
			connectionPool.putConnection(con);
			close(rs);
		}
		return null;
	}

	@Override
	public List<User> readAll() {
		List<User> users = null;
		ResultSet rs = null;
		Connection con = connectionPool.getConnection();
		try (Statement ps = con.createStatement()) {
			rs = ps.executeQuery(SQL_QUERY_USER_READ_ALL);
			users = new ArrayList<>();
			while (rs.next()) {
				users.add(buildEntity(rs));
			}
		} catch (SQLException e) {
			logger.error("SQLException in readAll method of UserDaoImpl class", e);
		} finally {
			connectionPool.putConnection(con);
			close(rs);
		}
		return users;
	}

	@Override
	public void update(User entity) {
		Connection con = connectionPool.getConnection();
		try (PreparedStatement ps = con.prepareStatement(SQL_QUERY_USER_UPDATE)) {
			ps.setString(1, entity.getLogin());
			ps.setString(2, entity.getEmail());
			ps.setString(3, entity.getPassword());
			ps.setString(4, entity.getSalt());
			ps.setInt(5, entity.getRoleId());
			ps.setInt(6, entity.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("SQLException in update method of UserDaoImpl class", e);
		} finally {
			connectionPool.putConnection(con);
		}
	}

	@Override
	public void delete(int entityId) {
		Connection con = connectionPool.getConnection();
		try (PreparedStatement ps = con.prepareStatement(SQL_QUERY_USER_DELETE)) {
			ps.setInt(1, entityId);
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("SQLException in delete method of UserDaoImpl class", e);
		} finally {
			connectionPool.putConnection(con);
		}
	}

	@Override
	public User buildEntity(ResultSet rs) throws SQLException {
		return User.builder().id(rs.getInt("id")).login(rs.getString("login")).email(rs.getString("email"))
				.password(rs.getString("password")).salt(rs.getString("salt"))
				.roleId(Integer.parseInt(rs.getString("role_id"))).build();
	}
}
