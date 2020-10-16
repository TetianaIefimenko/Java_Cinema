package by.htp.epam.cinema.service;

import by.htp.epam.cinema.domain.TicketsOrder;
import by.htp.epam.cinema.domain.User;

public interface TicketsOrderService extends Service {

	TicketsOrder readUserNonPaidOrder(User user);

	TicketsOrder createTicketsOrder(User user);

	void deleteNonPaidOrder(User user);

	void payOrder(int ticketOrderId);

}
