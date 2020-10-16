package by.htp.epam.cinema.domain.CompositeEntities;

import java.io.Serializable;
import java.util.List;

import by.htp.epam.cinema.domain.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompositeFilm implements Serializable {

	private static final long serialVersionUID = 8389696005488781866L;

	private int id;
	private String filmName;
	private String description;
	private String posterUrl;
	private String youTubeVideoId;

}
