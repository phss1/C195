/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195_proj;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.SQLException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import JDBC.DBConnection;
import Model.Customer;
import Utilities.UtilityMethods;
import java.sql.ResultSet;

public class C195_Proj extends Application
{
    UtilityMethods utility = new UtilityMethods();
    
    @Override
    public void start(Stage stage) throws Exception
    {
        //setting up data for customers in address main menu table
        try
        {
            ResultSet result = utility.runSqlQuery("Select customer.customerId, customer.customerName, address.addressId, "
                    + "address.address from customer, address " +
                    "where customer.addressId = address.addressId;");
            
            while(result.next())
            {
                int customerId = result.getInt("customerId");
                String customerName = result.getString("customerName");
                String address = result.getString("address");
                int addressId = result.getInt("addressId");
                
                Customer customer = new Customer(customerId, customerName, addressId, address);
                Customer.addCustomer(customer);
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        
        Parent root = FXMLLoader.load(getClass().getResource("/View/LogIn.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException, Exception
    {
        DBConnection.makeConnection();
        launch(args);
    }
}
