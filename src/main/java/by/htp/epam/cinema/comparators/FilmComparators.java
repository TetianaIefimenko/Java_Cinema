package by.htp.epam.cinema.comparators;

import by.htp.epam.cinema.domain.Film;
import by.htp.epam.cinema.domain.FilmSession;
import by.htp.epam.cinema.domain.Ticket;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import static by.htp.epam.cinema.db.dao.DaoFactory.*;

public class FilmComparators {
    public static FilmComparatorName NAME = new FilmComparatorName();
    public static FilmSessionComparatorData DATE = new FilmSessionComparatorData();
    //public static FilmSessionComparatorTickets TICKETS = new FilmSessionComparatorTickets();

    public static class FilmSessionComparatorData implements Comparator<FilmSession> {
        @Override
        public int compare(FilmSession o1, FilmSession o2) {
            Date date1 = null;
            Date date2 = null;
            try {
                date1 = new SimpleDateFormat("yyyy-MM-dd").parse(o1.getDate());
                date2 = new SimpleDateFormat("yyyy-MM-dd").parse(o2.getDate());
            }catch (ParseException ex){

            }
            return date1.compareTo(date2);
        }

    }

    public static class FilmComparatorName implements Comparator<Film> {

        @Override
        public int compare(Film o1, Film o2) {
            return (o1.getFilmName().charAt(0) > o2.getFilmName().charAt(0)) ? 1
                    : (o1.getFilmName().charAt(0) == o2.getFilmName().charAt(0)) ? 0 : -1;
        }

    }

    /*public static class FilmSessionComparatorTickets implements Comparator<Ticket> {

        @Override
        public int compare(Ticket o1, Ticket o2) {
            int c1 = getTicketDao(CUSTOM_CONNECTION_POOL).readCountOfSoldTickets();
            return (o1.getFilmId() > o2.getFilmId()) ? 1
                    : (o1.getFilmId() == o2.getFilmId()) ? 0 : -1;
        }

    }*/
}
