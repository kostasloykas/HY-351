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
import mainClasses.Vaccine;

public class EditVaccineTable {

    
     public void addVaccineFromJSON(String json) throws ClassNotFoundException{
         Vaccine vac=jsonToVaccine(json);
         createNewVaccine(vac);
    }
    
      public Vaccine jsonToVaccine(String json) {
        Gson gson = new Gson();
        Vaccine vac = gson.fromJson(json, Vaccine.class);
        return vac;
    }
     
    public String vaccineToJSON(Vaccine vac) {
        Gson gson = new Gson();

        String json = gson.toJson(vac, Vaccine.class);
        return json;
    }
   
    public Vaccine databaseToVaccine(int id) throws SQLException, ClassNotFoundException{
         Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM vaccine WHERE vaccine_id= '" + id + "'");
            rs.next();
            String json=DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Vaccine vac = gson.fromJson(json, Vaccine.class);
            return vac;
        } catch (JsonSyntaxException | SQLException e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public Vaccine databaseToVaccine(String manufacturer) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM vaccine WHERE manufacturer= '" + manufacturer + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Vaccine vac = gson.fromJson(json, Vaccine.class);
            return vac;
        } catch (JsonSyntaxException | SQLException e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public int UpdateStock(String manufacturer) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        try {
            Vaccine vac = databaseToVaccine(manufacturer);
            if (vac == null) {
                return 0;
            }
            stmt.executeUpdate("UPDATE vaccine SET stock ='" + (vac.getStock() - 1) + "' WHERE manufacturer= '" + manufacturer + "'");
            return 1;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return 0;
    }

    public void createVaccineTable() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String sql = "CREATE TABLE vaccine "
                + "(vaccine_id INTEGER not NULL AUTO_INCREMENT, "
                + "manufacturer VARCHAR(30) not NULL, "
                + "doses INTEGER not null,"
                + "stock INTEGER not null,"
                + "PRIMARY KEY ( vaccine_id ))";
        stmt.execute(sql);
        stmt.close();
        con.close();

    }

    public void createNewVaccine(Vaccine vac) throws ClassNotFoundException {
        try {
            Connection con = DB_Connection.getConnection();

            Statement stmt = con.createStatement();

            String insertQuery = "INSERT INTO "
                    + " vaccine (manufacturer,doses,stock) "
                    + " VALUES ("
                    + "'" + vac.getManufacturer() + "',"
                    + "'" + vac.getDoses() + "',"
                    + "'" + vac.getStock() + "'"
                    + ")";
            System.out.println(insertQuery);
            stmt.executeUpdate(insertQuery);
            System.out.println("# The vaccine was successfully added in the database.");
            stmt.close();

        } catch (SQLException ex) {
            Logger.getLogger(EditHospitalTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
