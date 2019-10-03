/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Appointment;
import Utilities.UtilityMethods;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.PasswordField;

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
    private PasswordField passwordTxtFld;

    @FXML
    private Button newUserBtn;

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
    void onActionNewUserBtn(ActionEvent event) throws IOException
    {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/CreateNewUser.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
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
                String currentDateTime = utility.createTimeStamp();
                utility.recordUserLogin(currentDateTime + " : User ***" + userNameTxtFld.getText() + "*** logged " +
                                            "into the application.");
                
                String sqlQuery = "update user set lastUpdate = now() where userId = " + result.getInt("userId");
                utility.setCurrentUserId(result.getInt("userId"));
                
                utility.changeGuiScreen(event, "MainMenu");
            }
        }
        
        try
        {
            if(utility.getCurLoggedInUserName().isEmpty())
            {

            }
        }
        catch(NullPointerException e)
        {
            if(userLocaleEqualsEng)
            {
                utility.displayLocaleError("Error", "", "Entry Error", "Either username/password was incorrect or null.");
            }
            else
            {
                utility.displayLocaleError("Fehler", "", "Fehler beim Eintritt", "Entwieder die Bunutzerinformation war Falsch eingetragen order nichts in den Textfelden geschrieben.");
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