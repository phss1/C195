/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

public class Report
{
    private String type;
    private int typeCount;
    private String user;
    private int customerId;
    private int appointmentId;
    private String appointmentTitle;
    private String apptLocation;
    private String startDate;
    
    public Report(String Type, int typeCount, String user, int customerId, int appointmentId, String appointmentTitle,
            String apptLocation, String startDate)
    {
        this.type = Type;
        this.typeCount = typeCount;
        this.user = user;
        this.customerId = customerId;
        this.appointmentId = appointmentId;
        this.appointmentTitle = appointmentTitle;
        this.apptLocation = apptLocation;
        this.startDate = startDate;
    }
    
    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public int getTypeCount()
    {
        return typeCount;
    }

    public void setTypeCount(int typeCount)
    {
        this.typeCount = typeCount;
    }

    public String getUser()
    {
        return user;
    }

    public void setUser(String user)
    {
        this.user = user;
    }

    public int getCustomerId()
    {
        return customerId;
    }

    public void setCustomerId(int customerId)
    {
        this.customerId = customerId;
    }

    public int getAppointmentId()
    {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId)
    {
        this.appointmentId = appointmentId;
    }

    public String getAppointmentTitle()
    {
        return appointmentTitle;
    }

    public void setAppointmentTitle(String appointmentTitle)
    {
        this.appointmentTitle = appointmentTitle;
    }

    public String getApptLocation()
    {
        return apptLocation;
    }

    public void setApptLocation(String apptLocation)
    {
        this.apptLocation = apptLocation;
    }
    
    public String getStartDate()
    {
        return startDate;
    }

    public void setStartDate(String startDate)
    {
        this.startDate = startDate;
    }
}