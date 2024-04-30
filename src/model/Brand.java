/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaentity;

import java.util.Date;

/**
 *
 * @author nm204
 */
public class Brand {
    private Integer brandId;
    private String name;
    private Date whenCreated;
    private Date lastUpDated;

    public Brand() {
    }

    public Brand(Integer brandId, String name, Date whenCreated, Date lastUpDated) {
        this.brandId = brandId;
        this.name = name;
        this.whenCreated = whenCreated;
        this.lastUpDated = lastUpDated;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getWhenCreated() {
        return whenCreated;
    }

    public void setWhenCreated(Date whenCreated) {
        this.whenCreated = whenCreated;
    }

    public Date getLastUpDated() {
        return lastUpDated;
    }

    public void setLastUpDated(Date lastUpDated) {
        this.lastUpDated = lastUpDated;
    }
    
    
}
