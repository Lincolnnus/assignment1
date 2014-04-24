package li.shaohuan;
 
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
/**
 * Servlet implementation class LogoutController
 */
public class LogoutController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void init() throws ServletException {
        //we can create DB connection resource here and set it to Servlet context
        if(getServletContext().getInitParameter("dbURL").equals("jdbc:mysql://localhost/shaohuan") &&
                getServletContext().getInitParameter("dbUser").equals("root") &&
                getServletContext().getInitParameter("dbUserPwd").equals("giveMePass"))
        getServletContext().setAttribute("DB_Success", "True");
        else throw new ServletException("DB Connection error");
    }
 
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession(true);
        Integer param = (Integer) session.getAttribute("mysession");
        if (param != null) {
            session.setAttribute("mysession", null);
        } 
        response.sendRedirect("/");   
    }
}