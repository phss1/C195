/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Utilities.UtilityMethods;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;

public class AddCustomerController implements Initializable
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
    private ComboBox<String> cityComboBx;
    @FXML
    private ComboBox<String> countryComboBx;
    @FXML
    private Button cancelBtn;
    @FXML
    private Button saveBtn;
    
    UtilityMethods utility = new UtilityMethods();

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try
        {
            ResultSet cityResults = utility.runSqlQuery("Select * from city");
            ObservableList<String> cityObsList = utility.prepareComboBxStrings(cityResults, "city");
            cityComboBx.setItems(cityObsList);
            cityComboBx.setValue(cityObsList.get(0));
            
            ResultSet countryResults = utility.runSqlQuery("Select * from country");
            ObservableList<String> countryObsList = utility.prepareComboBxStrings(countryResults, "country");
            countryComboBx.setItems(countryObsList);
            countryComboBx.setValue(countryObsList.get(0));
            
        }
        catch(SQLException ex)
        {
            Logger.getLogger(AddCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    void onActionCityComboBx(ActionEvent event)
    {

    }

    @FXML
    void onActionCountryComboBx(ActionEvent event)
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
