package by.htp.epam.cinema.db.dao.impl;

import java.math.BigDecimal;
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
import by.htp.epam.cinema.db.dao.TicketDao;
import by.htp.epam.cinema.domain.Ticket;

public class TicketDaoImpl extends AbstractDao implements TicketDao {
	private static Logger logger = LoggerFactory.getLogger(TicketDaoImpl.class);

	private static final String SQL_QUERY_TICKET_CREATE = "INSERT INTO `cinema_v2.0`.`tickets` (`session_id`, `seat_id`, `order_id`) VALUES (?,?,?);";
	private static final String SQL_QUERY_TICKET_READ = "SELECT `id`, `session_id`, `seat_id`, `order_id` FROM `cinema_v2.0`.`tickets` WHERE  `id`=?;";
	private static final String SQL_QUERY_TICKET_READ_ALL = "SELECT `id`, `session_id`, `seat_id`, `order_id` FROM `cinema_v2.0`.`tickets`;";
	private static final String SQL_QUERY_TICKET_READ_ALL_BY_ORDER_ID = "SELECT `id`, `session_id`, `seat_id`, `order_id` FROM `cinema_v2.0`.`tickets` WHERE `order_id`=?;";
	private static final String SQL_QUERY_TICKET_READ_ALL_BY_FILMSESSION_ID = "SELECT `id`, `session_id`, `seat_id`, `order_id` FROM `cinema_v2.0`.`tickets` WHERE `session_id`=?;";
	private static final String SQL_QUERY_TICKET_READ_ALL_BY_FILMSESSION_ID_WHERE_ORDER_PAID = "SELECT t.`id`, t.`session_id`, t.`seat_id`, t.`order_id` FROM `cinema_v2.0`.`tickets` t INNER JOIN `cinema_v2.0`.`orders` o ON t.`order_id`=o.`id` WHERE t.`session_id`=? AND o.`isPaid`=1 LIMIT ?, ?;";
	private static final String SQL_QUERY_TICKET_UPDATE = "UPDATE `cinema_v2.0`.`tickets` SET `session_id`=?, `seat_id`=?, `order_id`=? WHERE  `id`=?;";
	private static final String SQL_QUERY_TICKET_DELETE = "DELETE FROM `cinema_v2.0`.`tickets` WHERE  `id`=?;";
	private static final String SQL_QUERY_TICKET_COUNT_READ_BY_FILMSESSION_ID = "SELECT COUNT(t.`id`) FROM `cinema_v2.0`.`tickets` t INNER JOIN `cinema_v2.0`.`orders` o ON t.`order_id`=o.`id` WHERE t.`session_id`=? AND o.`isPaid`=1;";
	private static final String SQL_QUERY_TICKET_SUM_READ_BY_FILMSESSION_ID = "SELECT SUM(s.`ticketPrice`) FROM ((`cinema_v2.0`.`tickets` t INNER JOIN `cinema_v2.0`.`sessions` s ON t.`session_id`=s.`id`) INNER JOIN `cinema_v2.0`.`orders` o ON t.`order_id`=o.`id`) WHERE t.`session_id`=? AND o.`isPaid`=1;";

	@Override
	public void create(Ticket entity) {
		Connection con = connectionPool.getConnection();
		try (PreparedStatement ps = con.prepareStatement(SQL_QUERY_TICKET_CREATE)) {
			ps.setInt(1, entity.getFilmSessionId());
			ps.setInt(2, entity.getSeatId());
			ps.setInt(3, entity.getTicketsOrderId());
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("SQLException in create method of TicketDaoImpl class", e);
		} finally {
			connectionPool.putConnection(con);
		}
	}

