package sample;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLiteconnection {


    public static Connection Connector() {
        Connection con = null;
       try {
           Class.forName("org.sqlite.JDBC");
          con = DriverManager.getConnection("jdbc:sqlite:Registracija.sqlite");
        } catch(SQLException | ClassNotFoundException ex){
           Logger.getLogger(Controller.class.getName()).log(Level.SEVERE,null,ex);

        }
        return con;
    }
}




