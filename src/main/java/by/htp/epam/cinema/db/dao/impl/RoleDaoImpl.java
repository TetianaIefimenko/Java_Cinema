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
import by.htp.epam.cinema.db.dao.RoleDao;
import by.htp.epam.cinema.domain.Role;


public class RoleDaoImpl extends AbstractDao implements RoleDao {
	private static Logger logger = LoggerFactory.getLogger(RoleDaoImpl.class);

	private static final String SQL_QUERY_ROLE_CREATE = "INSERT INTO `cinema_v2.0`.`roles` (`roleName`) VALUES (?);";
	private static final String SQL_QUERY_ROLE_READ = "SELECT `id`, `roleName` FROM `cinema_v2.0`.`roles` WHERE `id`=?;";
	private static final String SQL_QUERY_ROLE_READ_ALL = "SELECT `id`, `roleName` FROM `cinema_v2.0`.`roles`;";
	private static final String SQL_QUERY_ROLE_UPDATE = "UPDATE `cinema_v2.0`.`roles` SET `roleName`=? WHERE `id`=?;";
	private static final String SQL_QUERY_ROLE_DELETE = "DELETE FROM `cinema_v2.0`.`roles` WHERE  `id`=?;";

	@Override
	public void create(Role entity) {
		Connection con = connectionPool.getConnection();
		try (PreparedStatement ps = con.prepareStatement(SQL_QUERY_ROLE_CREATE)) {
			ps.setString(1, entity.getRoleName());
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("SQLException in create method of RoleDaoImpl class", e);
		} finally {
			connectionPool.putConnection(con);
		}
	}

	@Override
	public Role read(int id) {
		ResultSet rs = null;
		Connection con = connectionPool.getConnection();
		try (PreparedStatement ps = con.prepareStatement(SQL_QUERY_ROLE_READ)) {
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next())
				return buildEntity(rs);
		} catch (SQLException e) {
			logger.error("SQLException in read method of RoleDaoImpl class", e);
		} finally {
			connectionPool.putConnection(con);
			close(rs);
		}
		return null;
	}

	@Override
	public List<Role> readAll() {
		List<Role> roles = null;
		ResultSet rs = null;
		Connection con = connectionPool.getConnection();
		try (Statement ps = con.createStatement()) {
			rs = ps.executeQuery(SQL_QUERY_ROLE_READ_ALL);
			roles = new ArrayList<>();
			while (rs.next()) {
				roles.add(buildEntity(rs));
			}
		} catch (SQLException e) {
			logger.error("SQLException in readAll method of RoleDaoImpl class", e);
		} finally {
			connectionPool.putConnection(con);
			close(rs);
		}
		return roles;
	}

	@Override
	public void update(Role entity) {
		Connection con = connectionPool.getConnection();
		try (PreparedStatement ps = con.prepareStatement(SQL_QUERY_ROLE_UPDATE)) {
			ps.setString(1, entity.getRoleName());
			ps.setInt(2, entity.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("SQLException in update method of RoleDaoImpl class", e);
		} finally {
			connectionPool.putConnection(con);
		}
	}

	@Override
	public void delete(int entityId) {
		Connection con = connectionPool.getConnection();
		try (PreparedStatement ps = con.prepareStatement(SQL_QUERY_ROLE_DELETE)) {
			ps.setInt(1, entityId);
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("SQLException in delete method of RoleDaoImpl class", e);
		} finally {
			connectionPool.putConnection(con);
		}
	}

	@Override
	public Role buildEntity(ResultSet rs) throws SQLException {
		return Role.builder().id(rs.getInt("id")).roleName(rs.getString("roleName")).build();
	}
}
