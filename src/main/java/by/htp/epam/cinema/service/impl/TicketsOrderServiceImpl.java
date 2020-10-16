package by.htp.epam.cinema.service.impl;

import by.htp.epam.cinema.db.dao.TicketsOrderDao;
import by.htp.epam.cinema.domain.TicketsOrder;
import by.htp.epam.cinema.domain.User;
import by.htp.epam.cinema.service.TicketsOrderService;

public class TicketsOrderServiceImpl implements TicketsOrderService {

	private TicketsOrderDao ticketsOrderDao;

	public TicketsOrderServiceImpl(TicketsOrderDao ticketsOrderDao) {
		this.ticketsOrderDao = ticketsOrderDao;
	}

	@Override
	public TicketsOrder readUserNonPaidOrder(User user) {
		return ticketsOrderDao.readByUserId(user.getId());
	}

	@Override
	public TicketsOrder createTicketsOrder(User user) {
		TicketsOrder ticketsOrder = TicketsOrder.builder().userId(user.getId()).build();
		ticketsOrderDao.create(ticketsOrder);
		return readUserNonPaidOrder(user);
	}

	@Override
	public void deleteNonPaidOrder(User user) {
		TicketsOrder order = readUserNonPaidOrder(user);
		if (order != null) {
			ticketsOrderDao.delete(order.getId());
		}
	}

	@Override
	public void payOrder(int ticketOrderId) {
		TicketsOrder ticketsOrder = ticketsOrderDao.read(ticketOrderId);
		ticketsOrder.setPaid(true);
		ticketsOrderDao.update(ticketsOrder);
	}
}
