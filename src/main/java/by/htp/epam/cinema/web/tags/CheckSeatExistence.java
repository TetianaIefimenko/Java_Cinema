package by.htp.epam.cinema.web.tags;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import by.htp.epam.cinema.domain.Seat;

public class CheckSeatExistence extends TagSupport {

	private static final long serialVersionUID = 662303206695921144L;

	private List<Seat> seats;

	private int row;

	private int column;

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	Seat getSeat(int r, int n) {
		for (Seat s : seats) {
			if (s.getRow() == r && s.getNumber() == n)
				return s;
		}
		return null;
	}

	@Override
	public int doStartTag() throws JspException {
		Seat seat = getSeat(row, column);
		pageContext.setAttribute("seat", seat);
		return SKIP_BODY;
	}
}