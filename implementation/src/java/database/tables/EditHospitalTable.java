package database.tables;

import mainClasses.Hospital;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import database.DB_Connection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EditHospitalTable {
    
    public void addHospitalFromJSON(String json) throws ClassNotFoundException{
         Hospital hos=jsonToHospital(json);
         createNewHospital(hos);
    }
    
     public Hospital jsonToHospital(String json){
        Gson gson = new Gson();
        Hospital hos = gson.fromJson(json, Hospital.class);
        return hos;
    }
    
     public String hospitalToJSON(Hospital hos){
         Gson gson = new Gson();

        String json = gson.toJson(hos, Hospital.class);
        return json;
    }
    
     public Hospital databaseToHospital(String name,String city) throws SQLException, ClassNotFoundException{
         Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM hospital WHERE name= '" + name + "' AND city='"+city+"'");
            rs.next();
            String json=DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Hospital hos = gson.fromJson(json, Hospital.class);
            return hos;
        } catch (JsonSyntaxException | SQLException e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }
     
     public Hospital printHospitals(String city) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM hospital WHERE city= '" + city + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Hospital doc = gson.fromJson(json, Hospital.class);
            return doc;
        } catch (JsonSyntaxException | SQLException e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }
    
    public String databaseToHospital_search(int id) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM hospital WHERE hospital_id= '" + id + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Hospital bt = gson.fromJson(json, Hospital.class);
            return bt.getName();
        } catch (JsonSyntaxException | SQLException e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }
  
       
       public void deleteHospital(int hospital_id) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String deleteQuery = "DELETE FROM hospital WHERE hospital_id='" + hospital_id + "'";
        stmt.executeUpdate(deleteQuery);
        stmt.close();
        con.close();
    }


    public void createHospitalTable() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String sql = "CREATE TABLE hospital "
                + "(hospital_id INTEGER not NULL AUTO_INCREMENT, "
                + " name VARCHAR(30) not null,"
                + " address VARCHAR(50) not null,"
                + " addr_no INTEGER not null,"
                + " city VARCHAR(50) not null,"
                + "PRIMARY KEY ( hospital_id ))";
        stmt.execute(sql);
        stmt.close();
        con.close();

    }

    public void createNewHospital(Hospital hos) throws ClassNotFoundException {
        try {
            Connection con = DB_Connection.getConnection();

            Statement stmt = con.createStatement();

            String insertQuery = "INSERT INTO "
                    + " hospital (name,address,addr_no,city) "
                    + " VALUES ("
                    + "'" + hos.getName()+ "',"
                    + "'" + hos.getAddress()+ "',"
                    + "'" + hos.getAddr_no()+ "',"
                    + "'" + hos.getCity()+ "'"
                    + ")";
            System.out.println(insertQuery);
            stmt.executeUpdate(insertQuery);
            System.out.println("# The hospital was successfully added in the database.");
            stmt.close();

        } catch (SQLException ex) {
            Logger.getLogger(EditHospitalTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
