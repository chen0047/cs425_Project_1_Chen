import java.io.IOException;
import java.sql.ResultSetMetaData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.sql.DataSource;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;


public class Registration_Database {
   
    public Connection getConnection() {
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
    
    public String getAttendeeList(int sessionid) {
        
        String results = "";
        
        Connection conn = getConnection();
        PreparedStatement pstatement = null;
        ResultSet resultset = null;
        
        String query = "";
        
        boolean hasresult;
        
        try{
            
            query = "SELECT * FROM registrations WHERE sessionid=?";
            
            pstatement = conn.prepareStatement(query);
            pstatement.setInt(1, sessionid);
            
            hasresult = pstatement.execute();

            if(hasresult) {
                resultset = pstatement.getResultSet();
                results = getResultSetTable(resultset);
                resultset.close();
            }
            
            pstatement.close();            
            
        }
        catch (Exception e) { e.printStackTrace(); }
        
        return results;
    }

        
    
    public String registerAttendee(String firstname, String lastname, String displayname, int sessionid) {
        
        String results = "";
        
        int key = 0;
        
        try {
            
            Connection conn = getConnection();
            
            String query = "INSERT INTO registrations (firstname, lastname, displayname, sessionid) VALUES (?,?,?,?)";
            PreparedStatement pUpdate = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            
            pUpdate.setString(1, firstname);
            pUpdate.setString(2, lastname);
            pUpdate.setString(3, displayname);
            pUpdate.setInt(4, sessionid);
            
            int updateCount = pUpdate.executeUpdate();
            
            if(updateCount > 0) {
                
                ResultSet resultset = pUpdate.getGeneratedKeys();
                
                if(resultset.next()) {
                    
                    key = resultset.getInt(1);
                    
                    JSONObject json = new JSONObject();
                    json.put("displayname", displayname);
                    json.put("code", ("R" + String.format("%06d", key)));
                    results = JSONValue.toJSONString(json);
                    
                }
                
                resultset.close();
                
            }
            
            pUpdate.close();
            
        }
        catch (Exception e) { e.printStackTrace(); }        
        
        return results;
        
    }
    
    public String getResultSetTable(ResultSet resultset) {
        
       ResultSetMetaData metadata = null;
       
       String table = "";
       String tableheading;
       String tablerow;
       
       String key;
       String value;
       
       try{
           
           System.out.println("getResultSetTable: Getting Query Result...");
           
           metadata = resultset.getMetaData();
           int numberOfColumns = metadata.getColumnCount();
           
           table += "<table border=\"1\">";
           tableheading = "<tr>";
           
           System.out.println("Number of the columns:" + numberOfColumns);
           
           for(int i = 1; i <= numberOfColumns; i++){
               
               key = metadata.getColumnLabel(i);
               
               tableheading +="<th>" + key + "</th>";
               
           }
           
           tableheading += "</tr>";
           
           table += tableheading;
           
           while(resultset.next()){
               
               tablerow = "<tr>";
               
               for(int i = 1 ; i <= numberOfColumns; i++){
                   
                   value = resultset.getString(i);
                   
                   if(resultset.wasNull()){
                       tablerow += "<td></td>";
                   }
                   
                   else{
                       tablerow += "<td>" + value + "</td>";
                   }
               }
               
               tablerow += "</tr>";
               
               table += tablerow;
                   
           }
           
           table += "</table><br />";
           
           
       }catch (Exception e){ e.printStackTrace(); }
       
       return table;
       
    }    
    
    public static String _getDataAsJSON(String displayName){
       
        String display = " ";
        PreparedStatement pstatement = null;
        ResultSet resultset = null;
        
        String query;
        boolean hasresult;
        
        
        try{
          
            
            JSONObject newFullName = new JSONObject();
            JSONObject displayCode = new JSONObject();
            
            Registration_Database db = new Registration_Database();
            Connection conn = db.getConnection();
            
            if(conn.isValid(0)){
                System.err.println("Connected!");
            }
            if(displayName != null){
                query = "SELECT *FROM registrations WHERE displayname=? AND id=?";
                
                pstatement = conn.prepareStatement(query);
                pstatement.setString(0, displayName);
                pstatement.setString(1, "id");
            }
            
            else{
                query = "SELECT * FROM registrations";
                
                pstatement = conn.prepareStatement(query);
                
            }
            
            hasresult = pstatement.execute();
            if(hasresult){
                resultset = pstatement.getResultSet();
                
            }
            
            
            
            
            
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return null;
       
    }
    
}

