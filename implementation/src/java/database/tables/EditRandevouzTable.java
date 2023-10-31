package database.tables;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import mainClasses.Randevouz;
import database.DB_Connection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EditRandevouzTable {

    public void addRandevouzFromJSON(String json) throws ClassNotFoundException {
        Randevouz r = jsonToRandevouz(json);
        createNewRandevouz(r);
    }

    public Randevouz jsonToRandevouz(String json) {
        Gson gson = new Gson();
        Randevouz r = gson.fromJson(json, Randevouz.class);
        return r;
    }

    public String randevouzToJSON(Randevouz r) {
        Gson gson = new Gson();

        String json = gson.toJson(r, Randevouz.class);
        return json;
    }

    public int countUserTimesWhoVaccinated(String id) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs;
        int count = 0;

        rs = stmt.executeQuery("SELECT COUNT(status) AS total FROM randevouz WHERE user_id='" + id + "' AND status = 'done' ");
        if (rs.next()) {
            count = rs.getInt("total");
        }

        return count;
    }

    public void deleteRandevouz(int randevouzID) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String deleteQuery = "DELETE FROM randevouz WHERE randevouz_id='" + randevouzID + "'";
        stmt.executeUpdate(deleteQuery);
        stmt.close();
        con.close();
    }

    public void updateRandevouz(int randevouzID, int userID, String status) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String updateQuery = "UPDATE randevouz SET user_id='" + userID + "',status='" + status + "',date_time=date_time WHERE randevouz_id = '" + randevouzID + "'";
        stmt.executeUpdate(updateQuery);
        stmt.close();
        con.close();
    }

    public int countRandevouz(int id) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        int count = -1;
        ResultSet rs = stmt.executeQuery("SELECT COUNT(user_id) AS total FROM randevouz WHERE user_id='" + id + "' AND status='" + "done" + "' OR user_id ='" + id + "' AND status='" + "selected" + "'");
        if (rs.next()) {
            count = rs.getInt("total");
        }
        return count;
    }

    public Randevouz printRandevouz(int id, int flag) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            switch (flag) {
                case 1:
                    rs = stmt.executeQuery("SELECT * FROM randevouz WHERE hospital_id= '" + id + "'");
                    break;
                case 2:
                    rs = stmt.executeQuery("SELECT * FROM randevouz WHERE user_id= '" + id + "' AND status='" + "done" + "'");
                    break;
                case 3:
                    rs = stmt.executeQuery("SELECT * FROM randevouz WHERE user_id = '" + id + "' AND status='" + "selected" + "'");
                    break;
                default:
                    rs = stmt.executeQuery("SELECT * FROM randevouz");
                    break;
            }

            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Randevouz doc = gson.fromJson(json, Randevouz.class);
            return doc;
        } catch (JsonSyntaxException | SQLException e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<Randevouz> databaseToRandevouz(int id, String date, int flag) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Randevouz> randevouz = new ArrayList<>();
        ResultSet rs;
        try {

            switch (flag) {
                case 1:
                    rs = stmt.executeQuery("SELECT * FROM randevouz WHERE hospital_id= '" + id + "' AND status='" + "free" + "' AND CAST(date_time AS DATE)='" + date + "'");
                    break;
                case 2:
                    rs = stmt.executeQuery("SELECT * FROM randevouz WHERE status = '" + "done" + "' AND user_id='" + id + "'");
                    break;
                case 3:
                    rs = stmt.executeQuery("SELECT * FROM randevouz WHERE user_id = '" + id + "' AND status='" + "selected" + "'");
                    break;
                default:
                    rs = stmt.executeQuery("SELECT * FROM randevouz");
                    break;
            }

            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                Randevouz hos = gson.fromJson(json, Randevouz.class);
                randevouz.add(hos);
            }
            return randevouz;

        } catch (JsonSyntaxException | SQLException e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<Randevouz> databaseToRandevouz(String doctor_id) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Randevouz> randevouz = new ArrayList<>();
        ResultSet rs;
        try {

            rs = stmt.executeQuery("SELECT * FROM randevouz WHERE doctor_id= '" + doctor_id + "' AND status='selected'");

            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                Randevouz r = new Randevouz();
                r = gson.fromJson(json, Randevouz.class);
                randevouz.add(r);
            }
            System.out.println(randevouz.size());
            return randevouz;

        } catch (JsonSyntaxException | SQLException e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public Randevouz databaseToRandevouz_search(int id) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM randevouz WHERE randevouz_id= '" + id + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Randevouz bt = gson.fromJson(json, Randevouz.class);
            return bt;
        } catch (JsonSyntaxException | SQLException e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public int cancelRandevouz(int randevouzID, String status, int id) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs = stmt.executeQuery("SELECT * FROM randevouz WHERE randevouz_id= '" + randevouzID + "'AND user_id = '" + id + "' AND status = '" + "selected" + "'");
        if (!rs.isBeforeFirst()) {
            return -1;
        } else {
            //String updateQuery = "UPDATE randevouz SET status='" + status + "' WHERE randevouz_id = '" + randevouzID + "'AND user_id = '" + id + "'";
            String updateQuery = "UPDATE randevouz SET status='" + status + "' WHERE status = '" + "selected" + "'AND user_id = '" + id + "'AND randevouz_id >= '" + randevouzID + "'";
            stmt.executeUpdate(updateQuery);
            //stmt.executeUpdate(updateQuery2);
            stmt.close();
            con.close();
            return 0;
        }
    }

    public void createRandevouzTable() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String sql = "CREATE TABLE randevouz "
                + "(randevouz_id INTEGER not NULL AUTO_INCREMENT, "
                + " doctor_id INTEGER not NULL, "
                + " user_id INTEGER not NULL, "
                + " hospital_id INTEGER not NULL, "
                + " date_time TIMESTAMP not NULL, "
                + " status VARCHAR(15) not null,"
                + " vaccine VARCHAR(30) not null,"
                + "FOREIGN KEY (hospital_id) REFERENCES hospital(hospital_id), "
                + " PRIMARY KEY ( randevouz_id  ))";
        stmt.execute(sql);
        stmt.close();
        con.close();

    }

    public void createNewRandevouz(Randevouz rand) throws ClassNotFoundException {
        try {
            Connection con = DB_Connection.getConnection();

            Statement stmt = con.createStatement();

            String insertQuery = "INSERT INTO "
                    + " randevouz (doctor_id,user_id,hospital_id,date_time,status,vaccine)"
                    + " VALUES ("
                    + "'" + rand.getDoctor_id() + "',"
                    + "'" + rand.getUser_id() + "',"
                    + "'" + rand.getHospital_id() + "',"
                    + "'" + rand.getDate_time() + "',"
                    + "'" + rand.getStatus() + "',"
                    + "'" + rand.getVaccine() + "'"
                    + ")";

            stmt.executeUpdate(insertQuery);
            System.out.println("# The randevouz was successfully added in the database.");
            stmt.close();

        } catch (SQLException ex) {
            Logger.getLogger(EditRandevouzTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
