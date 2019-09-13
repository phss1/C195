/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Utilities.UtilityMethods;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;

/**
 * FXML Controller class
 *
 * @author 39ds03d
 */
public class ModifyCustomerController implements Initializable
{

    @FXML
    private TextField nameTxtLbl;
    @FXML
    private TextField addressTxtLbl;
    @FXML
    private TextField address2TxtLbl;
    @FXML
    private TextField postalCodeTxtLbl;
    @FXML
    private TextField phoneTxtLbl;
    @FXML
    private ChoiceBox<?> countryChoiceBx;
    @FXML
    private ChoiceBox<?> cityChoiceBx;
    @FXML
    private Button cancelBtn;
    @FXML
    private Button saveBtn;
    
    UtilityMethods utility = new UtilityMethods();

    public void initialize(URL url, ResourceBundle rb)
    {
        
    }    

    @FXML
    private void onDragDroppedCountryChBx(DragEvent event)
    {
    }

    @FXML
    private void onDragDroppedCityChBx(DragEvent event)
    {
    }

    @FXML
    private void onActionCancelBtn(ActionEvent event) throws IOException
    {
        boolean result = utility.displayLocaleError("CONFIRMATION", "Cancel", "Close Window", "Are you sure you want to exit adding a new customer?");
        if(result)
        {
            utility.changeGuiScreen(event, "MainMenu");
        }
    }

    @FXML
    private void onActionSaveBtn(ActionEvent event)
    {
    }
    
}
