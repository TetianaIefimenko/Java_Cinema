package by.htp.epam.cinema.domain.CompositeEntities;

import java.io.Serializable;

import by.htp.epam.cinema.domain.Film;
import by.htp.epam.cinema.domain.FilmSession;
import by.htp.epam.cinema.domain.Seat;
import by.htp.epam.cinema.domain.TicketsOrder;
import by.htp.epam.cinema.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompositeTicket implements Serializable {

	private static final long serialVersionUID = 8309250469553709557L;

	private int id;
	private FilmSession filmSession;
	private Film film;
	private Seat seat;
	private TicketsOrder ticketsOrder;
	private User user;

}
