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
import java.sql.SQLException;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author 39ds03d
 */
public class ModifyAppointmentController implements Initializable {

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
        String appStartDateTime = ((Appointment.getAppointmentToModify()).get(0)).getStart();
        String[] dateTimeSplit = appStartDateTime.split(" ");
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
        int currentStartMonthIndex = startMonth.indexOf(dateSplit[1]);
        ObservableList<String> newStartMonths = FXCollections.observableArrayList(Appointment.createNewObsList(currentStartMonthIndex, startMonth));
        
        appStartMonthComboBx.setItems(newStartMonths);
        appStartMonthComboBx.setValue(newStartMonths.get(0));
        appEndMonthCmbBx.setValue(newStartMonths.get(0));
        
        ObservableList<String> startYear = FXCollections.observableArrayList();
        startYear.add("2019");
        appStartYearCmbBox.setItems(startYear);
        appStartYearCmbBox.setValue(startYear.get(0));
        
        ObservableList<String> endYear = FXCollections.observableArrayList();
        endYear.add("2019");
        appEndYearCmbBox.setItems(endYear);
        appEndYearCmbBox.setValue(endYear.get(0));
        
        //createAppointmentDays(dateSplit[1]);
        
        ObservableList<String> startAppTimesTemp = Appointment.createAppointmentTimes(9, 15);
        //utility.
        apptStartTimeComboBox.setItems(startAppTimesTemp);
        apptStartTimeComboBox.setValue(startAppTimesTemp.get(0));
        
        String [] endTimeMinArray = ((apptStartTimeComboBox.getSelectionModel().getSelectedItem()).split(":", 2));
        String endTimeHourTemp = endTimeMinArray[0];
        String endTimeMinTemp = endTimeMinArray[1];
        int endTimeInHours = Integer.valueOf(endTimeHourTemp);
        
        ObservableList<String> endAppTimes = Appointment.createAppointmentTimes(endTimeInHours, 15);
        endAppTimes.remove(0);
        apptEndTimeComboBx.setItems(endAppTimes);
        apptEndTimeComboBx.setValue(endAppTimes.get(0));
        
