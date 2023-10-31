package servlets;

import database.tables.EditHospitalTable;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mainClasses.Hospital;
import org.json.JSONObject;

public class NewHospital extends HttpServlet {

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
    //Kanei add sthn bash ena kainourgio nosokomeio
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try ( PrintWriter out = response.getWriter()) {
            String data;
            StringBuilder builder = new StringBuilder();
            while ((data = request.getReader().readLine()) != null) {
                builder.append(data);
            }

            JSONObject obj = new JSONObject(builder.toString());
            String name = obj.getString("name");
            String address = obj.getString("address");
            String addr_no = obj.getString("addr_no");
            String city = obj.getString("city");

            Hospital hospital = new Hospital();
            hospital.setAddr_no(addr_no);
            hospital.setAddress(address);
            hospital.setCity(city);
            hospital.setName(name);

            EditHospitalTable table = new EditHospitalTable();
            table.createNewHospital(hospital);
            response.setStatus(200);

        } catch (Exception ex) {
            response.setStatus(400);
            Logger.getLogger(GetDoctor.class.getName()).log(Level.SEVERE, null, ex);
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
