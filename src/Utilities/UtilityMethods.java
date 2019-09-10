/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import JDBC.DBConnection;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;

/**
 *
 * @author 39ds03d
 */
public class UtilityMethods
{
    public boolean displayLocaleError(String alertType, String title, String header, String message)
    {
        Optional<ButtonType> result;
        boolean selectionResult;
        
        if(alertType.equals("CONFIRMATION"))
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(message);
            result = alert.showAndWait();
            selectionResult = (result.get() == ButtonType.OK);
            
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.NONE);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(message);
            result = alert.showAndWait();
            selectionResult = (result.get() == ButtonType.OK);
        }
        return selectionResult;
    }
    
    public String closeProgram() throws SQLException, Exception
    {
        DBConnection.closeConnection();
        System.exit(0);
        return "";
    }
    
    public ResultSet runSqlQuery(String query) throws SQLException
    {
        Statement stmt = DBConnection.conn.createStatement();
        String sqlStatement = "select * from user";
        ResultSet result = stmt.executeQuery(sqlStatement);
        
        return result;
    }
    
    public void recordUserLogin(String logEntry) throws FileNotFoundException
    {
        try
        {
            Logger logger = Logger.getLogger("UserLog"); 
            FileHandler handler = new FileHandler("C:\\Users\\39ds03d\\Documents\\WGU\\C195\\C195_Proj\\src\\Utilities\\log.txt");
            logger.addHandler(handler);
            SimpleFormatter formatter = new SimpleFormatter();  
            handler.setFormatter(formatter);
            logger.info("\n" + logEntry); 
            
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
    }
    
    public String createTimeStamp()
    {
        Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
        return currentTimestamp.toString();
    }
}
