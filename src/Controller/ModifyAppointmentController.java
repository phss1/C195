/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.net.URL;
import java.util.ResourceBundle;
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
    private ComboBox<?> cityComboBx;
    @FXML
    private ComboBox<?> countryComboBx;
    @FXML
    private Button cancelBtn;
    @FXML
    private Button saveBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onActionCancelBtn(ActionEvent event) {
    }

    @FXML
    private void onActionSaveBtn(ActionEvent event) {
    }
    
}
