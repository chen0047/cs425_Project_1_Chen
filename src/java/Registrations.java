
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class Registrations extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Registrations</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Registrations at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html; charset=UTF-8");
        
        try {
            
            PrintWriter out = response.getWriter();        
            Registration_Database db = new Registration_Database();
            
            int sessionid = Integer.parseInt(request.getParameter("session"));
            
            out.println( db.getAttendeeList(sessionid) );
            
        }
        catch (Exception e) { e.printStackTrace(); }
        
        
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json;charset=UTF-8");
        
        try {
            
            Registration_Database db = new Registration_Database();
            
            PrintWriter out = response.getWriter();

            String newFirstname = request.getParameter("firstname");
            String newLastname = request.getParameter("lastname");
            String newDisplay = request.getParameter("displayname");
            int newSessionId = Integer.parseInt(request.getParameter("sessionid"));
            
            out.println( db.registerAttendee(newFirstname, newLastname, newDisplay, newSessionId) );
            
        }
        catch (Exception e) { e.printStackTrace(); }
        
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
