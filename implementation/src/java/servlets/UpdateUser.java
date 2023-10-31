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

public class UpdateUser extends HttpServlet {


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
    // Kanei update ena user me ta kainourgia stoixeia
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            PrintWriter out = response.getWriter();
            String data;
            StringBuilder builder = new StringBuilder();
            while ((data = request.getReader().readLine()) != null) {
                builder.append(data);
            }
            System.out.println(builder.toString());
            JSONObject obj = new JSONObject(builder.toString());
            String new_amka = obj.getString("new_amka");
            String amka = obj.getString("amka");
            String firstname = obj.getString("firstname");
            String lastname = obj.getString("lastname");
            String birthdate = obj.getString("birthdate");
            String email = obj.getString("email");
            String username = obj.getString("username");
            String city = obj.getString("city");
            String gender = obj.getString("gender");
            String address = obj.getString("address");
            String addr_no = obj.getString("addr_no");
            String telephone = obj.getString("telephone");

            EditSimpleUserTable user = new EditSimpleUserTable();

            user.updateSimpleUserFromAmka_string(amka, "amka", new_amka);
            amka = new_amka;
            user.updateSimpleUserFromAmka_string(amka, "firstname", firstname);
            user.updateSimpleUserFromAmka_string(amka, "lastname", lastname);
            user.updateSimpleUserFromAmka_string(amka, "birthdate", birthdate);
            user.updateSimpleUserFromAmka_string(amka, "email", email);
            user.updateSimpleUserFromAmka_string(amka, "username", username);
            user.updateSimpleUserFromAmka_string(amka, "city", city);
            user.updateSimpleUserFromAmka_string(amka, "gender", gender);
            user.updateSimpleUserFromAmka_string(amka, "address", address);
            user.updateSimpleUserFromAmka_string(amka, "addr_no", addr_no);
            user.updateSimpleUserFromAmka_string(amka, "telephone", telephone);

            response.setStatus(200);

        } catch (IOException | ClassNotFoundException | SQLException | JSONException ex) {
            Logger.getLogger(GetDoctor.class.getName()).log(Level.SEVERE, null, ex);
            response.setStatus(400);
        }
    }

}
