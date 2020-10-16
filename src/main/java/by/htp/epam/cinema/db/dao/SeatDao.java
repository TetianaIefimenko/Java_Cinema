package by.htp.epam.cinema.db.dao;

import by.htp.epam.cinema.domain.Seat;

public interface SeatDao extends BaseDao<Seat> {

	Seat read(int row, int number);

    int getAllSeatsCount();
}
