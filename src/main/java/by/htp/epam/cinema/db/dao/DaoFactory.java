package by.htp.epam.cinema.db.dao;

import java.util.HashMap;
import java.util.Map;

import by.htp.epam.cinema.db.dao.impl.FilmDaoImpl;
import by.htp.epam.cinema.db.dao.impl.FilmSessionDaoImpl;
import by.htp.epam.cinema.db.dao.impl.GenreDaoImpl;
import by.htp.epam.cinema.db.dao.impl.RoleDaoImpl;
import by.htp.epam.cinema.db.dao.impl.SeatDaoImpl;
import by.htp.epam.cinema.db.dao.impl.TicketDaoImpl;
import by.htp.epam.cinema.db.dao.impl.TicketsOrderDaoImpl;
import by.htp.epam.cinema.db.dao.impl.UserDaoImpl;
import by.htp.epam.cinema.db.pool.BaseConnectionPool;
import by.htp.epam.cinema.db.pool.impl.CustomConnectionPool;

public class DaoFactory {

	private DaoFactory() {
	}

	private static Map<Integer, BaseConnectionPool> connectionPools;

	private static final FilmDaoImpl filmDaoImpl = new FilmDaoImpl();

	private static final FilmSessionDaoImpl filmSessionDaoImpl = new FilmSessionDaoImpl();

	private static final GenreDaoImpl genreDaoImpl = new GenreDaoImpl();

	private static final RoleDaoImpl roleDaoImpl = new RoleDaoImpl();

	private static final SeatDaoImpl seatDaoImpl = new SeatDaoImpl();

	private static final TicketDaoImpl ticketDaoImpl = new TicketDaoImpl();

	private static final TicketsOrderDaoImpl ticketsOrderDaoImpl = new TicketsOrderDaoImpl();

	private static final UserDaoImpl userDaoImpl = new UserDaoImpl();

	public static final int CUSTOM_CONNECTION_POOL = 1;

	static {
		connectionPools = new HashMap<>();
		connectionPools.put(CUSTOM_CONNECTION_POOL, CustomConnectionPool.getInstance());
	}

	public static FilmDao getFilmDao(int connectionPoolNum) {
		filmDaoImpl.setConnectionPool(connectionPools.get(connectionPoolNum));
		return filmDaoImpl;
	}

	public static FilmSessionDao getFilmSessionDao(int connectionPoolNum) {
		filmSessionDaoImpl.setConnectionPool(connectionPools.get(connectionPoolNum));
		return filmSessionDaoImpl;
	}

	public static GenreDao getGenreDao(int connectionPoolNum) {
		genreDaoImpl.setConnectionPool(connectionPools.get(connectionPoolNum));
		return genreDaoImpl;
	}

	public static RoleDao getRoleDao(int connectionPoolNum) {
		roleDaoImpl.setConnectionPool(connectionPools.get(connectionPoolNum));
		return roleDaoImpl;
	}

	public static SeatDao getSeatDao(int connectionPoolNum) {
		seatDaoImpl.setConnectionPool(connectionPools.get(connectionPoolNum));
		return seatDaoImpl;
	}

	public static TicketDao getTicketDao(int connectionPoolNum) {
		ticketDaoImpl.setConnectionPool(connectionPools.get(connectionPoolNum));
		return ticketDaoImpl;
	}

	public static TicketsOrderDao getTicketsOrderDao(int connectionPoolNum) {
		ticketsOrderDaoImpl.setConnectionPool(connectionPools.get(connectionPoolNum));
		return ticketsOrderDaoImpl;
	}

	public static UserDao getUserDao(int connectionPoolNum) {
		userDaoImpl.setConnectionPool(connectionPools.get(connectionPoolNum));
		return userDaoImpl;
	}
}
