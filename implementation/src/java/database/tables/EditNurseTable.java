package database.tables;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import database.DB_Connection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import mainClasses.Nurse;

public class EditNurseTable {

    
    public void addNurseFromJSON(String json) throws ClassNotFoundException{
         Nurse nrs=jsonToNurse(json);
         createNewTreatment(nrs);
    }
    public String nurseToJSON(Nurse nrs) {
        Gson gson = new Gson();

        String json = gson.toJson(nrs, Nurse.class);
        return json;
    }

    public Nurse jsonToNurse(String json) {
        Gson gson = new Gson();
        Nurse nrs = gson.fromJson(json, Nurse.class);
        return nrs;
    }
    
    public Nurse databaseToNurse(String username, String password) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM nurse WHERE username = '" + username + "' AND password='" + password + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Nurse user = gson.fromJson(json, Nurse.class
            );
            return user;
        } catch (JsonSyntaxException | SQLException e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }
    
    public Nurse databaseNurseToJSON_uname(String username) throws SQLException, ClassNotFoundException{
         Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM nurse WHERE username = '" + username + "'");
            rs.next();
            String json=DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Nurse doc = gson.fromJson(json, Nurse.class);
            return doc;
        } catch (JsonSyntaxException | SQLException e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }
    
    public Nurse databaseNurseToJSON_email(String email) throws SQLException, ClassNotFoundException{
         Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM nurse WHERE email = '" + email + "'");
            rs.next();
            String json=DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Nurse doc = gson.fromJson(json, Nurse.class);
            return doc;
        } catch (JsonSyntaxException | SQLException e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }
    
    public void updateNurse_string(String username, String key, String value) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String update = "UPDATE nurse SET " + key + "='" + value + "' WHERE username = '" + username + "'";
        stmt.executeUpdate(update);
    }
    
    public Nurse databaseToNurse(int id) throws SQLException, ClassNotFoundException{
         Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM nurse WHERE nurse_id= '" + id + "'");
            rs.next();
            String json=DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Nurse nrs  = gson.fromJson(json, Nurse.class);
            return nrs;
        } catch (JsonSyntaxException | SQLException e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public void createNurseTable() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String sql = "CREATE TABLE nurse "
                + "(nurse_id INTEGER not NULL AUTO_INCREMENT, "
                + "doctor_id INTEGER not null,"
                + "hospital_id INTEGER not null,"
                + "    username VARCHAR(30) not null unique,"
                + "    email VARCHAR(40) not null unique,	"
                + "    password VARCHAR(32) not null,"
                + "    firstname VARCHAR(20) not null,"
                + "    lastname VARCHAR(30) not null,"
                + "    birthdate DATE not null,"
                + "    gender  VARCHAR (7) not null,"
                + "    telephone VARCHAR(14) not null,"
                + "FOREIGN KEY (doctor_id) REFERENCES doctors(doctor_id), "
                + "FOREIGN KEY (hospital_id) REFERENCES hospital(hospital_id), "
                + "PRIMARY KEY ( nurse_id ))";
        stmt.execute(sql);
        stmt.close();
        con.close();

    }

    public void createNewTreatment(Nurse tr) throws ClassNotFoundException {
        try {
            Connection con = DB_Connection.getConnection();

            Statement stmt = con.createStatement();

            String insertQuery = "INSERT INTO "
                    + " nurse (doctor_id,hospital_id,username,email,password,firstname,lastname,birthdate,gender,telephone) "
                    + " VALUES ("
                    + "'" + tr.getDoctor_id() + "',"
                    + "'" + tr.getHospital_id() + "',"
                    + "'" + tr.getUsername() + "',"
                    + "'" + tr.getEmail() + "',"
                    + "'" + tr.getPassword() + "',"
                    + "'" + tr.getFirstname() + "',"
                    + "'" + tr.getLastname() + "',"
                    + "'" + tr.getBirthdate() + "',"
                    + "'" + tr.getGender() + "',"
                    + "'" + tr.getTelephone() + "'"
                    + ")";
            System.out.println(insertQuery);
            stmt.executeUpdate(insertQuery);
            System.out.println("# The nurse was successfully added in the database.");
            stmt.close();
                 con.close();
        } catch (SQLException ex) {
            Logger.getLogger(EditHospitalTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
