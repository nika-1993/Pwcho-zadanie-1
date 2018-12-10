import java.sql.*;

public class MyApp{
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://mysql:3306/pwcho2018";
    
    static final String USER = "msliwczynska";
    static final String PASS = "password";
    
    static Connection conn = null;
    static Statement stmt = null;
    static String sql;
    
    
    public static void main(String[] args) throws InterruptedException {
        
        Boolean login = true;
        Boolean tableExists = false;
        
        while(login){
            try{
                Class.forName("com.mysql.jdbc.Driver");            
                System.out.println("Connecting to database...");
                conn = DriverManager.getConnection(DB_URL, USER, PASS);                
                login = false;
            } catch(SQLException se){
                Thread.sleep(10000);
            } catch(Exception e){
                Thread.sleep(10000);
            } finally{
                System.out.println("Connecting to database...");
            } 
        }
        
        try{
            System.out.println("Check if base exists");
            DatabaseMetaData md = conn.getMetaData();
            ResultSet rs = md.getTables(null, null, "Persons", null);
            while (rs.next()){
                System.out.println("Table exists");
                tableExists = true;
            }
            rs = null;
            if(!tableExists){
                System.out.println("Creating table");
                stmt = conn.createStatement();
                sql = "CREATE TABLE Persons (ID int, FirstName varchar(30), LastName varchar(30))";
                stmt.executeUpdate(sql);
                stmt = null;
            }
            
            stmt = conn.createStatement();
            System.out.println("Inserting data into table");
            sql = "INSERT INTO Persons(ID, FirstName, LastName) VALUES(1, 'Jan', 'Kowalski'), (2, 'Piotr', 'Nowak'), (3, 'Anna', 'Kot')";
            stmt.executeUpdate(sql);
            stmt = null;
            
            stmt = conn.createStatement();
            sql = "SELECT ID, FirstName, LastName FROM Persons";
            rs = stmt.executeQuery(sql);
        
            while(rs.next()){
                int id = rs.getInt("ID");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
            
                System.out.println("ID: " + id + " FirstName: " + firstName + " LastName: " + lastName);
            }
            
            rs.close();
            stmt.close();
            conn.close();
        } catch(Exception e){
            e.printStackTrace();
        } finally{
            try{
                if(stmt!=null)
                    stmt.close();
            } catch(Exception e){
                e.printStackTrace();
            }
            try{
                if(conn!=null)
                    conn.close();
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
}
