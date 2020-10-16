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
import by.htp.epam.cinema.db.dao.TicketsOrderDao;
import by.htp.epam.cinema.domain.TicketsOrder;


public class TicketsOrderDaoImpl extends AbstractDao implements TicketsOrderDao {
	private static Logger logger = LoggerFactory.getLogger(TicketsOrderDaoImpl.class);

	private static final String SQL_QUERY_TICKETS_ORDER_CREATE = "INSERT INTO `cinema_v2.0`.`orders` (`user_id`, `isPaid`) VALUES (?,?);";
	private static final String SQL_QUERY_TICKETS_ORDER_READ = "SELECT `id`, `orderNumber`, `user_id`, `isPaid` FROM `cinema_v2.0`.`orders` WHERE  `id`=?;";
	private static final String SQL_QUERY_TICKETS_ORDER_READ_NON_PAID_BY_USER = "SELECT `id`, `orderNumber`, `user_id`, `isPaid` FROM `cinema_v2.0`.`orders` WHERE  `user_id`=? AND isPaid=0;";
	private static final String SQL_QUERY_TICKETS_ORDER_READ_BY_SEAT_AND_FILMSESSION = "SELECT o.`id`, o.`orderNumber`, o.`user_id`, o.`isPaid` FROM `cinema_v2.0`.`orders` o "
			+ "INNER JOIN `cinema_v2.0`.`tickets` t ON o.`id`=t.`order_id` WHERE t.`seat_id`=? AND t.`session_id`=?";
	private static final String SQL_QUERY_TICKETS_ORDER_READ_ALL = "SELECT `id`, `orderNumber`, `user_id`, `isPaid` FROM `cinema_v2.0`.`orders`;";
	private static final String SQL_QUERY_TICKETS_ORDER_UPDATE = "UPDATE `cinema_v2.0`.`orders` SET `user_id`=?, `isPaid`=? WHERE  `id`=?;";
	private static final String SQL_QUERY_TICKETS_ORDER_DELETE = "DELETE FROM `cinema_v2.0`.`orders` WHERE  `id`=?;";

	@Override
	public void create(TicketsOrder entity) {
		Connection con = connectionPool.getConnection();
		try (PreparedStatement ps = con.prepareStatement(SQL_QUERY_TICKETS_ORDER_CREATE)) {
			ps.setInt(1, entity.getUserId());
			ps.setBoolean(2, entity.isPaid());
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("SQLException in create method of TicketsOrderDaoImpl class", e);
		} finally {
			connectionPool.putConnection(con);
		}
	}

	@Override
	public TicketsOrder read(int id) {
		ResultSet rs = null;
		Connection con = connectionPool.getConnection();
		try (PreparedStatement ps = con.prepareStatement(SQL_QUERY_TICKETS_ORDER_READ)) {
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next())
				return buildEntity(rs);
		} catch (SQLException e) {
			logger.error("SQLException in read method of TicketsOrderDaoImpl class", e);
		} finally {
			connectionPool.putConnection(con);
			close(rs);
		}
		return null;
	}

	@Override
	public TicketsOrder readByUserId(int userId) {
		ResultSet rs = null;
		Connection con = connectionPool.getConnection();
		try (PreparedStatement ps = con.prepareStatement(SQL_QUERY_TICKETS_ORDER_READ_NON_PAID_BY_USER)) {
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			if (rs.next())
				return buildEntity(rs);
		} catch (SQLException e) {
			logger.error("SQLException in read method of TicketsOrderDaoImpl class", e);
		} finally {
			connectionPool.putConnection(con);
			close(rs);
		}
		return null;
	}

	@Override
	public TicketsOrder read(int seatId, int filmSessionId) {
		ResultSet rs = null;
		Connection con = connectionPool.getConnection();
		try (PreparedStatement ps = con.prepareStatement(SQL_QUERY_TICKETS_ORDER_READ_BY_SEAT_AND_FILMSESSION)) {
			ps.setInt(1, seatId);
			ps.setInt(2, filmSessionId);
			rs = ps.executeQuery();
			if (rs.next())
				return buildEntity(rs);
		} catch (SQLException e) {
			logger.error("SQLException in read method of TicketsOrderDaoImpl class", e);
		} finally {
			connectionPool.putConnection(con);
			close(rs);
		}
		return null;
	}

	@Override
	public List<TicketsOrder> readAll() {
		List<TicketsOrder> orders = null;
		ResultSet rs = null;
		Connection con = connectionPool.getConnection();
		try (Statement ps = con.createStatement()) {
			rs = ps.executeQuery(SQL_QUERY_TICKETS_ORDER_READ_ALL);
			orders = new ArrayList<>();
			while (rs.next()) {
				orders.add(buildEntity(rs));
			}
		} catch (SQLException e) {
			logger.error("SQLException in readAll method of TicketsOrderDaoImpl class", e);
		} finally {
			connectionPool.putConnection(con);
			close(rs);
		}
		return orders;
	}

	@Override
	public void update(TicketsOrder entity) {
		Connection con = connectionPool.getConnection();
		try (PreparedStatement ps = con.prepareStatement(SQL_QUERY_TICKETS_ORDER_UPDATE)) {
			ps.setInt(1, entity.getUserId());
			ps.setBoolean(2, entity.isPaid());
			ps.setInt(3, entity.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("SQLException in update method of TicketsOrderDaoImpl class", e);
		} finally {
			connectionPool.putConnection(con);
		}
	}

	@Override
	public void delete(int entityId) {
		Connection con = connectionPool.getConnection();
		try (PreparedStatement ps = con.prepareStatement(SQL_QUERY_TICKETS_ORDER_DELETE)) {
			ps.setInt(1, entityId);
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("SQLException in delete method of TicketsOrderDaoImpl class", e);
		} finally {
			connectionPool.putConnection(con);
		}
	}

	@Override
	public TicketsOrder buildEntity(ResultSet rs) throws SQLException {
		return TicketsOrder.builder().id(rs.getInt("id")).orderNumber(rs.getInt("orderNumber"))
				.userId(rs.getInt("user_id")).isPaid(rs.getBoolean("isPaid")).build();
	}

}
