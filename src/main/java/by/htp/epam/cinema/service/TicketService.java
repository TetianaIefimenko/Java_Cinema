package by.htp.epam.cinema.service;

import java.math.BigDecimal;
import java.util.List;

import by.htp.epam.cinema.domain.FilmSession;
import by.htp.epam.cinema.domain.Seat;
import by.htp.epam.cinema.domain.TicketsOrder;
import by.htp.epam.cinema.domain.CompositeEntities.CompositeTicket;

public interface TicketService extends Service {

	void createTicket(FilmSession filmSession, Seat seat, TicketsOrder ticketsOrder);

	List<CompositeTicket> getOrderTickets(TicketsOrder order);

	List<CompositeTicket> getAllFilmSessionSoldTickets(int filmSessionId, int start, int step);

	int getSoldTicketCount(int filmSessionId);

	BigDecimal getSoldTicketSum(int filmSessionId);

}
