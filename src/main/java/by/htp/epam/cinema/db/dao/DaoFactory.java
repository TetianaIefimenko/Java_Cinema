package by.htp.epam.cinema.db.dao;

import java.util.HashMap;
import java.util.Map;

import by.htp.epam.cinema.db.dao.impl.*;
import by.htp.epam.cinema.db.dao.impl.DefaultFilmSessionDao;
import by.htp.epam.cinema.db.pool.BaseConnectionPool;
import by.htp.epam.cinema.db.pool.impl.CustomConnectionPool;

public class DaoFactory {

	private DaoFactory() {
	}

	private static Map<Integer, BaseConnectionPool> connectionPools;

	private static final DefaultFilmDao DEFAULT_FILM_DAO = new DefaultFilmDao();

	private static final DefaultFilmSessionDao DEFAULT_FILM_SESSION_DAO = new DefaultFilmSessionDao();

	private static final DefaultGenreDao DEFAULT_GENRE_DAO = new DefaultGenreDao();

	private static final DefaultRoleDao DEFAULT_ROLE_DAO = new DefaultRoleDao();

	private static final DefaultSeatDao DEFAULT_SEAT_DAO = new DefaultSeatDao();

	private static final DefaultTicketDao DEFAULT_TICKET_DAO = new DefaultTicketDao();

	private static final DefaultTicketsOrderDao DEFAULT_TICKETS_ORDER_DAO = new DefaultTicketsOrderDao();

	private static final DefaultUserDao DEFAULT_USER_DAO = new DefaultUserDao();

	public static final int CUSTOM_CONNECTION_POOL = 1;

	static {
		connectionPools = new HashMap<>();
		connectionPools.put(CUSTOM_CONNECTION_POOL, CustomConnectionPool.getInstance());
	}

	public static FilmDao getFilmDao(int connectionPoolNum) {
		DEFAULT_FILM_DAO.setConnectionPool(connectionPools.get(connectionPoolNum));
		return DEFAULT_FILM_DAO;
	}

	public static FilmSessionDao getFilmSessionDao(int connectionPoolNum) {
		DEFAULT_FILM_SESSION_DAO.setConnectionPool(connectionPools.get(connectionPoolNum));
		return DEFAULT_FILM_SESSION_DAO;
	}

	public static GenreDao getGenreDao(int connectionPoolNum) {
		DEFAULT_GENRE_DAO.setConnectionPool(connectionPools.get(connectionPoolNum));
		return DEFAULT_GENRE_DAO;
	}

	public static RoleDao getRoleDao(int connectionPoolNum) {
		DEFAULT_ROLE_DAO.setConnectionPool(connectionPools.get(connectionPoolNum));
		return DEFAULT_ROLE_DAO;
	}

	public static SeatDao getSeatDao(int connectionPoolNum) {
		DEFAULT_SEAT_DAO.setConnectionPool(connectionPools.get(connectionPoolNum));
		return DEFAULT_SEAT_DAO;
	}

	public static TicketDao getTicketDao(int connectionPoolNum) {
		DEFAULT_TICKET_DAO.setConnectionPool(connectionPools.get(connectionPoolNum));
		return DEFAULT_TICKET_DAO;
	}

	public static TicketsOrderDao getTicketsOrderDao(int connectionPoolNum) {
		DEFAULT_TICKETS_ORDER_DAO.setConnectionPool(connectionPools.get(connectionPoolNum));
		return DEFAULT_TICKETS_ORDER_DAO;
	}

	public static UserDao getUserDao(int connectionPoolNum) {
		DEFAULT_USER_DAO.setConnectionPool(connectionPools.get(connectionPoolNum));
		return DEFAULT_USER_DAO;
	}
}
