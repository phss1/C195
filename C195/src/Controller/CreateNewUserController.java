/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Utilities.UtilityMethods;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class CreateNewUserController implements Initializable {

    @FXML
    private Button createBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private PasswordField password2PwdFld;
    @FXML
    private PasswordField password1PwdFld;
    @FXML
    private TextField newUserNameTxtFld;
    @FXML
    private Label newUserLabel;
    @FXML
    private Label password1Lbl;
    @FXML
    private Label userNameLbl;
    @FXML
    private Label password2Lbl;

    UtilityMethods utility = new UtilityMethods();
    Locale userLocale = Locale.getDefault();
    boolean userLocaleEqualsEng = userLocale.toString().contains("en_US");

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        if(!userLocale.toString().contains("en_US"))
        {
            newUserLabel.setText("Neues Benutzer Erstellen");
            userNameLbl.setText("Neuer BenutzerName");
            password1Lbl.setText("Neues Passwort");
            password2Lbl.setText("Passwort Bestaetigen");
            createBtn.setText("Erstellen");
            cancelBtn.setText("Abbrechen");
        }
    }    

    @FXML
    private void onActionCreateBtn(ActionEvent event) throws IOException, SQLException
    {
        String userName = newUserNameTxtFld.getText();
        String password1 = password1PwdFld.getText();
        String password2 = password2PwdFld.getText();
        
        if(!userName.isEmpty() && !password1.isEmpty() && !password2.isEmpty() && password1.matches(password2))
        {
            String newUserQuery = "insert into user(userName, password, active, createDate, createdBy, lastUpdate, "
                    + "lastUpdateBy) values(\"" + userName + "\", \"" + password1 + "\", 1, now(), \"createUserScreen\", now(),"
                    + " \"createUserScreen\")";
            
            System.out.println(newUserQuery);
            utility.runUpdateSqlQuery(newUserQuery);
            utility.changeGuiScreen(event, "LogIn");
        }
        else
        {
            if(userLocaleEqualsEng)
                {
                    utility.displayLocaleError("Error", "", "Entry Error", "Either the the password did not match or you left a field blank.");
                }
                else
                {
                    utility.displayLocaleError("Fehler", "", "Fehler beim Eintragung", "Entwieder die Passworts stimmen nicht oder Sie haben ein Feld leer gelassen.");
                }
        }
    }

    @FXML
    private void onActionCancelBtn(ActionEvent event) throws IOException
    {
        boolean result = utility.displayLocaleError("CONFIRMATION", "Cancel", "Close Window", "Are you sure you want to exit adding a new appointment?");
        if(result)
        {
            utility.changeGuiScreen(event, "LogIn");
        }
    }
    
}
