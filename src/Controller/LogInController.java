/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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
    private Button logInBtn;
    @FXML
    private Button CancelBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        Locale userLocale = Locale.getDefault();
        
        
    }
    
    @FXML
    void onActionCancelBtn(ActionEvent event)
    {
        Locale userLocale = Locale.getDefault();
        
        if(userLocale.toString().contains("en_US"))
        {
            System.out.println("Value was a match for en_us");
        }
        
        //System.out.println();
        
        //System.out.println(userLocale);
        //System.out.println(userLanguage);
    }
}