package servlets;

import database.tables.EditSimpleUserTable;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mainClasses.SimpleUser;
import org.json.JSONObject;

public class NewUser extends HttpServlet {

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
    // o Admin pros8etei ena kainourgio user me skopo argotera o user na shmplhrwsh ta stoixeia tou otan kanei register
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try ( PrintWriter out = response.getWriter()) {
            String data;
            StringBuilder builder = new StringBuilder();
            while ((data = request.getReader().readLine()) != null) {
                builder.append(data);
            }

            JSONObject obj = new JSONObject(builder.toString());
            String firstname = obj.getString("firstname");
            String lastname = obj.getString("lastname");

            EditSimpleUserTable user = new EditSimpleUserTable();
            SimpleUser new_user = new SimpleUser();
            new_user.setFirstname(firstname);
            new_user.setLastname(lastname);
            user.InsertNewSimpleUserFL(new_user);
            response.setStatus(200);
        } catch (Exception ex) {
            response.setStatus(400);
            Logger.getLogger(GetDoctor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
