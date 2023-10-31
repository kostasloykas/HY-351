package servlets;

import database.tables.EditSimpleUserTable;
import database.tables.EditDoctorTable;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mainClasses.SimpleUser;
import mainClasses.Doctor;
import mainClasses.JSON_Converter;

public class GetUser extends HttpServlet {

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

        try {
            response.setContentType("text/html;charset=UTF-8");
            
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String amka = request.getParameter("amka");
            String firstname = request.getParameter("firstname");
            String lastname = request.getParameter("lastname");
            
            EditSimpleUserTable su = new EditSimpleUserTable();
            SimpleUser check_uname = su.databaseUserToJSON_string("username",username);
            SimpleUser check_email = su.databaseUserToJSON_string("email",email);
            SimpleUser check_amka = su.databaseUserToJSON_string("amka",amka);
            SimpleUser check_full = su.databaseUserToJSON_fullname(firstname,lastname);
            
            EditDoctorTable doc = new EditDoctorTable();
            Doctor check_doc_uname = doc.databaseDocToJSON_uname(username);
            Doctor check_doc_email = doc.databaseDocToJSON_email(email);
            Doctor check_doc_amka = doc.databaseDocToJSON_amka(amka);
            
            
            if (check_uname == null && check_email == null && check_amka == null && check_doc_uname == null && check_doc_email == null && check_doc_amka == null
                    && check_full != null) {
                response.setStatus(200);
            } else {
                if(check_full == null) {
                    response.setStatus(405);
                }
                else if (check_uname != null || check_doc_uname != null) {
                    response.setStatus(403);
                } else if (check_email != null || check_doc_email != null) {
                    response.setStatus(406);
                } else {
                    response.setStatus(404);
                }
                
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(GetUser.class.getName()).log(Level.SEVERE, null, ex);
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
            response.setContentType("text/html;charset=UTF-8");

            EditSimpleUserTable su = new EditSimpleUserTable();
            JSON_Converter converter = new JSON_Converter();
            String JSON = converter.getJSONFromAjax(request.getReader());
            su.addSimpleUserFromJSON(JSON);

            response.setStatus(200);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GetUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
