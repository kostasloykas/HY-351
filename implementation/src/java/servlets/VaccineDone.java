
package servlets;

import database.tables.EditRandevouzTable;
import database.tables.EditSimpleUserTable;
import database.tables.EditVaccineTable;
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

public class VaccineDone extends HttpServlet {

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
    /*enhmerwnei to status tou user opou ekane to emvolio
     emhmerwnei kai to status tou randevou se done
     emhmerwnei kai to stock tou sugkekrimenou emvoliou pou egine
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            EditRandevouzTable rand_table = new EditRandevouzTable();
            EditSimpleUserTable user_table = new EditSimpleUserTable();
            EditVaccineTable vaccine_table = new EditVaccineTable();
            PrintWriter out = response.getWriter();
            String tmp;
            StringBuilder builder = new StringBuilder();
            while ((tmp = request.getReader().readLine()) != null) {
                builder.append(tmp);
            }

            JSONObject obj = new JSONObject(builder.toString());
            String email = obj.getString("email");
            String randevou_id = obj.getString("randevou_id");
            String vaccine = obj.getString("vaccine");

            String value = "";
            String user_id = user_table.GetUserIdFromDatabase(email);
            int count = rand_table.countUserTimesWhoVaccinated(user_id);
            if (count < 2) {
                value = "semi_vaccinated";
            } else if (count >= 2) {
                value = "fully_vaccinated";
            }

            rand_table.updateRandevouz(Integer.parseInt(randevou_id), Integer.parseInt(user_id), "done");
            user_table.updateSimpleUserFromEmail(email, "status", value);

            if (vaccine_table.UpdateStock(vaccine) == 0) {
                response.setStatus(400);
                return;
            }
            response.setStatus(200);

        } catch (IOException | ClassNotFoundException | NumberFormatException | SQLException | JSONException ex) {
            response.setStatus(400);
            Logger.getLogger(GetDoctor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
