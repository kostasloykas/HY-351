package servlets;

import database.tables.EditDoctorTable;
import database.tables.EditHospitalTable;
import database.tables.EditNurseTable;
import database.tables.EditSimpleUserTable;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mainClasses.Doctor;
import mainClasses.Nurse;
import mainClasses.SimpleUser;

@WebServlet(name = "Update", urlPatterns = {"/Update"})
public class Update extends HttpServlet {

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

        try (PrintWriter out = response.getWriter()) {
            String hospital_id = request.getQueryString();
            EditHospitalTable hos = new EditHospitalTable();

            String res = hos.databaseToHospital_search(Integer.parseInt(hospital_id));
            out.println(res);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Update.class.getName()).log(Level.SEVERE, null, ex);
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
        response.setContentType("text/html;charset=UTF-8");

        response.setContentType("text/html;charset=UTF-8");

        String choice = request.getParameter("choice");
        String change = request.getParameter("change");
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("loggedIn");

        try (PrintWriter out = response.getWriter()) {

            EditSimpleUserTable log = new EditSimpleUserTable();
            SimpleUser check_uname = log.databaseUserToJSON_string("username", username);

            EditDoctorTable doc = new EditDoctorTable();
            Doctor check_doc_uname = doc.databaseDocToJSON_uname(username);
            
            EditNurseTable nur = new EditNurseTable();
            Nurse check_nur_uname = nur.databaseNurseToJSON_uname(username);
            
            if (null == choice) {
                response.setStatus(404);
            } else {
                switch (choice) {
                    case "password":
                    case "birthdate":
                    case "gender":
                    case "city":
                    case "address":
                    case "telephone":                 
                        if (check_uname != null) {
                            log.updateSimpleUser_string(username, choice, change);
                        } else if (check_doc_uname != null) {
                            doc.updateDoctor_string(username, choice, change);
                        } else if (check_nur_uname != null) {
                            nur.updateNurse_string(username, choice, change);
                        }
                        response.setStatus(200);
                        break;
                    case "doctor_info":
                    case "specialty":
                        doc.updateDoctor_string(username, choice, change);
                        response.setStatus(200);
                        break;
                    case "addr_no":
                        log.updateSimpleUser_integer(username, choice, Integer.parseInt(change));
                        response.setStatus(200);
                        break;
                    default:
                        response.setStatus(404);
                        break;
                }
            }

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Update.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
