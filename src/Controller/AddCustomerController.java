/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.*;
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
    private void onActionCancelBtn(ActionEvent event) throws IOException
    {
        boolean result = utility.displayLocaleError("CONFIRMATION", "Cancel", "Close Window", "Are you sure you want to exit adding a new customer?");
        if(result)
        {
            utility.changeGuiScreen(event, "MainMenu");
        }
    }

    @FXML
    private void onActionSaveBtn(ActionEvent event) throws SQLException
    {
        ResultSet addressResults = utility.runSqlQuery("select * from address");
        int newAddId =  utility.getSqlTableRowCount(addressResults);
        String address = addressTxtLbl.getText();
        String address2 = address2TxtLbl.getText();
        int cityId = utility.getIdFromCityName(cityComboBx.getSelectionModel().getSelectedItem());
        String postalCode = postalCodeTxtLbl.getText();
        String phone = phoneTxtLbl.getText();
        
        String sqlNewAddQuery = ("insert into address(addressId, address, address2, cityId, postalCode, phone, "
                                    + "createDate, createdBy, lastUpdate, lastUpdateBy)"
                                    + " values(" + newAddId + ", \"" + address + "\", \"" + address2 + "\", " + cityId
                                    + ", \"" + postalCode + "\", \"" + phone + "\", " + utility.buildSqlQueryEnding()).toString();
        //System.out.println(sqlNewAddQuery);
        utility.runUpdateSqlQuery(sqlNewAddQuery);
        
        Address newAddress = new Address(newAddId, address, address2, cityId, postalCode, phone);
        Address.addAddress(newAddress);
        
        
        String customerName = nameTxtLbl.getText();
        ResultSet customerResults = utility.runSqlQuery("select * from customer");
        int newCustId =  utility.getSqlTableRowCount(customerResults);
        String userName = utility.getCurLoggedInUserName();
        String sqlNewCustQuery = "insert into customer(customerId, customerName, addressId, active, createDate, createdBy,"
                                    + "lastUpdate, lastUpdateBy) values("
                                    + newCustId + ", \"" + customerName + "\", " + newAddId + ", " + 1 + ", "
                                    + utility.buildSqlQueryEnding();
        //System.out.println(sqlNewCustQuery);
        utility.runUpdateSqlQuery(sqlNewCustQuery);
        
        String fullCustAddress = address + " " + address2 + " " + cityComboBx.getSelectionModel().getSelectedItem()
                + " " + postalCode + " " + countryComboBx.getSelectionModel().getSelectedItem();
        Customer customer = new Customer(newCustId, customerName, newAddId, fullCustAddress);
        Customer.addCustomer(customer);
    }
}
