package by.htp.epam.cinema.db.dao;

import java.math.BigDecimal;
import java.util.List;

import by.htp.epam.cinema.domain.Ticket;

public interface TicketDao extends BaseDao<Ticket> {

	List<Ticket> readAllWhereOrderIdPresent(int orderId);

	List<Ticket> readAllWhereFilmSessionIdPresent(int filmSessionId);

	List<Ticket> readAllSoldTicketsByFilmSessionId(int filmSessionId, int start, int step);

	int readCountOfSoldTickets(int filmSessionId);

	BigDecimal readSumOfSoldTickets(int filmSessionId);

}
