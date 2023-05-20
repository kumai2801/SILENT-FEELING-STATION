package connectDB;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectDB {
    public static  Connection connectionDB(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/...", "root", "");
            return connect;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
