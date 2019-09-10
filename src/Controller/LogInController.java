/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.net.URL;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import Controller.*;
import JDBC.DBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author 39ds03d
 */
public class LogInController implements Initializable
{
    Locale userLocale = Locale.getDefault();
    boolean userLocaleEqualsEng = userLocale.toString().contains("en_US");
    
    @FXML
    private TextField userNameTxtFld;

    @FXML
    private TextField passwordTxtFld;

    @FXML
    private Label userNameLbl;

    @FXML
    private Label passwordLbl;

    @FXML
    private Button logInBtn;

    @FXML
    private Button cancelBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        
        if(!userLocale.toString().contains("en_US"))
        {
            userNameLbl.setText("BenutzerName");
            passwordLbl.setText("Benutzer Passwort");
            logInBtn.setText("Einloggin");
            cancelBtn.setText("Abbrechen");
        }
    }
    
    @FXML
    void onActionLogInBtn(ActionEvent event) throws SQLException, Exception
    {
        Statement stmt = DBConnection.conn.createStatement();
        String sqlStatement = "select * from user";
        ResultSet result = stmt.executeQuery(sqlStatement);

        while(result.next())
        {
            if(userNameTxtFld.getText().equals(result.getString("userName")) &&
                    passwordTxtFld.getText().equals(result.getString("password")))
            {
                System.out.println("username/passwords matched values in DB.");
                
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
            else
            {
                if(userLocaleEqualsEng)
                {
                    displayLocaleError("error", "", "Entry Error", "Either username/password was incorrect or null.");
                }
                else
                {
                    displayLocaleError("error", "", "Fehler beim Eintragung", "Entwieder die Bunutzerinformation war Falsch eingetragen order nichts in den Textfelden geschrieben.");
                }
            }
        }
    }
    
    public void displayLocaleError(String alertType, String title, String header, String message)
    {
        if(alertType.equals("CONFIRMATION"))
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(message);
            Optional<ButtonType> result = alert.showAndWait();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.NONE);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(message);
            Optional<ButtonType> result = alert.showAndWait();
        }
    }
    
    @FXML
    void onActionCancelBtn(ActionEvent event)
    {
        if(userLocaleEqualsEng)
        {
            displayLocaleError("CONFIRMATION", "Exit", "Exit Program", "Are you sure you want to close the program?");
        }
        else
        {
            displayLocaleError("error", "CONFIRMATION", "Program Schließen", "Möchten Sie wirklich das Program beended?");
        }
        System.exit(0);
    }
}