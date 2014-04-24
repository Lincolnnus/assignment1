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
 * Servlet implementation class LoginController
 */
public class LoginController extends HttpServlet {
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
            System.out.println("You have already logged in");
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/error.html");
            PrintWriter out= response.getWriter();
            out.println("<font color=red>You have already logged in</font>");
            rd.include(request, response);
        } else {
            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_OK);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
            rd.include(request, response); 
        }     
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 
        //get request parameters for userID and password
        String name = request.getParameter("user");
        String pwd = request.getParameter("pwd");

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection(getServletContext().getInitParameter("dbURL"),getServletContext().getInitParameter("dbUser"),getServletContext().getInitParameter("dbUserPwd"));
            String query = "SELECT * from User WHERE name = '" + name + "' AND password = '" + pwd +"'";
            PreparedStatement statement = con.prepareStatement(query);    
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                HttpSession session = request.getSession(true);
                Integer param = (Integer) session.getAttribute("mysession");
                if (param != null) {
                    session.setAttribute("mysession", new Integer(param.intValue() + 1));
                    param = (Integer) session.getAttribute("mysession");
                } else {
                    param = new Integer(1);
                    session.setAttribute("mysession", param);
                    session.setAttribute("username", name);
                }
                System.out.println("You have successfully logged in");
                response.sendRedirect("/");
            }else{
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
                PrintWriter out= response.getWriter();
                out.println("<font color=red>Either user name or password is wrong.</font>");
                rd.include(request, response);
            }
            con.close();
        }
        catch(Exception exce){
            exce.printStackTrace();
        }

       /* if(userID.equals(user) && password.equals(pwd)){
            response.sendRedirect("LoginSuccess.jsp");
        }else{
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
            PrintWriter out= response.getWriter();
            out.println("<font color=red>Either user name or password is wrong.</font>");
            rd.include(request, response);
             
        }*/
         
    }
 
}