package redisShortener;

import java.io.IOException;
 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
public class Insert extends HttpServlet {
 
    /**
     * Both POST and GET method are allowed to insert new record
     *
     */
 
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
 
    doGet(request, response);
    }
 
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
 
    String longUrl = request.getParameter("longUrl");
    request.getSession().setAttribute("a", "a");
    String a = response.encodeURL(request.getRequestURI());
    request.getSession().setAttribute("a", "a");
    System.out.println("->"+request.getRequestURI());
    System.out.println(a);
    System.out.println("shortening " + longUrl);
    String serverName = request.getServerName();
    int port = request.getServerPort();
    String contextPath = request.getContextPath();
    String shortUrl = null;
    try {
        shortUrl = new Logic().getShort(serverName, port, contextPath,
            longUrl);
    } catch (Exception e) {
 
        e.printStackTrace();
    }
    System.out.println("short url: " + shortUrl);
    request.getSession().setAttribute("shortUrl", shortUrl);
    response.sendRedirect("index.jsp");
    }
}