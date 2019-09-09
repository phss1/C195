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
import Controller.LogInController;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author 39ds03d
 */
public class LogInController implements Initializable
{

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
        Locale userLocale = Locale.getDefault();
        if(!userLocale.toString().contains("en_US"))
        {
            System.out.println("locale was en_US");
            userNameLbl.setText("BenutzerName");
            passwordLbl.setText("Benutzer Passwort");
            logInBtn.setText("Einloggin");
            cancelBtn.setText("Abbrechen");
        }
    }
    
    @FXML
    void onActionLogInBtn(ActionEvent event)
    {
        
    }
    
    @FXML
    void onActionCancelBtn(ActionEvent event)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirm Exit");
        alert.setContentText("Are you sure you want to exit the program?");
        Optional<ButtonType> result = alert.showAndWait();
        
        System.exit(0);
    }
}