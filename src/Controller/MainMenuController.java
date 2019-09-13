/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Utilities.UtilityMethods;
import Model.*;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class MainMenuController implements Initializable
{

    @FXML
    private TableView<Customer> customerTbl;

    @FXML
    private TableColumn<Customer, Integer> customerIdCol;

    @FXML
    private TableColumn<Customer, String> customerNameCol;

    @FXML
    private TableColumn<Customer, String> customerAddressCol;

    @FXML
    private TableView<Appointment> appointmentTbl;

    @FXML
    private TableColumn<Appointment, Integer> appCustomerIdCol;

    @FXML
    private TableColumn<Appointment, String> appTitleCol;

    @FXML
    private TableColumn<Appointment, String> appTypeCol;

    @FXML
    private TableColumn<Appointment, String> appLocationCol;

    @FXML
    private RadioButton monthViewRdBtn;

    @FXML
    private RadioButton weekViewRdBtn;

    @FXML
    private TableView<?> appMonthCal;

    @FXML
    private TableColumn<?, ?> monthMonCol;

    @FXML
    private TableColumn<?, ?> monthTueCol;

    @FXML
    private TableColumn<?, ?> monthWedCol;

    @FXML
    private TableColumn<?, ?> monthThuCol;

    @FXML
    private TableColumn<?, ?> monthFriCol;

    @FXML
    private TableColumn<?, ?> monthSatCol;

    @FXML
    private TableColumn<?, ?> monthSunCol;

    @FXML
    private Button exitBtn;

    @FXML
    private Button modCustomerBtn;

    @FXML
    private Button addCustomerBtn;

    @FXML
    private Button addAppBtn;

    @FXML
    private Button modAppBtn;

    @FXML
    private Button deleteCustomerBtn;

    @FXML
    private Button deleteAppBtn;
    
    UtilityMethods utility = new UtilityMethods();
    Stage stage;
    Parent scene;
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {        
        customerTbl.setItems(Customer.getAllCustomers());
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
    }    

        @FXML
    void onActionAddAppBtn(ActionEvent event) throws IOException
    {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/AddAppointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionAddCustomerBtn(ActionEvent event) throws IOException
    {
        utility.changeGuiScreen(event, "AddCustomer");
    }

    @FXML
    void onActionDeleteAppBtn(ActionEvent event)
    {

    }

    @FXML
    void onActionDeleteCustomerBtn(ActionEvent event)
    {

    }

    @FXML
    void onActionExitBtn(ActionEvent event) throws SQLException, Exception
    {
        boolean result = utility.displayLocaleError("CONFIRMATION", "Exit", "Exit Program", "Are you sure you want to close the program?");
        String test = result ? utility.closeProgram() : "";
    }

    @FXML
    void onActionModAppBtn(ActionEvent event) throws IOException
    {
        
    }

    @FXML
    void onActionModCustomerBtn(ActionEvent event) throws IOException
    {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/ModifyCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionMonthViewRdBtn(ActionEvent event)
    {
        weekViewRdBtn.setSelected(false);
    }

    @FXML
    void onActionWeekViewRdBtn(ActionEvent event)
    {
        monthViewRdBtn.setSelected(false);
    }
    
}
