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
    private TextField addressIdTxtLbl;
    @FXML
    private ComboBox<String> countryComboBx;
    @FXML
    private TextField customerIdTxtFld;
    @FXML
    private Button cancelBtn;
    @FXML
    private Button saveBtn;
    
    UtilityMethods utility = new UtilityMethods();
    ObservableList<String> cityObsList;
    ObservableList<String> countryObsList;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try
        {
            ResultSet cityResults = utility.runSqlQuery("Select * from city");
            cityObsList = utility.prepareComboBxStrings(cityResults, "city");
            cityComboBx.setItems(cityObsList);
            cityComboBx.setValue(cityObsList.get(0));
            
            ResultSet countryResults = utility.runSqlQuery("Select * from country");
            countryObsList = utility.prepareComboBxStrings(countryResults, "country");
            countryComboBx.setItems(countryObsList);
            countryComboBx.setValue(countryObsList.get(0));
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
            String phone = results.getString("phone");
            
            ObservableList<Address> tempAddresses = FXCollections.observableArrayList();
            Address tempAddress = new Address(addressId, address, address2, cityId, postalCode, phone);
            tempAddresses.add(tempAddress);
            Address.getAllAddressesFiltered().add(tempAddress);
        }
        
        Address tempAddress = Address.getAllAddressesFiltered().get(0);
        int cityId = tempAddress.getCityId();
        
        int tempCityId;
        int tempCountryId;
        switch(cityId)
        {
            case 1 : tempCityId = 0; tempCountryId = 0; setComboBoxValues(tempCityId, tempCountryId); break;
            case 2 : tempCityId = 1; tempCountryId = 0; setComboBoxValues(tempCityId, tempCountryId); break;
            case 3 : tempCityId = 2; tempCountryId = 1; setComboBoxValues(tempCityId, tempCountryId); break;
            case 4 : tempCityId = 3; tempCountryId = 1; setComboBoxValues(tempCityId, tempCountryId); break;
        }
        
        customerIdTxtFld.setText(String.valueOf(customer.getCustomerId()));
        addressIdTxtLbl.setText(String.valueOf(tempAddress.getAddressId()));
        addressTxtLbl.setText(tempAddress.getAddress());
        address2TxtLbl.setText(tempAddress.getAddress2());
        postalCodeTxtLbl.setText(tempAddress.getPostalCode());
        phoneTxtLbl.setText(tempAddress.getPhone());
    }
    
    public void setComboBoxValues(int cityId, int countryId)
    {
        cityComboBx.setValue(cityObsList.get(cityId));
        countryComboBx.setValue(countryObsList.get(countryId));
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
    private void onActionSaveBtn(ActionEvent event) throws SQLException, IOException
    {
        try
        {
            String customerName = nameTxtLbl.getText();
            String address = addressTxtLbl.getText();
            String address2 = address2TxtLbl.getText();
            String postalCode = postalCodeTxtLbl.getText();
            String phone = phoneTxtLbl.getText();
        
            if(!customerName.isEmpty() && !address.isEmpty() && !address2.isEmpty() && !postalCode.isEmpty()
                    && !phone.isEmpty())
            {
                int addressId = Integer.valueOf(addressIdTxtLbl.getText());
                int cityId = utility.getIdFromCityName(cityComboBx.getSelectionModel().getSelectedItem());

                String sqlAddressUpdateQuery = "update address set address = \"" + address
                        + "\", address2 = \"" + address2 + "\", cityId = \"" + cityId + "\", postalCode = \"" + postalCode 
                        + "\", phone = \"" + phone + "\", lastUpdate = now(), lastUpdateBy = \""
                        + utility.getCurLoggedInUserName().toString() + "\" where addressId = " + addressId + ";";
                utility.runUpdateSqlQuery(sqlAddressUpdateQuery);

                int customerId = Integer.valueOf(customerIdTxtFld.getText());
                String tempAddress = address + " " + address2 + " " + cityComboBx.getSelectionModel().getSelectedItem()
                        + " " + countryComboBx.getSelectionModel().getSelectedItem() + " " + postalCode;
                String sqlCustUpdateQuery = "update customer set customerName = \"" + customerName + 
                        "\" where customerId = " + customerId + ";";

                Customer tempCustomer = new Customer(customerId, customerName, addressId, tempAddress);
                Customer.modifyCustomer(tempCustomer, customerId);

                System.out.println(sqlCustUpdateQuery);
                utility.runUpdateSqlQuery(sqlCustUpdateQuery);

                utility.changeGuiScreen(event, "MainMenu");
            }
            else
            {
                utility.displayLocaleError("INFORMATION", "Empty Field", "Field Empty",
                        "Please make sure you don't leave a field with no value entered.");
            }
        }
        catch(Exception e)
        {
            
        }
    }
}
