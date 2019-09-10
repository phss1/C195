/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195_proj;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import JDBC.DBConnection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import JDBC.DBConnection;
import java.sql.ResultSet;
import java.util.Scanner;

public class C195_Proj extends Application
{
    
    @Override
    public void start(Stage stage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("/View/LogIn.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException, Exception
    {
        DBConnection.makeConnection();
        launch(args);
        
        /*try
        {
            
            
            Scanner keyboard = new Scanner(System.in);
            int employeeId;
            String employeeName, department, hireDate;
            
            System.out.print("Enter employee name: ");
            employeeName = keyboard.nextLine();
            
            
            Statement stmt = DBConnection.conn.createStatement();
            String sqlStatement = "select * from employee_tbl";
            ResultSet result = stmt.executeQuery(sqlStatement);
            
            while(result.next())
            {
                System.out.print(result.getInt("EmployeeID") + ", ");
                System.out.print(result.getString("EmployeeName") + ", ");
                System.out.print(result.getString("Department") + ", ");
                System.out.print(result.getDate("HireDate"));
                System.out.print(result.getTime("HireDate"));
                System.out.println();
            }
        }
        catch (Exception ex)
        {
            System.out.println("Error " + ex.getMessage());
        }*/
        
        
    }
}
