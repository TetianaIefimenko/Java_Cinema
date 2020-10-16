package by.htp.epam.cinema.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Film extends BaseEntity {

	private static final long serialVersionUID = -2248408866984238822L;

	private String filmName;
	private String description;
	private String posterUrl;
	private String youTubeVideoId;

}
