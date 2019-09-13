/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import JDBC.DBConnection;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author 39ds03d
 */
public class UtilityMethods
{
    Stage stage;
    Parent scene;
    
    public int createNewId(String tableName) throws SQLException
    {
        ResultSet result = runSqlQuery("Select * from " + tableName + "Id");
        
        return 1;
    }
    
    public ObservableList<String> prepareComboBxStrings(ResultSet results, String columnName) throws SQLException
    {
        //ListView<String> listView = new ListView<String>();
        ObservableList<String> tempObsList = FXCollections.observableArrayList();
        while(results.next())
        {
            String item = results.getString(columnName);
            tempObsList.add(item);
        }
        //listView.setItems(tempObsList);
        
        //return listView;
        return tempObsList;
    }
    
    public void changeGuiScreen(ActionEvent event, String viewName) throws IOException
    {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/" + viewName + ".fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
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
        ResultSet result = stmt.executeQuery(query);
        
        return result;
    }
    
    public void recordUserLogin(String logEntry) throws FileNotFoundException, IOException
    {
        String fileName = ".\\src\\Utilities\\log.txt", item;
        FileWriter fWriter = new FileWriter(fileName, true);
        PrintWriter outputFile = new PrintWriter(fWriter);
        
        for(int i=0; i<1; i++)
        {
            item = logEntry;
            outputFile.println(item);
        }
        outputFile.close();
    }
    
    public String createTimeStamp()
    {
        Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
        return currentTimestamp.toString();
    }
}
