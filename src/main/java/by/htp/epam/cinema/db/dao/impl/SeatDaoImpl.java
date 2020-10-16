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
import by.htp.epam.cinema.db.dao.SeatDao;
import by.htp.epam.cinema.domain.Seat;

public class SeatDaoImpl extends AbstractDao implements SeatDao {
	private static Logger logger = LoggerFactory.getLogger(SeatDaoImpl.class);

	private static final String SQL_QUERY_SEAT_CREATE = "INSERT INTO `cinema_v2.0`.`seats` (`row`, `number`) VALUES (?,?);";
	private static final String SQL_QUERY_SEAT_READ = "SELECT `id`, `row`, `number` FROM `cinema_v2.0`.`seats` WHERE  `id`=?;";
	private static final String SQL_QUERY_SEAT_READ_BY_ROW_AND_NUMBER = "SELECT `id`, `row`, `number` FROM `cinema_v2.0`.`seats` WHERE `row`=? AND `number`=?;";
	private static final String SQL_QUERY_SEAT_READ_ALL = "SELECT `id`, `row`, `number` FROM `cinema_v2.0`.`seats`;";
	private static final String SQL_QUERY_SEAT_UPDATE = "UPDATE `cinema_v2.0`.`seats` SET `row`=?, `number`=? WHERE `id`=?;";
	private static final String SQL_QUERY_SEAT_DELETE = "DELETE FROM `cinema_v2.0`.`seats` WHERE  `id`=?;";
	private static final String SQL_QUERY_SEAT_ALL_COUNT = "SELECT COUNT(seats.id) FROM seats";

	@Override
	public void create(Seat entity) {
		Connection con = connectionPool.getConnection();
		try (PreparedStatement ps = con.prepareStatement(SQL_QUERY_SEAT_CREATE)) {
			ps.setInt(1, entity.getRow());
			ps.setInt(2, entity.getNumber());
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("SQLException in create method of SeatDaoImpl class", e);
		} finally {
			connectionPool.putConnection(con);
		}
	}

	@Override
	public Seat read(int id) {
		ResultSet rs = null;
		Connection con = connectionPool.getConnection();
		try (PreparedStatement ps = con.prepareStatement(SQL_QUERY_SEAT_READ)) {
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next())
				return buildEntity(rs);
		} catch (SQLException e) {
			logger.error("SQLException in read method of SeatDaoImpl class", e);
		} finally {
			connectionPool.putConnection(con);
			close(rs);
		}
		return null;
	}

	@Override
	public Seat read(int row, int number) {
		ResultSet rs = null;
		Connection con = connectionPool.getConnection();
		try (PreparedStatement ps = con.prepareStatement(SQL_QUERY_SEAT_READ_BY_ROW_AND_NUMBER)) {
			ps.setInt(1, row);
			ps.setInt(2, number);
			rs = ps.executeQuery();
			if (rs.next())
				return buildEntity(rs);
		} catch (SQLException e) {
			logger.error("SQLException in read method of SeatDaoImpl class", e);
		} finally {
			connectionPool.putConnection(con);
			close(rs);
		}
		return null;
	}

	@Override
	public int getAllSeatsCount() {
		ResultSet rs = null;
		Connection con = connectionPool.getConnection();
		try (PreparedStatement ps = con.prepareStatement(SQL_QUERY_SEAT_ALL_COUNT)) {
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
	public List<Seat> readAll() {
		List<Seat> seats = null;
		ResultSet rs = null;
		Connection con = connectionPool.getConnection();
		try (Statement ps = con.createStatement()) {
			rs = ps.executeQuery(SQL_QUERY_SEAT_READ_ALL);
			seats = new ArrayList<>();
			while (rs.next()) {
				seats.add(buildEntity(rs));
			}
		} catch (SQLException e) {
			logger.error("SQLException in readAll method of SeatDaoImpl class", e);
		} finally {
			connectionPool.putConnection(con);
			close(rs);
		}
		return seats;
	}

	@Override
	public void update(Seat entity) {
		Connection con = connectionPool.getConnection();
		try (PreparedStatement ps = con.prepareStatement(SQL_QUERY_SEAT_UPDATE)) {
			ps.setInt(1, entity.getRow());
			ps.setInt(2, entity.getNumber());
			ps.setInt(3, entity.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("SQLException in update method of SeatDaoImpl class", e);
		} finally {
			connectionPool.putConnection(con);
		}
	}

	@Override
	public void delete(int entityId) {
		Connection con = connectionPool.getConnection();
		try (PreparedStatement ps = con.prepareStatement(SQL_QUERY_SEAT_DELETE)) {
			ps.setInt(1, entityId);
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("SQLException in delete method of SeatDaoImpl class", e);
		} finally {
			connectionPool.putConnection(con);
		}
	}

	@Override
	public Seat buildEntity(ResultSet rs) throws SQLException {
		return Seat.builder().id(rs.getInt("id")).row(rs.getInt("row")).number(rs.getInt("number")).build();
	}
}
