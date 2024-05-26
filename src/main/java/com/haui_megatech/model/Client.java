/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.model;

import java.util.List;

/**
 *
 * @author Phuc
 */
public class Client {
    private Integer clientId;
    private String name;
    private Integer phoneNumber;
    private String email;
    private String address;
    private List<ExportBill> ebs;

    public Client(
            Integer clientId, 
            String name, 
            Integer phoneNumber, 
            String email, 
            String address, 
            List<ExportBill> ebs
    ) {
        this.clientId = clientId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.ebs = ebs;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<ExportBill> getEbs() {
        return ebs;
    }

    public void setEbs(List<ExportBill> ebs) {
        this.ebs = ebs;
    }
    
    public void addExportBill(ExportBill exportbill){
        ebs.add(exportbill);
    }
}
