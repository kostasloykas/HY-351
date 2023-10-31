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
import mainClasses.Admin;

public class EditAdminTable {

    public void addAdminFromJSON(String json) throws ClassNotFoundException{
         Admin admin=jsonToAdmin(json);
         addNewSimpleUser(admin);
    }
    
     public Admin jsonToAdmin(String json){
         Gson gson = new Gson();

        Admin admin = gson.fromJson(json, Admin.class);
        return admin;
    }
    
    public String adminToJSON(Admin admin){
         Gson gson = new Gson();

        String json = gson.toJson(admin, Admin.class);
        return json;
    }
    
    public Admin databaseToAdmin(String username, String password) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM admins WHERE username = '" + username + "' AND password='" + password + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Admin user = gson.fromJson(json, Admin.class);
            return user;
        } catch (JsonSyntaxException | SQLException e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }
    
    public Admin databaseAdminToJSON_uname(String username) throws SQLException, ClassNotFoundException{
         Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM admins WHERE username = '" + username + "'");
            rs.next();
            String json=DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Admin doc = gson.fromJson(json, Admin.class);
            return doc;
        } catch (JsonSyntaxException | SQLException e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }
    

     public void createAdminTable() throws SQLException, ClassNotFoundException {

        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        String query = "CREATE TABLE admins "
                + "(admin_id INTEGER not NULL AUTO_INCREMENT, "
                + "    username VARCHAR(30) not null unique,"
                + "    email VARCHAR(40) not null unique,	"
                + "    password VARCHAR(32) not null,"
                + "    firstname VARCHAR(20) not null,"
                + "    lastname VARCHAR(30) not null,"
                + " PRIMARY KEY ( admin_id))";
        stmt.execute(query);
        stmt.close();
    }
    
    public void addNewSimpleUser(Admin admin) throws ClassNotFoundException {
        try {
            Connection con = DB_Connection.getConnection();

            Statement stmt = con.createStatement();

            String insertQuery = "INSERT INTO "
                    + " admins (username,email,password,firstname,lastname)"
                    + " VALUES ("
                    + "'" + admin.getUsername() + "',"
                    + "'" + admin.getEmail() + "',"
                    + "'" + admin.getPassword() + "',"
                    + "'" + admin.getFirstname() + "',"
                    + "'" + admin.getLastname() + "'"
                    + ")";
            System.out.println(insertQuery);
            stmt.executeUpdate(insertQuery);
            System.out.println("# The admin was successfully added in the database.");
            stmt.close();

        } catch (SQLException ex) {
            Logger.getLogger(EditSimpleUserTable.class.getName()).log(Level.SEVERE, null, ex);
        }
   

}

}
