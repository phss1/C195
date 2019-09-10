/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;


public class MainMenuController implements Initializable
{
    Stage stage;
    Parent scene;
    
    @FXML
    private TableView<?> customerTbl;
    @FXML
    private TableView<?> customerAppTbl;
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
    private Button DeletAppBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
