/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
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
    private String start;
    private String end;
//    private String startMonth;
//    private String startDay;
//    private String startYear;
//    private String startTime;
//    private String endMonth;
//    private String endDay;
//    private String endYear;
//    private String endTime;
    private static ObservableList<Customer> refCustToAppointment = FXCollections.observableArrayList();
    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    public Appointment(int appointmentId, int customerId, int userId, String title, String description, String location, 
                            String contact, String type, String url, String start, String end) /*String startMonth, String startDay, String startYear,
                            String startTime, String endMonth, String endDay, String endYear, String endTime)*/
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
//        this.startMonth = startMonth;
//        this.startDay = startDay;
//        this.startYear = startYear;
//        this.endMonth = endMonth;
//        this.endDay = endDay;
//        this.endYear = endYear;
//        this.endTime = endTime;
    }
    
    public static ObservableList<String> createNewObsList(int itemIndex, ObservableList<String> itemList)
    {
        ObservableList<String> tempList = FXCollections.observableArrayList(itemList);
        
        tempList.remove(0, itemIndex);
        return tempList;
    }
    
    public static ObservableList<String> createAppointmentTimes(int startHour, int minInterval)
    {
        int startOfDay = startHour;
        int endOfDay = 18;
        int appointmentIncrememnt = minInterval;
        int possApptInOneHr = 60 / appointmentIncrememnt;
        int hoursInDay = 18-9;
        boolean alreadyRanOnce = false;
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
    
    public String getStart()
    {
        return start;
    }

    public void setStart(String start)
    {
        this.start = start;
    }

    public String getEnd()
    {
        return end;
    }

    public void setEnd(String end)
    {
        this.end = end;
    }
    /*
    public String getStartMonth()
    {
        return startMonth;
    }

    public void setStartMonth(String startMonth)
    {
        this.startMonth = startMonth;
    }

    public String getStartDay()
    {
        return startDay;
    }

    public void setStartDay(String startDay)
    {
        this.startDay = startDay;
    }

    public String getStartYear()
    {
        return startYear;
    }

    public void setStartYear(String startYear)
    {
        this.startYear = startYear;
    }

    public String getStartTime()
    {
        return startTime;
    }

    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }

    public String getEndMonth()
    {
        return endMonth;
    }

    public void setEndMonth(String endMonth)
    {
        this.endMonth = endMonth;
    }

    public String getEndDay()
    {
        return endDay;
    }

    public void setEndDay(String endDay)
    {
        this.endDay = endDay;
    }

    public String getEndYear()
    {
        return endYear;
    }

    public void setEndYear(String endYear)
    {
        this.endYear = endYear;
    }

    public String getEndTime()
    {
        return endTime;
    }

    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }*/
}