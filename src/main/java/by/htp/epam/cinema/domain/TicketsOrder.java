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
public class TicketsOrder extends BaseEntity {

    private static final long serialVersionUID = -5147482581752279068L;

    private int orderNumber;
    private boolean isPaid;
    private int userId;


}
