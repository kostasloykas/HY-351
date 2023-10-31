package servlets;

import com.google.gson.Gson;
import database.tables.EditHospitalTable;
import database.tables.EditMessageTable;
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

public class GetMessages extends HttpServlet {


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
    // Epistrefei ola ta mhnuma ta pou aforoun to sugkekrimeno admin
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            PrintWriter out = response.getWriter();
            String data;
            StringBuilder builder = new StringBuilder();
            while ((data = request.getReader().readLine()) != null) {
                builder.append(data);
            }

            EditMessageTable table = new EditMessageTable();
            Gson gson = new Gson();
            String response_data = gson.toJson(table.GetMessagesForAdmin());
            System.out.println(response_data);
            response.setStatus(200);
            out.println(response_data);
        } catch (IOException | ClassNotFoundException | SQLException | JSONException e) {
            response.setStatus(400);
            Logger.getLogger(EditHospitalTable.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
