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

public class Customer
{
    private int customerId;
    private String customerName;
    private String address; 
    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private static ObservableList<Customer> allCustomersFiltered = FXCollections.observableArrayList();

    public Customer(int customerId, String customerName, String address)
    {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
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
}