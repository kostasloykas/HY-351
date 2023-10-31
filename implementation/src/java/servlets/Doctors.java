package servlets;

import database.tables.EditDoctorTable;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mainClasses.Doctor;

@WebServlet(name = "Doctors", urlPatterns = {"/Doctors"})
public class Doctors extends HttpServlet {


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");

        String username = request.getParameter("username");
        try (PrintWriter out = response.getWriter()) {

            EditDoctorTable log = new EditDoctorTable();
            Doctor su_login = log.databaseUserToJSON_string("username",username);

            HttpSession session = request.getSession();

            if (session.getAttribute("loggedIn") != null || su_login != null) {
                String json = log.doctorToJSON(su_login);
                out.println(json);
                response.setStatus(200);
            } else {

                response.setStatus(403);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Doctors.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            String s = session.getAttribute("loggedIn").toString();

            EditDoctorTable log = new EditDoctorTable();
            Doctor su_login = log.databaseDocToJSON_uname(s);
            
            if (session.getAttribute("username") != null || su_login != null) {
                String json = log.doctorToJSON(su_login);
                response.getWriter().write(json);
                response.setStatus(200);
            } else {
                response.setStatus(404);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Doctors.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
