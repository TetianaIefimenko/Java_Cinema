package by.htp.epam.cinema.web.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CharacterEncodingFilter implements Filter {

	private String encoding;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		encoding = filterConfig.getInitParameter("encoding");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String requestEncoding = request.getCharacterEncoding();
		if (encoding != null && !encoding.equalsIgnoreCase(requestEncoding)) {
			request.setCharacterEncoding(encoding);
		}
		String responseEncoding = response.getCharacterEncoding();
		if (encoding != null && !encoding.equalsIgnoreCase(responseEncoding)) {
			response.setCharacterEncoding(encoding);
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		encoding = null;
	}
}
