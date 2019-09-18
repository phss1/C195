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
public class Appointment
{
    private int appointmentId;
    private int customerId;
    private int userId;
    private String title;
    private String description;
    private String location;
    private String contact;
    private String type;
    private String url;
    private Date start;
    private Date end;
    private static ObservableList<Customer> refCustToAppointment = FXCollections.observableArrayList();
    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    public Appointment(int appointmentId, int customerId, int userId, String title, String description, String location, 
                            String contact, String type, String url, Date start, Date end)
    {
        this.appointmentId = appointmentId;
        this.customerId = customerId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.url = url;
        this.start = start;
        this.end = end;
    }
    
    public static ObservableList<String> createAppointmentTimes()
    {
        int startOfDay = 9;
        int endOfDay = 18;
        int appointmentIncrememnt = 15;
        int possApptInOneHr = 60 / appointmentIncrememnt;
        int hoursInDay = 18-9;
        ObservableList<String> possAppInOneDay = FXCollections.observableArrayList();
        
        for(int i = 0; i<hoursInDay; i++)
        {
            appointmentIncrememnt = 0;
            for(int z = 0; z<possApptInOneHr; z++)
            {
                String appTimeTemp = appointmentIncrememnt == 0 ? (String.valueOf(startOfDay) + ":00") : 
                        (String.valueOf(startOfDay) + ":" + String.valueOf(appointmentIncrememnt));
                appointmentIncrememnt = appointmentIncrememnt + 15;
                possAppInOneDay.add(appTimeTemp);
            }
            startOfDay++;
            
            String testCase = startOfDay == endOfDay ? String.valueOf(possAppInOneDay.add(endOfDay + ":00")) : "";
        }
        
        return possAppInOneDay;
    }
    
    public static ObservableList<String> prepDateComboBoxValues(int maxValues)
    {
        ObservableList<String> values = FXCollections.observableArrayList();
        for(int i = 0; i < maxValues ; i++)
        {
            values.add(String.valueOf(i + 1));
        }
        
        return values;
    }
    
    public static void deleteAppointment(Customer customer, int appointmentId)
    {
        int index = -1;
        for(Appointment currentAppointment : customer.getAllCustomerAppointments())
        {
            index++;
            if(currentAppointment.getAppointmentId() == appointmentId)
            {
                customer.getAllCustomerAppointments().remove(index);
                customer.setAllCustomerAppointments(customer.getAllCustomerAppointments());
                break;
            }
        }
    }
    
    public static void addItemToAllAppointments(Appointment appointment)
    {
         allAppointments.add(appointment);
    }
    
    public static void addRefCustToAppointment(Customer customer)
    {
         refCustToAppointment.add(customer);
    }
    
    public static ObservableList<Customer> getRefCustToAppointment()
    {
        return refCustToAppointment;
    }

    public static void setRefCustToAppointment(ObservableList<Customer> refCustToAppointment)
    {
        Appointment.refCustToAppointment = refCustToAppointment;
    }

    public int getAppointmentId()
    {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId)
    {
        this.appointmentId = appointmentId;
    }

    public int getCustomerId()
    {
        return customerId;
    }

    public void setCustomerId(int customerId)
    {
        this.customerId = customerId;
    }

    public int getUserId() 
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getLocation() 
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public String getContact()
    {
        return contact;
    }

    public void setContact(String contact)
    {
        this.contact = contact;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public Date getStart()
    {
        return start;
    }

    public void setStart(Date start)
    {
        this.start = start;
    }

    public Date getEnd()
    {
        return end;
    }

    public void setEnd(Date end)
    {
        this.end = end;
    }
}