package by.htp.epam.cinema.db.dao;

import by.htp.epam.cinema.domain.TicketsOrder;


public interface TicketsOrderDao extends BaseDao<TicketsOrder> {

	TicketsOrder read(int seatId, int filmSessionId);

	TicketsOrder readByUserId(int userId);

}
