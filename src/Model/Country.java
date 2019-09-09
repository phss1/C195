/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author 39ds03d
 */
public class Country
{
    private int countryId;
    private String country;
    private Date createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    
    public Country(int countryId, String country, Date createDate, String createdBy, Timestamp lastUpdate,
                        String lastUpdatedBy)
    {
        this.countryId = countryId;
        this.country = country;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public int getCountryId()
    {
        return countryId;
    }

    public void setCountryId(int countryId)
    {
        this.countryId = countryId;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
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
    }
}
