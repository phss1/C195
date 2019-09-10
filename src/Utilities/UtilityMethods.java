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
import java.util.Optional;
import java.util.Scanner;
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
    ObservableList<String> tempLogFileData = FXCollections.observableArrayList();
    
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
    
    public void recordUserLogin(String user, String timeStamp) throws FileNotFoundException
    {
        try
        {
            File logfile = new File("C:\\Users\\39ds03d\\Documents\\WGU\\C195\\C195_Proj\\src\\Utilities\\log.txt");
            Scanner inputFile = new Scanner(logfile);
            
            while(inputFile.hasNext())
            {
                tempLogFileData.add("\nUser **" + user + "** logged into program at: " + timeStamp);
            }
            //
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
    }
}
