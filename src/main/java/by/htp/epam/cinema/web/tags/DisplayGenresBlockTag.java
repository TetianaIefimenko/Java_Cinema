package by.htp.epam.cinema.web.tags;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.htp.epam.cinema.domain.Genre;
import by.htp.epam.cinema.service.GenreService;
import by.htp.epam.cinema.service.ServiceFactory;
import static by.htp.epam.cinema.web.util.constant.ContextParamNameConstantDeclaration.SESSION_PARAM_GENRELIST;

public class DisplayGenresBlockTag extends TagSupport {

	private static final long serialVersionUID = 6183940880542261086L;

	private static Logger logger = LoggerFactory.getLogger(DisplayGenresBlockTag.class);

	@SuppressWarnings("unchecked")
	private List<Genre> getGenres() {
		HttpSession session = pageContext.getSession();
		List<Genre> genres = (List<Genre>) session.getAttribute(SESSION_PARAM_GENRELIST);
		if (genres == null) {
			GenreService genreService = ServiceFactory.getGenreService();
			genres = genreService.getAllGenres();
			session.setAttribute(SESSION_PARAM_GENRELIST, genres);
		}
		return genres;
	}

	@Override
	public int doStartTag() {
		List<Genre> genres = getGenres();
		StringBuilder genresBlock = new StringBuilder();
		for (Genre g : genres) {
			genresBlock.append("<hr>").append("<a href=\"cinema?action=view_genre_films&chosenGenreId=")
					.append(g.getId()).append("\">").append(g.getGenreName()).append("</a>");
		}
		JspWriter out = pageContext.getOut();
		try {
			out.write(genresBlock.toString());
		} catch (IOException e) {
			logger.error("IOException in DisplayGenresBlockTag class", e);
		}
		return SKIP_BODY;
	}
}