/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Date;
import java.sql.Timestamp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author 39ds03d
 */
public class Customer
{
    private int customerId;
    private String customerName;
    private String address;
    //private String address2;
    //private String postalCode;
    //private String phone;
    //private int active;
    //private Date createDate;
    //private String createdBy;
    //private Timestamp lastUpdate;
    //private String lastUpdatedBy;    
    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private static ObservableList<Customer> allCustomersFiltered = FXCollections.observableArrayList();

    public Customer(int customerId, String customerName, String address)//, String address2,
            //String postalCode, String phone), int active, Date createDate, String createdBy,
            //Timestamp lastUpdate, String lastUpdatedBy)
    {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        /*this.address2 = address2;
        this.postalCode = postalCode;
        this.phone = phone;
        this.active = active;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;*/
    }
    
    public static void addCustomer(Customer customer)
    {
         allCustomers.add(customer);
    }
    
    public static ObservableList<Customer> getAllCustomersFiltered()
    {
        return allCustomersFiltered;
    }

    public static void setAllCustomersFiltered(ObservableList<Customer> allCustomersFiltered)
    {
        Customer.allCustomersFiltered = allCustomersFiltered;
    }
    
    public static ObservableList<Customer> getAllCustomers()
    {
        return allCustomers;
    }

    public static void setAllCustomers(ObservableList<Customer> allCustomers)
    {
        Customer.allCustomers = allCustomers;
    }

    public int getCustomerId()
    {
        return customerId;
    }

    public void setCustomerId(int customerId)
    {
        this.customerId = customerId;
    }

    public String getCustomerName()
    {
        return customerName;
    }

    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    /*public int getActive()
    {
        return active;
    }

    public void setActive(int active)
    {
        this.active = active;
    }

    public Date getCreateDate()
    {
        return createDate;
    }

    public void setCreateDate(Date createDate)
    {
        this.createDate = createDate;
    }

    public String getCreatedBy()
    {
        return createdBy;
    }

    public void setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
    }

    public Timestamp getLastUpdate()
    {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate)
    {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdatedBy()
    {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy)
    {
        this.lastUpdatedBy = lastUpdatedBy;
    }*/
}
