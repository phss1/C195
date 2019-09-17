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
import javafx.collections.FXCollections;
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
    private TextField customerIdTxtFld;
    @FXML
    private Button cancelBtn;
    @FXML
    private Button saveBtn;
    
    UtilityMethods utility = new UtilityMethods();

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        /*try
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
        }*/
    }
    
    public void sendInfo(Customer customer) throws SQLException
    {
        Address.getAllAddressesFiltered().clear();
        nameTxtLbl.setText(String.valueOf(customer.getCustomerName()));
        System.out.println(customer.getAddressId());
        ResultSet results = utility.runSqlQuery("Select * from address where addressId = "
                                                    + customer.getAddressId() + ";");
        
        while(results.next())
        {
            Address.getAllAddressesFiltered().clear();
            int addressId = results.getInt("addressId");
            String address = results.getString("address");
            String address2 = results.getString("address2");
            int cityId = results.getInt("cityId");
            String postalCode = results.getString("postalCode");
            //int countryId = results.getInt("countryId");
            String phone = results.getString("phone");
            
            ObservableList<Address> tempAddresses = FXCollections.observableArrayList();
            Address tempAddress = new Address(addressId, address, address2, cityId, postalCode, phone);
            tempAddresses.add(tempAddress);
            Address.getAllAddressesFiltered().add(tempAddress);
        }
        
        Address tempAddress = Address.getAllAddressesFiltered().get(0);
        int cityId = tempAddress.getCityId();
        
        String cityName;
        String countryName;
        switch(cityId)
        {
            case 1 : cityName = "Raleigh"; countryName = "USA";
            case 2 : cityName = "Durham"; countryName = "USA";
            case 3 : cityName = "Hamburg"; countryName = "Germany";
            case 4 : cityName = "Saarbrücken"; countryName = "Germany";
            cityComboBx.setValue(cityName);
            countryComboBx.setValue(countryName);
            break;
        }
        
        customerIdTxtFld.setText(String.valueOf(customer.getCustomerId()));
        addressTxtLbl.setText(tempAddress.getAddress());
        address2TxtLbl.setText(tempAddress.getAddress2());
        postalCodeTxtLbl.setText(tempAddress.getPostalCode());
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
