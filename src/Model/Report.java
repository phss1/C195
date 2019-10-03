/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

public class Report
{
    private String Type;
    private int typeCount;
    private String user;
    private int customerId;
    private int appointmentId;
    private int appointmentTitle;
    private String apptType;
    private String apptLocation;
    
    public Report(String Type, int typeCount, String user, int customerId, int appointmentId, int appointmentTitle, String apptType)
    {
        this.Type = Type;
        this.typeCount = typeCount;
        this.user = user;
        this.customerId = customerId;
        this.appointmentId = appointmentId;
        this.appointmentTitle = appointmentTitle;
        this.apptType = apptType;
    }
    
    public String getType()
    {
        return Type;
    }

    public void setType(String Type)
    {
        this.Type = Type;
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

    public int getAppointmentTitle()
    {
        return appointmentTitle;
    }

    public void setAppointmentTitle(int appointmentTitle)
    {
        this.appointmentTitle = appointmentTitle;
    }

    public String getApptType()
    {
        return apptType;
    }

    public void setApptType(String apptType)
    {
        this.apptType = apptType;
    }

    public String getApptLocation()
    {
        return apptLocation;
    }

    public void setApptLocation(String apptLocation)
    {
        this.apptLocation = apptLocation;
    }
}