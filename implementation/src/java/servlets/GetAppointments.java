package servlets;

import com.google.gson.Gson;
import database.DB_Connection;
import database.tables.EditRandevouzTable;
import database.tables.EditSimpleUserTable;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mainClasses.Hospital;
import mainClasses.Randevouz;
import mainClasses.SimpleUser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetAppointments extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    /* for date picker */
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

            rs = stmt.executeQuery("SELECT * FROM randevouz WHERE status = '" + "done" + "' AND user_id ='" + user_id + "' OR status = '" + "selected" + "' AND user_id='" + user_id + "'");
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
            Logger.getLogger(GetAppointments.class.getName()).log(Level.SEVERE, null, ex);
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
    // epistrefei se json morfh ola ta randevou pou aforoun ton sugkekrimeno giatro
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            PrintWriter out = response.getWriter();
            String tmp;
            StringBuilder builder = new StringBuilder();
            while ((tmp = request.getReader().readLine()) != null) {
                builder.append(tmp);
            }

            JSONObject obj = new JSONObject(builder.toString());
            String id_doctor = obj.getString("id_doctor");

            EditRandevouzTable table = new EditRandevouzTable();
            Gson gson = new Gson();
            ArrayList<Randevouz> randevou_list = table.databaseToRandevouz(id_doctor);
            Iterator<Randevouz> iter = randevou_list.iterator();
            EditSimpleUserTable user_table = new EditSimpleUserTable();
            JSONArray array = new JSONArray();
            JSONObject tmp2 = new JSONObject();

            while (iter.hasNext()) {
                Randevouz rand = iter.next();
                SimpleUser user = user_table.databaseToUser(rand.getUser_id());
                tmp2.put("randevou_id", rand.getRandevouz_id());
                tmp2.put("vaccine", rand.getVaccine());
                tmp2.put("date_time", rand.getDate_time());
                tmp2.put("firstname", user.getFirstname());
                tmp2.put("lastname", user.getLastname());
                tmp2.put("email", user.getEmail());
                array.put(tmp2);
                tmp2 = new JSONObject();
            }

            out.println(array);
            response.setStatus(200);

        } catch (IOException | ClassNotFoundException | SQLException | JSONException ex) {
            response.setStatus(400);
            Logger.getLogger(GetDoctor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
