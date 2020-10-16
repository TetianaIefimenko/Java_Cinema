package by.htp.epam.cinema.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket extends BaseEntity {

	private static final long serialVersionUID = -4113164580477789927L;

	private int filmSessionId;
	private int seatId;
	private int ticketsOrderId;

}
