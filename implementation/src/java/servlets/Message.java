package servlets;

import com.google.gson.Gson;
import database.DB_Connection;
import database.tables.EditMessageTable;
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
import mainClasses.Hospital;
import mainClasses.JSON_Converter;
import mainClasses.Randevouz;

@WebServlet(name = "Message", urlPatterns = {"/Message"})
public class Message extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    /* for PDF creation */
    @Override 
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         try (PrintWriter out = response.getWriter()) {
            response.setContentType("text/html;charset=UTF-8");
            
            String user_id = request.getQueryString();

            Connection con = DB_Connection.getConnection();
            Statement stmt = con.createStatement();
            Statement stmt2 = con.createStatement();
            ArrayList<String> ar = new ArrayList<>();
            ResultSet rs, rs2;

            rs = stmt.executeQuery("SELECT * FROM randevouz WHERE status = '" + "done" + "' AND user_id='" + user_id + "'");
            int count = 1;
            
            while (rs.next()) {
                
                
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                Randevouz rand = gson.fromJson(json, Randevouz.class);
                
                ar.add(String.valueOf(count));
                
                
                rs2 = stmt2.executeQuery("SELECT * FROM hospital WHERE hospital_id= '" + rand.getHospital_id() + "'");
                while (rs2.next()) {
                    String json2 = DB_Connection.getResultsToJSON(rs2);
                    Gson gson2 = new Gson();
                    Hospital hos = gson2.fromJson(json2, Hospital.class);
                    ar.add(hos.getName());
                }
                ar.add(rand.getDate_time());
                ar.add(rand.getVaccine());
                
                count++;
            }
            String json2 = new Gson().toJson(ar);
            out.println(json2);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
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
    /* uploads message in database */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            response.setContentType("text/html;charset=UTF-8");

            EditMessageTable mess = new EditMessageTable();           
            
            JSON_Converter converter = new JSON_Converter();
            String JSON = converter.getJSONFromAjax(request.getReader());            
            mess.addMessageFromJSON(JSON);

            response.setStatus(200);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
