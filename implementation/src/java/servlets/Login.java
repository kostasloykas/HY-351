package servlets;

import database.tables.EditAdminTable;
import database.tables.EditDoctorTable;
import database.tables.EditNurseTable;
import database.tables.EditSimpleUserTable;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mainClasses.Admin;
import mainClasses.Doctor;
import mainClasses.Nurse;
import mainClasses.SimpleUser;

public class Login extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    /* creates session for user to stay logged in */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        String username = request.getParameter("username");
        try (PrintWriter out = response.getWriter()) {

            EditSimpleUserTable log = new EditSimpleUserTable();
            SimpleUser su_login = log.databaseUserToJSON_string("username",username);

            HttpSession session = request.getSession();

            if (session.getAttribute("loggedIn") != null || su_login != null) {
                String json = log.simpleUserToJSON(su_login);
                out.println(json);
                response.setStatus(200);
            } else {

                response.setStatus(403);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            response.setContentType("text/html;charset=UTF-8");
            
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            
            EditSimpleUserTable log = new EditSimpleUserTable();
            SimpleUser su_login = log.databaseToSimpleUser(username, password);
            
            EditAdminTable adm = new EditAdminTable();
            Admin adm_login = adm.databaseToAdmin(username,password);
            
            EditDoctorTable doc = new EditDoctorTable();
            Doctor doc_login = doc.databaseToDoctorCertified(username, password);
            
            EditNurseTable nur = new EditNurseTable();
            Nurse nur_login = nur.databaseToNurse(username, password);
            
            
            if (su_login == null && adm_login == null && doc_login == null && nur_login == null) {
                response.setStatus(404);
            } else {
                if (adm_login != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("loggedIn", username);
                    response.setStatus(201);
                } else if(doc_login != null){
                    HttpSession session = request.getSession();
                    session.setAttribute("loggedIn", username);
                    response.setStatus(202); 
                } else if(nur_login != null){
                    HttpSession session = request.getSession();
                    session.setAttribute("loggedIn", username);
                    response.setStatus(203); 
                } else {
                    HttpSession session = request.getSession();
                    session.setAttribute("loggedIn", username);
                    response.setStatus(200);
                }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
