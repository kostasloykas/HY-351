package servlets;

import com.google.gson.Gson;
import database.tables.EditRandevouzTable;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mainClasses.Randevouz;

@WebServlet(name = "Appointments", urlPatterns = {"/Appointments"})
public class Appointments extends HttpServlet {

 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    /* gets appointments */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try (PrintWriter out = response.getWriter()) {
            response.setContentType("text/html;charset=UTF-8");
            
            String hospital_id = request.getParameter("hospital_id");
            String date = request.getParameter("date");

            EditRandevouzTable rand_table = new EditRandevouzTable();
            Randevouz rand = rand_table.printRandevouz(Integer.parseInt(hospital_id),1);

            if (rand == null) {
                response.setStatus(404);
                
            } else {
                ArrayList<Randevouz> ar = rand_table.databaseToRandevouz(Integer.parseInt(hospital_id),date,1);
                String json = new Gson().toJson(ar);
                out.println(json);
                response.setStatus(200);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Appointments.class.getName()).log(Level.SEVERE, null, ex);
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
    /* civilian selects and appointment */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        try {
            String randevouz_id = request.getParameter("randevouz_id");
            String user_id = request.getParameter("user_id");
            
            EditRandevouzTable rand_table = new EditRandevouzTable();
            
            int count= rand_table.countRandevouz(Integer.parseInt(user_id));
            if(count == 3){
                response.setStatus(403);
            } else {
                rand_table.updateRandevouz(Integer.parseInt(randevouz_id), Integer.parseInt(user_id), "selected");
                response.setStatus(200);
            }
            
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Appointments.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
