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
import java.sql.Time;
import java.util.Calendar;
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
    @FXML
    private TextField appIdTxtFld;
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        String currentDateTime = utility.getCurrentDateTime();
        String[] dateTimeSplit = currentDateTime.split(" ");
        String[] dateSplit = dateTimeSplit[0].split("-");
        String[] timeSplit = dateTimeSplit[1].split(":");
        
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
        //put something here to set up correct start month
        int currentStartMonthIndex = startMonth.indexOf(dateSplit[1]);
        ObservableList<String> newStartMonths = FXCollections.observableArrayList(Appointment.createNewObsList(currentStartMonthIndex, startMonth));//.get(0));
        
        appStartMonthComboBx.setItems(newStartMonths);
        appStartMonthComboBx.setValue(newStartMonths.get(0));
        appEndMonthCmbBx.setValue(newStartMonths.get(0));
        
        ObservableList<String> startYear = FXCollections.observableArrayList();
        //put something here to set up correct start year
        startYear.add("2019");
        //startYear.add("2020");
        appStartYearCmbBox.setItems(startYear);
        appStartYearCmbBox.setValue(startYear.get(0));
        
        ObservableList<String> endYear = FXCollections.observableArrayList();
        endYear.add("2019");
        //endYear.add("2020");
        appEndYearCmbBox.setItems(endYear);
        appEndYearCmbBox.setValue(endYear.get(0));
        
        createAppointmentDays();
        
        //something here to send current hour only
        ObservableList<String> startAppTimes = Appointment.createAppointmentTimes(9, 15);
        //put something here to set up correct start times
        apptStartTimeComboBox.setItems(startAppTimes);
        apptStartTimeComboBox.setValue(startAppTimes.get(0));
        
        String [] endTimeMinArray = ((apptStartTimeComboBox.getSelectionModel().getSelectedItem()).split(":", 2));
        String endTimeHourTemp = endTimeMinArray[0];
        String endTimeMinTemp = endTimeMinArray[1];
        int endTimeInHours = Integer.valueOf(endTimeHourTemp);
        
        ObservableList<String> endAppTimes = Appointment.createAppointmentTimes(endTimeInHours, 15);
        //put something here to set up correct end times
        endAppTimes.remove(0);
        apptEndTimeComboBx.setItems(endAppTimes);
        apptEndTimeComboBx.setValue(endAppTimes.get(0));
    }
    
    private void createAppointmentDays()
    {
        int daysInStartMonth = utility.getDaysInMonth(Integer.valueOf(appStartYearCmbBox.getValue()),
                                                   Integer.valueOf(appStartMonthComboBx.getValue()));
        ObservableList<String> startDays = Appointment.prepDateComboBoxValues(daysInStartMonth);
        appStartDayCmbBox.setItems(startDays);
        appStartDayCmbBox.setValue(startDays.get(0));
        
        ObservableList<String> endDay = FXCollections.observableArrayList();
        endDay.add(startDays.get(0));
        appEndDayCmbBx.setItems(endDay);
        appEndDayCmbBx.setValue(endDay.get(0));
    }
    
    @FXML
    void onActionCancelBtn(ActionEvent event) throws IOException
    {
        boolean result = utility.displayLocaleError("CONFIRMATION", "Cancel", "Close Window", "Are you sure you want to exit adding a new appointment?");
        if(result)
        {
            utility.changeGuiScreen(event, "MainMenu");
        }
    }

    @FXML
    void onActionSaveBtn(ActionEvent event) throws IOException, SQLException
    {
        int customerId = Integer.valueOf(custIdTxtFld.getText());
        int appointmentId = utility.createNewId("appointmentId", "account");
        int userId = UtilityMethods.getCurrentUserId();
        String title = titleTxtFld.getText();
        String description = descriptionTxtFld.getText();
        String location = locationComboBox.getSelectionModel().getSelectedItem();
        String contact = contactTxtFld.getText();
        String type = typeComboBx.getSelectionModel().getSelectedItem();
        String url = urlTxtFld.getText();
        
        String startMonth = appStartMonthComboBx.getSelectionModel().getSelectedItem();
        String startDay = appStartDayCmbBox.getSelectionModel().getSelectedItem();
        String startYear = appStartYearCmbBox.getSelectionModel().getSelectedItem();
        String startTime = apptStartTimeComboBox.getSelectionModel().getSelectedItem();
        String endMonth = appEndMonthCmbBx.getSelectionModel().getSelectedItem();
        String endDay = appEndDayCmbBx.getSelectionModel().getSelectedItem();
        String endYear = appEndYearCmbBox.getSelectionModel().getSelectedItem();
        String endTime = apptEndTimeComboBx.getSelectionModel().getSelectedItem();
        String startDateTime = startYear + "-" + startMonth + "-" + startDay + " " + startTime + ":00.0";
        String endDateTime = endYear + "-" + endMonth + "-" + endDay + " " + endTime + ":00.0";
        
        String sqlQuery = "insert into appointment(customerId, userId, title, description, location, contact, type, "
                + "url, start, end, createDate, createdBy, lastUpdate, lastUpdateBy)"    
                + "values(" + customerId + ", " + userId + ", \"" + title + "\", \""  + description + "\", \"" 
                + location + "\", \"" + contact + "\", \"" + type + "\", \"" + url + "\", " + utility.buildSqlQueryEnding();
        utility.runUpdateSqlQuery(sqlQuery);
        Appointment newAppointment = new Appointment(appointmentId, customerId, userId, title, description, location, contact, type,  url, startDateTime, endDateTime);
        
        utility.changeGuiScreen(event, "MainMenu");
    }

    @FXML
    void onActionAppStartMonth(ActionEvent event)
    {
        try
        {
            String selectedStartDay = appStartMonthComboBx.getSelectionModel().getSelectedItem();
            int selectedStartDayIndex = appStartMonthComboBx.getSelectionModel().getSelectedIndex();
            ObservableList<String> allapptStartDays = appStartMonthComboBx.getItems();

            ObservableList<String> newAppEndTimes = FXCollections.observableArrayList((Appointment.createNewObsList(selectedStartDayIndex, allapptStartDays)).get(0));
            appEndMonthCmbBx.setItems(newAppEndTimes);
            appEndMonthCmbBx.setValue(newAppEndTimes.get(0));

            createAppointmentDays();
            
            if(appStartMonthComboBx.getSelectionModel().getSelectedItem() == "1")
            {
                int daysInStartMonth = utility.getDaysInMonth(Integer.valueOf(appStartYearCmbBox.getValue()),
                                                   Integer.valueOf(appStartMonthComboBx.getValue()));
                ObservableList<String> startDays = Appointment.prepDateComboBoxValues(daysInStartMonth);
            }
        }
        catch(Exception e)
        {
            
        }
    }

    @FXML
    void onActionAppStartYear(ActionEvent event)
    {
        
        int selectedStartDayIndex = appStartYearCmbBox.getSelectionModel().getSelectedIndex();
        ObservableList<String> allapptStartDays = appStartYearCmbBox.getItems();

        ObservableList<String> newAppEndTimes = FXCollections.observableArrayList((Appointment.createNewObsList(selectedStartDayIndex, allapptStartDays)).get(0));
        appEndYearCmbBox.setItems(newAppEndTimes);
        appEndYearCmbBox.setValue(newAppEndTimes.get(0));
        
        createAppointmentDays();
    }
    
    @FXML
    void onActionAppStartTime(ActionEvent event)
    {
        String selectedStartTime = apptStartTimeComboBox.getSelectionModel().getSelectedItem();
        int selectedStartTimeIndex = apptStartTimeComboBox.getSelectionModel().getSelectedIndex();
        ObservableList<String> allapptStartTimes = apptStartTimeComboBox.getItems();
        
        ObservableList<String> newAppEndTimes = FXCollections.observableArrayList(Appointment.createNewObsList(selectedStartTimeIndex, allapptStartTimes));
        newAppEndTimes.remove(0);
        apptEndTimeComboBx.setItems(newAppEndTimes);
        apptEndTimeComboBx.setValue(newAppEndTimes.get(0));
    }
    
    @FXML
    void onActionAppStartDayCmbBox(ActionEvent event)
    {
        String selectedStartDay = appStartDayCmbBox.getSelectionModel().getSelectedItem();
        ObservableList<String> allapptStartDays = appStartDayCmbBox.getItems();
        
        ObservableList<String> newAppEndTimes = FXCollections.observableArrayList();
        newAppEndTimes.add(selectedStartDay);
        appEndDayCmbBx.setItems(newAppEndTimes);
        appEndDayCmbBx.setValue(newAppEndTimes.get(0));
    }
    
    private void resetStartMonths(ObservableList<String> list)
    {
        ObservableList<String> endDay = FXCollections.observableArrayList(list);
        appStartMonthComboBx.setItems(endDay);
        appStartMonthComboBx.setValue(endDay.get(0));
    }
    
    private void resetEndDateDays(ObservableList<String> list)
    {
        ObservableList<String> endDay = FXCollections.observableArrayList(list);
        appEndDayCmbBx.setItems(endDay);
        appEndDayCmbBx.setValue(endDay.get(0));
    }
}