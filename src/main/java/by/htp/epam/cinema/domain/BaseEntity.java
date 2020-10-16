package by.htp.epam.cinema.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = -294442612361371280L;

	private int id;

}
