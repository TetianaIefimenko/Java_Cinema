package by.htp.epam.cinema.service;

import static by.htp.epam.cinema.db.dao.DaoFactory.CUSTOM_CONNECTION_POOL;
import static by.htp.epam.cinema.db.dao.DaoFactory.getFilmDao;
import static by.htp.epam.cinema.db.dao.DaoFactory.getFilmSessionDao;
import static by.htp.epam.cinema.db.dao.DaoFactory.getGenreDao;
import static by.htp.epam.cinema.db.dao.DaoFactory.getRoleDao;
import static by.htp.epam.cinema.db.dao.DaoFactory.getSeatDao;
import static by.htp.epam.cinema.db.dao.DaoFactory.getTicketDao;
import static by.htp.epam.cinema.db.dao.DaoFactory.getTicketsOrderDao;
import static by.htp.epam.cinema.db.dao.DaoFactory.getUserDao;

import by.htp.epam.cinema.service.impl.FilmServiceImpl;
import by.htp.epam.cinema.service.impl.FilmSessionServiceImpl;
import by.htp.epam.cinema.service.impl.GenreServiceImpl;
import by.htp.epam.cinema.service.impl.RoleServiceImpl;
import by.htp.epam.cinema.service.impl.SeatServiceImpl;
import by.htp.epam.cinema.service.impl.TicketServiceImpl;
import by.htp.epam.cinema.service.impl.TicketsOrderServiceImpl;
import by.htp.epam.cinema.service.impl.UserServiceImpl;

public class ServiceFactory {

	private ServiceFactory() {
	}

	private static final FilmService filmService = new FilmServiceImpl(getFilmDao(CUSTOM_CONNECTION_POOL),
			getGenreDao(CUSTOM_CONNECTION_POOL), getTicketDao(CUSTOM_CONNECTION_POOL),
			getFilmSessionDao(CUSTOM_CONNECTION_POOL), getSeatDao(CUSTOM_CONNECTION_POOL));

	private static final FilmSessionService filmSessionService = new FilmSessionServiceImpl(
			getFilmSessionDao(CUSTOM_CONNECTION_POOL), getTicketDao(CUSTOM_CONNECTION_POOL));

	private static final GenreService genreService = new GenreServiceImpl(getGenreDao(CUSTOM_CONNECTION_POOL));

	private static final RoleService roleService = new RoleServiceImpl(getRoleDao(CUSTOM_CONNECTION_POOL));

	private static final SeatService seatService = new SeatServiceImpl(getSeatDao(CUSTOM_CONNECTION_POOL),
			getTicketsOrderDao(CUSTOM_CONNECTION_POOL));

	private static final TicketService ticketService = new TicketServiceImpl(getTicketDao(CUSTOM_CONNECTION_POOL),
			getFilmSessionDao(CUSTOM_CONNECTION_POOL), getFilmDao(CUSTOM_CONNECTION_POOL),
			getSeatDao(CUSTOM_CONNECTION_POOL), getTicketsOrderDao(CUSTOM_CONNECTION_POOL),
			getUserDao(CUSTOM_CONNECTION_POOL));

	private static final TicketsOrderService ticketsOrderService = new TicketsOrderServiceImpl(
			getTicketsOrderDao(CUSTOM_CONNECTION_POOL));

	private static final UserService userService = new UserServiceImpl(getUserDao(CUSTOM_CONNECTION_POOL));

	public static FilmService getFilmService() {
		return filmService;
	}

	public static FilmSessionService getFilmSessionService() {
		return filmSessionService;
	}

	public static GenreService getGenreService() {
		return genreService;
	}

	public static RoleService getRoleService() {
		return roleService;
	}

	public static SeatService getSeatService() {
		return seatService;
	}

	public static TicketService getTicketService() {
		return ticketService;
	}

	public static TicketsOrderService getTicketsOrderService() {
		return ticketsOrderService;
	}

	public static UserService getUserService() {
		return userService;
	}
}
