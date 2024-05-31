/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.model;

import java.util.Date;

/**
 *
 * @author Phuc
 */
public class ExportBill {
    private Integer exportBillId;
    private Client client;
    private User user;
    private Double total;
    private Date whenCreated;

    public ExportBill(
            Integer exportBillId, 
            Client client, 
            User user, 
            Double total, 
            Date whenCreated
    ) {
        this.exportBillId = exportBillId;
        this.client = client;
        this.user = user;
        this.total = total;
        this.whenCreated = whenCreated;
    }
    

    public Integer getExportBillId() {
        return exportBillId;
    }

    public void setExportBillId(Integer exportBillId) {
        this.exportBillId = exportBillId;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Date getWhenCreated() {
        return whenCreated;
    }

    public void setWhenCreated(Date whenCreated) {
        this.whenCreated = whenCreated;
    }
    
}
