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
        
        ObservableList<String> startYear = FXCollections.observableArrayList();
        startYear.add("2019");
        appStartYearCmbBox.setItems(startYear);
        appStartYearCmbBox.setValue(startYear.get(0));
        appEndYearCmbBox.setItems(startYear);
        appEndYearCmbBox.setValue(startYear.get(0));
        
        Calendar tempCal = Calendar.getInstance();
        int hourOfDay = tempCal.get(Calendar.HOUR_OF_DAY);
        int minuteOfHour = tempCal.get(Calendar.MINUTE);
        int dayOfMonth = tempCal.get(Calendar.DAY_OF_MONTH);
        
        int startMinuteForAppt = utility.getNextAppMinuteInterval(minuteOfHour);
        int startHourForAppt = minuteOfHour >= 45 ? hourOfDay + 1 : hourOfDay;
        int startDayForAppt = hourOfDay >= 18 ? dayOfMonth + 1 : dayOfMonth;
        System.out.println("hourOfDay: " +hourOfDay);
        System.out.println("minuteOfHour: "+minuteOfHour);
        System.out.println("dayOfMonth: "+dayOfMonth);
        System.out.println("startMinuteForAppt: "+startMinuteForAppt);
        System.out.println("startHourForAppt: "+startHourForAppt);
        System.out.println("startDayForAppt: "+startDayForAppt);
        
        
        //int startDay, int startHour, int startMinute)
        if(hourOfDay <= 9)
        {
            setupDateTimeFields(dayOfMonth, 9, 0);
        }
        else if(hourOfDay >= 9 && hourOfDay <= 18)
        {
            setupDateTimeFields(dayOfMonth, startHourForAppt, startMinuteForAppt);
        }
        else if(hourOfDay >= 18)
        {
            setupDateTimeFields(startDayForAppt, 9, 0);
        }
        
        String currentDateTime = utility.getCurrentDateTime();
        String[] dateTimeSplit = currentDateTime.split(" ");
        String[] dateSplit = dateTimeSplit[0].split("-");
        String[] timeSplit = dateTimeSplit[1].split(":");
        
        //start month and end month
        ObservableList<String> startMonth = Appointment.prepDateComboBoxValues(12);
        //field here to change value plus one day
        int currentStartMonthIndex = startMonth.indexOf(dateSplit[1]);
        ObservableList<String> newStartMonths = FXCollections.observableArrayList(Appointment.createNewObsList(currentStartMonthIndex, startMonth));//.get(0));
        appStartMonthComboBx.setItems(newStartMonths);
        appStartMonthComboBx.setValue(newStartMonths.get(0));
        appEndMonthCmbBx.setItems(newStartMonths);
        appEndMonthCmbBx.setValue(newStartMonths.get(0));
        
        //createAppointmentDays(dateSplit[1]);
        
        //setting up start times
        ObservableList<String> startAppTimes = Appointment.createAppointmentTimes(9, 15);
        apptStartTimeComboBox.setItems(startAppTimes);
        apptStartTimeComboBox.setValue(startAppTimes.get(0));
        
        //setting up end times
        String [] endTimeMinArray = ((apptStartTimeComboBox.getSelectionModel().getSelectedItem()).split(":", 2));
        String endTimeHourTemp = endTimeMinArray[0];
        String endTimeMinTemp = endTimeMinArray[1];
        int endTimeInHours = Integer.valueOf(endTimeHourTemp);
        ObservableList<String> endAppTimes = Appointment.createAppointmentTimes(endTimeInHours, 15);
        endAppTimes.remove(0);
        apptEndTimeComboBx.setItems(endAppTimes);
        apptEndTimeComboBx.setValue(endAppTimes.get(0));
        
        //something to change day of month
        Calendar cal = Calendar.getInstance();
        int numberOfDaysInMonth = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
        int currentDayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        ObservableList<String> daysInMonth = Appointment.prepDateComboBoxValues(numberOfDaysInMonth);
        int dayIndex = daysInMonth.indexOf(String.valueOf(currentDayOfMonth));
        ObservableList<String> newStartDays = FXCollections.observableArrayList(Appointment.createNewObsList(dayIndex, daysInMonth));
        appStartDayCmbBox.setItems(newStartDays);
        appStartDayCmbBox.setValue(newStartDays.get(0));
        appEndDayCmbBx.setItems(newStartDays);
        appEndDayCmbBx.setValue(newStartDays.get(0));
    }
    
    private void setupDateTimeFields(int startDay, int startHour, int startMinute)
    {
        
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
        
        String currentDateTime = utility.getCurrentDateTime();
        String[] dateTimeSplit = currentDateTime.split(" ");
        String[] dateSplit = dateTimeSplit[0].split("-");
        String currentMonth = dateSplit[1];
        
        String selectedMonth = appStartMonthComboBx.getSelectionModel().getSelectedItem();
        
        if(selectedMonth.equals(currentMonth))
        {
            //appStartDayCmbBox.getItems().clear();
            //appEndDayCmbBx.valueProperty().set(null);
            Calendar cal = Calendar.getInstance();
            int numberOfDaysInMonth = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
            int currentDayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
            ObservableList<String> daysInMonth = Appointment.prepDateComboBoxValues(numberOfDaysInMonth);
            int dayIndex = daysInMonth.indexOf(String.valueOf(currentDayOfMonth));
            ObservableList<String> newStartDays = FXCollections.observableArrayList(Appointment.createNewObsList(dayIndex, daysInMonth));
            appStartDayCmbBox.setItems(newStartDays);
            appStartDayCmbBox.setValue(newStartDays.get(0));
            appEndDayCmbBx.setItems(newStartDays);
            appEndDayCmbBx.setValue(newStartDays.get(0));
        }
    }
    
    private void createAppointmentDays(String currentDay)
    {
        int daysInStartMonth = utility.getDaysInMonth(Integer.valueOf(appStartYearCmbBox.getValue()),
                                                   Integer.valueOf(appStartMonthComboBx.getValue()));
        ObservableList<String> startDays = Appointment.prepDateComboBoxValues(daysInStartMonth);
        
        int currentStartDayIndex = startDays.indexOf(currentDay);
        ObservableList<String> newStartMonths = FXCollections.observableArrayList(Appointment.createNewObsList(currentStartDayIndex, startDays));//.get(0));
        
        appStartDayCmbBox.setItems(startDays);
        appStartDayCmbBox.setValue(startDays.get(0));
        
        ObservableList<String> endDay = FXCollections.observableArrayList();
        endDay.add(startDays.get(0));
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
        int appointmentId = utility.createNewId("appointmentId", "appointment");
        int userId = UtilityMethods.getCurrentUserId();
        String title = titleTxtFld.getText();
        String description = descriptionTxtFld.getText();
        String location = locationComboBox.getSelectionModel().getSelectedItem();
        String contact = contactTxtFld.getText();
        String type = typeComboBx.getSelectionModel().getSelectedItem();
        String url = urlTxtFld.getText();
        
        try
        {
            if(!title.isEmpty() && !description.isEmpty() && !contact.isEmpty() && !url.isEmpty())
            {
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

                String sqlQuery = "insert into appointment(appointmentId, customerId, userId, title, description, location, contact, type, "
                        + "url, start, end, createDate, createdBy, lastUpdate, lastUpdateBy)"    
                        + "values(" + appointmentId + ", " + customerId + ", " + userId + ", \"" + title + "\", \""  + description + "\", \"" 
                        + location + "\", \"" + contact + "\", \"" + type + "\", \"" + url + "\", " + "\"" + startDateTime + "\"" + ", "
                        + "\"" + endDateTime + "\"" + ", " + utility.buildSqlQueryEnding();
                System.out.println(sqlQuery);
                utility.runUpdateSqlQuery(sqlQuery);
                //Appointment newAppointment = new Appointment(appointmentId, customerId, userId, title, description, location, contact, type,  url, startDateTime, endDateTime);

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

            createAppointmentDays(selectedStartDay);
            
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
        
        String selectedStartDay = appStartDayCmbBox.getSelectionModel().getSelectedItem();
        createAppointmentDays(selectedStartDay);
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