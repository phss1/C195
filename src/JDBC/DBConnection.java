/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBC;

import Utilities.*;
import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author 39ds03d
 */
public class DBConnection
{
    private static final String databaseName = "U04PCG";
    private static final String db_Url = "jdbc:mysql://52.206.157.109/" + databaseName;
    private static final String userName = "U04PCG";
    private static final String userPassword = "53688304534";
    private static final String driver = "com.mysql.jdbc.Driver";
    public static Connection conn;
    
    public static void makeConnection() throws ClassNotFoundException, SQLException, Exception
    {
        Class.forName(driver);
        conn = (Connection) DriverManager.getConnection(db_Url, userName, userPassword);
        
        System.out.println("DB Connection successfull");
    }
    
    public static void closeConnection() throws ClassNotFoundException, SQLException, Exception
    {
        conn.close();
        System.out.println(" DB Connection closed");
    }
}
