package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import Model.*;
import Controller.*;

public class MainMenuController {

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

    @FXML
    void onActionAddAppBtn(ActionEvent event)
    {

    }

    @FXML
    void onActionAddCustomerBtn(ActionEvent event)
    {

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
    void onActionExitBtn(ActionEvent event)
    {

    }

    @FXML
    void onActionModAppBtn(ActionEvent event)
    {

    }

    @FXML
    void onActionModCustomerBtn(ActionEvent event)
    {

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
