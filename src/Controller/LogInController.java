/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Utilities.UtilityMethods;
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
import Model.*;
import java.io.IOException;

/**
 * FXML Controller class
 *
 * @author 39ds03d
 */
public class LogInController implements Initializable
{
    Locale userLocale = Locale.getDefault();
    boolean userLocaleEqualsEng = userLocale.toString().contains("en_US");
    UtilityMethods utility = new UtilityMethods();
    Stage stage;
    Parent scene;
    
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
    void onActionLogInBtn(ActionEvent event) throws SQLException, IOException
    {
        ResultSet result = utility.runSqlQuery("select * from user");

        while(result.next())
        {
            if(userNameTxtFld.getText().equals(result.getString("userName")) &&
                    passwordTxtFld.getText().equals(result.getString("password")))
            {                
                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
            else
            {
                if(userLocaleEqualsEng)
                {
                    utility.displayLocaleError("error", "", "Entry Error", "Either username/password was incorrect or null.");
                }
                else
                {
                    utility.displayLocaleError("error", "", "Fehler beim Eintragung", "Entwieder die Bunutzerinformation war Falsch eingetragen order nichts in den Textfelden geschrieben.");
                }
            }
        }
    }
    
    @FXML
    void onActionCancelBtn(ActionEvent event) throws Exception
    {
        if(userLocaleEqualsEng)
        {
            boolean result = utility.displayLocaleError("CONFIRMATION", "Exit", "Exit Program", "Are you sure you want to close the program?");
            String test = result ? utility.closeProgram() : "";
        }
        else
        {
            boolean result = utility.displayLocaleError("error", "CONFIRMATION", "Program Schließen", "Möchten Sie wirklich das Program beended?");
            String test = result ? utility.closeProgram() : "";
        }
        
    }
}