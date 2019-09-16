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
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.DragEvent;
import Model.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.ComboBox;

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
    private ComboBox<String> cityComboBx;

    @FXML
    private ComboBox<String> countryComboBx;
    @FXML
    private Button cancelBtn;
    @FXML
    private Button saveBtn;
    
    UtilityMethods utility = new UtilityMethods();
    ObservableList<String> cityObsListTemp;
    ObservableList<String> countryObsListTemp;

    public void initialize(URL url, ResourceBundle rb)
    {
        try
        {
            ResultSet cityResults = utility.runSqlQuery("Select * from city");
            ObservableList<String> cityObsList = utility.prepareComboBxStrings(cityResults, "city");
            cityComboBx.setItems(cityObsList);
            cityObsListTemp = cityObsList;
            //cityComboBx.setValue(cityObsList.get(0));
            
            ResultSet countryResults = utility.runSqlQuery("Select * from country");
            ObservableList<String> countryObsList = utility.prepareComboBxStrings(countryResults, "country");
            countryObsListTemp = countryObsList;
            countryComboBx.setItems(countryObsList);
            //countryComboBx.setValue(countryObsList.get(0));
        }
        catch(SQLException ex)
        {
            Logger.getLogger(AddCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sendInfo(Customer customer) throws SQLException
    {
        Address.getAllAddressesFiltered().clear();
        nameTxtLbl.setText(String.valueOf(customer.getCustomerName()));
        ResultSet results = utility.runSqlQuery("Select * from address where customerId = "
                                                    + customer.getAddressId() + ";");
        
        while(results.next())
        {
            int addressId = results.getInt("addressId");
            String address = results.getString("address");
            String address2 = results.getString("address2");
            int cityId = results.getInt("cityId");
            String postalCode = results.getString("postalCode");
            int countryId = results.getInt("countryId");
            String phone = results.getString("phone");
            
            Address tempAddress = new Address(addressId, address, address2, cityId, postalCode, phone);
            Address.setAllCustomersFiltered((ObservableList<Address>) tempAddress);
        }
        
        
        
        Address tempAddress = Address.getAllAddressesFiltered().get(0);
        addressTxtLbl.setText(tempAddress.getAddress());
        address2TxtLbl.setText(tempAddress.getAddress2());
        System.out.println("results data for cityId: " + results.getString("cityId"));
        int cityIdIndex = cityObsListTemp.indexOf(); //getInt("cityId")
        System.out.println("Int value of cityIdIndex: " + cityIdIndex);
        cityComboBx.setValue(cityObsListTemp.get(cityIdIndex)); //.getSelectionModel().select(results.getString("address2"));
        postalCodeTxtLbl.setText(tempAddress.getPostalCode());
        //countryComboBx.setText(String.valueOf(chosenProduct.getMax()));
        phoneTxtLbl.setText(tempAddress.getPhone());
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
