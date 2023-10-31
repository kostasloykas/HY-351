package servlets;

import database.tables.EditSimpleUserTable;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;

public class GetUserData extends HttpServlet {


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
    // epistrefei ola ta stoixeia tou user sumfwna me to amka
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            PrintWriter out = response.getWriter();
            String data = request.getReader().readLine();
            System.out.println(data);
            JSONObject obj = new JSONObject(data);
            String amka = obj.getString("amka");
            EditSimpleUserTable user = new EditSimpleUserTable();
            data = user.UserDataFromAmka(amka);
            if (data == null) {
                response.setStatus(400);
                out.println("null");
            } else {
                response.setStatus(200);
                out.println(data);
            }

        } catch (IOException | ClassNotFoundException | SQLException | JSONException ex) {
            Logger.getLogger(GetDoctor.class.getName()).log(Level.SEVERE, null, ex);
            response.setStatus(400);
        }
    }

}
