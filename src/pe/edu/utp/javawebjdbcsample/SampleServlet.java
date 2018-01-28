package pe.edu.utp.javawebjdbcsample;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "SampleServlet",urlPatterns = "/sample")
public class SampleServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        try {
            Connection con = ((DataSource) InitialContext.doLookup( "jdbc/MySQLDataSource")).getConnection();
            ResultSet rs= con.createStatement()
                    .executeQuery("SELECT COUNT(*) AS countries_count FROM countries");
            int countriesCount= rs.next()?rs.getInt("countries_count"):0;
            request.getSession().setAttribute("countriesCount",countriesCount);
            request
                    .getRequestDispatcher("showCountriesCount.jsp")
                    .forward(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }
}
