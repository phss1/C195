/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Appointment;
import Model.Customer;
import Utilities.UtilityMethods;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author 39ds03d
 */
public class AddAppointmentController implements Initializable {

    @FXML
    private TextField custIdTxtFld;

    @FXML
    private TextField titleTxtFld;

    @FXML
    private TextField descriptionTxtFld;

    @FXML
    private TextField contactTxtFld;

    @FXML
    private TextField urlTxtFld;

    @FXML
    private ComboBox<String> typeComboBx;

    @FXML
    private ComboBox<String> locationComboBox;

    @FXML
    private ComboBox<String> apptStartTimeComboBox;

    @FXML
    private ComboBox<String> apptEndTimeComboBx;

    @FXML
    private ComboBox<String> appStartMonthComboBx;

    @FXML
    private ComboBox<String> appStartDayCmbBox;

    @FXML
    private ComboBox<String> appStartYearCmbBox;

    @FXML
    private ComboBox<String> appEndMonthCmbBx;

    @FXML
    private ComboBox<String> appEndDayCmbBx;

    @FXML
    private ComboBox<String> appEndYearCmbBox;

    @FXML
    private Button saveBtn;

    @FXML
    private Button cancelBtn;
    
    UtilityMethods utility = new UtilityMethods();
    //int daysInMonth;
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        ObservableList<String> location = FXCollections.observableArrayList();
        location.add("Office");
        locationComboBox.setItems(location);
        locationComboBox.setValue(location.get(0));
        
        ObservableList<String> type = FXCollections.observableArrayList();
        type.add("Doctor");
        type.add("Chiropractic");
        typeComboBx.setItems(type);
        typeComboBx.setValue(type.get(0));
        custIdTxtFld.setText(String.valueOf((Appointment.getRefCustToAppointment().get(0)).getCustomerId()));
        
        ObservableList<String> startMonth = Appointment.prepDateComboBoxValues(12);
        appStartMonthComboBx.setItems(startMonth);
        appStartMonthComboBx.setValue(startMonth.get(0));
        
        ObservableList<String> startYear = FXCollections.observableArrayList();
        startYear.add("2019");
        startYear.add("2020");
        appStartYearCmbBox.setItems(startYear);
        appStartYearCmbBox.setValue(startYear.get(0));
        
        int daysInStartMonth = utility.getDaysInMonth(Integer.valueOf(appStartYearCmbBox.getValue()),
                                                   Integer.valueOf(appStartMonthComboBx.getValue()));
        ObservableList<String> startDays = Appointment.prepDateComboBoxValues(daysInStartMonth);
        appStartDayCmbBox.setItems(startDays);
        appStartDayCmbBox.setValue(startDays.get(0));
        
        ObservableList<String> endMonth = Appointment.prepDateComboBoxValues(12);
        appEndMonthCmbBx.setItems(endMonth);
        appEndMonthCmbBx.setValue(endMonth.get(0));
        
        ObservableList<String> endYear = FXCollections.observableArrayList();
        endYear.add("2019");
        endYear.add("2020");
        appEndYearCmbBox.setItems(endYear);
        appEndYearCmbBox.setValue(endYear.get(0));
        
        int daysInEndMonth = utility.getDaysInMonth(Integer.valueOf(appEndYearCmbBox.getValue()),
                                                   Integer.valueOf(appEndMonthCmbBx.getValue()));
        ObservableList<String> endDays = Appointment.prepDateComboBoxValues(daysInEndMonth);
        appEndDayCmbBx.setItems(endDays);
        appEndDayCmbBx.setValue(endDays.get(0));
        
        ObservableList<String> startAppTimes = Appointment.createAppointmentTimes(9, 15);
        apptStartTimeComboBox.setItems(startAppTimes);
        apptStartTimeComboBox.setValue(startAppTimes.get(0));
        
        createAppointmentEndTimes();
    }
    
    @FXML
    void onActionCancelBtn(ActionEvent event) throws IOException
    {
        boolean result = utility.displayLocaleError("CONFIRMATION", "Cancel", "Close Window", "Are you sure you want to exit adding a new customer?");
        if(result)
        {
            utility.changeGuiScreen(event, "MainMenu");
        }
    }

    @FXML
    void onActionSaveBtn(ActionEvent event) throws IOException, SQLException
    {
        int customerId = Integer.valueOf(custIdTxtFld.getText());
        ResultSet appointmentResults = utility.runSqlQuery("select * from appointment");
        int newAppointmentId =  utility.getSqlTableRowCount(appointmentResults);
        int userId = UtilityMethods.getCurrentUserId();
        String title = titleTxtFld.getText();
        String description = descriptionTxtFld.getText();
        String location = locationComboBox.getSelectionModel().getSelectedItem();
        String contact = contactTxtFld.getText();
        String type = typeComboBx.getSelectionModel().getSelectedItem();
        String url = urlTxtFld.getText();
        
        
        utility.changeGuiScreen(event, "MainMenu");
    }
    
    @FXML
    void onActionAppEndMonth(ActionEvent event)
    {
        createAppointmentEndTimes();
    }

    @FXML
    void onActionAppEndYear(ActionEvent event)
    {
        createAppointmentEndTimes();
    }

    @FXML
    void onActionAppStartMonth(ActionEvent event)
    {
        
    }

    @FXML
    void onActionAppStartYear(ActionEvent event)
    {
        int daysInMonth = utility.getDaysInMonth(Integer.valueOf(appStartYearCmbBox.getValue()),
                                                   Integer.valueOf(appStartMonthComboBx.getValue()));
        ObservableList<String> startDays = Appointment.prepDateComboBoxValues(daysInMonth);
        appStartDayCmbBox.setItems(startDays);
        appStartDayCmbBox.setValue(startDays.get(0));
    }
    
    private void createAppointmentEndTimes()
    {
        ObservableList<String> startAppTimes = Appointment.createAppointmentTimes(9, 15);
        apptStartTimeComboBox.setItems(startAppTimes);
        apptStartTimeComboBox.setValue(startAppTimes.get(0));
        
        String [] endTimeMinArray = ((apptStartTimeComboBox.getSelectionModel().getSelectedItem()).split(":", 2));
        String endTimeHourTemp = endTimeMinArray[0];
        String endTimeMinTemp = endTimeMinArray[1];
        int endTimeInHours = Integer.valueOf(endTimeHourTemp);
        int endTimeInMin = Integer.valueOf(endTimeMinTemp);
        System.out.println(endTimeInMin);
        
        ObservableList<String> endAppTimes = Appointment.createAppointmentTimes(endTimeInHours, 15);
        endAppTimes.remove(0);
        apptEndTimeComboBx.setItems(endAppTimes);
        apptEndTimeComboBx.setValue(endAppTimes.get(0));
    }
}
