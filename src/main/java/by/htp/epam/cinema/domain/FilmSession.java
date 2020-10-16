package by.htp.epam.cinema.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmSession extends BaseEntity {

	private static final long serialVersionUID = -7160885760512229728L;

	private String date;
	private String time;
	private BigDecimal ticketPrice;
	private int filmId;

}
