/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Utilities.UtilityMethods;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
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
    private static ObservableList<Customer> refCustToAppointment = FXCollections.observableArrayList();
    private static ObservableList<Appointment> appointmentToModify = FXCollections.observableArrayList();
    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    
    public Appointment(int appointmentId, int customerId, int userId, String title, String description, String location, 
                            String contact, String type, String url, String start, String end)
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
    
    public static boolean checkForApptAtLogIn() throws SQLException
    {
        ObservableList<String> allApptStartTimes = getApptStartTimes();
        Calendar tempCal = Calendar.getInstance();
        int currentDayOfMonth = tempCal.get(Calendar.DAY_OF_MONTH);
        int currentHour = tempCal.get(Calendar.HOUR_OF_DAY);
        int currentMinute = tempCal.get(Calendar.MINUTE);
        int numberOfApptStartTimes = allApptStartTimes.size();
        boolean alertOnApptAtLogIn = false;
        
        for(int i = 0; i < numberOfApptStartTimes; i++)
        {
            String [] dateTimeSplit = (allApptStartTimes.get(i)).split(" ");
            String [] timeSplit = dateTimeSplit[1].split(":");
            int apptDayOfMonth = Integer.valueOf(((dateTimeSplit[0]).split("-"))[2]);
            int nextApptStartHour = Integer.valueOf(timeSplit[0]);
            int [] windowTimes = new int[2];
            int apptHour = Integer.valueOf(dateTimeSplit[1].split(":")[0]);
            int hourMath = apptHour - currentHour;
            
            windowTimes = createExpectedNotifWindow(Integer.valueOf(timeSplit[1]));
            int windowStart = windowTimes[0];
            int windowEnd = windowTimes[1];
            
            if(apptDayOfMonth == currentDayOfMonth && currentHour <= nextApptStartHour && hourMath <= 1
                    && (currentMinute < windowEnd && currentMinute > windowStart))
            {
                alertOnApptAtLogIn = true;
                break;
            }
        }
        
        return alertOnApptAtLogIn;
    }
    
    private static int [] createExpectedNotifWindow(int startMinute)
    {
        int windowStart = -1;
        int windowEnd = -1;
        switch(startMinute)
        {
            case 0 : windowStart = 45; windowEnd = 59; break;
            case 15 : windowStart = 0; windowEnd = 15; break;
            case 30 : windowStart = 15; windowEnd = 30; break;
            case 45 : windowStart = 30; windowEnd = 45; break;
        }
        int [] windowTimes = {windowStart, windowEnd};
        return windowTimes;
    }
    
    public static boolean checkForOverLapAppt(String apptStartTime) throws SQLException
    {
        ObservableList<String> allApptStartTimes = getApptStartTimes();
        
        boolean foundExistingApptStart = false;
        int numberOfApptStartTimes = allApptStartTimes.size();
        for(int i = 0; i < numberOfApptStartTimes; i++)
        {
            if(apptStartTime.matches(allApptStartTimes.get(i)))
            {
                foundExistingApptStart = true;
                break;
            }
        }
        return foundExistingApptStart;
    }
    
    public static ObservableList<String> getApptStartTimes() throws SQLException
    {
        UtilityMethods utility = new UtilityMethods();
        String apptTimesQuery = "select start from appointment";
        ResultSet apptTimesQueryResults = utility.runSqlQuery(apptTimesQuery);
        
        ObservableList<String> allApptStartTimes = FXCollections.observableArrayList();
        while(apptTimesQueryResults.next())
        {
            String item = utility.convertTimeToLocal(utility.subStringOfDateTime(apptTimesQueryResults.getString("start")));
            allApptStartTimes.add(item);
        }
        
        return allApptStartTimes;
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
        ObservableList<String> possAppInOneDay = FXCollections.observableArrayList();
        
        for(int i = 0; i<hoursInDay; i++)
        {
            appointmentIncrememnt = 0;
            String longHour = startOfDay == 9 ? "09" : String.valueOf(startOfDay);
            for(int z = 0; z<possApptInOneHr; z++)
            {
                String appTimeTemp = appointmentIncrememnt == 0 ? (longHour + ":00") : 
                        (longHour + ":" + String.valueOf(appointmentIncrememnt));
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
            if(i<9)
            {
                values.add("0" + String.valueOf(i + 1));
            }
            else
            {
                values.add(String.valueOf(i + 1));
            }
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
    
    public static void modifyCustomerAppointment(Customer customer, int appointmentId, Appointment appointment)
    {
        int index = -1;
        for(Appointment currentAppointment : customer.getAllCustomerAppointments())
        {
            index++;
            if(currentAppointment.getAppointmentId() == appointmentId)
            {
                customer.getAllCustomerAppointments().remove(index);
                customer.addCustomerAppointment(appointment);
                customer.setAllCustomerAppointments(customer.getAllCustomerAppointments());
                break;
            }
        }
    }
    
    public static void addItemAppToModify(Appointment appointment)
    {
         appointmentToModify.add(appointment);
    }
    
     public static ObservableList<Appointment> getAppointmentToModify()
     {
        return appointmentToModify;
    }

    public static void setAppointmentToModify(ObservableList<Appointment> appointmentToModify)
    {
        Appointment.appointmentToModify = appointmentToModify;
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
}