package redisShortener;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Retrieve extends HttpServlet {

	private static final long serialVersionUID = 1293961717469276130L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String urlShort = request.getServletPath();

		String longUrl = null;
		if (urlShort != null && !"".equals(urlShort)) {
			try {
				// longUrl = new Logic().getLongUrl(urlShort);
				longUrl = new RedisLogic().getLongUrl(urlShort);
			} catch (Exception e) {
				// handling exception here
				e.printStackTrace();
			}
		}

		if (longUrl == null) {
			// if long url not found, send to index.jsp
			System.out.println("long url not found, back to index.jsp");
			response.sendRedirect("index.jsp");
		} else {
			// if long url found, so redirect the browser
			System.out.println("redirecting to " + longUrl);
			response.sendRedirect(longUrl);
		}
	}

}