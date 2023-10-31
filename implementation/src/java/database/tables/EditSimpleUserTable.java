package database.tables;

import mainClasses.SimpleUser;
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
import org.json.JSONObject;

public class EditSimpleUserTable {

    public void addSimpleUserFromJSON(String json) throws ClassNotFoundException {
        SimpleUser user = jsonToSimpleUser(json);
        addNewSimpleUser(user);
    }

    public SimpleUser jsonToSimpleUser(String json) {
        Gson gson = new Gson();

        SimpleUser user = gson.fromJson(json, SimpleUser.class);
        return user;
    }

    public String simpleUserToJSON(SimpleUser user) {
        Gson gson = new Gson();

        String json = gson.toJson(user, SimpleUser.class);
        return json;
    }

    public void printSimpleUserDetails(String username, String password) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM users WHERE username = '" + username + "' AND password='" + password + "'");
            while (rs.next()) {
                System.out.println("===Result===");
                DB_Connection.printResults(rs);
            }

        } catch (SQLException e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }


    public ArrayList<Integer> countUsers() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Integer> count = new ArrayList<>();
        ResultSet rs;

        rs = stmt.executeQuery("SELECT COUNT(status) AS total FROM users WHERE status='" + "unvaccinated" + "'");
        if (rs.next()) {
            count.add(rs.getInt("total"));
        }
        rs = stmt.executeQuery("SELECT COUNT(status) AS total FROM users WHERE status='" + "fully_vaccinated" + "'");
        if (rs.next()) {
            count.add(rs.getInt("total"));
        }
        rs = stmt.executeQuery("SELECT COUNT(status) AS total FROM users WHERE status='" + "semi_vaccinated" + "'");
        if (rs.next()) {
            count.add(rs.getInt("total"));
        }
        return count;
    }

    public void updateSimpleUser_string(String username, String key, String value) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String update = "UPDATE users SET " + key + "='" + value + "' WHERE username = '" + username + "'";
        stmt.executeUpdate(update);
    }

    public void updateSimpleUserFromAmka_string(String amka, String key, String value) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String update = "UPDATE users SET " + key + "='" + value + "' WHERE amka = '" + amka + "'";
        stmt.executeUpdate(update);
    }

    public void updateSimpleUserFromEmail(String email, String key, String value) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String update = "UPDATE users SET " + key + "='" + value + "' WHERE email = '" + email + "'";
        stmt.executeUpdate(update);
    }

    public void updateSimpleUser_integer(String username, String key, int value) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String update = "UPDATE users SET " + key + "='" + value + "' WHERE username = '" + username + "'";
        stmt.executeUpdate(update);
    }

    public SimpleUser databaseUserToJSON_string(String key, String value) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM users WHERE " + key + "='" + value + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            SimpleUser user = gson.fromJson(json, SimpleUser.class);
            return user;
        } catch (JsonSyntaxException | SQLException e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public SimpleUser databaseUserToJSON_fullname(String firstname, String lastname) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM users WHERE firstname = '" + firstname + "' AND lastname = '" + lastname + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            SimpleUser user = gson.fromJson(json, SimpleUser.class
            );
            return user;
        } catch (JsonSyntaxException | SQLException e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public SimpleUser databaseToSimpleUser(String username, String password) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM users WHERE username = '" + username + "' AND password='" + password + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            SimpleUser user = gson.fromJson(json, SimpleUser.class
            );
            return user;
        } catch (JsonSyntaxException | SQLException e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public String UserDataFromAmka(String amka) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM users WHERE amka = '" + amka + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            return json;
        } catch (JsonSyntaxException | SQLException e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public String GetUserIdFromDatabase(String email) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT user_id FROM users WHERE email = '" + email + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            JSONObject obj = new JSONObject(json);
            return obj.getString("user_id");
        } catch (JsonSyntaxException | SQLException e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public SimpleUser databaseToUser(int id) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM users WHERE user_id = '" + id + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            SimpleUser user = gson.fromJson(json, SimpleUser.class);
            return user;
        } catch (JsonSyntaxException | SQLException e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public void createSimpleUserTable() throws SQLException, ClassNotFoundException {

        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        String query = "CREATE TABLE users "
                + "(user_id INTEGER not NULL AUTO_INCREMENT, "
                + "    username VARCHAR(30) not null unique,"
                + "    email VARCHAR(40) not null unique,	"
                + "    password VARCHAR(32) not null,"
                + "    firstname VARCHAR(20) not null,"
                + "    lastname VARCHAR(30) not null,"
                + "    birthdate DATE not null,"
                + "    gender  VARCHAR (7) not null,"
                + "    amka VARCHAR (11) not null,"
                + "    city VARCHAR(50) not null,"
                + "    address VARCHAR(50) not null,"
                + "    addr_no INTEGER not null,"
                + "    telephone VARCHAR(14) not null,"
                + "    status VARCHAR(20) not null,"
                + " PRIMARY KEY ( user_id))";
        stmt.execute(query);
        stmt.close();
    }

    public void addNewSimpleUser(SimpleUser user) throws ClassNotFoundException {
        try {
            Connection con = DB_Connection.getConnection();

            Statement stmt = con.createStatement();

            String updateQuery = "UPDATE users SET username = '" + user.getUsername() + "', email = '" + user.getEmail()
                    + "', password = '" + user.getPassword() + "', birthdate= '" + user.getBirthdate()
                    + "', gender = '" + user.getGender() + "', amka = '" + user.getAmka()
                    + "', city = '" + user.getCity() + "', address = '" + user.getAddress()
                    + "', addr_no = '" + user.getAddr_no() + "', telephone= '" + user.getTelephone()
                    + "', status = '" + "unvaccinated" + "' WHERE firstname = '" + user.getFirstname()
                    + "' AND lastname = '" + user.getLastname() + "'";

            /*String insertQuery = "INSERT INTO "
                    + " users (username,email,password,firstname,lastname,birthdate,gender,amka,city,address,addr_no,"
                    + "telephone,status)"
                    + " VALUES ("
                    + "'" + user.getUsername() + "',"
                    + "'" + user.getEmail() + "',"
                    + "'" + user.getPassword() + "',"
                    + "'" + user.getFirstname() + "',"
                    + "'" + user.getLastname() + "',"
                    + "'" + user.getBirthdate() + "',"
                    + "'" + user.getGender() + "',"
                    + "'" + user.getAmka() + "',"
                    + "'" + user.getCity() + "',"
                    + "'" + user.getAddress() + "',"
                    + "'" + user.getAddr_no() + "',"
                    + "'" + user.getTelephone() + "',"
                    + "'" + user.getStatus() + "'"
                    + ")";*/
            System.out.println(updateQuery);
            stmt.executeUpdate(updateQuery);
            System.out.println("# The user was successfully added in the database.");
            stmt.close();

        } catch (SQLException ex) {
            Logger.getLogger(EditSimpleUserTable.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void InsertNewSimpleUserFL(SimpleUser user) throws ClassNotFoundException, SQLException {
        Connection con = DB_Connection.getConnection();

        Statement stmt = con.createStatement();


        String insertQuery = "INSERT INTO users (firstname,lastname)"
                + " VALUES ('" + user.getFirstname() + "','" + user.getLastname() + "');";

        System.out.println(insertQuery);
        stmt.executeUpdate(insertQuery);
        System.out.println("# The user was successfully added in the database.");
        stmt.close();
    }

}
