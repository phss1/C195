/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import JDBC.DBConnection;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import Model.*;
import java.sql.Date;
import java.time.YearMonth;

/**
 *
 * @author 39ds03d
 */
public class UtilityMethods
{
    Stage stage;
    Parent scene;
    private static int currentUserId;
    private static int selectedRowIndex;
    
    public int getDaysInMonth(int year, int month)
    {
        YearMonth yearMonthObject = YearMonth.of(year, month);
        int daysInMonth = yearMonthObject.lengthOfMonth(); 
        return daysInMonth;
    }
    
    public void deleteCustomerAppointments(Customer customer) throws SQLException
    {
        int numberOfCustAppt = customer.getAllCustomerAppointments().size();
        String deleteAppointmentQuery = "delete from appointment where customerId = " + customer.getCustomerId() + ";";
        runUpdateSqlQuery(deleteAppointmentQuery);
    }
    
    public void setApptTableViewItems(Customer customerData)
    {
        String sqlQuery = "select * from appointment where customerId = " + customerData.getCustomerId() + ";";

        try
        {
            ResultSet results = runSqlQuery(sqlQuery);
            addNewAppointmentToCustomer(customerData, results);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    
    public void addNewAppointmentToCustomer(Customer customer, ResultSet results) throws SQLException
    {
        while(results.next())
        {
            int customerId = results.getInt("customerId");
            int appointmentId = results.getInt("appointmentId");
            int userId = results.getInt("userId");
            String title = results.getString("title");
            String description = results.getString("description");
            String location = results.getString("location");
            String contact = results.getString("contact");
            String type = results.getString("type");
            String url = results.getString("url");
            Date start = results.getDate("start");
            Date end = results.getDate("end");
            
            Appointment appointment = new Appointment(appointmentId, customerId, userId, title, description, location,
                                        contact, type, url, start, end);
            
            customer.addCustomerAppointment(appointment);
        }
    }
    
    public String buildSqlQueryEnding() throws SQLException
    {
        String userName = getCurLoggedInUserName();
        String sqlQueryEnding = "now(), \"" +  userName + "\", now(), \"" + userName + "\");";
        
        return sqlQueryEnding;
    }
    
    public int getIdFromCityName(String cityName) throws SQLException
    {
        ResultSet cityNameResults = runSqlQuery(("select cityId from city where city = \"" + cityName
                                                    + "\";").toString());
        int cityId = 0;
        while(cityNameResults.next())
        {
            cityId = cityNameResults.getInt("cityId");
        }
        
        return cityId;
    }
    
    public static int getCurrentUserId()
    {
        return currentUserId;
    }
    
    public String getCurLoggedInUserName() throws SQLException
    {
        String sqlCurrentUsrQuery = ("select userName from user where userId = "
                                        + UtilityMethods.getCurrentUserId() + ";").toString();
        ResultSet currentUserData = runSqlQuery(sqlCurrentUsrQuery);
        String currentUser = null;
        
        while(currentUserData.next())
        {
            currentUser = currentUserData.getString("userName");
        }
        
        return currentUser;
    }

    public void setCurrentUserId(int currentUserId)
    {
        UtilityMethods.currentUserId = currentUserId;
    }
    
    public int createNewId(String tableName) throws SQLException
    {
        ResultSet result = runSqlQuery("Select * from " + tableName + "Id");
        
        return 1;
    }
    
    public int getSqlTableRowCount(ResultSet resultSet) throws SQLException
    {
        int itemTotalCount = 1;
        while(resultSet.next())
        {
            itemTotalCount++;
        }
        
        return itemTotalCount;
    }
    
    public ObservableList<String> prepareComboBxStrings(ResultSet results, String columnName) throws SQLException
    {
        ObservableList<String> tempObsList = FXCollections.observableArrayList();
        while(results.next())
        {
            String item = results.getString(columnName);
            tempObsList.add(item);
        }
        
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
    
    public void runUpdateSqlQuery(String query) throws SQLException
    {
        Statement stmt = DBConnection.conn.createStatement();
        stmt.executeUpdate(query);
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
    
    public int getSelectedRowIndex()
    {
        return selectedRowIndex;
    }

    public void setSelectedRowIndex(int selectedRowIndex)
    {
        this.selectedRowIndex = selectedRowIndex;
    }
}
