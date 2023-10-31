package servlets;

import com.google.gson.Gson;
import database.tables.EditDoctorTable;
import database.tables.EditRandevouzTable;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mainClasses.JSON_Converter;
import mainClasses.Randevouz;

public class GetUncertifiedDoctors extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    //epistrefei se json morfh olous tous giatrous pou den exoun ginei certify
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            PrintWriter out = response.getWriter();
            EditDoctorTable doc = new EditDoctorTable();
            Gson gson = new Gson();
            String data = gson.toJson(doc.databaseToUncertifiedDoctors());
            response.setStatus(200);
            out.println(data);

        } catch (IOException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(GetDoctor.class.getName()).log(Level.SEVERE, null, ex);
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
        System.out.println("helo");
        String doctor_id = request.getParameter("doctor_id");
        //String hospital_id = request.getParameter("hospital_id");
        String date_time = request.getParameter("date_time"); 
        String vaccine = request.getParameter("vaccine");
        System.out.println(vaccine);
        date_time = date_time.replace("T", " ") + ":00";
        try {
            Randevouz randev = new Randevouz();
            randev.setDate_time(date_time);
            randev.setDoctor_id(Integer.parseInt(doctor_id));
            randev.setHospital_id(1);
            randev.setVaccine(vaccine);
            randev.setStatus("free");
            (new EditRandevouzTable()).createNewRandevouz(randev);
            response.setStatus(200);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Randevouz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