        int currentStartDay = startMonth.indexOf(dateSplit[2]);
        ObservableList<String> newStartDays = FXCollections.observableArrayList(Appointment.createNewObsList(currentStartDay, startMonth));
    }
    
    public void sendInfo(Appointment appointment) throws SQLException
    {
        custIdTxtFld.setText(String.valueOf(appointment.getCustomerId()));
        appIdTxtFld.setText(String.valueOf(appointment.getAppointmentId()));
        titleTxtFld.setText(appointment.getTitle());
        descriptionTxtFld.setText(appointment.getDescription());
        contactTxtFld.setText(appointment.getContact());
        urlTxtFld.setText(appointment.getUrl());
    }
    
    private void createAppointmentDays(String currentDay)
    {
        int daysInStartMonth = utility.getDaysInMonth(Integer.valueOf(appStartYearCmbBox.getValue()),
                                                   Integer.valueOf(appStartMonthComboBx.getValue()));
        ObservableList<String> startDays = Appointment.prepDateComboBoxValues(daysInStartMonth);
        
        int currentStartDayIndex = startDays.indexOf(currentDay);
        ObservableList<String> newStartMonths = FXCollections.observableArrayList(Appointment.createNewObsList(currentStartDayIndex, startDays));
        
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
        String enteredMonth = appStartMonthComboBx.getSelectionModel().getSelectedItem();
        String enteredDay = appStartDayCmbBox.getSelectionModel().getSelectedItem();
        String [] enteredStartTime = (apptStartTimeComboBox.getSelectionModel().getSelectedItem()).split(":");
        String enteredHour = enteredStartTime[0];
        String enteredMinute = enteredStartTime[1];
        
        Calendar cal = Calendar.getInstance();
        int currentMonth = cal.get(Calendar.MONTH) +1;
        int currentDay = cal.get(Calendar.DAY_OF_MONTH);
        int currentHour = cal.get(Calendar.HOUR_OF_DAY);
        int currentMinute = cal.get(Calendar.MINUTE);
        
        try
        {
            int customerId = Integer.valueOf(custIdTxtFld.getText());
            int appointmentId = Integer.valueOf(appIdTxtFld.getText());
            int userId = UtilityMethods.getCurrentUserId();
            String title = titleTxtFld.getText();
            String description = descriptionTxtFld.getText();
            String location = locationComboBox.getSelectionModel().getSelectedItem();
            String contact = contactTxtFld.getText();
            String type = typeComboBx.getSelectionModel().getSelectedItem();
            String url = urlTxtFld.getText();
            
            System.out.println("!title.isEmpty() - " +!title.isEmpty());
            System.out.println("!description.isEmpty() - " +!description.isEmpty());
            System.out.println("!contact.isEmpty() - " +!contact.isEmpty());
            System.out.println("!url.isEmpty() - " +!url.isEmpty());
            
            if(!title.isEmpty() && !description.isEmpty() && !contact.isEmpty() && !url.isEmpty()
                    && Integer.valueOf(enteredMonth) >= currentMonth && Integer.valueOf(enteredDay) >= currentDay 
                    && Integer.valueOf(enteredHour) >= currentHour && currentMinute <= Integer.valueOf(enteredMinute))
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

                String sqlQuery = "update appointment set userId = " + userId + ", title = \"" + title + "\", description = \""
                        + description + "\", location = \"" + location + "\", contact = \"" + contact + "\", type = \"" + type
                        + "\", url = \"" + url + "\", start = \"" + startDateTime + "\", end = \"" + endDateTime
                        + "\", lastUpdate = now(), " + "lastUpdateBy = \"" + utility.getCurLoggedInUserName()
                        + "\" where appointmentId = " + appointmentId + ";";
                utility.runUpdateSqlQuery(sqlQuery);

                utility.changeGuiScreen(event, "MainMenu");
            }
            else if(!(Integer.valueOf(enteredMonth) >= currentMonth))
            {
                utility.displayLocaleError("INFORMATION", "Incorrect Month", "",
                        "Please make sure you select a month equal to or greater than the current calendar month.\n\n"
                                + "EnteredMonth compared to currentMonth: "+enteredMonth +" : "+currentMonth);
            }else if(!(Integer.valueOf(enteredDay) >= currentDay))
            {
                utility.displayLocaleError("INFORMATION", "Incorrect Day", "",
                        "Please make sure you select a day equal or grater than the current calendar day.\n\n"
                                + "EnteredDay compared to currentDay: "+enteredDay +" : "+currentDay);
            }
            else if(!(Integer.valueOf(enteredHour) >= currentHour))
            {
                utility.displayLocaleError("INFORMATION", "Incorrect Time", "",
                        "Please make sure you select a start hour that is equal to or greater than the current hour on your PC.\n\n"
                                + "EnteredHour compared to currentHour: "+enteredHour +" : "+currentHour);
            }
            else if(!(currentMinute <= Integer.valueOf(enteredMinute)))
            {
                utility.displayLocaleError("INFORMATION", "Incorrect Time", "",
                        "Please make sure you select a start time where the next 15 minute interval is greater than "
                                + "the current minute shown on your system clock.\n\n"
                                + "enteredMinute compared to currentMinute: "+enteredMinute +" : "+currentMinute);
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
        ObservableList<String> startDay = FXCollections.observableArrayList(list);
        appStartMonthComboBx.setItems(startDay);
        appStartMonthComboBx.setValue(startDay.get(0));
    }
    
    private void resetEndDateDays(ObservableList<String> list)
    {
        ObservableList<String> endDay = FXCollections.observableArrayList(list);
        appEndDayCmbBx.setItems(endDay);
        appEndDayCmbBx.setValue(endDay.get(0));
    }
}