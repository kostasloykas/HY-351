package servlets;

import com.google.gson.Gson;
import database.DB_Connection;
import database.tables.EditSimpleUserTable;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mainClasses.Randevouz;

@WebServlet(name = "Appointments2", urlPatterns = {"/Appointments2"})
public class Appointments2 extends HttpServlet {



    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    /* upcoming appointments */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         try (PrintWriter out = response.getWriter()) {
             
            String user_id = request.getQueryString();
            
            Connection con = DB_Connection.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs;

            rs = stmt.executeQuery("SELECT * FROM randevouz WHERE user_id = '" + user_id + "' AND status='" + "selected" + "'");

            while (rs.next()) {
                
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                Randevouz rand = gson.fromJson(json, Randevouz.class);

                out.println("<tr>");
                out.println("<td>" + rand.getRandevouz_id() + "</td>");
                out.println("<td>" + rand.getHospital_id() + "</td>");              
                out.println("<td>" + rand.getDate_time() + "</td>");
                out.println("<td>" + rand.getVaccine() + "</td>");
                out.println("</tr>");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Appointments2.class.getName()).log(Level.SEVERE, null, ex);
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
        
        try (PrintWriter out = response.getWriter()){
            
            EditSimpleUserTable user_table = new EditSimpleUserTable();
            ArrayList<Integer> ar = user_table.countUsers();
            if(ar == null){
                response.setStatus(404);
            } else {
                out.println(ar);
                response.setStatus(200);
            }
            
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Appointments.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
