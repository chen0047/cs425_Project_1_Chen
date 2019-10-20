import java.sql.ResultSetMetaData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;


public class Registration_Database {
   
    private Connection getConnection() {
        Connection conn = null;
        try {
            Context envContext = new InitialContext();
            Context initContext  = (Context)envContext.lookup("java:/comp/env");
            DataSource ds = (DataSource)initContext.lookup("jdbc/db_pool");
            conn = ds.getConnection();
            
            
            
            
            
            
            
        }        
        catch (Exception e) { e.printStackTrace(); }
        return conn;
    }
}