	@Override
	public Ticket read(int id) {
		ResultSet rs = null;
		Connection con = connectionPool.getConnection();
		try (PreparedStatement ps = con.prepareStatement(SQL_QUERY_TICKET_READ)) {
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next())
				return buildEntity(rs);
		} catch (SQLException e) {
			logger.error("SQLException in read method of TicketDaoImpl class", e);
		} finally {
			connectionPool.putConnection(con);
			close(rs);
		}
		return null;
	}

	@Override
	public List<Ticket> readAll() {
		List<Ticket> tickets = null;
		ResultSet rs = null;
		Connection con = connectionPool.getConnection();
		try (Statement ps = con.createStatement()) {
			rs = ps.executeQuery(SQL_QUERY_TICKET_READ_ALL);
			tickets = new ArrayList<>();
			while (rs.next()) {
				tickets.add(buildEntity(rs));
			}
		} catch (SQLException e) {
			logger.error("SQLException in readAll method of TicketDaoImpl class", e);
		} finally {
			connectionPool.putConnection(con);
			close(rs);
		}
		return tickets;
	}

	@Override
	public List<Ticket> readAllWhereOrderIdPresent(int orderId) {
		List<Ticket> tickets = null;
		ResultSet rs = null;
		Connection con = connectionPool.getConnection();
		try (PreparedStatement ps = con.prepareStatement(SQL_QUERY_TICKET_READ_ALL_BY_ORDER_ID)) {
			ps.setInt(1, orderId);
			rs = ps.executeQuery();
			tickets = new ArrayList<>();
			while (rs.next()) {
				tickets.add(buildEntity(rs));
			}
		} catch (SQLException e) {
			logger.error("SQLException in readAll method of TicketDaoImpl class", e);
		} finally {
			connectionPool.putConnection(con);
			close(rs);
		}
		return tickets;
	}

	@Override
	public List<Ticket> readAllWhereFilmSessionIdPresent(int filmSessionId) {
		List<Ticket> tickets = null;
		ResultSet rs = null;
		Connection con = connectionPool.getConnection();
		try (PreparedStatement ps = con.prepareStatement(SQL_QUERY_TICKET_READ_ALL_BY_FILMSESSION_ID)) {
			ps.setInt(1, filmSessionId);
			rs = ps.executeQuery();
			tickets = new ArrayList<>();
			while (rs.next()) {
				tickets.add(buildEntity(rs));
			}
		} catch (SQLException e) {
			logger.error("SQLException in readAllWhereFilmSessionIdPresent method of TicketDaoImpl class", e);
		} finally {
			connectionPool.putConnection(con);
			close(rs);
		}
		return tickets;
	}

	@Override
	public List<Ticket> readAllSoldTicketsByFilmSessionId(int filmSessionId, int start, int step) {
		List<Ticket> tickets = null;
		ResultSet rs = null;
		Connection con = connectionPool.getConnection();
		try (PreparedStatement ps = con
				.prepareStatement(SQL_QUERY_TICKET_READ_ALL_BY_FILMSESSION_ID_WHERE_ORDER_PAID)) {
			ps.setInt(1, filmSessionId);
			ps.setInt(2, start);
			ps.setInt(3, step);
			rs = ps.executeQuery();
			tickets = new ArrayList<>();
			while (rs.next()) {
				tickets.add(buildEntity(rs));
			}
		} catch (SQLException e) {
			logger.error("SQLException in readAllSoldTicketsByFilmSessionId method of TicketDaoImpl class", e);
		} finally {
			connectionPool.putConnection(con);
			close(rs);
		}
		return tickets;
	}

	@Override
	public void update(Ticket entity) {
		Connection con = connectionPool.getConnection();
		try (PreparedStatement ps = con.prepareStatement(SQL_QUERY_TICKET_UPDATE)) {
			ps.setInt(1, entity.getFilmSessionId());
			ps.setInt(2, entity.getSeatId());
			ps.setInt(3, entity.getTicketsOrderId());
			ps.setInt(4, entity.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("SQLException in update method of TicketDaoImpl class", e);
		} finally {
			connectionPool.putConnection(con);
		}
	}

	@Override
	public void delete(int entityId) {
		Connection con = connectionPool.getConnection();
		try (PreparedStatement ps = con.prepareStatement(SQL_QUERY_TICKET_DELETE)) {
			ps.setInt(1, entityId);
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("SQLException in delete method of TicketDaoImpl class", e);
		} finally {
			connectionPool.putConnection(con);
		}
	}

	@Override
	public int readCountOfSoldTickets(int filmSessionId) {
		ResultSet rs = null;
		Connection con = connectionPool.getConnection();
		try (PreparedStatement ps = con.prepareStatement(SQL_QUERY_TICKET_COUNT_READ_BY_FILMSESSION_ID)) {
			ps.setInt(1, filmSessionId);
			rs = ps.executeQuery();
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
	public BigDecimal readSumOfSoldTickets(int filmSessionId) {
		ResultSet rs = null;
		Connection con = connectionPool.getConnection();
		try (PreparedStatement ps = con.prepareStatement(SQL_QUERY_TICKET_SUM_READ_BY_FILMSESSION_ID)) {
			ps.setInt(1, filmSessionId);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getBigDecimal(1);
			}
		} catch (SQLException e) {
			logger.error("SQLException in readAll method of FilmDaoImpl class", e);
		} finally {
			connectionPool.putConnection(con);
			close(rs);
		}
		return null;
	}

	@Override
	public Ticket buildEntity(ResultSet rs) throws SQLException {
		return Ticket.builder().id(rs.getInt("id")).filmSessionId(rs.getInt("session_id"))
				.seatId(rs.getInt("seat_id")).ticketsOrderId(rs.getInt("order_id")).build();
	}
}
