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
public class Address
{
    private int addressId;
    private String address;
    private String address2;
    private int cityId;
    private String postalCode;
    private String phone;
    private static ObservableList<Address> allAddresses = FXCollections.observableArrayList();
    private static ObservableList<Address> allAddressesFiltered = FXCollections.observableArrayList();
    
    public Address(int addressId, String address, String address2, int cityId, String postalCode, String phone)
    {
        this.addressId = addressId;
        this.address = address;
        this.address2 = address2;
        this.cityId = cityId;
        this.postalCode = postalCode;
        this.phone = phone;
    }

    public int getAddressId()
    {
        return addressId;
    }

    public void setAddressId(int addressId)
    {
        this.addressId = addressId;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getAddress2()
    {
        return address2;
    }

    public void setAddress2(String address2)
    {
        this.address2 = address2;
    }

    public int getCityId()
    {
        return cityId;
    }

    public void setCityId(int cityId)
    {
        this.cityId = cityId;
    }

    public String getPostalCode()
    {
        return postalCode;
    }

    public void setPostalCode(String postalCode)
    {
        this.postalCode = postalCode;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }
    
    public static void addAddress(Address address)
    {
         allAddresses.add(address);
    }
    
    public static ObservableList<Address> getAllAddressesFiltered()
    {
        return allAddressesFiltered;
    }

    public static void setAllCustomersFiltered(ObservableList<Address> allCustomersFiltered)
    {
        Address.allAddressesFiltered = allAddressesFiltered;
    }
    
    public static ObservableList<Address> getAllCustomers()
    {
        return allAddresses;
    }

    public static void setAllCustomers(ObservableList<Address> allAddresses)
    {
        Address.allAddresses = allAddresses;
    }
}