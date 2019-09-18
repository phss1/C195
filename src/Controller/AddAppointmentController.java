/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Appointment;
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
    private TextField locationTxtFld;
    @FXML
    private TextField contactTxtFld;
    @FXML
    private ComboBox<String> typeComboBx;
    @FXML
    private TextField urlTxtFld;
    @FXML
    private TextField apptEndTxtFld;
    @FXML
    private TextField apptStartTxtFld;
    @FXML
    private Button saveBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private ComboBox<String> locationComboBox;
    
    UtilityMethods utility = new UtilityMethods();

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        ObservableList<String> locationObsList = FXCollections.observableArrayList();
        locationObsList.add("Office");
        locationComboBox.setItems(locationObsList);
        locationComboBox.setValue(locationObsList.get(0));

        ObservableList<String> typeObsList = FXCollections.observableArrayList();
        typeObsList.add("Doctor");
        typeObsList.add("Chiropractic");
        typeComboBx.setItems(typeObsList);
        typeComboBx.setValue(typeObsList.get(0));
        custIdTxtFld.setText(String.valueOf((Appointment.getRefCustToAppointment().get(0)).getCustomerId()));
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
}
