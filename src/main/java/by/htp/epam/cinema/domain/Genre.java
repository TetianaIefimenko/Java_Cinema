package by.htp.epam.cinema.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Genre extends BaseEntity {

	private static final long serialVersionUID = -1045134943121533438L;

	private String genreName;

}
