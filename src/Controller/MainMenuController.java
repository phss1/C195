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
import java.sql.SQLException;
import java.util.ResourceBundle;
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
    private TableColumn<Appointment, Integer> appIdCol;
            
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
        weekViewRdBtn.setSelected(true);
        customerTbl.getItems().clear();
        appointmentTbl.getItems().clear();
        
        customerTbl.setItems(Customer.getAllCustomers());
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        
        
        int rowIndexValue = utility.getSelectedRowIndex();
        if(rowIndexValue == 0)
        {
            customerTbl.getSelectionModel().selectFirst();
        }
        else
        {
            customerTbl.getSelectionModel().select(rowIndexValue);
        }
        
        Customer selectedCustomer = customerTbl.getSelectionModel().getSelectedItem();
        utility.setApptTableViewItems(selectedCustomer);
        
        appointmentTbl.setItems(selectedCustomer.getAllCustomerAppointments());
        appCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        appTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        appTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        appLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        appIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        
        //using lamba expression here because it's a listener for populating the appointment table when 
        //an item is selected in the customer table
        customerTbl.getSelectionModel().selectedItemProperty().addListener
            (
                (obs, oldSelection, newSelection) ->
                {
                    if (newSelection != null)
                    {
                        appointmentTbl.getItems().clear();
                        customerTbl.setItems(Customer.getAllCustomers());
                        Customer customer = customerTbl.getSelectionModel().getSelectedItem();
                        utility.setApptTableViewItems(customer);

                        appointmentTbl.setItems(customer.getAllCustomerAppointments());
                        appCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
                        appTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
                        appTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
                        appLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
                        appIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
                    }
                }
            );
    }

    @FXML
    void onActionAddAppBtn(ActionEvent event) throws IOException
    {
        Appointment.getRefCustToAppointment().clear();
        Appointment.getAppointmentToModify().clear();
        Appointment.addRefCustToAppointment(customerTbl.getSelectionModel().getSelectedItem());
        Appointment.addItemAppToModify(appointmentTbl.getSelectionModel().getSelectedItem());
        appointmentTbl.getItems().clear();
        
        utility.changeGuiScreen(event, "AddAppointment");
    }

    @FXML
    void onActionAddCustomerBtn(ActionEvent event) throws IOException
    {
        appointmentTbl.getItems().clear();
        utility.changeGuiScreen(event, "AddCustomer");
    }

    @FXML
    void onActionDeleteAppBtn(ActionEvent event) throws SQLException
    {
        Customer customerToDelete = customerTbl.getSelectionModel().getSelectedItem();
        int selectedAppointmentId = (appointmentTbl.getSelectionModel().getSelectedItem()).getAppointmentId();
        Appointment.deleteAppointment(customerToDelete, selectedAppointmentId);
        
        String deleteCustomerQuery = "delete from appointment where appointmentId = " + selectedAppointmentId + ";";
        utility.runUpdateSqlQuery(deleteCustomerQuery);
    }

    @FXML
    void onActionDeleteCustomerBtn(ActionEvent event) throws SQLException
    {
        boolean selectionResult = utility.displayLocaleError("CONFIRMATION", "Delete Customer",
                                    "Confirm Customer Deletion", "Are you sure you want to delete this customer"
                                            + " and all associated records?");
        if(selectionResult == true)
        {
            Customer selectedCustomer = customerTbl.getSelectionModel().getSelectedItem();
            int addressId = selectedCustomer.getAddressId();
            int customerId = selectedCustomer.getCustomerId();
            
            utility.deleteCustomerAppointments(selectedCustomer);
            
            String deleteCustomerQuery = "delete from customer where customerId = " + customerId + ";";
            utility.runUpdateSqlQuery(deleteCustomerQuery);
            
            String deleteAddressQuery = "delete from address where addressId = " + addressId + ";";
            utility.runUpdateSqlQuery(deleteAddressQuery);
            
            Customer.deleteCustomer(selectedCustomer);
            customerTbl.setItems(Customer.getAllCustomers());
            customerTbl.getSelectionModel().selectFirst();
        }
    }

    @FXML
    void onActionExitBtn(ActionEvent event) throws SQLException, Exception
    {
        boolean result = utility.displayLocaleError("CONFIRMATION", "Exit", "Exit Program", 
                                                        "Are you sure you want to close the program?");
        String test = result ? utility.closeProgram() : "";
    }

    @FXML
    void onActionModAppBtn(ActionEvent event) throws IOException, SQLException
    {
        utility.setSelectedRowIndex(customerTbl.getSelectionModel().getSelectedIndex());
        Appointment.getRefCustToAppointment().clear();
        Appointment.getAppointmentToModify().clear();
        Appointment.addRefCustToAppointment(customerTbl.getSelectionModel().getSelectedItem());
        Appointment.addItemAppToModify(appointmentTbl.getSelectionModel().getSelectedItem());
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/ModifyAppointment.fxml"));
        loader.load();

        ModifyAppointmentController MPSController = loader.getController();
        MPSController.sendInfo(appointmentTbl.getSelectionModel().getSelectedItem());
        appointmentTbl.getItems().clear();

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionModCustomerBtn(ActionEvent event) throws IOException, SQLException
    {
        utility.setSelectedRowIndex(customerTbl.getSelectionModel().getFocusedIndex());
        int rowIndexValue = utility.getSelectedRowIndex();
        appointmentTbl.getItems().clear();
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/ModifyCustomer.fxml"));
        loader.load();

        ModifyCustomerController MPSController = loader.getController();
        MPSController.sendInfo(customerTbl.getSelectionModel().getSelectedItem());

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
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
