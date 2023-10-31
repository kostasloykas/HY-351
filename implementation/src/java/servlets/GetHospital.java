package servlets;

import com.google.gson.Gson;
import database.DB_Connection;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mainClasses.Hospital;
import database.tables.EditRandevouzTable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import mainClasses.Randevouz;


@WebServlet(name = "GetHospital", urlPatterns = {"/GetHospital"})
public class GetHospital extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    /* hospitals in specific city */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            String city = request.getQueryString();
            
            Connection con = DB_Connection.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs;

            rs = stmt.executeQuery("SELECT * FROM hospital WHERE city= '" + city + "'");

            while (rs.next()) {
                
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                Hospital hos = gson.fromJson(json, Hospital.class);
                
                out.println("<tr>");
                out.println("<td>" + hos.getHospital_id() + "</td>");
                out.println("<td>" + hos.getName() + "</td>");              
                out.println("<td>" + hos.getAddress() + "</td>");
                out.println("<td>" + hos.getAddr_no() + "</td>");
                out.println("<td>" + hos.getCity() + "</td>");
                out.println("</tr>");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(GetHospital.class.getName()).log(Level.SEVERE, null, ex);
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
            
            String randevouz_id = request.getParameter("randevouz_id2");
            String user_id = request.getParameter("user_id");
            
            EditRandevouzTable rand_table = new EditRandevouzTable();
            Randevouz rand = rand_table.databaseToRandevouz_search(Integer.parseInt(randevouz_id));
            
            if (rand == null) {
                response.setStatus(404);             
            } else{
                int res = rand_table.cancelRandevouz(Integer.parseInt(randevouz_id), "cancelled",Integer.parseInt(user_id));
                
                if(res==0)
                    response.setStatus(200);
                else if(res ==-1)
                    response.setStatus(403); 
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(GetHospital.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
